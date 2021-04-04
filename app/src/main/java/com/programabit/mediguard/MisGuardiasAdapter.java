package com.programabit.mediguard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MisGuardiasAdapter extends RecyclerView.Adapter<MisGuardiasAdapter.MyViewHolder>{

    private Context context;
    private List<MisGuardiasModel> misGuardiasModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView  centroSalud, fecha, turno;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            centroSalud = itemView.findViewById(R.id.card_centro_salud_id);
            fecha = itemView.findViewById(R.id.card_fecha_id);
            turno = itemView.findViewById(R.id.card_turno_id);

        }
    }

    public MisGuardiasAdapter(Context context, List<MisGuardiasModel> misGuardiasModelList) {
        this.context = context;
        this.misGuardiasModelList = misGuardiasModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mis_guardias_card,parent);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MisGuardiasModel guardiasModel = misGuardiasModelList.get(position);
        holder.centroSalud.setText(MisGuardiasModel.getCentroSalud());
        holder.fecha.setText(MisGuardiasModel.getFecha());
        holder.turno.setText(MisGuardiasModel.getTurno());
    }

    @Override
    public int getItemCount() {
        return misGuardiasModelList.size();
    }
}
