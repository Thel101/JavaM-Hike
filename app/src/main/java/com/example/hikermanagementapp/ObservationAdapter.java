package com.example.hikermanagementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationViewHolder> {
    Context context;

    ArrayList<Observation> observationArrayList;

    DataBaseHelper dataBaseHelper;
    private final OnItemClickListener listener;

    public ObservationAdapter(Context context, ArrayList observationArrayList, OnItemClickListener listener) {
        this.context = context;
        this.observationArrayList = observationArrayList;
        dataBaseHelper= new DataBaseHelper(context);
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(Observation observation);
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.observation_list_item, parent,false);
        ObservationViewHolder viewHolder= new ObservationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ObservationViewHolder holder, int position) {
        Observation observation= observationArrayList.get(position);
        holder.observationTitle.setText(observation.getObservation_title());
        holder.observationTime.setText(observation.getObservation_time());
        holder.observationComments.setText(observation.getObservation_comments());
    }


    @Override
    public int getItemCount() {
        return observationArrayList.size();
    }
}
