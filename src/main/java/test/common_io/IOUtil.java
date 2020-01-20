package test.common_io;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/7/10
 * @描述
 */
public class IOUtil {
    public static void main(String[] args){
        try {
            String aa = "你好中国";
            InputStream is = new ByteArrayInputStream(aa.getBytes());
            String requestJson = IOUtils.toString(is, "UTF-8");
            System.out.println(requestJson);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
