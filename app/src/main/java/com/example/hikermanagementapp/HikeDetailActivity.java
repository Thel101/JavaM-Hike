package com.example.hikermanagementapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HikeDetailActivity extends AppCompatActivity {

    TextView hikeNameData, hikeLocationData, hikeLengthData, hikeCountryData, hikeDateData, parkingData, hikeDifficultyData, hikeTypeData, hikeDescriptionData;
    TextView hikeLabel;
    ImageButton btnEdit, btnDelete, btnAdd;

    RecyclerView observationView;
    DataBaseHelper dataBaseHelper;
    ArrayList<Observation> observationArrayList;
    String name, date, location, length, parking, difficulty, description, country, type;
    int detail_id,hike_id_no;
    int id;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_detail);
        hikeNameData = findViewById(R.id.nameData);
        hikeLocationData = findViewById(R.id.locationData);
        hikeLengthData = findViewById(R.id.lengthData);
        hikeCountryData = findViewById(R.id.countryData);
        hikeDateData = findViewById(R.id.dateDate);
        parkingData = findViewById(R.id.parkingData);
        hikeDifficultyData = findViewById(R.id.difficultyData);
        hikeTypeData = findViewById(R.id.typeData);
        hikeDescriptionData = findViewById(R.id.descriptionData);

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnAdd = findViewById(R.id.btnAddObservation);

        Intent intent2= getIntent();
        String source= intent2.getExtras().getString("resource");
        if(source.equals("HikeList")) {
            id = getIntent().getExtras().getInt("Hike_id");
            name = getIntent().getExtras().getString("name");
            date = getIntent().getExtras().getString("date");
            location = getIntent().getExtras().getString("location");
            country = getIntent().getExtras().getString("country");
            length = getIntent().getExtras().getString("length");
            parking = getIntent().getExtras().getString("parking");
            difficulty = getIntent().getExtras().getString("difficulty");
            type = getIntent().getExtras().getString("type");
            description = getIntent().getExtras().getString("description");
            detail_id = intent2.getIntExtra("Hike_id", 0);
            hikeNameData.setText(name);
            hikeLocationData.setText(location);
            hikeLengthData.setText(length);
            hikeCountryData.setText(country);
            hikeDateData.setText(date);
            parkingData.setText(parking);
            hikeDifficultyData.setText(difficulty);
            hikeTypeData.setText(type);
            hikeDescriptionData.setText(description);
        }
        else if(source.equals("SearchList")){
            id= getIntent().getExtras().getInt("search_Hike_id");
            name= getIntent().getExtras().getString("search_name");
            date= getIntent().getExtras().getString("search_date");
            location= getIntent().getExtras().getString("search_location");
            country = getIntent().getExtras().getString("search_country");
            length= getIntent().getExtras().getString("search_length");
            parking = getIntent().getExtras().getString("search_parking");
            difficulty= getIntent().getExtras().getString("search_difficulty");
            type = getIntent().getExtras().getString("search_type");
            description= getIntent().getExtras().getString("search_description");
            hike_id_no= intent2.getIntExtra("search_Hike_id",0);
            hikeNameData.setText(name);
            hikeLocationData.setText(location);
            hikeLengthData.setText(length);
            hikeCountryData.setText(country);
            hikeDateData.setText(date);
            parkingData.setText(parking);
            hikeDifficultyData.setText(difficulty);
            hikeTypeData.setText(type);
            hikeDescriptionData.setText(description);
        }



        observationView = findViewById(R.id.observationRecyclerView);
        dataBaseHelper = new DataBaseHelper(this);



        if (detail_id != 0) {
//            Toast.makeText(this, "Hike ID is " + id, Toast.LENGTH_LONG).show();
            observationArrayList= dataBaseHelper.getAllObservations(detail_id);
            Log.d("Observation List ", "List is" + String.valueOf(observationArrayList.size()));
            if(observationArrayList.size()!=0) {

                observationView.setLayoutManager(new LinearLayoutManager(this));
                observationView.hasFixedSize();

                ObservationAdapter adapter= new ObservationAdapter(this, observationArrayList);
                observationView.setAdapter(adapter);

            }



        } else {
            Toast.makeText(this, "Hike ID is null", Toast.LENGTH_LONG).show();
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(HikeDetailActivity.this, AddObservationActivity.class);
                intent1.putExtra("Hike_ID", detail_id);
                startActivity(intent1);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editHike(detail_id);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteHike(detail_id);
            }
        });
    }

    private void deleteHike(int hike_id) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            // Attempt to delete the hike from the database
                            dataBaseHelper.deleteHike(hike_id);

                            Intent intent = new Intent(HikeDetailActivity.this, HikeListActivity.class);
                            intent.putExtra("DELETE_SUCCESS_MESSAGE", "Hike successfully deleted!");
                            startActivity(intent);
                        } catch (Exception e) {
                            // Log any exceptions that occur during the deletion process
                            Log.e("DeleteHike", "Error deleting hike", e);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle the case where the user chooses not to delete the hike
                    }
                }).show();
    }



    private void editHike(Integer hike_id) {
        String hikeId= String.valueOf(hike_id);
        String hikeName= name;
        String hikeLocation = location;
        String hikeLength = length;
        String hikeDate = date;
        String hikeParking = parking;
        String hikeDifficulty = difficulty;
        String hikeDescription = description ;

        Intent intent= new Intent(HikeDetailActivity.this,EditHikeActivity.class);
        intent.putExtra("id", hikeId);
        intent.putExtra("name", hikeName);
        intent.putExtra("location", hikeLocation);
        intent.putExtra("length", hikeLength);
        intent.putExtra("date", hikeDate);
        intent.putExtra("parking", hikeParking);
        intent.putExtra("difficulty", hikeDifficulty);
        intent.putExtra("description", hikeDescription);

        startActivity(intent);
    }
}
