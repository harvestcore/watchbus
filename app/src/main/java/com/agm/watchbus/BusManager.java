package com.agm.watchbus;

import android.content.res.XmlResourceParser;
import android.view.View;

import com.agm.watchbus.Models.Autobus;
import com.agm.watchbus.Models.Horario;
import com.agm.watchbus.Models.NavigationTab;
import com.agm.watchbus.Models.Point;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class BusManager {
    private static BusManager busManager;

    private static final String FAVS_FILE_NAME = "favs.txt";

    private ArrayList<NavigationTab> navigationTabs;
    private ArrayList<Autobus> autobuses;
    private ArrayList<String> favBuses;
    private Horario horario;
    private View view;

    private BusManager() {
        horario = new Horario();
        autobuses = new ArrayList<>();
        favBuses = new ArrayList<>();
        navigationTabs = new ArrayList<>();

        // Initialize navigation tabs
        navigationTabs.add(new NavigationTab("Home", R.drawable.home, FragmentType.MAIN));
        navigationTabs.add(new NavigationTab("Horarios", R.drawable.timetable, FragmentType.TIMETABLE));
        navigationTabs.add(new NavigationTab("Buses", R.drawable.autobus, FragmentType.LINES));
        navigationTabs.add(new NavigationTab("Favoritos", R.drawable.fav, FragmentType.FAVS));
    }

    public static BusManager getInstance() {
        if (busManager == null) {
            busManager = new BusManager();
        }

        return busManager;
    }

    public void setView(View view) {
        this.view = view;
    }

    public ArrayList<NavigationTab> getNavigationTabs() {
        return navigationTabs;
    }

    public ArrayList<Autobus> getFavBuses() {
        ArrayList<Autobus> aux = new ArrayList<>();
        for (Autobus bus: this.autobuses) {
            if (this.favBuses.contains(bus.numero)) {
                aux.add(bus);
            }
        }
        return aux;
    }

    public ArrayList<Autobus> getAutobuses() {
        return autobuses;
    }

    public Horario getHorario() {
        return horario;
    }

    public void loadHorarios(XmlResourceParser parser) {
        if (horario.getInicio().size() == 0) {
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
    }

    public void loadAutobuses(XmlResourceParser parser) {
        if (this.autobuses.size() == 0) {
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
        }
    }

    public void toggleFavBus(String num) {
        if (this.favBuses.contains(num)) {
            this.favBuses.remove(num);
        } else {
            this.favBuses.add(num);
        }

        try {
            saveFavs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFav(String num) {
        return this.favBuses.contains(num);
    }

    public void saveFavs() throws IOException {
        FileOutputStream fos = null;
        try {
            fos = view.getContext().openFileOutput(FAVS_FILE_NAME, view.getContext().MODE_PRIVATE);
            String toWrite = "";

            for (String bus: this.favBuses) {
                toWrite += bus + "#";
            }

            fos.write(toWrite.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    public void restoreFavs() throws IOException {
        FileInputStream fis = null;
        String text = "";

        try {
            fis = view.getContext().openFileInput(FAVS_FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }

            text = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        String[] c = text.split("#");

        this.favBuses = new ArrayList<>(Arrays.asList(c));
    }
}
