package test;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/14
 * @描述
 */
public class UrlEncode {
    public static void main(String[] args) {
        String url = URLEncoder.encode("http://www.baidu.com?returl=www.xinlang.com");
        System.out.println(url);
        System.out.println(URLDecoder.decode(url));

        System.out.println(URLDecoder.decode("https://ft.jdpay.com/account/auth?redirect=http%3a%2f%2f66125719%2fa%3fhttps%3a%2f%2fft.jd.com%2fabssp%2fauth%3ffrom%3dhttps%253A%252F%252Fft.jd.com%252Fabssp"));

        String url2 = "https://ft.jdpay.com/account/auth?redirect=http://66125719/a?https://ft.jd.com/abssp/auth?from=https://ft.jd.com/abssp";
    }
}
