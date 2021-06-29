package com.mzq.population_flow_monitoring_xc.service;

import com.mzq.population_flow_monitoring_xc.entity.*;
import com.mzq.population_flow_monitoring_xc.mapper.DataFakeItemMapper;
import com.mzq.population_flow_monitoring_xc.utils.GeographicUtils;
import javafx.util.Pair;
import net.mindview.util.ThreeTuple;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mzq.population_flow_monitoring_xc.utils.gecoderUtils;

import java.time.Year;
import java.util.*;
import java.util.Date;
import net.mindview.util.FourTuple;

import static java.lang.Thread.sleep;

@Service
public class DataFakeItemService {
    @Autowired
    DataFakeItemMapper dataFakeItemMapper;

    public DataFakeItem getDataFakeItemById(Integer id) {
        return dataFakeItemMapper.getDataFakeItemById(id);
    }

    public List<String> getAllSSN() {
        return dataFakeItemMapper.getAllSSN();
    }

    public List<Pair<String, Integer>> getRegionalPopulation() {
        List<String> listALLSSN = dataFakeItemMapper.getAllSSN();
        Map<String, Integer> mapRegionalPopulation = new HashMap<>();
        Map<String, String> no_distirct = new HashMap<>();
        List<Pair<String,Integer>> Population = new ArrayList<>();

        no_distirct.put("411001", "许昌市直属");
        no_distirct.put("411002", "魏都区");
        no_distirct.put("411023", "许昌县");
        no_distirct.put("411024", "鄢陵县");
        no_distirct.put("411025", "襄城县");
        no_distirct.put("411081", "禹州市");
        no_distirct.put("411082", "长葛市");

        mapRegionalPopulation.put("许昌市直属", 0);
        mapRegionalPopulation.put("魏都区", 0);
        mapRegionalPopulation.put("许昌县", 0);
        mapRegionalPopulation.put("鄢陵县", 0);
        mapRegionalPopulation.put("襄城县", 0);
        mapRegionalPopulation.put("禹州市", 0);
        mapRegionalPopulation.put("长葛市", 0);
        mapRegionalPopulation.put("其他", 0);

        for (String ssn : listALLSSN) {
            if (!mapRegionalPopulation.containsKey(no_distirct.get(ssn.substring(0, 6)))) {
                mapRegionalPopulation.put("其他", mapRegionalPopulation.get("其他") + 1);
            } else {
                mapRegionalPopulation.put(no_distirct.get(ssn.substring(0, 6)), mapRegionalPopulation.get(no_distirct.get(ssn.substring(0, 6))) + 1);
            }
        }

        Pair<String,Integer> p = new Pair<>("魏都区",mapRegionalPopulation.get("许昌市直属")+mapRegionalPopulation.get("魏都区"));
        Population.add(p);
        p = new Pair<>("建安区",mapRegionalPopulation.get("许昌县"));
        Population.add(p);
        p = new Pair<>("鄢陵县",mapRegionalPopulation.get("鄢陵县"));
        Population.add(p);
        p = new Pair<>("襄城县",mapRegionalPopulation.get("襄城县"));
        Population.add(p);
        p = new Pair<>("禹州市",mapRegionalPopulation.get("禹州市"));
        Population.add(p);
        p = new Pair<>("长葛市",mapRegionalPopulation.get("长葛市"));
        Population.add(p);
        p = new Pair<>("其他",mapRegionalPopulation.get("其他"));
        Population.add(p);

        return Population;
    }


    public List<Pair<String,Integer>> getAgeDistribution() {
        List<String> allSSN = getAllSSN();
        Map<String, Integer> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        List<Pair<String,Integer>> age_num = new ArrayList<>();

        set.add("411001");
        set.add("411002");
        set.add("411003");
        set.add("411023");
        set.add("411024");
        set.add("411025");
        set.add("411081");
        set.add("411082");

        map.put("0-9", 0);
        map.put("10-19", 0);
        map.put("20-29", 0);
        map.put("30-39", 0);
        map.put("40-49", 0);
        map.put("50-59", 0);
        map.put("60-69", 0);
        map.put("70-79", 0);
        map.put("80-89", 0);
        map.put("90-", 0);

        int yearNow = Year.now().getValue();
        for (String ssn : allSSN) {
            if (set.contains(ssn.substring(0, 6))) {
                int yearyou = new Integer(ssn.substring(6, 10));
                //System.out.println(yearyou);
                int yearcha = yearNow - yearyou;
                if (yearcha > 0 && yearcha < 9) {
                    map.put("0-9", map.get("0-9") + 1);
                } else if (yearcha > 10 && yearcha < 19) {
                    map.put("10-19", map.get("10-19") + 1);
                } else if (yearcha > 20 && yearcha < 29) {
                    map.put("20-29", map.get("20-29") + 1);
                } else if (yearcha > 30 && yearcha < 39) {
                    map.put("30-39", map.get("30-39") + 1);
                } else if (yearcha > 40 && yearcha < 49) {
                    map.put("40-49", map.get("40-49") + 1);
                } else if (yearcha > 50 && yearcha < 59) {
                    map.put("50-59", map.get("50-59") + 1);
                } else if (yearcha > 60 && yearcha < 69) {
                    map.put("60-69", map.get("60-69") + 1);
                } else if (yearcha > 70 && yearcha < 79) {
                    map.put("70-79", map.get("70-79") + 1);
                } else if (yearcha > 80 && yearcha < 89) {
                    map.put("80-89", map.get("80-89") + 1);
                } else if (yearcha > 90){
                    map.put("90-", map.get("90-") + 1);
                }
            }
        }

        Pair<String,Integer> p = new Pair<>("0-9",map.get("0-9"));
        age_num.add(p);
        p = new Pair<>("10-19",map.get("10-19"));
        age_num.add(p);
        p = new Pair<>("20-29",map.get("20-29"));
        age_num.add(p);
        p = new Pair<>("30-39",map.get("30-39"));
        age_num.add(p);
        p = new Pair<>("40-49",map.get("40-49"));
        age_num.add(p);
        p = new Pair<>("50-59",map.get("50-59"));
        age_num.add(p);
        p = new Pair<>("60-69",map.get("60-69"));
        age_num.add(p);
        p = new Pair<>("70-79",map.get("70-79"));
        age_num.add(p);
        p = new Pair<>("80-89",map.get("80-89"));
        age_num.add(p);
        p = new Pair<>("90-",map.get("90-"));
        age_num.add(p);

        return age_num;
    }


    public Map<String, Integer> getSSNLLByTime(String time) {
        List<SSNLL> ssnllList = dataFakeItemMapper.getSSNLLByTime(time);
        Map<String, Integer> map = new HashMap<>();
        Set<String> set = new HashSet<>();

        set.add("411001");
        set.add("411002");
        set.add("411003");
        set.add("411023");
        set.add("411024");
        set.add("411025");
        set.add("411081");
        set.add("411082");

        Set<String> setDistrict = new HashSet<>();
        setDistrict.add("建安区");
        setDistrict.add("魏都区");
        setDistrict.add("禹州市");
        setDistrict.add("长葛市");
        setDistrict.add("鄢陵县");
        setDistrict.add("襄城县");
        setDistrict.add("许昌县");


        for (SSNLL ssnll : ssnllList) {
            Map<String, Object> result = GeographicUtils.GetLocationMsg(ssnll.getLatitude().substring(0, ssnll.getLatitude().length() - 2), ssnll.getLongitude());

//            try {
//                sleep(2);
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }
            if (result.get("district") != null){
                String district =  result.get("district").toString();
                //System.out.println(district);
                if (setDistrict.contains(district)) {
                    if (!map.containsKey(district)) {
                        if (set.contains(ssnll.getSsn().substring(0, 6))) {
                            map.put(district, 1);
                        } else {
                            map.put(district + "_外来流动人口", 1);
                        }
                    } else {
                        if (set.contains(ssnll.getSsn().substring(0, 6))) {
                            map.put(district, map.get(district) + 1);
                        } else {
                            map.put(district + "_外来流动人口", map.get(district) + 1);
                        }
                    }
                }
            }
        }
        return map;
    }

    public Map<String, Integer> getDetailSSNLLByTime(String time) {
        List<SSNLL> ssnllList = dataFakeItemMapper.getSSNLLByTime(time);
        Map<String, Integer> map = new HashMap<>();
        Set<String> set = new HashSet<>();

        set.add("411001");
        set.add("411002");
        set.add("411003");
        set.add("411023");
        set.add("411024");
        set.add("411025");
        set.add("411081");
        set.add("411082");

        Set<String> setDistrict = new HashSet<>();
        setDistrict.add("建安区");
        setDistrict.add("魏都区");
        setDistrict.add("禹州市");
        setDistrict.add("长葛市");
        setDistrict.add("鄢陵县");
        setDistrict.add("襄城县");
        setDistrict.add("许昌县");


        for (SSNLL ssnll : ssnllList) {
            Map<String, Object> result = GeographicUtils.GetLocationMsg(ssnll.getLatitude().substring(0, ssnll.getLatitude().length() - 2), ssnll.getLongitude());

//            try {
//                sleep(5);
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }

            if (result.get("recommend") != null) {
                String district = (String) result.get("recommend");
                //System.out.println(district);
                if (setDistrict.contains(district.substring(6, 9))) {
                    if (!map.containsKey(district)) {
                        if (set.contains(ssnll.getSsn().substring(0, 6))) {
                            map.put(district, 1);
                            map.put(district.substring(6, 9), 1);
                        } else {
                            map.put(district + "_外来流动人口", 1);
                            map.put(district.substring(6, 9) + "_外来流动人口", 1);
                        }
                    } else {
                        if (set.contains(ssnll.getSsn().substring(0, 6))) {
                            map.put(district, map.get(district) + 1);
                            map.put(district.substring(6, 9), map.get(district.substring(6, 9)) + 1);
                        } else {
                            map.put(district + "_外来流动人口", map.get(district) + 1);
                            map.put(district.substring(6, 9) + "_外来流动人口", map.get(district.substring(6, 9)) + 1);
                        }
                    }
                }
            }
        }
        return map;
    }

    public Map<Pair<String, String>, Integer> getFloatingByTime(String time_1, String time_2) {
        List<SSNLL> ssnllList_1 = dataFakeItemMapper.getSSNLLByTime(time_1);
        //List<SSNLL> ssnllList_2 = dataFakeItemMapper.getSSNLLByTime(time_2);
        Map<Pair<String, String>, Integer> map = new HashMap<>();

        Set<String> setDistrict = new HashSet<>();
        setDistrict.add("建安区");
        setDistrict.add("魏都区");
        setDistrict.add("禹州市");
        setDistrict.add("长葛市");
        setDistrict.add("鄢陵县");
        setDistrict.add("襄城县");
        setDistrict.add("许昌县");

        for (SSNLL ssnll : ssnllList_1) {
            Map<String, Object> result = GeographicUtils.GetLocationMsg(ssnll.getLatitude().substring(0, ssnll.getLatitude().length() - 2), ssnll.getLongitude());

//            try {
//                sleep(5);
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }
            String ssn = ssnll.getSsn();
            List<LLT> lltList = dataFakeItemMapper.getLLTBySsn(ssn);
            List<LLT> lltList_test = lltList.subList(0, 10);
            if (result.get("district") != null) {
                String district = (String) result.get("district");
                for (LLT llt : lltList_test) {
                    Map<String, Object> result_llt = GeographicUtils.GetLocationMsg(llt.getLatitude().substring(0, llt.getLatitude().length() - 2), llt.getLongitude());
//                    try {
//                        sleep(5);
//                    } catch (InterruptedException e) {
//                        System.out.println(e);
//                    }

                    if (result_llt.get("district") != null) {
                        String district_llt = (String) result_llt.get("district");
                        if (llt.getTime().toString().equals(time_2)) {
                            Pair<String, String> p = new Pair<>(district.substring(0, 3), district_llt.substring(0, 3));
                            map.put(p, map.get(p) + 1);
                        }
                    }
                }
            }
        }
        return map;
    }

    private boolean timeInRange(String time1,String time2,String time){
        return (time.compareTo(time1) >= 0) && (time.compareTo(time2) <= 0);
    }
    public List<JSONObject> getTrajectoryByName(String name, String time1, String time2) {
        List<JSONObject> Trajectory = new ArrayList<>();
        List<LLTH> Trajectory_nosort = dataFakeItemMapper.getLLTHByName(name);

        Trajectory_nosort.sort(Comparator.comparing(LLTH::getTime));
//        List<LLTH> Trajectory_top10 = Trajectory_nosort;
//        if (Trajectory_nosort.size() > 10) {
//            Trajectory_top10 = Trajectory_nosort.subList(0, 10);
//        }

        //int total = 0;
        for (LLTH llth : Trajectory_nosort) {
            if (Objects.equals(time1, "null")){
                time1 = "1000-01-01";
            }
            if (Objects.equals(time2, "null")){
                time2 = "3000-01-01";
            }
            if (timeInRange(time1, time2, llth.getTime())) {
                Map<String, Object> result_llt = GeographicUtils.GetLocationMsg(llth.getLatitude().substring(0, llth.getLatitude().length() - 2), llth.getLongitude());
//            try {
//                sleep(5);
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }
                if (result_llt.get("recommend") != null) {
                    String district = (String) result_llt.get("recommend");
                    traj_json tj = new traj_json();
                    tj.setName(name);
                    tj.setLatitude(llth.getLatitude());
                    tj.setLongitude(llth.getLongitude());
                    tj.setTime(llth.getTime());
                    JSONObject json_tj = JSONObject.fromObject(tj);
                    Trajectory.add(json_tj);
                }
            }
        }

        return Trajectory;
    }

    public List<ThreeTuple<String, Integer, String>> getTop5GatherDistrict(String time) {
        List<ThreeTuple<String, Integer, String>> Top5Gather = new ArrayList<>();
        List<SSNLL> ssnllList = dataFakeItemMapper.getSSNLLByTime(time);
        Map<String, Integer> District_num = new HashMap<>();

        int total = 0;
        for (SSNLL ssnll : ssnllList) {
            //System.out.println(ssnll.getLongitude());
            Map<String, Object> result_llt = GeographicUtils.GetLocationMsg(ssnll.getLatitude().substring(0, ssnll.getLatitude().length() - 2), ssnll.getLongitude());
//            try {
//                sleep(5);
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }
            if (result_llt.get("recommend") != null) {
                String district = (String) result_llt.get("recommend");
                total = total + 1;
                if (District_num.containsKey(district)) {
                    District_num.put(district, District_num.get(district) + 1);
                } else {
                    District_num.put(district, 1);
                }
            }
        }

        List<String> mapKeyList = new ArrayList<>(District_num.keySet());
        for (String key : mapKeyList) {
            Double i = Double.valueOf(District_num.get(key));
            ThreeTuple<String, Integer, String> ttp = new ThreeTuple<>(key, District_num.get(key), String.format("%.2f", i / total * 100) + "%");
            Top5Gather.add(ttp);
        }

        Top5Gather.sort((o1, o2) -> o2.second.compareTo(o1.second));

        return Top5Gather.subList(0,5);
    }

    public List<JSONObject> getLLN(String time){
        List<JSONObject> LLN = new ArrayList<>();
        List<SSNLL> ssnllList = dataFakeItemMapper.getSSNLLByTime(time);
        Map<Pair<String,String>,Integer> lln_map = new HashMap<>();
        for (SSNLL ssnll :ssnllList){
            Pair<String,String> p = new Pair<>(ssnll.getLatitude(),ssnll.getLongitude());
            if (lln_map.containsKey(p)){
                lln_map.put(p,lln_map.get(p) + 1);
            }
            else{
                lln_map.put(p,1);
            }
        }
        List<Pair<String,String>> mapKeyList = new ArrayList<>(lln_map.keySet());
        for (Pair<String,String> key: mapKeyList){
            lln_json llnj = new lln_json();
            List<Double> coord = new ArrayList<>();
            coord.add(Double.valueOf(key.getKey()));
            coord.add(Double.valueOf(key.getValue()));
            llnj.setCoord(coord);
            llnj.setElevation(lln_map.get(key));
            JSONObject json_llnj = JSONObject.fromObject(llnj);
            LLN.add(json_llnj);
        }

        return LLN;
    }

    private String getyesterday(String today){
        String yesterday = "";
        System.out.println(today);
        int toaday_day = Integer.parseInt(today.substring(8,10));
        int toaday_month = Integer.parseInt(today.substring(5,7));
        int yesterday_day = toaday_day - 1;
        int yesterday_month = toaday_month;
        String month = "";
        String day = "";
        if (yesterday_day == 0){
           yesterday_month = yesterday_month - 1;
           switch (yesterday_month){
              case 1: yesterday_day = 31;break;
              case 2: yesterday_day = 28;break;
              case 3: yesterday_day = 31;break;
              case 4: yesterday_day = 30;break;
              case 5: yesterday_day = 31;break;
              case 6: yesterday_day = 30;break;
              case 7: yesterday_day = 31;break;
              case 8: yesterday_day = 31;break;
              case 9: yesterday_day = 30;break;
              case 10: yesterday_day = 31;break;
              case 11: yesterday_day = 30;break;
              case 12: yesterday_day = 31;break;
           }
        }
        if (yesterday_month < 10){
            month = month + "0" + yesterday_month;
        }
        else{
            month = month + yesterday_month;
        }
        if (yesterday_day < 10){
            day = day + "0" + yesterday_day;
        }
        else{
            day = day + yesterday_day;
        }
        yesterday = today.substring(0,5) + month + "-" + day;
        return yesterday;
    }
    public LinkedHashMap<String,Integer> getTralPopulation(String time,Integer days){
        LinkedHashMap<String,Integer> tralPopulation_map = new LinkedHashMap<>();
        String now_time = time;
        for (int i = 0; i< days; i = i+1) {
            int tralPopulation = 0;
            List<SSNLL> ssnllList = dataFakeItemMapper.getSSNLLByTime(now_time);
            Set<String> set = new HashSet<>();

            set.add("411001");
            set.add("411002");
            set.add("411003");
            set.add("411023");
            set.add("411024");
            set.add("411025");
            set.add("411081");
            set.add("411082");

            for (SSNLL ssnll : ssnllList) {
                if (!(set.contains(ssnll.getSsn().substring(0, 6)))) {
                    tralPopulation = tralPopulation + 1;
                }
            }
            tralPopulation_map.put(now_time,tralPopulation);
            now_time = getyesterday(now_time);
        }
        return tralPopulation_map;
    }
}
