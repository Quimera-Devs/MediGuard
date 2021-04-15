package com.programabit.mediguard.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.programabit.mediguard.R;

public class AvaibleGuardsViewHolder extends RecyclerView.ViewHolder {

    private final TextView guardItemDateView;
    private final TextView guardItemCenterView;
    private final TextView guardItemShiftView;


    private AvaibleGuardsViewHolder(View itemView) {
        super(itemView);
        guardItemCenterView = itemView.findViewById(R.id.card_centro_salud_id);
        guardItemDateView = itemView.findViewById(R.id.card_fecha_id);
        guardItemShiftView = itemView.findViewById(R.id.card_turno_id);
    }

    public void bind(String center, String date, String shift) {
        guardItemCenterView.setText(center);
        guardItemDateView.setText(date);
        guardItemShiftView.setText(shift);
    }

    static AvaibleGuardsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guard_item, parent, false);
        return new AvaibleGuardsViewHolder(view);
    }
}
