package cn.wwq.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {
 *     "HeWeather6":[
 *         {
 *             "basic":{
 *                 "cid":"CN101010100",
 *                 "location":"北京",
 *                 "parent_city":"北京",
 *                 "admin_area":"北京",
 *                 "cnty":"中国",
 *                 "lat":"39.90498734",
 *                 "lon":"116.40528870",
 *                 "tz":"8.0"
 *             },
 *             "daily_forecast":[
 *                 {
 *                     "cond_code_d":"103",
 *                     "cond_code_n":"101",
 *                     "cond_txt_d":"晴间多云",
 *                     "cond_txt_n":"多云",
 *                     "date":"2017-10-26",
 *                     "hum":"57",
 *                     "pcpn":"0.0",
 *                     "pop":"0",
 *                     "pres":"1020",
 *                     "tmp_max":"16",
 *                     "tmp_min":"8",
 *                     "uv_index":"3",
 *                     "vis":"16",
 *                     "wind_deg":"0",
 *                     "wind_dir":"无持续风向",
 *                     "wind_sc":"微风",
 *                     "wind_spd":"5"
 *                 },
 *                 {
 *                     "cond_code_d":"101",
 *                     "cond_code_n":"501",
 *                     "cond_txt_d":"多云",
 *                     "cond_txt_n":"雾",
 *                     "date":"2017-10-27",
 *                     "hum":"56",
 *                     "pcpn":"0.0",
 *                     "pop":"0",
 *                     "pres":"1018",
 *                     "tmp_max":"18",
 *                     "tmp_min":"9",
 *                     "uv_index":"3",
 *                     "vis":"20",
 *                     "wind_deg":"187",
 *                     "wind_dir":"南风",
 *                     "wind_sc":"微风",
 *                     "wind_spd":"6"
 *                 },
 *                 {
 *                     "cond_code_d":"101",
 *                     "cond_code_n":"101",
 *                     "cond_txt_d":"多云",
 *                     "cond_txt_n":"多云",
 *                     "date":"2017-10-28",
 *                     "hum":"26",
 *                     "pcpn":"0.0",
 *                     "pop":"0",
 *                     "pres":"1029",
 *                     "tmp_max":"17",
 *                     "tmp_min":"5",
 *                     "uv_index":"2",
 *                     "vis":"20",
 *                     "wind_deg":"2",
 *                     "wind_dir":"北风",
 *                     "wind_sc":"3-4",
 *                     "wind_spd":"19"
 *                 }
 *             ],
 *             "status":"ok",
 *             "update":{
 *                 "loc":"2017-10-26 23:09",
 *                 "utc":"2017-10-26 15:09"
 *             }
 *         }
 *     ]
 * }
 */
public class JsonFour {
    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "\"HeWeather6\": [\n" +
                "{\n" +
                "\"basic\": {\n" +
                "\"cid\": \"CN101010100\",\n" +
                "\"location\": \"北京\",\n" +
                "\"parent_city\": \"北京\",\n" +
                "\"admin_area\": \"北京\",\n" +
                "\"cnty\": \"中国\",\n" +
                "\"lat\": \"39.90498734\",\n" +
                "\"lon\": \"116.40528870\",\n" +
                "\"tz\": \"8.0\"\n" +
                "},\n" +
                "\"daily_forecast\": [\n" +
                "{\n" +
                "\"cond_code_d\": \"103\",\n" +
                "\"cond_code_n\": \"101\",\n" +
                "\"cond_txt_d\": \"晴间多云\",\n" +
                "\"cond_txt_n\": \"多云\",\n" +
                "\"date\": \"2017-10-26\",\n" +
                "\"hum\": \"57\",\n" +
                "\"pcpn\": \"0.0\",\n" +
                "\"pop\": \"0\",\n" +
                "\"pres\": \"1020\",\n" +
                "\"tmp_max\": \"16\",\n" +
                "\"tmp_min\": \"8\",\n" +
                "\"uv_index\": \"3\",\n" +
                "\"vis\": \"16\",\n" +
                "\"wind_deg\": \"0\",\n" +
                "\"wind_dir\": \"无持续风向\",\n" +
                "\"wind_sc\": \"微风\",\n" +
                "\"wind_spd\": \"5\"\n" +
                "},\n" +
                "{\n" +
                "\"cond_code_d\": \"101\",\n" +
                "\"cond_code_n\": \"501\",\n" +
                "\"cond_txt_d\": \"多云\",\n" +
                "\"cond_txt_n\": \"雾\",\n" +
                "\"date\": \"2017-10-27\",\n" +
                "\"hum\": \"56\",\n" +
                "\"pcpn\": \"0.0\",\n" +
                "\"pop\": \"0\",\n" +
                "\"pres\": \"1018\",\n" +
                "\"tmp_max\": \"18\",\n" +
                "\"tmp_min\": \"9\",\n" +
                "\"uv_index\": \"3\",\n" +
                "\"vis\": \"20\",\n" +
                "\"wind_deg\": \"187\",\n" +
                "\"wind_dir\": \"南风\",\n" +
                "\"wind_sc\": \"微风\",\n" +
                "\"wind_spd\": \"6\"\n" +
                "},\n" +
                "{\n" +
                "\"cond_code_d\": \"101\",\n" +
                "\"cond_code_n\": \"101\",\n" +
                "\"cond_txt_d\": \"多云\",\n" +
                "\"cond_txt_n\": \"多云\",\n" +
                "\"date\": \"2017-10-28\",\n" +
                "\"hum\": \"26\",\n" +
                "\"pcpn\": \"0.0\",\n" +
                "\"pop\": \"0\",\n" +
                "\"pres\": \"1029\",\n" +
                "\"tmp_max\": \"17\",\n" +
                "\"tmp_min\": \"5\",\n" +
                "\"uv_index\": \"2\",\n" +
                "\"vis\": \"20\",\n" +
                "\"wind_deg\": \"2\",\n" +
                "\"wind_dir\": \"北风\",\n" +
                "\"wind_sc\": \"3-4\",\n" +
                "\"wind_spd\": \"19\"\n" +
                "}\n" +
                "],\n" +
                "\"status\": \"ok\",\n" +
                "\"update\": {\n" +
                "\"loc\": \"2017-10-26 23:09\",\n" +
                "\"utc\": \"2017-10-26 15:09\"\n" +
                "}\n" +
                "}\n" +
                "]\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONArray heWeather6 = jsonObject.getJSONArray("HeWeather6");
        JSONObject jsonObject1 = heWeather6.getJSONObject(0);
        JSONObject basic = jsonObject1.getJSONObject("basic");
        Object cid = basic.get("cid");
        System.out.println("cid ------------------"+cid);
        Object status = jsonObject1.get("status");
        System.out.println("status ---------------"+ status);
        System.out.println("-------------------------------------------");
        JSONArray daily_forecast = jsonObject1.getJSONArray("daily_forecast");

        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();

        for (int i = 0; i < daily_forecast.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("天气情况",daily_forecast.getJSONObject(i).getString("cond_txt_d"));
            map.put("当前日期",daily_forecast.getJSONObject(i).getString("date"));
            map.put("风向",daily_forecast.getJSONObject(i).getString("wind_dir"));
            mapList.add(map);
        }
        System.out.println(mapList.toString());

    }
}
