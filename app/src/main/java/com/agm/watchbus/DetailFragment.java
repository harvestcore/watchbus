package com.agm.watchbus;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agm.watchbus.Models.Autobus;
import com.agm.watchbus.Models.LineAdapter;


public class DetailFragment extends Fragment {
    RecyclerView detailRecyclerView;
    Autobus bus;

    public DetailFragment() {}

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    public void setBus(Autobus bus) {
        this.bus = bus;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_detail, container, false);

        detailRecyclerView = root.findViewById(R.id.detailRecyclerView);
        if (this.bus != null) {
            detailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            LineAdapter lineAdapter = new LineAdapter(getContext(), bus.linea);
            detailRecyclerView.setAdapter(lineAdapter);
        }

        return root;
    }
}