package com.example.hikermanagementapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ObservationViewHolder extends RecyclerView.ViewHolder {
    TextView observationTitle, observationTime, observationComments, hikeName;
    public ObservationViewHolder(@NonNull View itemView) {
        super(itemView);
        observationTitle = itemView.findViewById(R.id.observation_title_view);
        observationTime = itemView.findViewById(R.id.observation_time_view);
        observationComments = itemView.findViewById(R.id.observation_comments_view);
        hikeName = itemView.findViewById(R.id.ob_hike_name_view);
    }
}
