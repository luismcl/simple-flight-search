package com.luismiguelcisneros.sfs.exceptions;


public class CityNotFoundException  extends Exception {
    public CityNotFoundException(String city) {
        super(String.format("City '%s' Not Found", city));
    }
}
