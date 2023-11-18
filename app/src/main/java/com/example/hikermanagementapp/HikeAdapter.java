package com.example.hikermanagementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter <HikeViewHolder>{
    Context context;
    ArrayList<Hike> hikeArrayList;
    private final RecyclerViewInterface recyclerViewInterface;

    DataBaseHelper dataBaseHelper;
    public HikeAdapter(Context context, ArrayList<Hike> hikeArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context= context;
        this.hikeArrayList = hikeArrayList;
        dataBaseHelper = new DataBaseHelper(context);
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setSearchedList(ArrayList<Hike> searchedList){
        this.hikeArrayList= searchedList;
        notifyDataSetChanged();
    }

    @Override
    public HikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_list_item, parent,false);
        HikeViewHolder viewHolder= new HikeViewHolder(view, recyclerViewInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( HikeViewHolder holder, int position) {
        Hike hike = hikeArrayList.get(position);
        holder.hikeName.setText(hike.getHike_name());
        holder.hikeLocation.setText(hike.getHike_location());
        holder.hikeDate.setText(hike.getHike_date());

    }
    @Override
    public int getItemCount() {
        return hikeArrayList.size();
    }
}
