package com.mzq.population_flow_monitoring_xc.mapper;

import com.mzq.population_flow_monitoring_xc.entity.DataFakeItem;
import com.mzq.population_flow_monitoring_xc.entity.LLT;
import com.mzq.population_flow_monitoring_xc.entity.LLTH;
import com.mzq.population_flow_monitoring_xc.entity.SSNLL;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



@Mapper
public interface DataFakeItemMapper {
    DataFakeItem getDataFakeItemById(Integer id);
    List<String> getAllSSN();
    List<SSNLL> getSSNLLByTime(String time);
    List<LLT> getLLTBySsn(String ssn);
    List<LLTH> getLLTHByName(String name);
}
