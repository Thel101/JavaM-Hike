package com.example.hikermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditObservationActivity extends AppCompatActivity {

    EditText txt_title, txt_time, txt_comments;
    Button btnUpdateObs;
    DataBaseHelper dataBaseHelper;
    String obs_id, obs_title, obs_time, obs_comments;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_observation);
        Intent updateIntent = getIntent();
        dataBaseHelper= new DataBaseHelper(this);
        txt_title = findViewById(R.id.edtTxtObservationTitle);
        txt_time = findViewById(R.id.edtTxtObservationTime);
        txt_comments = findViewById(R.id.edtTxtObservationComment);
        btnUpdateObs = findViewById(R.id.btnUpdObservation);

        txt_title.setText(updateIntent.getExtras().getString("obs_title"));
        txt_time.setText(updateIntent.getExtras().getString("obs_time"));
        txt_comments.setText(updateIntent.getExtras().getString("obs_comments"));
        obs_id = updateIntent.getExtras().getString("obs_id");

        txt_time.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (txt_time.getRight() - txt_time.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        openDateDialog();

                        return true;
                    }
                }
                return false;
            }
        });

        btnUpdateObs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obs_title= txt_title.getText().toString();
                obs_time= txt_time.getText().toString();
                obs_comments = txt_comments.getText().toString();

                Observation observation= new Observation(Integer.parseInt(obs_id), obs_title, obs_time, obs_comments);
                dataBaseHelper.updateObservation(observation);
                Toast.makeText(getApplicationContext(), "Observation Successfully updated!", Toast.LENGTH_LONG).show();
                finish();
                Intent intent1= new Intent(EditObservationActivity.this, HikeDetailActivity.class);
                startActivity(intent1);
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
                                EditObservationActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                        // Handle the selected time
                                        String formattedDateTime = String.format(
                                                "%04d-%02d-%02d %02d:%02d",
                                                selectedYear, selectedMonth + 1, selectedDayOfMonth, selectedHour, selectedMinute
                                        );
                                        txt_time.setText( formattedDateTime);
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
