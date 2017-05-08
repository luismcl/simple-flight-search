package com.luismiguelcisneros.sfs.dao;

import static org.junit.Assert.*;
import org.junit.Test;

public class FlightsDAOTest {

    @Test
    public void shouldCreateANewDAOObjectFromACVSFile() {

        String cvsFile = getClass().getResource("/Flights.csv").getFile();

        //Experimentation
        FlightsDAO flightsDAO = new FlightsDAO(cvsFile);

        //Expectations
        assertEquals("Not expected rows count", 89, flightsDAO.getFlights().size());
       }
}
