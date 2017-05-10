package com.luismiguelcisneros.sfs.services;

import com.luismiguelcisneros.sfs.dao.AirlinesDAO;
import com.luismiguelcisneros.sfs.dao.AirportsDAO;
import com.luismiguelcisneros.sfs.dao.FlightsDAO;
import com.luismiguelcisneros.sfs.domain.*;
import com.luismiguelcisneros.sfs.exceptions.CityNotFoundException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FlightService {


    private AirlinesDAO airlinesDAO;
    private AirportsDAO airportsDAO;
    private FlightsDAO flightsDAO;

    public FlightService(AirlinesDAO airlinesDAO, AirportsDAO airportsDAO, FlightsDAO flightsDAO) {
        this.airlinesDAO = airlinesDAO;
        this.airportsDAO = airportsDAO;
        this.flightsDAO = flightsDAO;
    }

    private static final TreeMap<Integer, Double> DATE_RULES = new TreeMap<>();

    static {
        DATE_RULES.put(31, 0.8);
        DATE_RULES.put(16, 1d);
        DATE_RULES.put(3, 1.2);
        DATE_RULES.put(0, 1.5);
    }

    public List<FlightResult> searchForFlight(FlightRequest request) throws CityNotFoundException {
        List<Flight> availableFlights = queryFlights(request);

        return availableFlights.stream()
                .map(flight -> calculateTotalPrice(flight, request))
                .collect(Collectors.toList());
    }

    Double getPrice(Flight flight, LocalDate date, PassengerType passengerType) {

        Double discountFactor = 1.0;

        switch (passengerType) {
            case INFANT:
                return airlinesDAO.queryByCode(flight.getAirline()).get().getInfantPrice();
            case CHILD:
                discountFactor = 0.67;
            default:
                Long days = ChronoUnit.DAYS.between(LocalDate.now(), date);
                Double rate = DATE_RULES.floorEntry(days.intValue()).getValue();
                return flight.getBasePrice() * rate * discountFactor;
        }
    }

    FlightResult calculateTotalPrice(Flight flight, FlightRequest request) {
        Double totalFlight =
                request.getPassagers().entrySet().stream().map(entry ->
                        getPrice(flight, request.getDepartureDate(), entry.getKey()) * entry.getValue()
                ).mapToDouble(Double::doubleValue).sum();

        return new FlightResult(flight.getCode(), totalFlight);
    }

    List<Flight> queryFlights(FlightRequest request) throws CityNotFoundException {
        Airport origin = airportsDAO.queryByCity(request.getOriginCity())
                .orElseThrow(() -> new CityNotFoundException(request.getOriginCity()));

        Airport destiny = airportsDAO.queryByCity(request.getDestinyCity())
                .orElseThrow(() -> new CityNotFoundException(request.getDestinyCity()));

        return flightsDAO.queryByOriginAndDestination(origin.getIataCode(), destiny.getIataCode());
    }
}