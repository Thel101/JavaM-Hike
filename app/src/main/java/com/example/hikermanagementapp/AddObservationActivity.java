package com.example.hikermanagementapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class  AddObservationActivity extends AppCompatActivity {

    EditText observation_title, observation_time, observation_comments;
    Button btnSave, btnUploadPhoto;
    ImageView observationImage;
    private static final int CAMERA_REQUEST = 1888;
    ActivityResultLauncher<Intent> activityResultLauncher;

    String title, time, comments;
    Bitmap photo;

    byte[] byteArray;
    String[] m = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
   DataBaseHelper dataBaseHelper;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);
        observation_title =findViewById(R.id.edtObservationTitle);
        observation_time = findViewById(R.id.edtObservationTime);
        observation_comments = findViewById(R.id.edtObservationComment);
        btnSave = findViewById(R.id.btnSaveObservation);

        btnUploadPhoto = findViewById(R.id.uploadPhoto);
        observationImage = findViewById(R.id.imageView1);

        observation_time.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (observation_time.getRight() - observation_time.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        openDateDialog();

                        return true;
                    }
                }
                return false;
            }
        });

        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                activityResultLauncher.launch(cameraIntent);

                 startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData()!=null) {
                    photo = (Bitmap) result.getData().getExtras().get("data");
                    int targetWidth = 800;
                    int targetHeight = 600;
                    photo = Bitmap.createScaledBitmap(photo, targetWidth, targetHeight, true);
                    observationImage.setImageBitmap(photo);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    try {
                        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();
                        Log.d("Byte Array" , "===>" + byteArray);
                        // Handle the byte array as needed (e.g., store in a database or send over a network)
                    } finally {
                        try {
                            stream.close(); // Close the ByteArrayOutputStream in the finally block
                        } catch (IOException e) {
                            // Handle the IOException when closing the stream
                            e.printStackTrace(); // Print the stack trace or log the error
                        }
                    }
                }
            }
        });

        Intent intent= getIntent();
        int id = (int) intent.getExtras().getInt("Hike_ID");
        dataBaseHelper= new DataBaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int hike_id= id;
                title = observation_title.getText().toString();
                time = observation_time.getText().toString();
                comments = observation_comments.getText().toString();

                if((title.isEmpty())){
                    Toast.makeText(getApplicationContext(), "Please fill the observation title", Toast.LENGTH_LONG).show();
                }
                else if((time.isEmpty())){
                    Toast.makeText(getApplicationContext(), "Please fill the observation time", Toast.LENGTH_LONG).show();
                }
                else{
                    Observation observation= new Observation(title,time,comments,byteArray, hike_id);
                    dataBaseHelper.saveObservation(observation);
                    Intent intent= new Intent(AddObservationActivity.this, HikeListActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Observation Saved", Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    private void openDateDialog() {
        Calendar calendar= Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Handle the selected date
                        // Now, create a TimePickerDialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                AddObservationActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                        // Handle the selected time
                                        String formattedDateTime = String.format(
                                                "%04d-%02d-%02d %02d:%02d",
                                                selectedYear, selectedMonth + 1, selectedDayOfMonth, selectedHour, selectedMinute
                                        );
                                        observation_time.setText( formattedDateTime);
                                    }
                                },
                                hour,
                                minute,
                                true // 24-hour format
                        );

                        // Show the TimePickerDialog after selecting the date
                        timePickerDialog.show();
                    }
                },
                year,
                month,
                day
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }



}




