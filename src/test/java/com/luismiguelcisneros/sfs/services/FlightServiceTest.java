package com.luismiguelcisneros.sfs.services;


import com.luismiguelcisneros.sfs.dao.AirlinesDAO;
import com.luismiguelcisneros.sfs.domain.Airline;
import com.luismiguelcisneros.sfs.domain.Flight;
import com.luismiguelcisneros.sfs.domain.PassengerType;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class FlightServiceTest {

    @Test
    public void shouldCalculateTheFinalPriceForAdult(){

        AirlinesDAO airlinesDAO = Mockito.mock(AirlinesDAO.class);
        FlightService flightService = new FlightService(airlinesDAO);
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
        FlightService flightService = new FlightService(airlinesDAO);
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

        FlightService flightService = new FlightService(airlinesDAO);
        Flight flight = new Flight("LHR,IST,TK8891,250".split(","));
        LocalDate date = LocalDate.now().plus(15, ChronoUnit.DAYS);

        //Experimentation
        Double price = flightService.getPrice(flight, date, PassengerType.INFANT);

        //Expectations
        Assert.assertEquals(5, price, 0.001);
    }
}



