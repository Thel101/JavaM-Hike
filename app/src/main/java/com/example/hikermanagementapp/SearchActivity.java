package com.example.hikermanagementapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements RecyclerViewInterface {
    EditText edtSearchLocation, edtSearchDate;
    Button btnSearch;
    String[] m = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    DataBaseHelper dataBaseHelper;
    ArrayList<Hike> searchedResultList;
    RecyclerView searchResultView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edtSearchLocation = findViewById(R.id.edtSearchLocation);
        edtSearchDate = findViewById(R.id.edtSearchDate);
        btnSearch = findViewById(R.id.btnSearchHike);
        searchResultView = findViewById(R.id.searchResultRecyclerView);
        dataBaseHelper = new DataBaseHelper(this);

        edtSearchDate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (edtSearchDate.getRight() - edtSearchDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        openDateDialog();

                        return true;
                    }
                }
                return false;
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchLocation = edtSearchLocation.getText().toString();
                String searchDate = edtSearchDate.getText().toString();
                searchedResultList = dataBaseHelper.searchSpecificHike(searchLocation, searchDate);

                if (searchedResultList != null && searchedResultList.size() >0) {
                    searchResultView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    searchResultView.hasFixedSize();

                    SearchHikeAdapter adapter = new SearchHikeAdapter(getApplicationContext(), searchedResultList,SearchActivity.this);
                    if (searchResultView.getAdapter() == null) {
                        searchResultView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {

                        searchResultView.swapAdapter(adapter, false);
                    }

                    // Notify the adapter of the data set changes

                } else {
                    searchResultView.setAdapter(null);
                    Toast.makeText(SearchActivity.this, "Search Result Not Found", Toast.LENGTH_LONG).show();
                }


            }
        });

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
                edtSearchDate.setText(day + "-" + m[month] + "-" + year);
            }
        };
    }

    @Override
    public void itemClick(int position) {
        Intent intent= new Intent(SearchActivity.this, HikeDetailActivity.class);
        intent.putExtra("search_Hike_id", searchedResultList.get(position).getHike_id());
        intent.putExtra("search_name", searchedResultList.get(position).getHike_name());
        intent.putExtra("search_date", searchedResultList.get(position).getHike_date());
        intent.putExtra("search_location", searchedResultList.get(position).getHike_location());
        intent.putExtra("search_country", searchedResultList.get(position).getHike_country());
        intent.putExtra("search_length", searchedResultList.get(position).getHike_length());
        intent.putExtra("search_parking", searchedResultList.get(position).getParking_availability());
        intent.putExtra("search_difficulty", searchedResultList.get(position).getHike_difficulty());
        intent.putExtra("search_type", searchedResultList.get(position).getHike_type());
        intent.putExtra("search_description", searchedResultList.get(position).getHike_description());
        intent.putExtra("resource", "SearchList");
        startActivity(intent);
    }
}