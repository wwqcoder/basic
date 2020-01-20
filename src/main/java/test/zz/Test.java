package test.zz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/17
 * @描述
 */
public class Test {
    public static void main(String[] args) {
        String str = "uriote30629@chacuo.net,,,,,,,,,,,,,,,,,,,,,,,";
        //String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        String regex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        System.out.println(match(regex, str));
    }

    /**
     * @param regex
     * 正则表达式字符串
     * @param str
     * 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
