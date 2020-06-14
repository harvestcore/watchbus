package com.agm.watchbus.Models;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableRecyclerView;

import com.agm.watchbus.BusManager;
import com.agm.watchbus.LinesFragment;
import com.agm.watchbus.R;

import java.util.ArrayList;

public class BusAdapter extends WearableRecyclerView.Adapter<BusAdapter.Holder>  {
    Context context;
    LayoutInflater inflater;
    ArrayList<Autobus> data;

    Function onBusClick;

    public BusAdapter(Context context, ArrayList<Autobus> data) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;

        if (this.data.size() == 0) {
            this.data.add(new Autobus("#", null));
        }
    }

    public void setOnBusClick(Function onBusClick) {
        this.onBusClick = onBusClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.bus_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final Autobus bus = this.data.get(position);
        boolean isFav = BusManager.getInstance().isFav(bus.numero);

        if (bus.numero.equals("#")) {
            holder.numero.setText("No hay buses");
            holder.favToggleIcon.setVisibility(View.GONE);
            holder.busIcon.setImageResource(R.drawable.sad);
        } else {
            holder.numero.setText("Línea " + bus.numero);
            holder.favToggleIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BusManager.getInstance().toggleFavBus(bus.numero);
                    boolean isFav = BusManager.getInstance().isFav(bus.numero);
                    holder.favToggleIcon.setImageResource(isFav ? R.drawable.fav : R.drawable.emptyfav);
                }
            });

            holder.favToggleIcon.setImageResource(isFav ? R.drawable.fav : R.drawable.emptyfav);

            holder.busRowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBusClick != null) {
                        onBusClick.apply(bus);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ConstraintLayout busRowLayout;
        TextView numero;
        ImageView favToggleIcon;
        ImageView busIcon;

        public Holder(@NonNull View itemView) {
            super(itemView);
            numero = itemView.findViewById(R.id.numero);
            favToggleIcon = itemView.findViewById(R.id.favToggleIcon);
            busIcon = itemView.findViewById(R.id.busIcon);
            busRowLayout = itemView.findViewById(R.id.busRowLayout);
        }
    }
}
