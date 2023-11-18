package com.example.hikermanagementapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationViewHolder> {
    Context context;

    ArrayList<Observation> observationArrayList;

    DataBaseHelper dataBaseHelper;

    public ObservationAdapter(Context context, ArrayList observationArrayList) {
        this.context = context;
        this.observationArrayList = observationArrayList;
        dataBaseHelper= new DataBaseHelper(context);
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

        byte[] obs_photo_array = observation.getObservation_photo(); // Retrieve the byte array from your Observation model


        if (obs_photo_array != null && obs_photo_array.length > 0) {

            Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(obs_photo_array));
            // Attempt to decode the bitmap (but not into memory)
                try {
                    if (bitmap != null) {

                        // Set the bitmap to the ImageView
                        holder.observation_image.setImageBitmap(bitmap);
                        Log.d("Image", "Setting custom image for Observation ID " + observation.getObservation_id());
                    } else {
                        holder.observation_image.setImageResource(R.drawable.mountain);
                        Log.e("Image", "Failed to decode image for Observation ID " + observation.getObservation_id());

                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }


        } else {
            // Handle the case where obs_photo_array is null or empty
            holder.observation_image.setImageResource(R.drawable.mountain);
            Log.d("Image", "No image data for Observation ID " + observation.getObservation_id());
        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteObservation(observation);
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateObservation(observation);
            }
        });
    }

    private void updateObservation(Observation observation) {
        String observation_id= String.valueOf(observation.getObservation_id());
        String title= observation.getObservation_title();
        String time = observation.getObservation_time();
        String comments = observation.getObservation_comments();

        Intent intent= new Intent(context,EditObservationActivity.class);
        intent.putExtra("obs_id", observation_id);
        intent.putExtra("obs_title", title);
        intent.putExtra("obs_time", time);
        intent.putExtra("obs_comments", comments);


        context.startActivity(intent);
    }

    private void deleteObservation(Observation observation) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete observation")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataBaseHelper.deleteObservation(observation.getObservation_id());
                        Log.d("Observation ID is : ", "+********" +observation.getObservation_id());
                        Toast.makeText(context.getApplicationContext(), "Observation Deleted!", Toast.LENGTH_LONG).show();
                        ((Activity)context).finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }


    @Override
    public int getItemCount() {
        return observationArrayList.size();
    }
}
