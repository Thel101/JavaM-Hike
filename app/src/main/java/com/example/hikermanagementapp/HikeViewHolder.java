package com.example.hikermanagementapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HikeViewHolder extends RecyclerView.ViewHolder {
    TextView hikeName, hikeLocation, hikeLength, hikeDate, hikeParking, hikeDifficulty, hikeDescription;
    ImageButton btnEdit, btnDelete, btnAdd;
    public HikeViewHolder(View itemView) {
        super(itemView);
        hikeName = itemView.findViewById(R.id.hike_name_view);
        hikeLocation = itemView.findViewById(R.id.hike_location_view);
        hikeLength = itemView.findViewById(R.id.hike_length_view);
        hikeDate = itemView.findViewById(R.id.hike_date_view);
        hikeParking = itemView.findViewById(R.id.hike_parking_view);
        hikeDifficulty = itemView.findViewById(R.id.hike_difficulty_view);
        hikeDescription = itemView.findViewById(R.id.hike_description_view);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDelete = itemView.findViewById(R.id.btnDelete);
        btnAdd = itemView.findViewById(R.id.btnAddObservation);
    }
}
