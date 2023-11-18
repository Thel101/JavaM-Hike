package com.example.hikermanagementapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class SearchHikeViewHolder extends RecyclerView.ViewHolder {
    TextView searchHikeName, searchHikeLocation, searchHikeDate;
    public SearchHikeViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        searchHikeName= itemView.findViewById(R.id.search_hike_name_view);
        searchHikeLocation = itemView.findViewById(R.id.search_hike_location_view);
        searchHikeDate = itemView.findViewById(R.id.search_hike_date_view);
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
