package com.luismiguelcisneros.sfs.dao;

import static org.junit.Assert.*;

import com.luismiguelcisneros.sfs.domain.Flight;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class FlightsDAOTest {

    private final String csvFile = getClass().getResource("/Flights.csv").getFile();

    @Test
    public void shouldCreateANewDAOObjectFromACVSFile() throws IOException {

        //Experimentation
        FlightsDAO flightsDAO = new FlightsDAO(csvFile);

        //Expectations
        assertEquals("Not expected rows count", 89, flightsDAO.getData().size());
    }

    @Test
    public void shouldFindAFlightAndReturnJustOne() throws IOException {
        FlightsDAO flightsDAO = new FlightsDAO(csvFile);

        //Experimentation
        List<Flight> results = flightsDAO.queryByOriginAndDestination("FCO", "MAD");

        //Expectations
        assertNotNull(results);
        assertEquals(1, results.size());

        Flight flight = results.get(0);

        assertEquals("FCO", flight.getOrigin());
        assertEquals("MAD", flight.getDestination());
        assertEquals("BA1164", flight.getCode());
        assertEquals(118.0, flight.getBasePrice(), 0);
    }

    @Test
    public void shouldFindAFlightAndReturnMultiple() throws IOException {
        FlightsDAO flightsDAO = new FlightsDAO(csvFile);

        //Experimentation
        List<Flight> results = flightsDAO.queryByOriginAndDestination("AMS", "FRA");

        //Expectations
        assertNotNull(results);
        assertEquals(3, results.size());
    }

    @Test
    public void shouldFindAFlightAndReturnEmptyList() throws IOException {
        FlightsDAO flightsDAO = new FlightsDAO(csvFile);

        //Experimentation
        List<Flight> results = flightsDAO.queryByOriginAndDestination("CDG", "FRA");

        //Expectations
        assertNotNull(results);
        assertEquals(0, results.size());
    }
}
