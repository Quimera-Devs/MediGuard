package com.programabit.mediguard;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.programabit.mediguard.rest.GuardDto;

import java.util.List;

public class AvaibleGuardsListAdapter extends ListAdapter<GuardDto, AvaibleGuardsViewHolder> {

    public AvaibleGuardsListAdapter(@NonNull DiffUtil.ItemCallback<GuardDto> diffCallBack) {
        super(diffCallBack);
    }

    @NonNull
    @Override
    public AvaibleGuardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AvaibleGuardsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AvaibleGuardsViewHolder holder, int position) {
        GuardDto currentGuard = getItem(position);
        holder.bind(currentGuard.getCentroSalud(),currentGuard.getFecha(),currentGuard.getTurno());
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
