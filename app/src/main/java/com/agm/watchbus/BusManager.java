package com.agm.watchbus;

import android.content.res.XmlResourceParser;

import com.agm.watchbus.Models.Autobus;
import com.agm.watchbus.Models.Horario;
import com.agm.watchbus.Models.Point;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class BusManager {
    private static BusManager busManager;

    private ArrayList<Autobus> autobuses;
    private Horario horario;

    private BusManager() {
        horario = new Horario();
        autobuses = new ArrayList<>();
    }

    public static BusManager getInstance() {
        if (busManager == null) {
            busManager = new BusManager();
        }

        return busManager;
    }

    public ArrayList<Autobus> getAutobuses() {
        return autobuses;
    }

    public ArrayList<String> getHorarioIda() {
        return horario.inicio;
    }

    public ArrayList<String> getHorarioVuelta() {
        return horario.fin;
    }

    public void loadHorarios(XmlResourceParser parser) {
        ArrayList<String> inicio = new ArrayList<>();
        ArrayList<String> fin = new ArrayList<>();

        boolean addingIni = false;
        boolean addingFin = false;

        int eventType = -1;
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                String pointValue = parser.getName();
                if (pointValue.equals("inicio") && !addingIni) {
                    addingIni = true;
                    addingFin = false;
                }

                if (pointValue.equals("fin") && !addingFin) {
                    addingFin = true;
                    addingIni = false;
                }

                if (pointValue.equals("hora") && addingIni) {
                    inicio.add(parser.getAttributeValue(null, "time"));
                }

                if (pointValue.equals("hora") && addingFin) {
                    fin.add(parser.getAttributeValue(null, "time"));
                }
            }

            try {
                eventType = parser.next();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }

        this.horario.setInicio(new ArrayList<>(inicio));
        this.horario.setFin(new ArrayList<>(fin));
    }

    public void loadAutobuses(XmlResourceParser parser) {
        ArrayList<Point> currentRoute = new ArrayList<>();
        String currentNum = "";


        int eventType = -1;
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                String pointValue = parser.getName();
                if (pointValue.equals("bus")) {
                    if (currentRoute.size() > 0 && !currentNum.equals("")) {
                        this.autobuses.add(new Autobus(currentNum, new ArrayList<>(currentRoute)));
                        currentNum = "";
                        currentRoute.clear();
                    }
                } else if (pointValue.equals("numero")) {
                    currentNum = parser.getAttributeValue(null, "value");
                } else if (pointValue.equals("punto")) {
                    currentRoute.add(new Point(parser.getAttributeValue(null, "nombre")));
                }
            }

            try {
                eventType = parser.next();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }

        // Add last route
        this.autobuses.add(new Autobus(currentNum, new ArrayList<>(currentRoute)));

        System.out.println("a");
    }
}
