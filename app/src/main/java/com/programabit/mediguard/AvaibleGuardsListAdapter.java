package com.programabit.mediguard;

import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.programabit.mediguard.rest.GuardDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AvaibleGuardsListAdapter extends ListAdapter<GuardDto, AvaibleGuardsViewHolder> {

    private AvaibleGuardsListAdapter.onItemClickListener listener;

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

        //tenes que crear el image button
        ImageButton assignGuard = holder.itemView.findViewById(R.id.assign_Guard);
        assingGuard.setOnClickListener(view ->{
            if(listener!=null){
                try {
                    listener.onItemAssign(guardActual);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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
    public interface onItemClickListener{
        void onItemAssign(GuardDto myGuard) throws ExecutionException, InterruptedException;
    }

    public void setOnItemClickListener(AvaibleGuardsListAdapter.onItemClickListener listener) {
        this.listener = listener;
    }
}
