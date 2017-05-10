package com.luismiguelcisneros.sfs.dao;

import com.luismiguelcisneros.sfs.domain.Flight;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FlightsDAO extends AbstractDAO<Flight> {

    public FlightsDAO(String csvFile) throws IOException {
        super(csvFile);
    }

    @Override
    protected Flight buildObject(String flightLine) {
        return new Flight(flightLine.split(","));
    }

    public List<Flight> queryByOriginAndDestination(String origin, String destination){
        return data.stream()
                .filter(f->f.getOrigin().equals(origin) && f.getDestination().equals(destination))
                .collect(Collectors.toList());
    }
}
