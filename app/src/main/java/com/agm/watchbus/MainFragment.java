package com.agm.watchbus;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainFragment extends Fragment {
    ImageView favButton;
    ImageView routesButton;

    public MainFragment() {}

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        routesButton = root.findViewById(R.id.routesButton);
        routesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment lines = new LinesFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, lines).commit();
            }
        });

        favButton = root.findViewById(R.id.favButton);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment favs = new FavsFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, favs).commit();
            }
        });

        return root;
    }
}