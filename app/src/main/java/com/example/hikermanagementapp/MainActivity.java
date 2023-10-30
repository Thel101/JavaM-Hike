package com.example.hikermanagementapp;

import static com.example.hikermanagementapp.R.id.rdoChallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText txtName, txtLocation,txtDate, txtDescription, txtLength;
    SwitchCompat switchParking;

    RadioGroup rdoDifficulty;
    RadioButton radioDifficulty;

    Button btnApply;
    String difficultyResult;
    String[] m = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    DataBaseHelper dataBaseHelper;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting components with ID
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtName = findViewById(R.id.edtTxtName);
        txtLocation = findViewById(R.id.edtTxtLocation);
        txtDate = findViewById(R.id.edtTxtDate);
        txtDescription = findViewById(R.id.edtTxtDescription);
        txtLength = findViewById(R.id.edtTxtLength);
        btnApply = findViewById(R.id.btnApply);
        switchParking = findViewById(R.id.parking);
        rdoDifficulty = findViewById(R.id.rdoDifficulty);
        dataBaseHelper= new DataBaseHelper(this);
        txtDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (txtDate.getRight() - txtDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        openDateDialog();

                        return true;
                    }
                }
                return false;
            }
        });
        //getting value from each components

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String location = txtLocation.getText().toString();
                String date = txtDate.getText().toString();
                String description = txtDescription.getText().toString();
                String hike_length = txtLength.getText().toString();
                boolean isParkingOn = switchParking.isChecked();

                String parkingAvailability = isParkingOn ? "available" : "unavailable";

                Toast.makeText(getApplicationContext(), "Parking value:" + parkingAvailability, Toast.LENGTH_LONG).show();


                String result = checkRadio(view);

                   if((name.isEmpty())){
                       Toast.makeText(getApplicationContext(), "Please fill the hike name!", Toast.LENGTH_LONG).show();
                   }
                   else if((location.isEmpty())){
                       Toast.makeText(getApplicationContext(), "Please fill the hike location", Toast.LENGTH_LONG).show();
                   }
                   else if((date.isEmpty())){
                       Toast.makeText(getApplicationContext(), "Please fill the hike date", Toast.LENGTH_LONG).show();
                   }
                   else if((hike_length.isEmpty())){
                       Toast.makeText(getApplicationContext(), "Please fill the height of hike", Toast.LENGTH_LONG).show();
                   }
                   else if(result.isEmpty()){
                       Toast.makeText(getApplicationContext(), "Please choose hike difficulty level", Toast.LENGTH_LONG).show();
                   }
                   else if(parkingAvailability ==null){
                       Toast.makeText(getApplicationContext(), "Please select the parking availability", Toast.LENGTH_LONG).show();
                   }
                   else{

                       Hike hike= new Hike(name, location, date,description, hike_length, parkingAvailability, result);
                       String userData = "Hike:   " + hike.getHike_name() + "\nLocation of hike: " + hike.getHike_location()
                               + "\nLength of hike: " + hike.getHike_length() + "\nDate of hike: " + hike.getHike_date()+
                               "\nParking Availability: " + hike.getParking_availability() + "\nDifficulty of hike: " + hike.getHike_difficulty()+
                               "\nDescription: " + hike.getHike_description();
                       AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                       builder.setTitle("Confirmation");
                               builder.setMessage(userData);
                                       builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialogInterface, int i) {
                                                dataBaseHelper.saveHikeDetails(hike);
                                               Intent intent= new Intent(MainActivity.this, HikeListActivity.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(R.id.itemView==item.getItemId())
        {
            Intent intent= new Intent(MainActivity.this, HikeListActivity.class);
            startActivity(intent);
            return true;
        }
        else if(R.id.itemEnter==item.getItemId()){
            //Exit
            Toast.makeText(this, "Enter Item Selected", Toast.LENGTH_LONG).show();
            finish();
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }
    // ---  Menu Bar --- //

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
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                txtDate.setText( day + "-"+ m[month] +"-"+ year);
            }
        };
    }
    public String checkRadio(View view) {
        int radioId = rdoDifficulty.getCheckedRadioButtonId();
        radioDifficulty = findViewById(radioId);
        difficultyResult = radioDifficulty.getText().toString();
        return difficultyResult;

    }
}