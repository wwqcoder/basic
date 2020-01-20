package test.动态创建管理定时任务.request;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
public class FindAllJobRequest {
    private Integer pageSize;
    private Integer pageNo;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

}
