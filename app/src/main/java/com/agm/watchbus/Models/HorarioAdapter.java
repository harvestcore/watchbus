package com.agm.watchbus.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableRecyclerView;

import com.agm.watchbus.R;

import java.util.ArrayList;

public class HorarioAdapter extends WearableRecyclerView.Adapter<HorarioAdapter.Holder>  {
    Context context;
    LayoutInflater inflater;
    Horario horario;

    public HorarioAdapter(Context context, Horario horario) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.horario = horario;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.timetable_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        String start = this.horario.inicio.get(position);
        String end = this.horario.fin.get(position);

        holder.timetableText.setText(start + " - " + end);
    }

    @Override
    public int getItemCount() {
        return this.horario.inicio.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView timetableText;

        public Holder(@NonNull View itemView) {
            super(itemView);
            timetableText = itemView.findViewById(R.id.timetableText);
        }
    }
}
