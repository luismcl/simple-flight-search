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

    public String getName() {
        return name;
    }

    public Double getInfantPrice() {
        return infantPrice;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "IATACode='" + IATACode + '\'' +
                ", name='" + name + '\'' +
                ", infantPrice=" + infantPrice +
                '}';
    }
}
