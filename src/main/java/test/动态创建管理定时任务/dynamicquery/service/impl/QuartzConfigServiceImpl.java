package test.动态创建管理定时任务.dynamicquery.service.impl;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import test.动态创建管理定时任务.dynamicquery.repository.QuartzConfigRepository;
import test.动态创建管理定时任务.dynamicquery.service.QuartzConfigService;
import test.动态创建管理定时任务.entity.QuartzConfigModel;
import test.动态创建管理定时任务.entity.identity.QuartzConfigIdentity;


@SuppressWarnings("all")
@Service
public class QuartzConfigServiceImpl
        extends GenericRepositoryServiceImpl<QuartzConfigModel, QuartzConfigIdentity, QuartzConfigRepository>
        implements QuartzConfigService {

    @Autowired
    QuartzConfigRepository repository;

    @Override
    public List<QuartzConfigModel> findByCondition(String jobName, String jobGroup, Integer jobStatus) {
        return findAll(findByConditionSpec(jobName, jobGroup, jobStatus));
    }

    private Specification<QuartzConfigModel> findByConditionSpec(String jobName, String jobGroup, Integer jobStatus) {
        return new Specification<QuartzConfigModel>() {

            @Override
            public Predicate toPredicate(Root<QuartzConfigModel> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!StringUtils.isEmpty(jobName)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id").get("jobName"), jobName)));
                }

                if (!StringUtils.isEmpty(jobGroup)) {
                    predicates
                            .add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id").get("jobGroup"), jobGroup)));
                }

                if (jobStatus != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("jobStatus"), jobStatus)));
                }

                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return query.getRestriction();
            }

        };
    }

}
