package com.programabit.mediguard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyGuardsViewHolder extends RecyclerView.ViewHolder {

    private final TextView centroSaludTextView, fechaTextView, turnoTextView;

    private MyGuardsViewHolder(View itemView) {
        super(itemView);
        centroSaludTextView = itemView.findViewById(R.id.card_centro_salud_id);
        fechaTextView = itemView.findViewById(R.id.card_fecha_id);
        turnoTextView = itemView.findViewById(R.id.card_turno_id);
    }

    public void bind(String centro, String fecha, String turno) {
        centroSaludTextView.setText(centro);
        fechaTextView.setText(fecha);
        turnoTextView.setText(turno);
    }

    static MyGuardsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_guard_card, parent, false);
        return new MyGuardsViewHolder(view);
    }
}

