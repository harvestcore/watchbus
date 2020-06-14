package com.agm.watchbus;

import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

import android.app.Fragment;
import android.view.DragEvent;
import android.view.View;

import androidx.wear.widget.drawer.WearableNavigationDrawerView;

import com.agm.watchbus.Models.Horario;
import com.agm.watchbus.Models.NavigationTab;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends WearableActivity {
    WearableNavigationDrawerView top_navigation_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enables Always-on
        setAmbientEnabled();

        // Set view
        BusManager.getInstance().setView(findViewById(android.R.id.content).getRootView());

        // Restore favs
        try {
            BusManager.getInstance().restoreFavs();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse buses data
        XmlResourceParser parserHorarios = getResources().getXml(R.xml.horarios);
        XmlResourceParser parserBuses = getResources().getXml(R.xml.buses);
        BusManager.getInstance().loadHorarios(parserHorarios);
        BusManager.getInstance().loadAutobuses(parserBuses);


        top_navigation_drawer = findViewById(R.id.top_navigation_drawer);
        NavigationAdapter adapter = new NavigationAdapter(BusManager.getInstance().getNavigationTabs());
        top_navigation_drawer.setAdapter(adapter);
        top_navigation_drawer.addOnItemSelectedListener(new WearableNavigationDrawerView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int pos) {
                switchFragment(BusManager.getInstance().getNavigationTabs().get(pos).fragment);
            }
        });

        switchFragment(FragmentType.MAIN);
    }

    private void switchFragment(FragmentType fragment) {
        switch (fragment) {
            case MAIN:
                Fragment main = new MainFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, main).commit();
                break;

            case LINES:
                Fragment lines = new LinesFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, lines).commit();
                break;

            case FAVS:
                Fragment favs = new FavsFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, favs).commit();
                break;

            case TIMETABLE:
                Fragment timetable = new HorarioFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, timetable).commit();
                break;
        }
    }

    private class NavigationAdapter extends WearableNavigationDrawerView.WearableNavigationDrawerAdapter {
        private ArrayList<NavigationTab> tabs;

        public NavigationAdapter(ArrayList<NavigationTab> tabs) {
            this.tabs = tabs;
        }

        @Override
        public CharSequence getItemText(int pos) {
            return this.tabs.get(pos).title;
        }

        @Override
        public Drawable getItemDrawable(int pos) {
            return getDrawable(this.tabs.get(pos).icon);
        }

        @Override
        public int getCount() {
            return tabs.size();
        }


    }
}
