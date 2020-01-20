package test.多个类同一接口调用.impl;

import org.springframework.stereotype.Service;
import test.多个类同一接口调用.common.BaseRespDto;
import test.多个类同一接口调用.common.CommonVerifyRespDto;
import test.多个类同一接口调用.common.PreCommonVerifyReqDto;
import test.多个类同一接口调用.comtants.CommonVerifyContants;
import test.多个类同一接口调用.init.CommonVerifyServiceSupport;
import test.多个类同一接口调用.service.CertCardService;

/**
 * @author super
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
@Service
public class CertCardServiceImpl extends CommonVerifyServiceSupport implements CertCardService {

    public CertCardServiceImpl() {
        super(CommonVerifyContants.FUNCTION_NAME_ACCOUNT,CommonVerifyContants.FUNCTION_NAME_ACCOUNT);
    }

    @Override
    public BaseRespDto<CommonVerifyRespDto> preCommonVerify(PreCommonVerifyReqDto reqDto) throws Exception {
        return null;
    }

    public static void main(String[] args) {
        new CertCardServiceImpl();
    }
}
