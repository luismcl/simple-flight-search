package com.luismiguelcisneros.sfs.domain;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class FlightResult {

    private String flightCode;
    private Double totalPrice;

    public FlightResult(String flightCode, Double totalPrice) {
        this.flightCode = flightCode;
        this.totalPrice = round(totalPrice);
    }

    public String getFlightCode() {
        return flightCode;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightResult that = (FlightResult) o;

        if (flightCode != null ? !flightCode.equals(that.flightCode) : that.flightCode != null) return false;
        return totalPrice != null ? totalPrice.equals(that.totalPrice) : that.totalPrice == null;
    }

    @Override
    public int hashCode() {
        int result = flightCode != null ? flightCode.hashCode() : 0;
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        return result;
    }

    private static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public String toString() {
        return "FlightResult{" +
                "flightCode='" + flightCode + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
