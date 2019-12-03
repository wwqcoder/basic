package cn.wwq.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 解决json中带 [] 的问题
 */
public class JsonTwo {
    public static void main(String[] args) {
        /*JSONObject map = new JSONObject();
        map.put("database", "model_budding");
        map.put("url", "jdbc:mysql://172.25.4.215:3306/model_budding?useUnicode=true&characterEncoding=utf8&amp;useSSL=false");
        map.put("userName", "m_model_budding");
        map.put("password", "M4IFzH");
        map.put("sqls", tabModels);
        map.put("jobName", "exeHSql");
        String jsonMap = JSONArray.toJSONString(map);
        logger.info("得到了json字符串为："+ jsonMap);
        restTemplate.getMessageConverters().add(new ModelTableJackson2HttpMessageConverter());
        ResponseEntity<ExeHSqlResult> responseEntity = restTemplate.postForEntity(executeSqlUrl,jsonMap,ExeHSqlResult.class);
        logger.info("调用执行SQL返回值为："+ JSON.toJSONString(responseEntity));
        return responseEntity.getBody();*/
    }
}
