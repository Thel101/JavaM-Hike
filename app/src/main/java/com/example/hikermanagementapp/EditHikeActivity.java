package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class EditHikeActivity extends AppCompatActivity {

    EditText name, location, date, length, description;
    SwitchCompat parking;
    Button updateHike;
    DataBaseHelper dataBaseHelper;

    String id;
    RadioGroup rdoDifficulty, rdoHikeType;
    Spinner spinnerCountry;
    String difficultyValue, difficultyResult, typeValue, typeResult, country;
    String[] m = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    String[] countries = {"UK", "USA", "Europe", "Japan", "Korea"};
    @SuppressLint("ClickableViewAccessibility")
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
        rdoDifficulty = findViewById(R.id.modifyHikeDifficulty);
        rdoHikeType = findViewById(R.id.modifyHikeType);
        spinnerCountry = findViewById(R.id.modifyCountry);
        description = findViewById(R.id.modifyHikeDescription);
        updateHike = findViewById(R.id.btnUpdate);

        //for difficulty radio buttons
        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (date.getRight() - date.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        openDateDialog();

                        return true;
                    }
                }
                return false;
            }
        });

        id= intent.getExtras().getString("id");
        Log.d("ID is", "Intent ID: *****" + id);
        name.setText(intent.getExtras().getString("name"));
        location.setText(intent.getExtras().getString("location"));
        date.setText(intent.getExtras().getString("date"));
        length.setText(intent.getExtras().getString("length"));
        description.setText(intent.getExtras().getString("description"));

        String country = intent.getExtras().getString("country");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCountry.setAdapter(adapter);

        int position = adapter.getPosition(country);
        spinnerCountry.setSelection(position);

        difficultyValue = intent.getExtras().getString("difficulty");
        if(difficultyValue.equals("High")){
            rdoDifficulty.check(R.id.rdoChallenge);
            difficultyResult = "High";
        }
        else{
            rdoDifficulty.check(R.id.rdoSmooth);
            difficultyResult = "Low";
        }

        typeValue = intent.getExtras().getString("type");
        if(typeValue.equals("One-way")){
            rdoHikeType.check(R.id.rdoOneway);
            typeResult= "One-way";
        }
        else{
            rdoHikeType.check(R.id.rdoRoundTrip);
            typeResult= "Round-trip";
        }
        rdoDifficulty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) { // Check if any radio button is checked
                    RadioButton checkedRadio = findViewById(checkedId);

                    if (checkedRadio != null) {
                        difficultyResult = checkedRadio.getText().toString();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Difficulty is not selected!", Toast.LENGTH_LONG).show();
                }
            }
        });

        rdoHikeType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) { // Check if any radio button is checked
                    RadioButton checkedRadio = findViewById(checkedId);

                    if (checkedRadio != null) {
                        typeResult = checkedRadio.getText().toString();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Type is not selected!", Toast.LENGTH_LONG).show();
                }
            }
        });

        updateHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hike_name = name.getText().toString();
                String hike_location = location.getText().toString();
                String hike_date = date.getText().toString();
                String hike_description = description.getText().toString();
                String hike_length = length.getText().toString();
                String selectedCountry = spinnerCountry.getSelectedItem().toString();
                boolean isParkingOn = parking.isChecked();
                String parkingAvailability = isParkingOn ? "available" : "unavailable";

                    Hike hike= new Hike(Integer.parseInt(id),hike_name,hike_location, selectedCountry, hike_date,hike_description, hike_length, parkingAvailability, difficultyResult, typeResult);
                    String userData = "Hike:   " + hike.getHike_name() + "\nLocation of hike: " + hike.getHike_location()
                            + "\nLength of hike: " + hike.getHike_length() + "\nDate of hike: " + hike.getHike_date()+
                            "\nParking Availability: " + hike.getParking_availability() + "\nDifficulty of hike: " + hike.getHike_difficulty()+
                            "\nDescription: " + hike.getHike_description();
                    AlertDialog.Builder builder= new AlertDialog.Builder(EditHikeActivity.this);
                    builder.setTitle("Confirmation");
                    builder.setMessage(userData);
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dataBaseHelper.updateHike(hike);
                            Toast.makeText(getApplicationContext(), "Successfully updated!", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent1= new Intent(EditHikeActivity.this, HikeListActivity.class);
                            startActivity(intent1);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();


            }
        });

    }

    private void openDateDialog() {
        Calendar calendar= Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog= new DatePickerDialog(this, dateSet(),year, month, day);
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSet() {
        return new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date.setText( day + "-"+ m[month] +"-"+ year);
            }
        };
    }
}