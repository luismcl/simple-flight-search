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

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "iataCode='" + iataCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
