package com.mzq.population_flow_monitoring_xc.utils;

import com.mzq.population_flow_monitoring_xc.entity.DataFakeGenerate;
import javafx.util.Pair;
import net.mindview.util.ThreeTuple;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

public class DataGenerate {

    public static void main(String[] args) {

        try {
            OutputStream os = new FileOutputStream("DataFake.txt");
            PrintWriter pw=new PrintWriter(os);
            for (int i=0;i < 29000;i=i+1){
                String test_name = getName();
                String test_ssn = getSsn();
                String test_phone = getPhone();
                ThreeTuple<String,String,String> test_lonlat = getLonLat();
                String test_lontitude = test_lonlat.first;
                String test_latitude = test_lonlat.second;
                String test_district = test_lonlat.third;
                String test_time = getTime();
                List<DataFakeGenerate> test_data_fake = generateTrajectory(test_lontitude,test_latitude,test_time,test_ssn,test_phone,test_name,test_district);
                //System.out.println(test_district);
                for (DataFakeGenerate data : test_data_fake){
                    pw.println(data.getName() + "," + data.getPhone() + "," + data.getSsn() + "," + data.getLongitude() + "," + data.getLatitude() + "," + data.getTime());// + "," + data.getDistrict());
                    //System.out.println(data.getDistrict());
                }
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getName(){
        String name = "";

        Random random=new Random(System.currentTimeMillis());

        String[] Surname= {"赵","钱","孙","李","周","吴","郑","王","冯","陈","褚","卫","蒋","沈","韩","杨","朱","秦","尤","许","何","吕"};

        String[] male_name = {"伟","强","军","勇","杰","辉","刚","明","俊","宇","坚","庆","民","聪","凡","雨","波","卓"};
        String[] female_name = {"若","芳","诗","莹","子","美","静","悠","柔","嘉","妍","莎","君","婉","黛","娴","仪","婷","倩"};

        int index_surname =random.nextInt(Surname.length-1);
        name = name + Surname[index_surname];

        boolean m_f = random.nextBoolean();
        if (m_f) {
            int index_name_1 = random.nextInt(male_name.length-1);
            int index_name_2 = random.nextInt(male_name.length-1);
            if (random.nextBoolean()) {
                name += male_name[index_name_1] + male_name[index_name_2];
            } else {
                name += male_name[index_name_1];
            }
        }
        else{
            int index_name_1 = random.nextInt(female_name.length-1);
            int index_name_2 = random.nextInt(female_name.length-1);
            if (random.nextBoolean()) {
                name += female_name[index_name_1] + female_name[index_name_2];
            } else {
                name += female_name[index_name_1];
            }
        }

        return name;
    }

    public static String getSsn(){
        Random random=new Random(System.currentTimeMillis());

        String ssn = "";

        String[] reginal_district = {"411001","411002","411023","411024","411025","411081","411082"};
        String[] other_district = {"411003","411004","411027","411080","411005","411008"};

        int dis_possi = random.nextInt(10);
        if (dis_possi < 3){
            ssn = ssn + other_district[random.nextInt(other_district.length - 1)];
        }
        else{
            ssn = ssn + reginal_district[random.nextInt(reginal_district.length - 1)];
        }

        int age_possi = random.nextInt(100);
        int age;
        if (age_possi < 20){
            age = random.nextInt(15) + 75;
        }
        else {
            if (age_possi > 96){
                age = random.nextInt(30);
            }
            else {
                age = random.nextInt(45) + 30;
            }
        }

        int birth = 1930 + age;
        ssn = ssn + birth;

        int month = random.nextInt(11) + 1;
        if (month >= 10) {
            ssn = ssn + month;
        }
        else {
            ssn = ssn + "0" + month;
        }

        int day = random.nextInt(29);
        if (day >= 10) {
            ssn = ssn + day;
        }
        else {
            ssn = ssn + "0" + day;
        }

        int tail = random.nextInt(8999) + 1000;
        if (tail % 10 == 0){
            tail = tail / 10;
            ssn = ssn + tail + "X";
        }
        else{
            ssn = ssn + tail;
        }

        return ssn;
    }

    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    public static String getPhone() {
        String phone;
        String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);

        phone = first + second + third;

        return phone;
    }

    public static ThreeTuple<String, String, String> getLonLat() {
//        double MinLon = 113.03;
//        double MaxLon = 114.19;
//        double MinLat = 33.16;
//        double MaxLat = 34.34;
//
//        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
//        String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
//
//        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
//        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
//
//        String[] dis_xc = {"许昌市魏都区许昌站","许昌市魏都区胖东来时代广场","许昌市魏都区许昌兴华路小学","许昌市魏都区许昌大酒店","许昌市魏都区许昌市人民医院","许昌市魏都区三鼎华悦大酒店","许昌市魏都区九弟万丰购物广场",
//        "许昌市魏都区万达广场","许昌市魏都区许昌汽车西站","许昌市魏都区许昌汽车南站","许昌市魏都区百瑞国际酒店","许昌市魏都区许昌市立医院","许昌市魏都区许昌汽车北站","许昌市建安区灞陵桥景区"};
//        ThreeTuple<String,String,String> lon_lat_dis = new ThreeTuple<>("113.76212","34.07318","许昌市建安区灞陵桥景区");
//        Random random=new Random(System.currentTimeMillis());
//        try {
//            int index=random.nextInt(dis_xc.length - 1);
//            String dis = dis_xc[index];
//            Map<String,String> lon_lat = LatitudeUtils.getGeocoderLatitude(dis);
//            String lon = lon_lat.get("lng");
//            String lat = lon_lat.get("lat");
//            lon_lat_dis = new ThreeTuple<>(lon,lat,dis);
//            return lon_lat_dis;
//        }catch (Exception e ){
//            e.printStackTrace();
//        }
        List<ThreeTuple<String,String,String>> dis_xc = new ArrayList<>();
        ThreeTuple<String,String,String> P = new ThreeTuple<>("113.827392","34.025294","许昌市魏都区许昌站");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.839795","34.024324","许昌市魏都区胖东来时代广场");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.82818","34.020998","许昌市魏都区许昌兴华路小学");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.834915","34.026367","许昌市魏都区许昌大酒店");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.829265","34.041801","许昌市魏都区许昌市人民医院");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.844808","34.031516","许昌市魏都区三鼎华悦大酒店");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.843095","34.047401","许昌市魏都区九弟万丰购物广场");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.76212","34.07318","许昌市魏都区万达广场");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.804757","34.035722","许昌市魏都区许昌汽车西站");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.821351","34.015737","许昌市魏都区许昌汽车南站");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.881527","34.033276","许昌市魏都区许昌市立医院");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.76212","34.07318","许昌市魏都区许昌汽车北站");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.792422","34.028845","许昌市建安区灞陵桥景区");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.691003","33.849327","许昌市襄城县范湖乡");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.87893","34.284221","许昌市长葛市大周镇");
        dis_xc.add(P);
        P = new ThreeTuple<>("113.477154","34.20279","许昌市禹州市朱阁镇");
        dis_xc.add(P);
        P = new ThreeTuple<>("114.228912","34.151627","许昌市鄢陵县马坊镇");
        dis_xc.add(P);
        Random random=new Random(System.currentTimeMillis());
        int index=random.nextInt(dis_xc.size() - 1);

        return dis_xc.get(index);
    }

    public static String getTime(){
        String time;

        Random random=new Random(System.currentTimeMillis());

        long seed = Calendar.getInstance().getTimeInMillis();
        long rand_seed = random.nextInt(86400000 * 20);//这里86400000代表着一天的毫秒数，*5代表着五天，随机生成今天之前五天内的时间
        Timestamp time_ts = new Timestamp(seed - rand_seed);
        time  = time_ts.toString().substring(0,19);

        return time;
    }

    public static List<DataFakeGenerate> generateTrajectory(String origin_lon,String origin_lat,String time,String ssn,String phone,String name,String district){
        Random random=new Random(System.currentTimeMillis());

        List<DataFakeGenerate> Trajectory = new ArrayList<>();

        DataFakeGenerate origin = new DataFakeGenerate();
        origin.setId(0);
        origin.setName(name);
        origin.setLatitude(origin_lat);
        origin.setLongitude(origin_lon);
        origin.setPhone(phone);
        origin.setSsn(ssn);
        origin.setTime(time);
        origin.setDistrict(district);
        Trajectory.add(origin);


        double trajectory_length_gaussian = 2 * random.nextGaussian() + 8;

        long trajectory_length = Math.round(trajectory_length_gaussian);
        //System.out.println(trajectory_length);
        String now_lon = origin_lon;
        String now_lat = origin_lat;
        String now_time = time;
        for (int i = 1; i <= trajectory_length;i = i + 1){
            origin = new DataFakeGenerate();
            origin.setId(i);
            origin.setName(name);
            origin.setPhone(phone);
            origin.setSsn(ssn);

            //time generate----------------------------------------------
            int time_hour = Integer.parseInt(now_time.substring(11, 13));
            int time_day = Integer.parseInt(now_time.substring(8, 10));
            int time_month = Integer.parseInt(now_time.substring(5,7));

            int rand_time = random.nextInt(10) + 1;

            int chage_day = (time_hour + rand_time) / 24;
            int chage_hour = (time_hour + rand_time) % 24;

            int time_next_day = time_day + chage_day;


            if (chage_day + time_day >31){
                time_next_day = 1;
                time_month = time_month + 1;
            }

            String next_day_s;
            if (time_next_day < 10){
                next_day_s = "0" + time_next_day;
            }
            else{
                next_day_s = "" + time_next_day;
            }
            String next_hour_s;
            if (chage_hour < 10){
                next_hour_s = "0" + chage_hour;
            }
            else{
                next_hour_s = "" + chage_hour;
            }

            String next_time = now_time.substring(0,5) + "0" + time_month + "-" + next_day_s + " " + next_hour_s + now_time.substring(13,19);
            origin.setTime(next_time);
            now_time = next_time;

            //-----------------------------------------------

            //location generate------------------------------------------------
            int range = 1000 * rand_time + 1;
            int rand_dis = random.nextInt(range);
            int rand_lon = random.nextInt(rand_dis + 1);
            int rand_lat = rand_dis - rand_lon;

            boolean if_east = random.nextBoolean();
            String next_lon;
            String next_lat;

            double next_lon_d;
            double v = rand_lon / 111000.0 * Math.cos(Double.parseDouble(now_lat));
            if(if_east) {
                next_lon_d = Double.parseDouble(now_lon) + v;
            }
            else {
                next_lon_d = Double.parseDouble(now_lon) - v;
            }
            //next_lon = Double.toString(next_lon_d);
            next_lon = String.format("%.6f",next_lon_d);

            boolean if_north = random.nextBoolean();
            double next_lat_d;
            if (if_north) {
                next_lat_d = Double.parseDouble(now_lat) + rand_lat / 111000.0;
            }
            else {
                next_lat_d = Double.parseDouble(now_lat) - rand_lat / 111000.0;
            }
            //next_lat = Double.toString(next_lat_d);
            next_lat = String.format("%.6f",next_lat_d);

            origin.setLongitude(next_lon);
            origin.setLatitude(next_lat);
            now_lat = next_lat;
            now_lon = next_lon;
            //-------------------------------------------------------------------------------------------------------------------------------------------

            Trajectory.add(origin);
        }

        return Trajectory;
    }

}
