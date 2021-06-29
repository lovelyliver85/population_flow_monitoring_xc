package com.mzq.population_flow_monitoring_xc.utils;

import net.sf.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class GeographicUtils {
    // key
    private static final String KEY = "Z7SBZ-KPNCX-G2H4E-Z4GXO-3FTJ2-ADB6J";

    // 腾讯地图
    public static Map<String, Object> getLocation(String lng, String lat) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 参数解释：lng：经度，lat：维度。KEY：腾讯地图key，get_poi：返回状态。1返回，0不返回
        String urlString = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + lat + "," + lng + "&key=" + KEY + "&get_poi=1";
        String result = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            // 腾讯地图使用GET
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            // 获取地址解析结果
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
        } catch (Exception e) {
            e.getMessage();
        }

       //System.out.println(result);

        // 转JSON格式
        JSONObject jsonObject = JSONObject.fromObject(result).getJSONObject("result");
        // 获取地址（行政区划信息） 包含有国籍，省份，城市
        JSONObject adInfo = jsonObject.getJSONObject("ad_info");
        JSONObject formattedAddresses = jsonObject.getJSONObject("formatted_addresses");
        resultMap.put("nation", adInfo.get("nation"));
        resultMap.put("nationCode", adInfo.get("nation_code"));
        resultMap.put("province", adInfo.get("province"));
        resultMap.put("provinceCode", adInfo.get("adcode"));
        resultMap.put("city", adInfo.get("city"));
        resultMap.put("cityCode", adInfo.get("city_code"));
        resultMap.put("district", adInfo.get("district"));
        resultMap.put("recommend", formattedAddresses.get("recommend"));
        //System.out.println(resultMap);
        return resultMap;
    }


    public static Map<String,Object> GetLocationMsg(String longitude, String latitude) {
        String message = "";
        String address = "";
        Map<String,Object> result_map = new HashMap<>();
        // 高德地图逆地理编码API
        String url = String.format(
                "https://restapi.amap.com/v3/geocode/regeo?output=JSON&key=15798499e43bf2a38f365fb8f3a75e98&radius=100&extensions=all&batch=false&roadlevel=0&location=%s,%s",
                longitude, latitude);
        try {
            URL url_url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url_url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(100);
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Connection", "Keep-Alive");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String data;
            while ((data = in.readLine()) != null) {
                message = message + data;
            }
            //System.out.println(message);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!Objects.equals(message, "")) {
            JSONObject jsonObject = JSONObject.fromObject(message).getJSONObject("regeocode");
            result_map.put("recommend", jsonObject.get("formatted_address"));
            result_map.put("district", jsonObject.getJSONObject("addressComponent").get("district"));
            //System.out.println(result_map);
        }
        return result_map;
    }

    public static void main(String[] args) {
        //System.out.println((String)  getLocation("113.76212","34.07318").get("district"));
        System.out.println( GetLocationMsg("113.827392","34.025294").get("district"));


    }
}
