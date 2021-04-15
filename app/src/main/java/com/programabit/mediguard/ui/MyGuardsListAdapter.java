package com.programabit.mediguard.ui;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.programabit.mediguard.R;
import com.programabit.mediguard.domain.GuardDto;

import java.util.concurrent.ExecutionException;

public class MyGuardsListAdapter extends ListAdapter<GuardDto, MyGuardsViewHolder> {

    private onItemClickListener listener;

    public MyGuardsListAdapter(@NonNull DiffUtil.ItemCallback<GuardDto> diffCallback){
        super(diffCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGuardsViewHolder holder, int position) {
        Log.i("MyGuardsListAdapter","on bind view holder");
        GuardDto guardActual = getItem(position);
        holder.bind(guardActual.getCentroSalud(),guardActual.getFecha(),guardActual.getTurno());

        ImageButton deleteGuard = holder.itemView.findViewById(R.id.delete_myGuard);
        deleteGuard.setOnClickListener(view ->{
            if(listener!=null){
                try {
                    listener.onItemDelete(guardActual);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        );
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

    public interface onItemClickListener{
        void onItemDelete(GuardDto myGuard) throws ExecutionException, InterruptedException;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
