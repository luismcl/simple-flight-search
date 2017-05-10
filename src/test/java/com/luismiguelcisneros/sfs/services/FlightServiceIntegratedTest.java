package com.luismiguelcisneros.sfs.services;


import com.luismiguelcisneros.sfs.dao.AirlinesDAO;
import com.luismiguelcisneros.sfs.dao.AirportsDAO;
import com.luismiguelcisneros.sfs.dao.FlightsDAO;
import com.luismiguelcisneros.sfs.domain.Flight;
import com.luismiguelcisneros.sfs.domain.FlightRequest;
import com.luismiguelcisneros.sfs.domain.FlightResult;
import com.luismiguelcisneros.sfs.exceptions.CityNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FlightServiceIntegratedTest {

    private FlightService flightService;

    @Before
    public void init() throws IOException {

        String flightFile = getClass().getResource("/Flights.csv").getFile();
        String airportsFile = getClass().getResource("/Airports.csv").getFile();
        String airlinesFile = getClass().getResource("/Airlines.csv").getFile();

        AirlinesDAO airlinesDAO = new AirlinesDAO(airlinesFile);
        AirportsDAO airportsDAO = new AirportsDAO(airportsFile);
        FlightsDAO flightsDAO = new FlightsDAO(flightFile);

        flightService = new FlightService(airlinesDAO, airportsDAO, flightsDAO);
    }


    @Test
    //1 adult, 31 days to the departure date, flying AMS -> FRA
    public void test1() throws CityNotFoundException {

        FlightRequest request = new FlightRequest();
        request.setAdults(1);
        request.setOriginCity("Amsterdam");
        request.setDestinyCity("Frakfurt");
        request.setDepartureDate(LocalDate.now().plus(31, ChronoUnit.DAYS));

        List<FlightResult> result = flightService.searchForFlight(request);

        Assert.assertTrue(result.contains(new FlightResult("TK2372", 157.6)));
        Assert.assertTrue(result.contains(new FlightResult("TK2659", 198.4)));
        Assert.assertTrue(result.contains(new FlightResult("LH5909", 90.4)));
    }

    @Test
    //2 adults, 1 child, 1 infant, 15 days to the departure date, flying LHR -> IST
    public void test2() throws CityNotFoundException {

        FlightRequest request = new FlightRequest();
        request.setAdults(2);
        request.setChildren(1);
        request.setInfants(1);
        request.setOriginCity("London");
        request.setDestinyCity("Istanbul");
        request.setDepartureDate(LocalDate.now().plus(15, ChronoUnit.DAYS));

        List<FlightResult> result = flightService.searchForFlight(request);

        Assert.assertTrue(result.contains(new FlightResult("TK8891", 806.0)));
        Assert.assertTrue(result.contains(new FlightResult("LH1085", 481.19)));
    }

    //1 adult, 2 children, 2 days to the departure date, flying BCN -> MAD
    @Test
    public void test3() throws CityNotFoundException {

        FlightRequest request = new FlightRequest();
        request.setAdults(1);
        request.setChildren(2);
        request.setOriginCity("Barcelona");
        request.setDestinyCity("Madrid");
        request.setDepartureDate(LocalDate.now().plus(2, ChronoUnit.DAYS));

        List<FlightResult> result = flightService.searchForFlight(request);

        Assert.assertTrue(result.contains(new FlightResult("IB2171", 909.09)));
        Assert.assertTrue(result.contains(new FlightResult("LH5496", 1028.43)));
    }

    //CDG -> FRA
    @Test
    public void test4() throws CityNotFoundException {

        FlightRequest request = new FlightRequest();
        request.setAdults(1);
        request.setChildren(2);
        request.setOriginCity("Paris");
        request.setDestinyCity("Frakfurt");
        request.setDepartureDate(LocalDate.now().plus(2, ChronoUnit.DAYS));

        List<FlightResult> result = flightService.searchForFlight(request);

        Assert.assertTrue(result.isEmpty());
    }
}

