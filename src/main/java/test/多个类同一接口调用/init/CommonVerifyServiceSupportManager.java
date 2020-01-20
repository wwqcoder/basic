package test.多个类同一接口调用.init;

import org.springframework.stereotype.Service;
import test.多个类同一接口调用.init.CommonVerifyServiceSupport;
import test.多个类同一接口调用.service.CommonVerifyService;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
@Service
public class CommonVerifyServiceSupportManager {
    /**
     * map
     */
    private static Map<String, CommonVerifyService> COMMON_VERIFY_SERVICE_MAP = new HashMap<String, CommonVerifyService>();


    /**
     * 注册到管理器
     *
     * @param commonVerifyServiceSupport
     */
    public void registService(CommonVerifyServiceSupport commonVerifyServiceSupport) {
        COMMON_VERIFY_SERVICE_MAP.put(commonVerifyServiceSupport.getFunName() + "_" + commonVerifyServiceSupport.getFundType(), commonVerifyServiceSupport);
    }

    /**
     * 从管理器获取service
     *
     * @param funName
     * @param funType
     * @return
     */
    public CommonVerifyService getService(String funName, String funType) {
        return COMMON_VERIFY_SERVICE_MAP.get(funName + "_" + funType);
    }
}
