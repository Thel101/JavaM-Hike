package com.example.hikermanagementapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ObservationViewHolder extends RecyclerView.ViewHolder {
    TextView observationTitle, observationTime, observationComments;
    ImageView observation_image;
    ImageButton btnEdit, btnDelete;
    public ObservationViewHolder(@NonNull View itemView) {
        super(itemView);
        observation_image = itemView.findViewById(R.id.observationImage);
        observationTitle = itemView.findViewById(R.id.observation_title_view);
        observationTime = itemView.findViewById(R.id.observation_time_view);
        observationComments = itemView.findViewById(R.id.observation_comments_view);
        btnEdit = itemView.findViewById(R.id.btnEditObservation);
        btnDelete = itemView.findViewById(R.id.btnDeleteObservation);
    }
}
