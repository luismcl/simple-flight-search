package com.luismiguelcisneros.sfs.domain;


public class Airport {

    private String iataCode;
    private String city;

    public Airport(String[] data) {
        this.iataCode = data[0];
        this.city = data[1];
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
