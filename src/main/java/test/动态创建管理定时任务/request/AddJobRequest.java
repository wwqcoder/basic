package test.动态创建管理定时任务.request;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
public class AddJobRequest {

    private String jobName;// 任务名称

    private String jobGroup;// 任务分组

    private String jobDescription;// 任务描述

    private String cronExpression;// 执行时间

    private String taskParameter;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
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

    public String getTaskParameter() {
        return taskParameter;
    }

    public void setTaskParameter(String taskParameter) {
        this.taskParameter = taskParameter;
    }

}
