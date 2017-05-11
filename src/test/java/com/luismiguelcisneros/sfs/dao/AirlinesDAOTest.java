package com.luismiguelcisneros.sfs.dao;

import com.luismiguelcisneros.sfs.domain.Airline;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class AirlinesDAOTest {

    private final String csvFile = getClass().getResource("/Airlines.csv").getFile();

    @Test
    public void shouldCreateANewDAOObjectFromACVSFile() throws IOException {

        //Experimentation
        AirlinesDAO airlinesDAO = new AirlinesDAO(csvFile);

        //Expectations
        assertEquals("Not expected rows count", 7, airlinesDAO.getData().size());
    }

    @Test
    public void shouldFindAAirlineFromCode() throws IOException {
        AirlinesDAO airlinesDAO = new AirlinesDAO(csvFile);

        //Experimentation
        Optional<Airline> result = airlinesDAO.queryByCode("IB");

        //Expectations
        assertNotNull(result);
        assertNotEquals(Optional.empty(), result);

        Airline airline = result.get();

        assertEquals("IB", airline.getIATACode());
        assertEquals("Iberia", airline.getName());
        assertEquals(10.0, airline.getInfantPrice(), 0);
    }

    @Test
    public void shouldFindAAirlineFromCodeButReturnEmpty() throws IOException {
        AirlinesDAO airlinesDAO = new AirlinesDAO(csvFile);

        //Experimentation
        Optional<Airline> result = airlinesDAO.queryByCode("CV");

        //Expectations
        assertNotNull(result);
        assertEquals(Optional.empty(), result);
    }
}
