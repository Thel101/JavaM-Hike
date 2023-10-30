package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditHikeActivity extends AppCompatActivity {

    EditText name, location, date, length, parking, difficulty,description;
    Button updateHike;
    DataBaseHelper dataBaseHelper;

    String id,modifyName, modifyLocation, modifyDate, modifyLength, modifyParking, modifyDifficulty, modifyDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);
        Intent intent=getIntent();
        dataBaseHelper= new DataBaseHelper(this);
        name= findViewById(R.id.modifyHikeName);
        location = findViewById(R.id.modifyHikeLocation);
        date = findViewById(R.id.modifyHikeDate);
        length = findViewById(R.id.modifyHikeLength);
        parking = findViewById(R.id.modifyHikeParking);
        difficulty = findViewById(R.id.modifyHikeDifficulty);
        description = findViewById(R.id.modifyHikeDescription);
        updateHike = findViewById(R.id.btnUpdate);

        id= intent.getExtras().getString("id");
        Log.d("ID is", "Intent ID: *****" + id);
        name.setText(intent.getExtras().getString("id") + intent.getExtras().getString("name"));
        location.setText(intent.getExtras().getString("location"));
        date.setText(intent.getExtras().getString("date"));
        length.setText(intent.getExtras().getString("length"));
        parking.setText(intent.getExtras().getString("parking"));
        difficulty.setText(intent.getExtras().getString("difficulty"));
        description.setText(intent.getExtras().getString("description"));


        updateHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyName = name.getText().toString();
                modifyLocation = location.getText().toString() ;
                modifyDate = date.getText().toString() ;
                modifyLength = length.getText().toString() ;
                modifyParking = parking.getText().toString();
                modifyDifficulty = difficulty.getText().toString();
                modifyDescription = description.getText().toString();
                Hike hike= new Hike(Integer.parseInt(id), modifyName, modifyLocation, modifyDate, modifyDescription, modifyLength, modifyParking, modifyDifficulty);
                dataBaseHelper.updateHike(hike);
                Toast.makeText(EditHikeActivity.this, "Update Success" , Toast.LENGTH_LONG).show();
                Intent intent1= new Intent(EditHikeActivity.this, HikeListActivity.class);
                startActivity(intent1);
            }
        });

    }
}