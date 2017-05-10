package com.luismiguelcisneros.sfs.dao;

import com.luismiguelcisneros.sfs.domain.Airline;

import java.io.IOException;
import java.util.Optional;


public class AirlinesDAO extends AbstractDAO<Airline>  {
    public AirlinesDAO(String csvFile) throws IOException {
        super(csvFile);
    }

    @Override
    protected Airline buildObject(String line) {
        return new Airline(line.split(","));
    }

    public Optional<Airline> queryByCode(String code){
        return data.stream()
                .filter(f->f.getIATACode().equals(code)).findFirst();
    }
}
