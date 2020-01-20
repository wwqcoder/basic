pom.xml: 

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
 
    <groupId>com.lz.quartz</groupId>
    <artifactId>TestQuartz</artifactId>
    <version>1.0-SNAPSHOT</version>
 
    <dependencies>
        <!--quartz核心类-->
        <dependency>
            <groupId>org.quartz-scheduler</groupId>
            <artifactId>quartz</artifactId>
            <version>2.3.0</version>
        </dependency>
        <!--quartz工具类-->
        <dependency>
            <groupId>org.quartz-scheduler</groupId>
            <artifactId>quartz-jobs</artifactId>
            <version>2.3.0</version>
        </dependency>
 
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.12</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
    </dependencies>
 
 
</project>
log4j.properties :

#config root logger
log4j.rootLogger = INFO,system.out  
log4j.appender.system.out=org.apache.log4j.ConsoleAppender  
log4j.appender.system.out.layout=org.apache.log4j.PatternLayout  
log4j.appender.system.out.layout.ConversionPattern=MINAServer Logger-->%5p{%F:%L}-%m%n  
 
#config this Project.file logger
log4j.logger.thisProject.file=INFO,thisProject.file.out  
log4j.appender.thisProject.file.out=org.apache.log4j.DailyRollingFileAppender  
log4j.appender.thisProject.file.out.File=logContentFile.log  
log4j.appender.thisProject.file.out.layout=org.apache.log4j.PatternLayout