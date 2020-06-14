package com.agm.watchbus;

import android.os.Bundle;

import android.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agm.watchbus.Models.HorarioAdapter;

public class HorarioFragment extends Fragment {
    RecyclerView recyclerView;

    public static HorarioFragment newInstance() {
        HorarioFragment fragment = new HorarioFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_horario, container, false);

        // Main recycler view
        recyclerView = root.findViewById(R.id.timetableRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        HorarioAdapter horarioAdapter = new HorarioAdapter(getContext(), BusManager.getInstance().getHorario());
        recyclerView.setAdapter(horarioAdapter);

        return root;
    }
}
