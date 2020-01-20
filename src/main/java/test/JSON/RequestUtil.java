package test.JSON;

import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/16
 * @描述
 */
public class RequestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);

    private RequestUtil() {}


    /**
     * request的全部参数转换成map
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String[]> getMap(HttpServletRequest request){

        return request.getParameterMap();
    }


    /**
     * request的部分参数转换成map：
     * 根据的keys指定的的Key从request取出值放到Map中
     * @param request
     * @param keys 需要转的key,多个key用豆号隔开
     * @return
     */
    public static Map<String,Object> getMap(HttpServletRequest request, String keys){
        String[] split = keys.split(",");
        Map<String,Object> map = new HashMap<String, Object>(split.length);
        for (String key : split) {
            map.put(key, request.getParameter(key));
        }
        return map;
    }

    /**
     * 解析JSONObject对象转换为 map
     * @param keys
     * @param MainJsonObj
     * @return
     */
    public static Map<String,Object> getMap(String keys, JSONObject MainJsonObj){
        String[] split = keys.split(",");
        Map<String,Object> map = new HashMap<String, Object>(split.length);
        for(String key:split){
            map.put(key,MainJsonObj.getString(key));
        }
        return map;
    }

    /**
     * 将map转换成bean对象：
     * 根据Map的keyValues创建Bean对象
     * @param theClass
     * @param keyValues
     * @return
     */
    public static <T> T getBean(Class<T> theClass,Map<String,Object> keyValues){
        T bean = null;
        try {
            bean = theClass.newInstance();
            BeanUtils.populate(bean, keyValues);
        } catch (Exception e) {
            LOGGER.error("map转换成bean对象出错", e);
        }
        return bean;
    }
}
