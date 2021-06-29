package com.mzq.population_flow_monitoring_xc.entity;

import com.sun.xml.internal.ws.api.server.SDDocument;

import java.util.List;

public class lln_json {
    List<Double> coord;
    Integer elevation;
    public List<Double> getCoord(){return coord;}
    public void setCoord(List<Double> coord){this.coord = coord;}
    public Integer getElevation(){return elevation;}
    public void setElevation(Integer elevation){this.elevation = elevation;}
}
