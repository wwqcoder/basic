package test.BeanUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/16
 * @描述
 */
public class BeanUtilTest {

    public User user = User.getInstance();



    /**
     * @Name: testSetProperty
     * @Description:
     * 1、设置Bean对象的单个属性
     *   public static void setProperty(Object bean, String name, Object value)
     *   =
     *   public static void copyProperty(Object bean, String name, Object value)
     * @Author: XXX
     * @Version: V1.0
     * @CreateDate: XXX
     * @Parameters: @throws Exception
     * @Return: void
     */
    @Test
    public void testSetProperty() throws Exception {
        System.out.println(user);
        //设置String类型的属性
        BeanUtils.setProperty(user, "user", "李四") ;
        //设置int类型的属性
        BeanUtils.setProperty(user, "age", 25) ;
        //设置boolean类型的属性
        BeanUtils.setProperty(user, "gender", "false") ;
        //设置Date类型的属性
        //异常信息：DateConverter does not support default String to 'Date' conversion.
        //注册String->日期类型转换器
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class type, Object value) {
                if(type != Date.class) {
                    return null ;
                }
                if(value == null && "".equals(value.toString().trim())) {
                    return null ;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
                Date date = null ;
                try {
                    date = sdf.parse((String) value) ;
                } catch (ParseException e) {
                    throw new RuntimeException(e) ;
                }
                return date;
            }
        }, Date.class) ;
        BeanUtils.setProperty(user, "birthday", "2016-92-11") ;
        //设置数组类型的属性
        String[] hobbies = {"11", "22", "33"} ;
        BeanUtils.setProperty(user, "hobbies", hobbies) ;
        //设置List、Set、Map、内嵌对象等...
        System.out.println(user);
    }

    /**
     * @Name: testPupulate
     * @Description:
     * 2、将Map<String, Object>集合中的内容设置到JavaBean的属性上
     * 说明：
     *   Map的key：必须与JavaBean的属性名称相同；
     *   Map的value：注入到JavaBean的属性；
     * 备注：
     *   此方法经常用于将表单提交的参数设置到表单Bean对象中
     * @Author: XXX
     * @Version: V1.0
     * @CreateDate: XXX
     * @Parameters:
     * @Return: void
     */
    @Test
    public void testPupulate() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>() ;
        //字符串->字符串属性
        map.put("name", "李大魁") ;
        //字符串->整型属性
        map.put("age", "25") ;
        //字符串->布尔型属性
        map.put("gender", "false") ;
        //字符串->日期类型属性
        map.put("birthday", "2016-12-10") ;
        //字符串->数组类型属性
        String[] hobbies = {"11", "22", "33"} ;
        map.put("hobbies", hobbies) ;
        //字符串->List集合属性
        List<String> strong = new ArrayList<String>() ;
        strong.add("1111") ;
        strong.add("22222") ;
        strong.add("33333") ;
        map.put("strong", strong) ;
        //字符串->Map集合属性
        Map<String, String> fault = new HashMap<String, String>() ;
        fault.put("学习222", "偷懒，反应慢") ;
        fault.put("感情222", "磨叽，不勇敢") ;
        map.put("fault", fault) ;
        //字符串->内嵌对象属性
        Student student = new Student("三思", 25) ;
        map.put("student", student) ;
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
                Date date = null ;
                try {
                    date = sdf.parse((String) value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date;
            }
        }, Date.class) ;
        //将Map集合注入到JavaBean
        BeanUtils.populate(user, map) ;
        System.out.println(user);
    }


}
