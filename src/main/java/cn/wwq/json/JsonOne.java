package cn.wwq.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * json  JSONObject适用于单个值
 *      JSONArray 适用于 数组
 */
public class JsonOne {
    public static void main(String[] args) {

        String s = "\"permissionList\":[{\"dsName\":\"seerHive\",\"dbName\":\"afd\",\"type\":\"SELECT\",\"tableName\":\"test_table9991\"}]";

        JSONObject jsonObject = JSONObject.parseObject(s);

        String retCode = jsonObject.getString("retCode");

        if ("0000".equals(retCode)) {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray permissionList = data.getJSONArray("permissionList");
            if (permissionList != null) {
                //存在没有权限的库或是表
                for (Object o : permissionList) {
                    JSONObject permisssion = (JSONObject) o;
                    String dsName = permisssion.getString("dsName");
                    String dbName = permisssion.getString("dbName");
                    String type = permisssion.getString("type");
                    String tableName = permisssion.getString("tableName");

                    System.out.println(dsName + "\t" + dbName + "\t" + type + "\t" + tableName);
                }
            } else {
                System.out.println("库表都有权限可以执行");
            }


        } else {
            String retMsg = jsonObject.getString("retMsg");
            System.out.println(retMsg);
        }
    }
}
