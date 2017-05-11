package com.luismiguelcisneros.sfs.dao;

import com.luismiguelcisneros.sfs.domain.Airport;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class AirportsDAOTest {

    private final String csvFile = getClass().getResource("/Airports.csv").getFile();

    @Test
    public void shouldCreateANewDAOObjectFromACVSFile() throws IOException {

        //Experimentation
        AirportsDAO airportsDAO = new AirportsDAO(csvFile);

        //Expectations
        assertEquals("Not expected rows count", 9, airportsDAO.getData().size());
    }

    @Test
    public void shouldFindAnAirportFromCity() throws IOException {
        AirportsDAO airportsDAO = new AirportsDAO(csvFile);

        //Experimentation
        Optional<Airport> result = airportsDAO.queryByCity("Madrid");

        //Expectations
        assertNotNull(result);
        assertNotEquals(Optional.empty(), result);

        Airport airport = result.get();

        assertEquals("Madrid", airport.getCity());
        assertEquals("MAD", airport.getIataCode());
    }

    @Test
    public void shouldFindAAirportFromCityButReturnEmpty() throws IOException {
        AirportsDAO airportsDAO = new AirportsDAO(csvFile);

        //Experimentation
        Optional<Airport> result = airportsDAO.queryByCity("Ciudad Real");

        //Expectations
        assertNotNull(result);
        assertEquals(Optional.empty(), result);
    }

}
