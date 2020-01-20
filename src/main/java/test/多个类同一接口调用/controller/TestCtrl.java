package test.多个类同一接口调用.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import test.多个类同一接口调用.common.BaseRespDto;
import test.多个类同一接口调用.service.CommonVerifyService;
import test.多个类同一接口调用.common.PreCommonVerifyReqDto;

import javax.annotation.Resource;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
@Controller
public class TestCtrl {

    @Resource
    private CommonVerifyService commonVerifyService;

    @RequestMapping("/preCommonVerify")
    public BaseRespDto preCommonVerify(@RequestBody PreCommonVerifyReqDto baseReqDto) throws Exception{
        return commonVerifyService.preCommonVerify(baseReqDto);
    }
}
