package com.luismiguelcisneros.sfs.domain;

public class Airline {

    private String IATACode;
    private String name;
    private Double infantPrice;

    public Airline(String[] data) {
        this.IATACode = data[0];
        this.name = data[1];
        this.infantPrice = Double.parseDouble(data[2]);
    }

    public String getIATACode() {
        return IATACode;
    }

    public void setIATACode(String IATACode) {
        this.IATACode = IATACode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInfantPrice() {
        return infantPrice;
    }

    public void setInfantPrice(Double infantPrice) {
        this.infantPrice = infantPrice;
    }
}
