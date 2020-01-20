package test.动态创建管理定时任务.dynamicquery.repository;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */

import org.springframework.stereotype.Repository;
import test.动态创建管理定时任务.entity.QuartzConfigModel;
import test.动态创建管理定时任务.entity.identity.QuartzConfigIdentity;


@Repository
public interface QuartzConfigRepository extends GenericRepository<QuartzConfigModel, QuartzConfigIdentity> {

}
