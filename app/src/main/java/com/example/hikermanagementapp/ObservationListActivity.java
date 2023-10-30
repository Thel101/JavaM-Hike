package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        observationArrayList= dataBaseHelper.getAllObservations();

        observationView.setLayoutManager(new LinearLayoutManager(this));
        observationView.hasFixedSize();

        ObservationAdapter adapter= new ObservationAdapter(this, observationArrayList, listener);
        observationView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}