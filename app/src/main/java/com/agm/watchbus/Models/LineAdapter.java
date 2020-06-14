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

public class LineAdapter extends WearableRecyclerView.Adapter<LineAdapter.Holder>  {
    Context context;
    LayoutInflater inflater;
    ArrayList<Point> data;

    public LineAdapter(Context context, ArrayList<Point> data) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.point_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final Point p = this.data.get(position);

        holder.busStopText.setText(p.name);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView busStopText;

        public Holder(@NonNull View itemView) {
            super(itemView);
            busStopText = itemView.findViewById(R.id.busStopText);
        }
    }
}
