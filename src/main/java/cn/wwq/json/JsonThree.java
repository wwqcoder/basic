package cn.wwq.json;

import com.alibaba.fastjson.JSONObject;

/**
 *    {
 *     "code":0,
 *     "message":"success",
 *     "sid":"igr0007e88e@dx16e35035e9e020d802",
 *     "data":{
 *         "result":{
 *             "age":{
 *                 "age_type":"0",
 *                 "child":"0.1452",
 *                 "middle":"0.5371",
 *                 "old":"0.3177"
 *             },
 *             "gender":{
 *                 "female":"0.1970",
 *                 "gender_type":"1",
 *                 "male":"0.8030"
 *             }
 *         },
 *         "status":2
 *     }
 * }
 */
public class JsonThree {
    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "\"code\": 0,\n" +
                "\"message\": \"success\",\n" +
                "\"sid\": \"igr0007e88e@dx16e35035e9e020d802\",\n" +
                "\"data\": {\n" +
                "\"result\": {\n" +
                "\"age\": {\n" +
                "\"age_type\": \"0\",\n" +
                "\"child\": \"0.1452\",\n" +
                "\"middle\": \"0.5371\",\n" +
                "\"old\": \"0.3177\"\n" +
                "},\n" +
                "\"gender\": {\n" +
                "\"female\": \"0.1970\",\n" +
                "\"gender_type\": \"1\",\n" +
                "\"male\": \"0.8030\"\n" +
                "}\n" +
                "},\n" +
                "\"status\": 2\n" +
                "}\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONObject resultObject = dataObject.getJSONObject("result");
        JSONObject genderObject = resultObject.getJSONObject("gender");
        Object gender_type = genderObject.get("gender_type");
        System.out.println("gender_type ---------------"+ gender_type);
    }
}
