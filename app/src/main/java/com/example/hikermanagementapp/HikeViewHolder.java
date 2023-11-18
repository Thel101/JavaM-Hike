package com.example.hikermanagementapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HikeViewHolder extends RecyclerView.ViewHolder {
    TextView hikeName, hikeLocation, hikeDate;
    public HikeViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        hikeName = itemView.findViewById(R.id.hike_name_view);
        hikeLocation = itemView.findViewById(R.id.hike_location_view);
        hikeDate = itemView.findViewById(R.id.hike_date_view);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewInterface!= null){
                    int pos= getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.itemClick(pos);
                    }
                    }
                }

        });



    }
}
