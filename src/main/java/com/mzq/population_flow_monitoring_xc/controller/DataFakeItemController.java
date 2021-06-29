package com.mzq.population_flow_monitoring_xc.controller;

import com.mzq.population_flow_monitoring_xc.entity.DataFakeItem;
import com.mzq.population_flow_monitoring_xc.service.DataFakeItemService;

import net.mindview.util.FourTuple;
import net.mindview.util.ThreeTuple;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

@RestController
@RequestMapping("/pmxc")
public class DataFakeItemController {

    @Autowired
    DataFakeItemService dataFakeItemService;

    @CrossOrigin
    @RequestMapping(value = "/Find/{id}", method = RequestMethod.GET)
    public Map<String, Object>  getDataFakeItemById(@PathVariable Integer id){
        Map<String, Object> map = new HashMap<>();
        System.out.println(id);
        DataFakeItem dataFakeItem = dataFakeItemService.getDataFakeItemById(id);
        map.put("data", dataFakeItem);
        return map;
    }

    @CrossOrigin
    @RequestMapping(value = "/Age", method = RequestMethod.GET)
    public Map<String, Object>  getAgeDistribution(){
        Map<String, Object> map = new HashMap<>();
        List<Pair<String,Integer>>  mapha = dataFakeItemService.getAgeDistribution();
        System.out.println(mapha);
        map.put("age", mapha);
        return map;
    }

    @CrossOrigin
    @RequestMapping(value = "/Population", method = RequestMethod.GET)
    public Map<String, Object>  getRegionalPopulation(){
        Map<String, Object> map = new HashMap<>();
        List<Pair<String,Integer>> mapRegionalPopulation = dataFakeItemService.getRegionalPopulation();
        map.put("population", mapRegionalPopulation);
        return map;
    }

    @CrossOrigin
    @RequestMapping(value = "/DistrictByTime/{time}", method = RequestMethod.GET)
    public Map<String, Object>  getRegionalSta(@PathVariable String time){
        Map<String, Object> map = new HashMap<>();
        Map<String, Integer>  mapha = dataFakeItemService.getSSNLLByTime(time);
        map.put("district", mapha);
        return map;
    }

    @CrossOrigin
    @RequestMapping(value = "/DetailDistrictByTime/{time}", method = RequestMethod.GET)
    public Map<String, Object>  getDetailRegionalSta(@PathVariable String time){
        Map<String, Object> map = new HashMap<>();
        Map<String, Integer>  mapha = dataFakeItemService.getDetailSSNLLByTime(time);
        map.put("district", mapha);
        return map;
    }

    @CrossOrigin
    @RequestMapping(value = "/FloatingByTime/{time}", method = RequestMethod.GET)
    public Map<Pair<String,String>, Object>  getFloating(@PathVariable String time){
        String time1 = time.substring(0,10);
        String time2 = time.substring(12,20);
        Map<Pair<String,String>, Object> map = new HashMap<>();
        Map<Pair<String,String>, Integer>  mapha = dataFakeItemService.getFloatingByTime(time1,time2);
        Pair<String,String> p = new Pair<>("origin","destination");
        map.put(p, mapha);
        return map;
    }

    @CrossOrigin
    @RequestMapping(value = "/TrajectoryByName/name/{name}/time1/{time1}/time2/{time2}", method = RequestMethod.GET)
    public JSONArray  getTrajectory(@PathVariable String name,@PathVariable String time1, @PathVariable String time2){
        List<JSONObject> mapha = dataFakeItemService.getTrajectoryByName(name,time1,time2);
        return JSONArray.fromObject(mapha);
    }

    @CrossOrigin
    @RequestMapping(value = "/Top5GatherDistrct/{time}", method = RequestMethod.GET)
    public Map<String, Object>  getTop5Gather(@PathVariable String time){
        Map<String, Object> map = new HashMap<>();
        List<ThreeTuple<String,Integer,String>> mapha = dataFakeItemService.getTop5GatherDistrict(time);
        map.put("Top5Gather", mapha);
        return map;
    }

    @CrossOrigin
    @RequestMapping(value = "/Lln/{time}", method = RequestMethod.GET)
    public JSONArray getllnj(@PathVariable String time){
        List<JSONObject> mapha = dataFakeItemService.getLLN(time);
        return JSONArray.fromObject(mapha);
    }

    @CrossOrigin
    @RequestMapping(value = "/TralPopulation/time/{time}/days/{days}", method = RequestMethod.GET)
    public Map<String, Object> getTral(@PathVariable String time,@PathVariable Integer days){
        Map<String, Object> map = new HashMap<>();
        Map<String, Integer>  mapha = dataFakeItemService.getTralPopulation(time,days);
        map.put("tralPopulation", mapha);
        return map;
    }
}
