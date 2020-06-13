package com.agm.watchbus;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

public class MainActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enables Always-on
        setAmbientEnabled();

        // Parse buses data
        XmlResourceParser parserHorarios = getResources().getXml(R.xml.horarios);
        XmlResourceParser parserBuses = getResources().getXml(R.xml.buses);
        BusManager.getInstance().loadHorarios(parserHorarios);
        BusManager.getInstance().loadAutobuses(parserBuses);

    }
}
