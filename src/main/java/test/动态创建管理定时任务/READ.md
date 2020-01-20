设计思路：
1：通过任务名称结合反射动态拼接组成任务（jobdetail）,并注入参数，实现真正的动态创建定时任务
2：可以修改时间在不重启服务的基础上
3：可以立即执行某个任务
4：重启服务后要扫描所有有效任务并启动
5：支持再不重启服务的基础上 暂停，恢复，删除任务
6: 只需要一张表


--定时任务 oracle版的sql
--job_status 是任务的状态 0启用1暂停
--task_parameter代表任务的方法参数 用逗号分割，按照顺序

CREATE TABLE quartz_config (
    job_name          VARCHAR2(50 BYTE),
    job_group         VARCHAR2(50 BYTE),
    job_description   VARCHAR2(500 BYTE),
    cron_expression   VARCHAR2(500 BYTE),
    job_status        NUMBER(10, 0),
    create_time       DATE,
    update_time       DATE,
    create_user       VARCHAR2(255 BYTE),
    task_parameter    VARCHAR2(4000 BYTE),
    PRIMARY KEY ( job_name, job_group )
)

