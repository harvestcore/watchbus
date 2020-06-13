package com.agm.watchbus.Models;

import java.util.ArrayList;

public class Autobus {
    public String numero;
    public ArrayList<Point> linea;

    public Autobus(String numero, ArrayList<Point> linea) {
        this.numero = numero;
        this.linea = linea;
    }
}
