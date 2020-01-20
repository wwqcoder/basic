package test.BeanUtils.表单数据转换工具类;



import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/16
 * @描述
 */
public class WebUtils {
    private static final String FORMAT = "yyyy-MM-dd" ;

    /**
     * @Name: fillBean
     * @Description: 将Map集合中的数据封装到JavaBean
     * 说明：
     *   Map的key：与属性名称保持一致；
     *   Map的value：设置为属性值；
     * @Author: XXX
     * @Version: V1.0
     * @CreateDate: XXX
     * @Parameters: @param map
     * @Parameters: @param clazz
     * @Return: T
     */
    public static <T> T fillBean(Map<String, Object> map, Class<T> clazz) {
        T bean = null ;
        try {
            bean = clazz.newInstance() ;
            //注册日期类型转换器
            ConvertUtils.register(new Converter() {
                @Override
                public Object convert(Class type, Object value) {
                    if(type != Date.class) {
                        return null ;
                    }
                    if(value == null || "".equals(value.toString().trim())) {
                        return null ;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT) ;
                    Date date = null ;
                    try {
                        date = sdf.parse((String) value) ;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return date;
                }
            }, Date.class) ;
            //将Map集合中的数据封装到JavaBean
            BeanUtils.populate(bean, map) ;
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
        return bean ;
    }

    /**
     * @Name: copyProperties
     * @Description: 实现JavaBean对象之间的属性复制
     * 说明：原对象与目标对象内的属性名称必须相同
     * @Author: XXX
     * @Version: V1.0
     * @CreateDate: XXX
     * @Parameters: @param tClazz
     * @Parameters: @param oClazz
     * @Return: void
     */
    public static <T, O> void copyProperties(Class<T> tClazz, Class<O> oClazz) {
        try {
            T target = tClazz.newInstance() ;
            O origin = oClazz.newInstance() ;
            //注册日期类型转换器
            ConvertUtils.register(new Converter() {
                @Override
                public Object convert(Class type, Object value) {
                    if(type != Date.class) {
                        return null ;
                    }
                    if(value == null || "".equals(value.toString().trim())) {
                        return null ;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT) ;
                    Date date = null ;
                    try {
                        date = sdf.parse((String) value) ;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return date;
                }
            }, Date.class) ;
            //将源对象内的属性值复制到目标对象的属性上
            //BeanUtils.copyProperties(target, origin) ;
            org.springframework.beans.BeanUtils.copyProperties(target,origin);
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
    }
}
