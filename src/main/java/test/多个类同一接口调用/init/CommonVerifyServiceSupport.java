package test.多个类同一接口调用.init;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import test.多个类同一接口调用.service.CommonVerifyService;

import javax.annotation.Resource;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述 使用抽象类可以不实现CommonVerifyService接口中的类，只实现InitializingBean里的接口
 */
@Data
public abstract class CommonVerifyServiceSupport implements CommonVerifyService, InitializingBean {

    /**
     * 通用认证service support管理器
     */
    @Resource
    private CommonVerifyServiceSupportManager commonVerifyServiceSupportManager;


    private String funName;

    private String fundType;


    public CommonVerifyServiceSupport(String funName, String fundType) {
        this.fundType = fundType;
        this.funName = funName;
    }

    /**
     * regist 2 COMMON_VERIFY_SERVICE_MAP
     */
    private void init() {
        commonVerifyServiceSupportManager.registService(this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }


}
