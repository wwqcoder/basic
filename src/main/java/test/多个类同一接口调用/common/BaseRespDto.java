package test.多个类同一接口调用.common;

import lombok.Data;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
@Data
public class BaseRespDto<T> {

    private int code;

    private String msg;

    private T data;
}
