package test.动态创建管理定时任务.request;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
public class PauseAndResumeRequest {

    private String jobName;// 任务名称

    private String jobGroup;// 任务分组

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

}
