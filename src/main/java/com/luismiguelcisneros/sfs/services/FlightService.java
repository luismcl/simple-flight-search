package com.luismiguelcisneros.sfs.services;

import com.luismiguelcisneros.sfs.dao.AirlinesDAO;
import com.luismiguelcisneros.sfs.domain.Flight;
import com.luismiguelcisneros.sfs.domain.PassengerType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.TreeMap;

/**
 * Created by lcisneros on 10/05/17.
 */
public class FlightService {


    private AirlinesDAO airlinesDAO;

    public FlightService(AirlinesDAO airlinesDAO){
        this.airlinesDAO = airlinesDAO;
    }

    private static final TreeMap<Integer, Double> DATE_RULES = new TreeMap<>();
    static {
        DATE_RULES.put(31, 0.8);
        DATE_RULES.put(16, 1d);
        DATE_RULES.put(3, 1.2);
        DATE_RULES.put(0, 1.5);
    }


    Double getPrice(Flight flight, LocalDate date, PassengerType passengerType) {

        Double discountFactor = 1.0;

        switch (passengerType) {
            case INFANT:
                return airlinesDAO.queryByCode(flight.getAirline()).get().getInfantPrice();
            case CHILD:
                discountFactor = 0.67;
        }

        Long days = ChronoUnit.DAYS.between(LocalDate.now(), date);
        Double rate = DATE_RULES.floorEntry(days.intValue()).getValue();

        return flight.getBasePrice() * rate * discountFactor;
    }


}
