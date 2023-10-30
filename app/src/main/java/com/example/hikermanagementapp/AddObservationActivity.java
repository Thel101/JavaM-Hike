package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddObservationActivity extends AppCompatActivity {

    EditText observation_title, observation_time, observation_comments;
    Button btnSave;

    String title, time, comments;
    TextView hike_name, hike_date, hike_location;
   DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);
        observation_title =findViewById(R.id.edtObservationTitle);
        observation_time = findViewById(R.id.edtObservationTime);
        observation_comments = findViewById(R.id.edtObservationComment);
        hike_name= findViewById(R.id.txtHikeName);
        hike_date = findViewById(R.id.txtHikeDate);
        hike_location = findViewById(R.id.txtHikeLocation);
        btnSave = findViewById(R.id.btnSaveObservation);

        Intent intent= getIntent();
        hike_name.setText(intent.getExtras().getString("Hike_name") );
        hike_date.setText(intent.getExtras().getString("Hike_date"));
        hike_location.setText(intent.getExtras().getString("Hike_location"));
        int id = (int) intent.getExtras().getLong("Hike_id");
        dataBaseHelper= new DataBaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int hike_id= id;
                title = observation_title.getText().toString();
                time = observation_time.getText().toString();
                comments = observation_comments.getText().toString();

                Observation observation= new Observation(title,time,comments, hike_id);
                dataBaseHelper.saveObservation(observation);
                Toast.makeText(getApplicationContext(), "Observation Saved", Toast.LENGTH_LONG).show();
            }
        });



    }
}