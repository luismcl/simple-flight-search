package com.luismiguelcisneros.sfs.dao;
import com.luismiguelcisneros.sfs.domain.Flight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FlightsDAO {

    private List<Flight>flights;

    public FlightsDAO(String cvsFile) {
        try (Stream<String> stream = Files.lines(Paths.get(cvsFile))) {

            List<Flight> flights = stream.map(flightLine->
                    new Flight(flightLine.split(",")))
                    .collect(Collectors.toList());

            this.flights = Collections.unmodifiableList(flights);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
