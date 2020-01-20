package test.反射.java中使用反射来遍历实体类和其父类的所有属性和值;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
public class Reflect {
    /**
     * 获取本类中的所有的属性和属性值的方法
     * @param model
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InvocationTargetException
     */
    public static void testReflect(Object model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvocationTargetException {
        Field[] field = model.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
        for(int j=0 ; j<field.length ; j++){     //遍历所有属性
            String name = field[j].getName();    //获取属性的名字

            System.out.println("attribute name:"+name);
            name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
            String type = field[j].getGenericType().toString();    //获取属性的类型
            if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
                Method m = model.getClass().getMethod("get"+name);
                String value = (String) m.invoke(model);    //调用getter方法获取属性值
                if(value != null){
                    System.out.println("attribute value:"+value);
                }
            }
            if(type.equals("class java.lang.Integer")){
                Method m = model.getClass().getMethod("get"+name);
                Integer value = (Integer) m.invoke(model);
                if(value != null){
                    System.out.println("attribute value:"+value);
                }
            }
            if(type.equals("class java.lang.Short")){
                Method m = model.getClass().getMethod("get"+name);
                Short value = (Short) m.invoke(model);
                if(value != null){
                    System.out.println("attribute value:"+value);                    }
            }
            if(type.equals("class java.lang.Double")){
                Method m = model.getClass().getMethod("get"+name);
                Double value = (Double) m.invoke(model);
                if(value != null){
                    System.out.println("attribute value:"+value);
                }
            }
            if(type.equals("class java.lang.Boolean")){
                Method m = model.getClass().getMethod("get"+name);
                Boolean value = (Boolean) m.invoke(model);
                if(value != null){
                    System.out.println("attribute value:"+value);
                }
            }
            if(type.equals("class java.util.Date")){
                Method m = model.getClass().getMethod("get"+name);
                Date value = (Date) m.invoke(model);
                if(value != null){
                    System.out.println("attribute value:"+value.toLocaleString());
                }
            }

        }
    }

    /**
     * 如果各位朋友们想进一步把获取到的对象的类名和属性名、属性的类型以及属性对应的值封装起来并且返回给调用者，
     * 那么，可以使用以下方法，注意下面的方法需要导入org.json.jar，调用这个getObjVal（）方法就会返回一个jsonObject对象，
     * 这个对象封装了传递过来的未知对象的所有的非空属性对应的值，如果想要把空的值对应的属性也添加进来，
     * 只需将getObjProVal方法中的判断值为空的代码去掉即可
     * @param object
     * @return
     */
    public static JSONObject getObjVal(Object object) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("className", object.getClass().getSimpleName());
            jsonObject.put("condition", getObjProVal(object, object.getClass()));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        return jsonObject;
    }

    @SuppressWarnings("rawtypes")
    public static JSONArray getObjProVal(Object o, Class _class)
            throws JSONException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException,
            NoSuchFieldException, SecurityException {
        JSONArray jsonArray = new JSONArray();
        while (_class != null) {
            Method[] methods = _class.getDeclaredMethods();// 获得类的方法集合
            // 遍历方法集合
            for (int i = 0; i < methods.length; i++) {
                // 获取所有getXX()的返回值
                if (methods[i].getName().equals("getClass")) {
                    continue;
                }
                if (methods[i].getName().startsWith("get")) {// 方法返回方法名
                    JSONObject jsonObject = new JSONObject();
                    methods[i].setAccessible(true);// 允许private被访问(以避免private
                    // getXX())
                    String methodName = methods[i].getName();
                    String proName = methodName.substring(3, 4).toLowerCase()
                            + methodName.substring(4);
                    jsonObject.put("pro", proName);

                    String type = _class.getDeclaredField(proName)
                            .getGenericType().toString();
                    int index = type.lastIndexOf(".");
                    jsonObject.put("type",index > 0 ? type.substring(index + 1) : type);
                    if (index > 0 && type.substring(index + 1).equals("Date")) {
                        Date value = (Date) methods[i].invoke(o, null);
                        if (value != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String str = sdf.format(value);
                            jsonObject.put("value", str);
                            jsonArray.put(jsonObject);
                        }
                    } else {
                        Object object = methods[i].invoke(o, null);
                        if (object != null) {
                            jsonObject.put("value", object);
                            jsonArray.put(jsonObject);
                        }
                    }

                }
            }
            _class = _class.getSuperclass();
        }
        return jsonArray;
    }
}
