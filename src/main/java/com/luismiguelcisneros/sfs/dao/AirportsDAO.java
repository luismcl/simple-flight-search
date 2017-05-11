package com.luismiguelcisneros.sfs.dao;

import com.luismiguelcisneros.sfs.domain.Airport;

import java.io.IOException;
import java.util.Optional;

public class AirportsDAO extends AbstractDAO<Airport>  {

    public AirportsDAO(String csvFile) throws IOException {
        super(csvFile);
    }

    @Override
    protected Airport buildObject(String line) {
        return new Airport(line.split(","));
    }

    public Optional<Airport> queryByCity(String city){
        return data.stream()
                .filter(f->f.getCity().equals(city)).findFirst();
    }
}
