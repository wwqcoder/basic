package test.annotation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/11/5
 * @描述
 */
public class Test {
    String aa;

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    @Override
    public String toString() {
        return "Test{" +
                "aa='" + aa + '\'' +
                '}';
    }

    public static void main(String[] args) {
        String bb = "{\"aa\":\"bb\"}";
        Test object = JSON.parseObject(bb,Test.class);
        System.out.println(object);
    }

}
