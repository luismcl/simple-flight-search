package com.luismiguelcisneros.sfs.domain;


public class Flight {
    private String origin;
    private String destination;
    private String code;
    private double basePrice;

    public Flight(String[] fields) {
        origin = fields[0];
        destination = fields[1];
        code = fields[2];
        basePrice = Double.parseDouble(fields[3]);
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getCode() {
        return code;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getAirline() {
        return code.substring(0, 2);
    }
}
