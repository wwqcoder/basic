package test.多个类同一接口调用.service;

import test.多个类同一接口调用.common.BaseRespDto;
import test.多个类同一接口调用.common.CommonVerifyRespDto;
import test.多个类同一接口调用.common.PreCommonVerifyReqDto;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
public interface CommonVerifyService {

    BaseRespDto<CommonVerifyRespDto> preCommonVerify(PreCommonVerifyReqDto reqDto) throws Exception;
}
