package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class ObservationListActivity extends AppCompatActivity {

    RecyclerView observationView;
    DataBaseHelper dataBaseHelper;
    ArrayList<Observation> observationArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_list);
        observationView = findViewById(R.id.observationListView);
        dataBaseHelper = new DataBaseHelper(this);

        Intent intent= getIntent();
        int id= intent.getExtras().getInt("Hike_id");

        if (id != 0) {
//            Toast.makeText(this, "Hike ID is " + id, Toast.LENGTH_LONG).show();
            observationArrayList= dataBaseHelper.getAllObservations(id);
            Log.d("Observation List ", "List is" + observationArrayList);
            if(observationArrayList.size()!=0) {

                observationView.setLayoutManager(new LinearLayoutManager(this));
                observationView.hasFixedSize();

                ObservationAdapter adapter= new ObservationAdapter(this, observationArrayList);
                observationView.setAdapter(adapter);

            }


        } else {
            Toast.makeText(this, "Hike ID is null", Toast.LENGTH_LONG).show();
        }

    }
}