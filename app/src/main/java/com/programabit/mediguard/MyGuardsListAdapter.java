package com.programabit.mediguard;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.programabit.mediguard.rest.GuardDto;

public class MyGuardsListAdapter extends ListAdapter<GuardDto, MyGuardsViewHolder> {

    public MyGuardsListAdapter(@NonNull DiffUtil.ItemCallback<GuardDto> diffCallback){
        super(diffCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGuardsViewHolder holder, int position) {
        GuardDto guardActual = getItem(position);
        holder.bind(guardActual.getCentroSalud(),guardActual.getFecha(),guardActual.getTurno());
    }

    @NonNull
    @Override
    public MyGuardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MyGuardsViewHolder.create(parent);
    }

    static class guardDiff extends DiffUtil.ItemCallback<GuardDto>{
        @Override
        public boolean areItemsTheSame(@NonNull GuardDto oldItem, @NonNull GuardDto newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull GuardDto oldItem, @NonNull GuardDto newItem) {
            return oldItem.getCentroSalud().equals(newItem.getCentroSalud()) &&
                    oldItem.getFecha().equals(newItem.getFecha()) &&
                    oldItem.getTurno().equals(newItem.getTurno());
        }
    }
}
