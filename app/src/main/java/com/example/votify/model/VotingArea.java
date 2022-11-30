package com.example.votify.model;

public class VotingArea {

    double lat;
    double lon;
    String Station;

    public VotingArea(double lat, double lon, String station) {
        this.lat = lat;
        this.lon = lon;
        Station = station;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getStation() {
        return Station;
    }

    public void setStation(String station) {
        Station = station;
    }
}
