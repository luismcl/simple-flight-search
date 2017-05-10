package com.luismiguelcisneros.sfs.services;


import com.luismiguelcisneros.sfs.dao.AirlinesDAO;
import com.luismiguelcisneros.sfs.dao.AirportsDAO;
import com.luismiguelcisneros.sfs.dao.FlightsDAO;
import com.luismiguelcisneros.sfs.domain.*;
import com.luismiguelcisneros.sfs.exceptions.CityNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FlightServiceTest {

    @Test
    public void shouldCalculateTheFinalPriceForAdult(){

        AirlinesDAO airlinesDAO = Mockito.mock(AirlinesDAO.class);
        FlightService flightService = new FlightService(airlinesDAO, null, null);
        Flight flight = new Flight("AMS,FRA,TK2372,197".split(","));
        LocalDate date = LocalDate.now().plus(31, ChronoUnit.DAYS);

        //Experimentation
        Double price = flightService.getPrice(flight, date, PassengerType.ADULT);

        //Expectations
        Assert.assertEquals(157.6, price, 0.001);
    }

    @Test
    public void shouldCalculateTheFinalPriceForChildWith15Days(){

        AirlinesDAO airlinesDAO = Mockito.mock(AirlinesDAO.class);
        FlightService flightService = new FlightService(airlinesDAO, null, null);
        Flight flight = new Flight("LHR,IST,TK8891,250".split(","));
        LocalDate date = LocalDate.now().plus(15, ChronoUnit.DAYS);

        //Experimentation
        Double price = flightService.getPrice(flight, date, PassengerType.CHILD);

        //Expectations
        Assert.assertEquals(201, price, 0.001);
    }

    @Test
    public void shouldCalculateTheFinalPriceForInfant(){

        AirlinesDAO airlinesDAO = Mockito.mock(AirlinesDAO.class);
        Airline tk = new Airline("TK,Turkish Airlines,5".split(","));
        Mockito.when(airlinesDAO.queryByCode("TK")).thenReturn(Optional.of(tk));

        FlightService flightService = new FlightService(airlinesDAO, null, null);
        Flight flight = new Flight("LHR,IST,TK8891,250".split(","));
        LocalDate date = LocalDate.now().plus(15, ChronoUnit.DAYS);

        //Experimentation
        Double price = flightService.getPrice(flight, date, PassengerType.INFANT);

        //Expectations
        Assert.assertEquals(5, price, 0.001);
    }

    @Test
    public void shouldFindFlights() throws CityNotFoundException {

        AirportsDAO airportsDAO = Mockito.mock(AirportsDAO.class);

        Airport originAirport = new Airport(new String[]{"MAD","Madrid"});
        Mockito.when(airportsDAO.queryByCity("Madrid")).thenReturn(Optional.of(originAirport));

        Airport destinyAirport = new Airport(new String[]{"BCN","Barcelona"});
        Mockito.when(airportsDAO.queryByCity("Barcelona")).thenReturn(Optional.of(destinyAirport));

        FlightsDAO flightsDAO = Mockito.mock(FlightsDAO.class);
        List<Flight> flightsList = Collections.singletonList(Mockito.mock(Flight.class));
        Mockito.when(flightsDAO.queryByOriginAndDestination("MAD", "BCN")).thenReturn(flightsList);

        FlightService flightService = new FlightService(null, airportsDAO, flightsDAO);
        FlightRequest request = new FlightRequest();
        request.setOriginCity("Madrid");
        request.setDestinyCity("Barcelona");


        //Experimentation
        List<Flight> result = flightService.queryFlights(request);

        //Espectations
        Assert.assertEquals(1, result.size());
    }

    @Test(expected = CityNotFoundException.class)
    public void shouldThrowsAnErrorWhenFindFlightsWithInvalidOriginCity() throws CityNotFoundException {

        AirportsDAO airportsDAO = Mockito.mock(AirportsDAO.class);

        Airport originAirport = new Airport(new String[]{"MAD","Madrid"});
        Mockito.when(airportsDAO.queryByCity("Madrid")).thenReturn(Optional.of(originAirport));

        FlightService flightService = new FlightService(null, airportsDAO, null);
        FlightRequest request = new FlightRequest();
        request.setOriginCity("Ciudad Real");
        request.setDestinyCity("Barcelona");

        //Experimentation
        List<Flight> result = flightService.queryFlights(request);
    }

    @Test(expected = CityNotFoundException.class)
    public void shouldThrowsAnErrorWhenFindFlightsWithInvalidDestinyCity() throws CityNotFoundException {

        AirportsDAO airportsDAO = Mockito.mock(AirportsDAO.class);
        Airport originAirport = new Airport(new String[]{"MAD","Madrid"});
        Mockito.when(airportsDAO.queryByCity("Madrid")).thenReturn(Optional.of(originAirport));

        Mockito.when(airportsDAO.queryByCity("Ciudad Real")).thenReturn(Optional.empty());

        FlightService flightService = new FlightService(null, airportsDAO, null);
        FlightRequest request = new FlightRequest();
        request.setOriginCity("Madrid");
        request.setDestinyCity("Ciudad Real");

        //Experimentation
        List<Flight> result = flightService.queryFlights(request);
    }

    @Test
    public void shouldCalculateThePriceFor2Adults(){
        FlightService flightService = new FlightService(null, null, null);

        Flight flight = new Flight("AMS,FRA,TK2372,197".split(","));
        FlightRequest request = new FlightRequest();
        request.setAdults(2);
        request.setDepartureDate(LocalDate.now().plus(31, ChronoUnit.DAYS));

        //Experimentation
        FlightResult result = flightService.calculateTotalPrice(flight, request);

        //Expectations
        Assert.assertEquals(315.2, result.getTotalPrice(), 0.001);
        Assert.assertEquals("TK2372", result.getFlightCode());
    }

    /*
     * 2 adults, 1 child, 1 infant, 15 days to the departure date
     * TK8891, 806 € (2 * (120% of 250) + 67% of (120% of 250) + 5)
     */
    @Test
    public void shouldCalculateThePriceFor2Adults1Child1Infant(){
        AirlinesDAO airlinesDAO = Mockito.mock(AirlinesDAO.class);
        Airline tk = new Airline("TK,Turkish Airlines,5".split(","));
        Mockito.when(airlinesDAO.queryByCode("TK")).thenReturn(Optional.of(tk));

        FlightService flightService = new FlightService(airlinesDAO, null, null);

        Flight flight = new Flight("LHR,IST,TK8891,250".split(","));
        FlightRequest request = new FlightRequest();
        request.setAdults(2);
        request.setChildren(1);
        request.setInfants(1);

        request.setDepartureDate(LocalDate.now().plus(15, ChronoUnit.DAYS));

        //Experimentation
        FlightResult result = flightService.calculateTotalPrice(flight, request);

        //Expectations
        Assert.assertEquals(806, result.getTotalPrice(), 0.001);
        Assert.assertEquals("TK8891", result.getFlightCode());
    }



}



