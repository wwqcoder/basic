package test.多个类同一接口调用.impl;

import org.springframework.stereotype.Service;
import test.多个类同一接口调用.init.CommonVerifyServiceSupportManager;
import test.多个类同一接口调用.common.PreCommonVerifyReqDto;
import test.多个类同一接口调用.common.BaseRespDto;
import test.多个类同一接口调用.common.CommonVerifyRespDto;
import test.多个类同一接口调用.service.CommonVerifyService;

import javax.annotation.Resource;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
@Service
public class CommonVerifyServiceImpl implements CommonVerifyService {

    @Resource
    private CommonVerifyServiceSupportManager commonVerifyServiceSupportManager;

    @Override
    public BaseRespDto<CommonVerifyRespDto> preCommonVerify(PreCommonVerifyReqDto reqDto) throws Exception {
        CommonVerifyService commonVerifyInstance = commonVerifyServiceSupportManager.getService(reqDto.getFunName(), reqDto.getFunType());
        return commonVerifyInstance.preCommonVerify(reqDto);
    }
}
