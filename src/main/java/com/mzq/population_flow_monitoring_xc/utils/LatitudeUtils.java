package com.mzq.population_flow_monitoring_xc.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang.StringUtils;


public class LatitudeUtils {

    public static final String KEY_1 = "15798499e43bf2a38f365fb8f3a75e98";

    /**
     * 返回输入地址的经纬度坐标
     * key lng(经度),lat(纬度)
     */
    public static Map<String,String> getGeocoderLatitude(String address){
        BufferedReader in = null;
        Map<String,String> map = new HashMap<>();
        map.put("lng","113.76212");
        map.put("lat","34.07318");

        try {
            //将地址转换成utf-8的16进制
            address = URLEncoder.encode(address, "UTF-8");
            URL tirc = new URL("http://api.map.baidu.com/geocoder?address="+ address +"&output=json&key="+ KEY_1);

            in = new BufferedReader(new InputStreamReader(tirc.openStream(),"UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while((res = in.readLine())!=null){
                sb.append(res.trim());
            }
            String str = sb.toString();

            if(StringUtils.isNotEmpty(str)){
                int lngStart = str.indexOf("lng\":");
                int lngEnd = str.indexOf(",\"lat");
                int latEnd = str.indexOf("},\"precise");
                if(lngStart > 0 && lngEnd > 0 && latEnd > 0){
                    String lng = str.substring(lngStart+5, lngEnd);
                    String lat = str.substring(lngEnd+7, latEnd);
                    map = new HashMap<>();
                    map.put("lng", lng);
                    map.put("lat", lat);
                    return map;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    public static void main(String[] args){
        try {
            Map<String, String> json = LatitudeUtils.getGeocoderLatitude("许昌市鄢陵县马坊镇");
            System.out.println("lng : " + json.get("lng"));
            System.out.println("lat : " + json.get("lat"));
        }catch (Exception e ){
            e.printStackTrace();
        }
    }


}
