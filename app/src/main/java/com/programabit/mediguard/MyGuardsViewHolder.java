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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_guards, parent, false);
        return new MyGuardsViewHolder(view);
    }
}


/*
    private Context context;
    private List<MisGuardiasModel> misGuardiasModelList;


    public MisGuardiasViewHolder(Context context, List<MisGuardiasModel> misGuardiasModelList) {
        this.context = context;
        this.misGuardiasModelList = misGuardiasModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mis_guardias_card,parent);
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

 */

