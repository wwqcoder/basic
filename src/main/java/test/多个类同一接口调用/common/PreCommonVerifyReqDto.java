package test.多个类同一接口调用.common;

import lombok.Data;
import test.多个类同一接口调用.common.BaseReqDto;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
@Data
public class PreCommonVerifyReqDto extends BaseReqDto {
    /**
     * 功能名称：
     * USERPHONE ：用户手机号
     * TRADEPWD：交易密码
     * LOGINPWD ： 登陆密码
     * BANKCARD：银行卡
     */
    private String funName;


    /**
     * 操作类型：
     * ADD：添加
     * DELETE：删除
     * MODIFY：修改
     * RESET：重置
     */
    private String funType;

    /**
     * 非必传，业务额外字段
     * <p>
     * 1、登录增验需要传
     */
    private String funParam;


}
