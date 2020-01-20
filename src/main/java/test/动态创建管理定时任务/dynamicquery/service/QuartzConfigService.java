package test.动态创建管理定时任务.dynamicquery.service;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import test.动态创建管理定时任务.dynamicquery.repository.QuartzConfigRepository;
import test.动态创建管理定时任务.entity.QuartzConfigModel;
import test.动态创建管理定时任务.entity.identity.QuartzConfigIdentity;

import java.util.List;



public interface QuartzConfigService
        extends GenericRepositoryService<QuartzConfigModel, QuartzConfigIdentity, QuartzConfigRepository> {
    List<QuartzConfigModel> findByCondition(String jobName, String jobGroup, Integer jobStatus);
}
