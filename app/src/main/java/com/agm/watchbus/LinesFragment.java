package com.agm.watchbus;

import android.os.Bundle;

import android.app.Fragment;

import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agm.watchbus.Models.Autobus;
import com.agm.watchbus.Models.BusAdapter;

public class LinesFragment extends Fragment {
    RecyclerView recyclerView;

    public static LinesFragment newInstance() {
        LinesFragment fragment = new LinesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.lineas, container, false);

        // Main recycler view
        recyclerView = root.findViewById(R.id.recycler_launcher_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BusAdapter busAdapter = new BusAdapter(getContext(), BusManager.getInstance().getAutobuses());
        busAdapter.setOnBusClick(new Function() {
            @Override
            public Object apply(final Object input) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinesFragment.this.busDetail((Autobus) input);
                    }
                });
                return null;
            }
        });
        recyclerView.setAdapter(busAdapter);

        return root;
    }

    public void busDetail(Autobus bus) {
        DetailFragment detail = new DetailFragment();
        detail.setBus(bus);
        getFragmentManager().beginTransaction().replace(R.id.container, detail).commit();
    }
}
