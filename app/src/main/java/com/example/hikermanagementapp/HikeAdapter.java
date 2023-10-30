package com.example.hikermanagementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter <HikeViewHolder>{
    Context context;
    ArrayList<Hike> hikeArrayList;

    DataBaseHelper dataBaseHelper;
    public HikeAdapter(Context context, ArrayList<Hike> hikeArrayList) {
        this.context= context;
        this.hikeArrayList = hikeArrayList;
        dataBaseHelper = new DataBaseHelper(context);
    }


    @Override
    public HikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_list_item, parent,false);
        HikeViewHolder viewHolder= new HikeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( HikeViewHolder holder, int position) {
        Hike hike= hikeArrayList.get(position);
        holder.hikeName.setText(hike.getHike_name());
        holder.hikeLocation.setText(hike.getHike_location());
        holder.hikeLength.setText(hike.getHike_length() + " meters");
        holder.hikeDate.setText(hike.getHike_date());
        holder.hikeParking.setText(String.valueOf(hike.getParking_availability()));
        holder.hikeDifficulty.setText(hike.getHike_difficulty());
        holder.hikeDescription.setText(hike.getHike_description());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editHike(hike);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
              deleteHike(hike);

            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQualification(hike);
            }
        });
    }

    private void addQualification(Hike hike) {
        Intent intent= new Intent(context, AddObservationActivity.class);
        intent.putExtra("Hike_id", hike.getHike_id());
        intent.putExtra("Hike_name", hike.getHike_name());
        intent.putExtra("Hike_location", hike.getHike_location());
        intent.putExtra("Hike_date", hike.getHike_date());
        context.startActivity(intent);

    }

    private void deleteHike(Hike hike) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataBaseHelper.deleteHike(hike.getHike_id());
                        ((Activity)context).finish();
                        context.startActivity(((Activity) context).getIntent());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    private void editHike(Hike hike) {
        String hikeId= String.valueOf(hike.getHike_id());
        String hikeName= hike.getHike_name();
        String hikeLocation = hike.getHike_location();
        String hikeLength = hike.getHike_length();
        String hikeDate = hike.getHike_date();
        String hikeParking = hike.getParking_availability().toString();
        String hikeDifficulty = hike.getHike_difficulty();
        String hikeDescription = hike.getHike_description() ;

        Intent intent= new Intent(context,EditHikeActivity.class);
        intent.putExtra("id", hikeId);
        intent.putExtra("name", hikeName);
        intent.putExtra("location", hikeLocation);
        intent.putExtra("length", hikeLength);
        intent.putExtra("date", hikeDate);
        intent.putExtra("parking", hikeParking);
        intent.putExtra("difficulty", hikeDifficulty);
        intent.putExtra("description", hikeDescription);

        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return hikeArrayList.size();
    }
}
