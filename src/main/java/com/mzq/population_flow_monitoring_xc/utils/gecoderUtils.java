package com.mzq.population_flow_monitoring_xc.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class gecoderUtils {
    public static Map<String,Object> GetLocation(String longitude, String latitude) {
        String message = "";
        String address = "";
        Map<String,Object> result_map = new HashMap<>();
        // 高德地图逆地理编码API
        String url = String.format(
                "https://restapi.amap.com/v3/geocode/regeo?output=JSON&key=15798499e43bf2a38f365fb8f3a75e98&radius=1000&extensions=all&batch=false&roadlevel=0&location=%s,%s",
                longitude, latitude);

        URL myURL = null;
        URLConnection httpsConn;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            httpsConn = myURL.openConnection();
            httpsConn.setConnectTimeout(100000);
            InputStreamReader insr = new InputStreamReader(
                    httpsConn.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(insr);
            String data = null;
            while ((data = br.readLine()) != null) {
                message = message + data;
            }
            JsonParser jp = new JsonParser();
            //将json字符串转化成json对象
            //System.out.println(message);
            JsonObject jo = jp.parse(message).getAsJsonObject();
            String status = jo.get("status").getAsString();
            String addressJsonEle = jo.get("regeocode").getAsJsonObject().get("formatted_address").toString();
            result_map.put("district",jo.get("regeocode").getAsJsonObject().get("addressComponent").getAsJsonObject().get("district"));
            if (addressJsonEle.equals("[]")) {
                address = null;
            } else {
                if (jo.get("regeocode").getAsJsonObject().get("pois").getAsJsonArray().size() <= 0) {
                    String detail = jo.get("regeocode").getAsJsonObject().get("addressComponent").getAsJsonObject().get("streetNumber").getAsJsonObject().get("street").getAsString() + jo.get("regeocode").getAsJsonObject().get("addressComponent").getAsJsonObject().get("streetNumber").getAsJsonObject().get("number").getAsString();
                    if (status.equals("1")) {
                        address = detail;
                    }
                } else {
                    String detail = jo.get("regeocode").getAsJsonObject().get("pois").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                    String detailDistance = jo.get("regeocode").getAsJsonObject().get("pois").getAsJsonArray().get(0).getAsJsonObject().get("distance").getAsString();
                    if (status.equals("1")) {
                        address = detail +detailDistance;
                    }
                }
            }
            insr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result_map.put("recommend",address);


        return result_map;
    }
    public static void main(String[] args) {
        //System.out.println((String)  getLocation("113.76212","34.07318").get("district"));
        System.out.println(GetLocation("113.792422","34.028845"));


    }
}
