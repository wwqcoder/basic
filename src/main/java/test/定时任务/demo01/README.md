Quartz任务调度--集群
简介
(1)quartz定时任务调度,quartz最基本的概念就是job，在job内调用具体service完成具体功能，
  quartz需要把每个job存储起来，方便调度，quartz存储job方式就分三种，
  最常用的也是quartz默认的是RAMJobStore，RAMJobStore顾名思义就是把job的相关信息存储在内存里，
  如果用spring配置quartz的job信息的话，所有信息是配置在xml里，当spirng context启动的时候就把xml里的job信息装入内存。
  这一性质就决定了一旦JVM挂掉或者容器挂掉，内存中的job信息就随之消失，无法持久化。
 
  另外两种方式是JobStoreTX和JobStoreCMT，使用这两种JobStore，
  quartz就会通过jdbc直连或者应用服务器jndi连接数据库，读取配置在数据库里的job初始化信息，
  并且把job通过java序列化到数据库里，这样就使得每个job信息得到了持久化，
  即使在jvm或者容器挂掉的情况下，也能通过数据库感知到job的状态和信息。
 
  创建mysql数据库表,见文尾: 0. quartz持久化--mysql表
 
 (2)quartz集群各节点之间是通过同一个数据库实例来感知彼此的。


1. 相关jar包引入
<spring.version>3.2.8.RELEASE</spring.version>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-support</artifactId>
    <version>${spring.version}</version>
</dependency>
等
 
<!-- quartz -->
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>2.2.1</version>
    <exclusions><!-- 如果单独引入org.slf4j了,此处去掉 -->
    <exclusion>
        <artifactId>slf4j-api</artifactId>
        <groupId>org.slf4j</groupId>
    </exclusion>
    </exclusions>
</dependency>
注意:spring与quartz版本；需要引入spring-context-support.jar包

3. spring-quartz.xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                     http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"
       default-autowire="byName">
 
    <!-- quartz线程池 -->
    <bean id="quartzThreadPool" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
        <!-- 核心线程数  -->
        <property name="corePoolSize" value="100"/>
        <!-- 最大线程数 -->
        <property name="maxPoolSize" value="200"/>
        <!-- 队列最大长度 >=mainExecutor.maxSize -->
        <property name="queueCapacity" value="1000"/>
        <!-- 线程池维护线程所允许的空闲时间 -->
        <property name="keepAliveSeconds" value="300"/>
        <!-- 拒绝任务策略:被拒绝后直接在调用者线程中运行当前被放弃任务 -->
        <property name="rejectedExecutionHandler">
            <bean class="java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy"/>
        </property>
    </bean>
 
    <!-- 分布式QuartzScheduler -->
    <bean name="clusterScheduler" lazy-init="false" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
        <property name="dataSource" ref="dataSource"></property> <!-- datasource引入 -->
        <property name="taskExecutor" ref="quartzThreadPool"/>
        <property name="transactionManager" ref="transactionManager"/><!-- transactionManager引入 -->
        <property name="quartzProperties">
            <props>
                <prop key="org.quartz.scheduler.instanceName">CacheCloudScheduler</prop>
                <prop key="org.quartz.scheduler.instanceId">AUTO</prop>
                <prop key="org.quartz.jobStore.class">org.quartz.impl.jdbcjobstore.JobStoreTX</prop>
                <prop key="org.quartz.jobStore.driverDelegateClass">org.quartz.impl.jdbcjobstore.StdJDBCDelegate</prop>     
                <prop key="org.quartz.jobStore.tablePrefix">CACHE_QRTZ_</prop><!-- 表名前缀 -->
                <prop key="org.quartz.jobStore.isClustered">${isClustered}</prop>
                <prop key="org.quartz.jobStore.clusterCheckinInterval">15000</prop>
                <prop key="org.quartz.jobStore.maxMisfiresToHandleAtATime">120</prop>
                <prop key="org.quartz.jobStore.misfireThreshold">60000</prop>
                <prop key="org.quartz.scheduler.jmx.export">true</prop><!-- 打开JMX 配置 -->
                <prop key="org.quartz.plugin.shutdownHook.class">org.quartz.plugins.management.ShutdownHookPlugin</prop>
                <prop key="org.quartz.plugin.shutdownHook.cleanShutdown">true</prop>
                <prop key="org.terracotta.quartz.skipUpdateCheck">true</prop>
                <prop key="org.quartz.plugin.triggHistory.class">org.quartz.plugins.history.LoggingJobHistoryPlugin</prop>
            </props>
        </property>
        <property name="schedulerName" value="CacheCloudScheduler"/>
        <property name="applicationContextSchedulerContextKey" value="applicationContext"/>
        <property name="overwriteExistingJobs" value="true"/>
        <property name="waitForJobsToCompleteOnShutdown" value="false"/>
        <property name="startupDelay" value="0"/>
        <property name="autoStartup" value="true"/>
        <property name="triggers">
            <array>
               <ref bean="testQuarzClusterTrigger"/>
            </array>
        </property>
        <property name="jobDetails">
            <array>
                <ref bean="testQuarzClusterJobDetail"/>
                <ref bean="customJobDetail"/>
            </array>
        </property>
    </bean>
 
    <!-- start 每隔1分钟 -->
    <bean id="testQuarzClusterTrigger" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
        <property name="name" value="testQuarzClusterTrigger"/>
        <property name="group" value="testQuarzCluster"/>
        <property name="jobDetail" ref="testQuarzClusterJobDetail"/>
        <property name="cronExpression" value="0 */1 * * * ?"/>
    </bean>
    <bean id="testQuarzClusterJobDetail" class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
        <property name="name" value="instanceStatusJob"></property>
        <property name="group" value="instance"></property>
        <property name="jobClass" value="com.taiyang.quartz.TestQuartzCluster"></property>
        <property name="requestsRecovery" value="true" /><!-- spring-context-support 3.2.8.RELEASE支持,低版本可能报错 -->
        <property name="durability" value="true"/>
    </bean>
    <!-- end 每隔1分钟 -->
    
    <!-- start customJob -->
    <!-- [只创建JobDetail,Trigger在QuartzManager中管理,实现动态][JobDetail也可以在QuartzManager中实现] -->
    <bean id="customJobDetail" class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
        <property name="name" value="customJob_name"></property>
        <property name="group" value="customJob_group"></property>
        <property name="jobClass" value="com.taiyang.quartz.CustomQuartzJob"></property>
        <property name="requestsRecovery" value="true"/>
        <property name="durability" value="true"/>
    </bean>
    <!-- end customJob -->
    
    <!--quartz定时任务管理类 -->
    <bean id="quartzManager" class="com.taiyang.quartz.QuartzManager" init-method="init" />
    
</beans>
注意:datasource配置时<property name="defaultAutoCommit" value="true" />打开默认提交事务

注意：
Error creating bean with name 'schedulerFactory' defined in file [/usr/local/tomcat/apache-tomcat-7.0.64_2/webapps/ashare/WEB-INF/classes/config/spring-quartz.xml]:
Invocation of init method failed; nested exception is java.lang.IllegalStateException: Cannot run without an instance id.
解决方法：
vim 编辑/etc/hosts文件，将当前服务器主机名(输入命令username查看主机名)添加到127.0.0.1 后面任意位置

4.分布式集群

tomcat1
tomcat2

都启动时,tomcat1和tomcat2轮流执行
tomcat1停掉后,tomcat2自己执行
重启tomcat1后,tomcat1和tomcat2轮流执行

0. quartz持久化--mysql表
-- ----------------------------
--  Table structure for `cache_qrtz_blob_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_blob_triggers`;
CREATE TABLE `cache_qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度名',
  `TRIGGER_NAME` varchar(200) NOT NULL COMMENT '触发器名',
  `TRIGGER_GROUP` varchar(200) NOT NULL COMMENT '触发器组名',
  `BLOB_DATA` blob COMMENT 'data',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型';

-- ----------------------------
--  Table structure for `cache_qrtz_calendars`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_calendars`;
CREATE TABLE `cache_qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'scheduler名称',
  `CALENDAR_NAME` varchar(200) NOT NULL COMMENT 'calendar名称',
  `CALENDAR` blob NOT NULL COMMENT 'calendar信息',
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='以 Blob 类型存储 Quartz 的 Calendar 信息';

-- ----------------------------
--  Table structure for `cache_qrtz_cron_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_cron_triggers`;
CREATE TABLE `cache_qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'scheduler名称',
  `TRIGGER_NAME` varchar(200) NOT NULL COMMENT 'trigger名',
  `TRIGGER_GROUP` varchar(200) NOT NULL COMMENT 'trigger组',
  `CRON_EXPRESSION` varchar(120) NOT NULL COMMENT 'cron表达式',
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储 Cron Trigger，包括 Cron 表达式和时区信息';

-- ----------------------------
--  Table structure for `cache_qrtz_fired_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_fired_triggers`;
CREATE TABLE `cache_qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度名',
  `ENTRY_ID` varchar(95) NOT NULL COMMENT '条目id',
  `TRIGGER_NAME` varchar(200) NOT NULL COMMENT '出触发器名',
  `TRIGGER_GROUP` varchar(200) NOT NULL COMMENT '触发器组名',
  `INSTANCE_NAME` varchar(200) NOT NULL COMMENT '实例名',
  `FIRED_TIME` bigint(13) NOT NULL COMMENT '执行时间',
  `SCHED_TIME` bigint(13) NOT NULL COMMENT '调度时间',
  `PRIORITY` int(11) NOT NULL COMMENT '优先级',
  `STATE` varchar(16) NOT NULL COMMENT '状态',
  `JOB_NAME` varchar(200) DEFAULT NULL COMMENT 'job名',
  `JOB_GROUP` varchar(200) DEFAULT NULL COMMENT 'job组',
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL COMMENT '是否非并行执行',
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL COMMENT '是否持久化',
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储已触发的 Trigger相关的状态信息，以及关联 Job 的执行信息';

-- ----------------------------
--  Table structure for `cache_qrtz_job_details`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_job_details`;
CREATE TABLE `cache_qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度名',
  `JOB_NAME` varchar(200) NOT NULL COMMENT 'job名',
  `JOB_GROUP` varchar(200) NOT NULL COMMENT 'job组名',
  `DESCRIPTION` varchar(250) DEFAULT NULL COMMENT '描述',
  `JOB_CLASS_NAME` varchar(250) NOT NULL COMMENT 'job类名',
  `IS_DURABLE` varchar(1) NOT NULL COMMENT '是否持久化，0不持久化，1持久化',
  `IS_NONCONCURRENT` varchar(1) NOT NULL COMMENT '是否非并发，0非并发，1并发',
  `IS_UPDATE_DATA` varchar(1) NOT NULL COMMENT '是否更新数据',
  `REQUESTS_RECOVERY` varchar(1) NOT NULL COMMENT '是否可恢复，0不恢复，1恢复',
  `JOB_DATA` blob COMMENT 'job数据',
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储每一个已配置的 Job 的详细信息';

-- ----------------------------
--  Table structure for `cache_qrtz_locks`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_locks`;
CREATE TABLE `cache_qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度名',
  `LOCK_NAME` varchar(40) NOT NULL COMMENT '锁名',
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储程序的悲观锁的信息(假如使用了悲观锁)';

-- ----------------------------
--  Table structure for `cache_qrtz_paused_trigger_grps`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_paused_trigger_grps`;
CREATE TABLE `cache_qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度名',
  `TRIGGER_GROUP` varchar(200) NOT NULL COMMENT '触发器组',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储已暂停的 Trigger 组的信息';

-- ----------------------------
--  Table structure for `cache_qrtz_scheduler_state`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_scheduler_state`;
CREATE TABLE `cache_qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度名',
  `INSTANCE_NAME` varchar(200) NOT NULL COMMENT '执行quartz实例的主机名',
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL COMMENT '实例将状态报告给集群中的其它实例的上一次时间',
  `CHECKIN_INTERVAL` bigint(13) NOT NULL COMMENT '实例间状态报告的时间频率',
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储少量的有关 Scheduler 的状态信息';

-- ----------------------------
--  Table structure for `cache_qrtz_simple_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_simple_triggers`;
CREATE TABLE `cache_qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度名',
  `TRIGGER_NAME` varchar(200) NOT NULL COMMENT '触发器名',
  `TRIGGER_GROUP` varchar(200) NOT NULL COMMENT '触发器组',
  `REPEAT_COUNT` bigint(7) NOT NULL COMMENT '重复次数',
  `REPEAT_INTERVAL` bigint(12) NOT NULL COMMENT '重复间隔',
  `TIMES_TRIGGERED` bigint(10) NOT NULL COMMENT '已触发次数',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储简单的 Trigger，包括重复次数，间隔，以及已触的次数';

-- ----------------------------
--  Table structure for `cache_qrtz_simprop_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_simprop_triggers`;
CREATE TABLE `cache_qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度名',
  `TRIGGER_NAME` varchar(200) NOT NULL COMMENT '触发器名',
  `TRIGGER_GROUP` varchar(200) NOT NULL COMMENT '触发器组',
  `STR_PROP_1` varchar(512) DEFAULT NULL COMMENT 'str参数1',
  `STR_PROP_2` varchar(512) DEFAULT NULL COMMENT 'str参数2',
  `STR_PROP_3` varchar(512) DEFAULT NULL COMMENT 'str参数3',
  `INT_PROP_1` int(11) DEFAULT NULL COMMENT 'int参数1',
  `INT_PROP_2` int(11) DEFAULT NULL COMMENT 'int参数2',
  `LONG_PROP_1` bigint(20) DEFAULT NULL COMMENT 'long参数1',
  `LONG_PROP_2` bigint(20) DEFAULT NULL COMMENT 'long参数2',
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal参数1',
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal参数2',
  `BOOL_PROP_1` varchar(1) DEFAULT NULL COMMENT 'bool参数1',
  `BOOL_PROP_2` varchar(1) DEFAULT NULL COMMENT 'bool参数2',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `cache_qrtz_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `cache_qrtz_triggers`;
CREATE TABLE `cache_qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度名',
  `TRIGGER_NAME` varchar(200) NOT NULL COMMENT '触发器名',
  `TRIGGER_GROUP` varchar(200) NOT NULL COMMENT '触发器组',
  `JOB_NAME` varchar(200) NOT NULL COMMENT 'job名',
  `JOB_GROUP` varchar(200) NOT NULL COMMENT 'job组',
  `DESCRIPTION` varchar(250) DEFAULT NULL COMMENT '描述',
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL COMMENT '下次执行时间',
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL COMMENT '上次执行时间',
  `PRIORITY` int(11) DEFAULT NULL COMMENT '优先级',
  `TRIGGER_STATE` varchar(16) NOT NULL COMMENT '触发器状态',
  `TRIGGER_TYPE` varchar(8) NOT NULL COMMENT '触发器类型',
  `START_TIME` bigint(13) NOT NULL COMMENT '开始时间',
  `END_TIME` bigint(13) DEFAULT NULL COMMENT '结束时间',
  `CALENDAR_NAME` varchar(200) DEFAULT NULL COMMENT 'calendar名',
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL COMMENT 'misfire',
  `JOB_DATA` blob COMMENT 'job数据',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储已配置的 Trigger 的信息';