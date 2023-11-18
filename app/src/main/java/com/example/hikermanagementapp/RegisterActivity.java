package com.example.hikermanagementapp;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;


public class RegisterActivity extends AppCompatActivity {

    EditText txtName, txtLocation, txtDate, txtDescription, txtLength;
    SwitchCompat switchParking;

    RadioGroup regRdoDifficulty, rdoHikeType;

    Button btnApply;
    String difficultyResult, hikeType, country;
    Spinner countrySpinner;
    String[] m = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

    String[] countries = {"UK", "USA", "Europe", "Japan", "Korea"};
    DataBaseHelper dataBaseHelper;
    Toolbar toolbar;
    boolean isDifficultyClicked, isTypeClicked;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getting components with ID

        txtName = findViewById(R.id.regHikeName);
        txtLocation = findViewById(R.id.regHikeLocation);
        txtDate = findViewById(R.id.regHikeDate);
        txtDescription = findViewById(R.id.regHikeDescription);
        txtLength = findViewById(R.id.regHikeLength);
        btnApply = findViewById(R.id.btnRegisterHike);
        switchParking = findViewById(R.id.regParking);
        regRdoDifficulty = findViewById(R.id.regRdoDifficulty);
        rdoHikeType = findViewById(R.id.regRdoHikeType);
        isDifficultyClicked = false;
        isTypeClicked = false;
        countrySpinner = findViewById(R.id.spinner_country);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        countrySpinner.setAdapter(adapter);

        dataBaseHelper = new DataBaseHelper(this);
        btnApply.setEnabled(false);
        txtDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (txtDate.getRight() - txtDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        openDateDialog();

                        return true;
                    }
                }
                return false;
            }
        });
        //getting value from each components

        txtName.addTextChangedListener(createTextWatcher(txtName, btnApply));
        txtLocation.addTextChangedListener(createTextWatcher(txtLocation, btnApply));
        txtDate.addTextChangedListener(createTextWatcher(txtDate, btnApply));
        txtDescription.addTextChangedListener(createTextWatcher(txtDescription, btnApply));
        txtLength.addTextChangedListener(createTextWatcher(txtLength, btnApply));

//      boolean isRadioButtonClicked = false;
        regRdoDifficulty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) { // Check if any radio button is checked
                    RadioButton checkedRadio = findViewById(checkedId);

                    if (checkedRadio != null) {
                        difficultyResult = checkedRadio.getText().toString();
                        isDifficultyClicked = true;
                    }
                } else {
                    isDifficultyClicked= false;
                    Toast.makeText(getApplicationContext(), "Difficulty is not selected!", Toast.LENGTH_LONG).show();
                }
            }
        });
        rdoHikeType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) { // Check if any radio button is checked
                    RadioButton checkedType = findViewById(checkedId);

                    if (checkedType != null) {
                        hikeType = checkedType.getText().toString();
                        isTypeClicked = true;
                    }
                } else {
                    isTypeClicked= false;
                    Toast.makeText(getApplicationContext(), "Difficulty is not selected!", Toast.LENGTH_LONG).show();
                }
               
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String location = txtLocation.getText().toString();
                String date = txtDate.getText().toString();
                String description = txtDescription.getText().toString();
                String hike_length = txtLength.getText().toString();
                country = countrySpinner.getSelectedItem().toString();

                boolean isParkingOn = switchParking.isChecked();
                String parkingAvailability = isParkingOn ? "available" : "unavailable";

                btnApply.setEnabled(true);
                if((name.isEmpty())){
                    Toast.makeText(getApplicationContext(), "Please fill the hike name!", Toast.LENGTH_LONG).show();
                }
                else if((location.isEmpty())){
                    Toast.makeText(getApplicationContext(), "Please fill the hike location", Toast.LENGTH_LONG).show();
                }
                else if(country.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please choose hike country", Toast.LENGTH_LONG).show();
                }
                else if((hike_length.isEmpty())){
                    Toast.makeText(getApplicationContext(), "Please fill the height of hike", Toast.LENGTH_LONG).show();
                }
                else if((date.isEmpty())){
                    Toast.makeText(getApplicationContext(), "Please fill the hike date", Toast.LENGTH_LONG).show();
                }
                else if (!isDifficultyClicked) {
                    Toast.makeText(getApplicationContext(), "Please select a difficulty level", Toast.LENGTH_LONG).show();
                }
                else if(difficultyResult.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please choose hike difficulty level", Toast.LENGTH_LONG).show();
                }
                else if (!isTypeClicked) {
                    Toast.makeText(getApplicationContext(), "Please select a hike type", Toast.LENGTH_LONG).show();
                }
                else if(hikeType.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please choose hike type", Toast.LENGTH_LONG).show();
                }
                else{

                    Hike hike= new Hike(name, location,country,date,description, hike_length, parkingAvailability, difficultyResult, hikeType);
                    String userData = "Hike:   " + hike.getHike_name() + "\nLocation of hike: " + hike.getHike_location()
                            + "\nCountry of hike:" + hike.getHike_country()
                            + "\nLength of hike: " + hike.getHike_length() + "\nDate of hike: " + hike.getHike_date()+
                            "\nParking Availability: " + hike.getParking_availability() + "\nDifficulty of hike: " + hike.getHike_difficulty()
                            + "\nHike Type :" + hike.getHike_type() + "\nDescription: " + hike.getHike_description();
                    AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Confirmation");
                    builder.setMessage(userData);
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dataBaseHelper.saveHikeDetails(hike);
                            Intent intent= new Intent(RegisterActivity.this, HikeListActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();

                }
            }

        });
    }

    private TextWatcher createTextWatcher(EditText txtField, Button btnApply) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 3) {
                    btnApply.setEnabled(true); // Enable button when text is entered
                } else {
                    btnApply.setEnabled(false); // Disable button when text is empty
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    ///Date Dialog///
    private void openDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSet(), year, month, day);
        datePickerDialog.show();
    }

    //start of DatePicker///
    private DatePickerDialog.OnDateSetListener dateSet() {
        return new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                txtDate.setText(day + "-" + m[month] + "-" + year);
            }
        };
    }
    //end of DatePicker///

}