package com.agm.watchbus.Models;

import java.util.ArrayList;

public class Horario {
    public ArrayList<String> inicio;
    public ArrayList<String> fin;

    public Horario() {
        inicio =  new ArrayList<>();
        fin =  new ArrayList<>();
    }

    public ArrayList<String> getInicio() {
        return inicio;
    }

    public void setInicio(ArrayList<String> inicio) {
        this.inicio = inicio;
    }

    public ArrayList<String> getFin() {
        return fin;
    }

    public void setFin(ArrayList<String> fin) {
        this.fin = fin;
    }
}
