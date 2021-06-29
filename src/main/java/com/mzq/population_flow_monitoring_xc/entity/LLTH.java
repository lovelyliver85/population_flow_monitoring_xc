package com.mzq.population_flow_monitoring_xc.entity;

public class LLTH {
    String time;
    String longitude;
    String latitude;

    public String getTime() {
        return time.substring(0,13);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude.trim();
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
