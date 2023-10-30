package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import java.util.ArrayList;

public class HikeListActivity extends AppCompatActivity {

    RecyclerView hikeView;

    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list);
        hikeView =findViewById(R.id.hike_recycler_view);
        dataBaseHelper= new DataBaseHelper(this);
        ArrayList<Hike> hikeArrayList= dataBaseHelper.getAllHikeInfo();

        hikeView.setLayoutManager(new LinearLayoutManager(this));
        hikeView.hasFixedSize();

        HikeAdapter adapter= new HikeAdapter(this, hikeArrayList);
        hikeView.setAdapter(adapter);
        

    }
}