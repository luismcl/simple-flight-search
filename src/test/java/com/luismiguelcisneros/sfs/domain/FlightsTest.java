package com.luismiguelcisneros.sfs.domain;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FlightsTest {


    private final String cvsLine = "CPH,FRA,IB2818,186";
    @Test
    public void shouldBuildANewFlightFromArray() {

        //Expectations
        Flight flight = new Flight(cvsLine.split(","));

        //origin,destination,airline,base price (€)
        assertEquals("CPH", flight.getOrigin());
        assertEquals("FRA", flight.getDestination());
        assertEquals("IB2818", flight.getCode());
        assertEquals(186.0, flight.getBasePrice(), 0);
        assertEquals("IB", flight.getAirline());
    }
}
