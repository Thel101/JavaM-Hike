package com.example.hikermanagementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchHikeAdapter extends RecyclerView.Adapter<SearchHikeViewHolder> {

    Context context;
    ArrayList<Hike> searchHikeArrayList;
    private final RecyclerViewInterface recyclerViewInterface;
    DataBaseHelper dataBaseHelper;

    public SearchHikeAdapter(Context context, ArrayList<Hike> searchHikeArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.searchHikeArrayList = searchHikeArrayList;
        dataBaseHelper= new DataBaseHelper(context);
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @Override
    public SearchHikeViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_search_list, parent,false);
        SearchHikeViewHolder viewHolder= new SearchHikeViewHolder(view, recyclerViewInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchHikeViewHolder holder, int position) {
        Hike hike = searchHikeArrayList.get(position);
        holder.searchHikeName.setText(hike.getHike_id() + hike.getHike_name());
        holder.searchHikeLocation.setText(hike.getHike_location());
        holder.searchHikeDate.setText(hike.getHike_date());
    }

    @Override
    public int getItemCount() {
        return searchHikeArrayList.size();
    }
}
