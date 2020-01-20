package test.动态创建管理定时任务.entity.identity;

import java.io.Serializable;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QuartzConfigIdentity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6107873034694904498L;

    @Column(name = "JOB_NAME")
    private String jobName;// 任务名称

    @Column(name = "JOB_GROUP")
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

    public QuartzConfigIdentity(String jobName, String jobGroup) {
        super();
        this.jobName = jobName;
        this.jobGroup = jobGroup;
    }

    public QuartzConfigIdentity() {
        super();
        // TODO Auto-generated constructor stub
    }

}
