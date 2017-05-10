package com.luismiguelcisneros.sfs.domain;


import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.luismiguelcisneros.sfs.domain.PassengerType.*;

public class FlightRequest {

    private String originCity;
    private String destinyCity;
    private LocalDate departureDate;
    private Map<PassengerType, Integer> passagers  = new HashMap<>();

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinyCity() {
        return destinyCity;
    }

    public void setDestinyCity(String destinyCity) {
        this.destinyCity = destinyCity;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public int getAdults() {
        return passagers.getOrDefault(ADULT, 0);
    }

    public void setAdults(int adults) {
        passagers.put(ADULT, adults);
    }

    public int getChildren() {
        return passagers.getOrDefault(CHILD, 0);
    }

    public void setChildren(int children) {
        passagers.put(CHILD, children);
    }

    public int getInfants() {
        return passagers.getOrDefault(INFANT, 0);
    }

    public void setInfants(int infants) {
        passagers.put(INFANT, infants);
    }

    public Map<PassengerType, Integer> getPassagers() {
        return Collections.unmodifiableMap(passagers);
    }
}
