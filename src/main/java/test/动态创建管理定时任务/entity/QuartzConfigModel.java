package test.动态创建管理定时任务.entity;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import test.动态创建管理定时任务.entity.identity.QuartzConfigIdentity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;


/**
 * 任务类 创建者
 */
@Entity
@Table(name = "QUARTZ_CONFIG")
public class QuartzConfigModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2001992304078127000L;

    @EmbeddedId
    private QuartzConfigIdentity id;

    @Column(name = "JOB_DESCRIPTION")
    private String jobDescription;// 任务描述

    @Column(name = "CRON_EXPRESSION")
    private String cronExpression;// 执行时间

    @Column(name = "JOB_STATUS")
    private Integer jobStatus;// 任务状态

    @Column(name = "CREATE_TIME")
    private Date createTime;// 创建时间

    @Column(name = "UPDATE_TIME")
    private Date updateTime;// 更新时间

    @Column(name = "CREATE_USER")
    private String createUser;// 创建者

    @Column(name = "TASK_PARAMETER")
    private String taskParameter;

    public String getTaskParameter() {
        return taskParameter;
    }

    public void setTaskParameter(String taskParameter) {
        this.taskParameter = taskParameter;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public QuartzConfigIdentity getId() {
        return id;
    }

    public void setId(QuartzConfigIdentity id) {
        this.id = id;
    }

    @PrePersist
    public void save() {
        createTime = new Date();
        jobStatus = 0;
    }

    @PreUpdate
    public void update() {
        updateTime = new Date();
    }

}
