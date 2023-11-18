package com.example.hikermanagementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HikeListActivity extends AppCompatActivity implements RecyclerViewInterface {

    SearchView searchView;
    RecyclerView hikeView;
    TextView noRecordTxtView;

    DataBaseHelper dataBaseHelper;
    ArrayList<Hike> hikeArrayList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent.hasExtra("DELETE_SUCCESS_MESSAGE")) {
            String message = intent.getStringExtra("DELETE_SUCCESS_MESSAGE");

            // Show a toast message with the retrieved text
            Toast.makeText(HikeListActivity.this, message, Toast.LENGTH_LONG).show();
        }
        noRecordTxtView= findViewById(R.id.txtViewNoRecord);
        hikeView = findViewById(R.id.hike_recycler_view);
        dataBaseHelper = new DataBaseHelper(this);

        submitData();
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

    }

    private void submitData() {
        hikeArrayList = dataBaseHelper.getAllHikeInfo();
        if(hikeArrayList.size()==0){
            noRecordTxtView.setVisibility(View.VISIBLE);
            noRecordTxtView.setText("No Record Hike History!!");
        }
        else{
            noRecordTxtView.setVisibility(View.GONE);
        hikeView.setLayoutManager(new LinearLayoutManager(this));
        hikeView.hasFixedSize();
        HikeAdapter adapter = new HikeAdapter(this, hikeArrayList, this);
        hikeView.setAdapter(adapter);
        }
    }


    private void filterList(String newText) {
        ArrayList<Hike> searchedList = dataBaseHelper.searchHike(newText);
        hikeArrayList = searchedList;
        if (searchedList.isEmpty()) {
            Toast.makeText(this, "No Record Found", Toast.LENGTH_LONG).show();
        } else {
            HikeAdapter adapter= new HikeAdapter(this, hikeArrayList, this);
            hikeView.setAdapter(adapter);
            adapter.setSearchedList(searchedList);
        }
    }

    @Override
    public void itemClick(int position) {
        Log.d("HikeListActivity", "Clicked on position: " + position);
        Intent intent = new Intent(HikeListActivity.this, HikeDetailActivity.class);
        intent.putExtra("Hike_id", hikeArrayList.get(position).getHike_id());
        intent.putExtra("name", hikeArrayList.get(position).getHike_name());
        intent.putExtra("date", hikeArrayList.get(position).getHike_date());
        intent.putExtra("location", hikeArrayList.get(position).getHike_location());
        intent.putExtra("country", hikeArrayList.get(position).getHike_country());
        intent.putExtra("length", hikeArrayList.get(position).getHike_length());
        intent.putExtra("parking", hikeArrayList.get(position).getParking_availability());
        intent.putExtra("difficulty", hikeArrayList.get(position).getHike_difficulty());
        intent.putExtra("type", hikeArrayList.get(position).getHike_type());
        intent.putExtra("description", hikeArrayList.get(position).getHike_description());
        intent.putExtra("resource", "HikeList");
        startActivity(intent);

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (R.id.itemView == item.getItemId()) {
            Intent intent = new Intent(HikeListActivity.this, HikeListActivity.class);
            startActivity(intent);
            return true;
        } else if (R.id.itemEnter == item.getItemId()) {

            Intent intent = new Intent(HikeListActivity.this, RegisterActivity.class);
            startActivity(intent);
            return true;
        } else if (R.id.itemDelete == item.getItemId()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HikeListActivity.this);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to delete all hikes?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dataBaseHelper.deleteAllHikes();
                    Toast.makeText(getApplicationContext(), "All hikes have been deleted successfully!", Toast.LENGTH_LONG).show();
                    submitData();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();

            return true;
        } else if (R.id.itemSearch == item.getItemId()) {
            Intent searchIntent = new Intent(HikeListActivity.this, SearchActivity.class);
            startActivity(searchIntent);
            return true;

        } else return super.onOptionsItemSelected(item);
    }

    // ---  Menu Bar --- //
}