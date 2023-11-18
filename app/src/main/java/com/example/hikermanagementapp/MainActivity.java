package com.example.hikermanagementapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting components with ID
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataBaseHelper= new DataBaseHelper(this);

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

            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            return true;
        }
        else if(R.id.itemDelete==item.getItemId()){
            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to delete all hikes?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dataBaseHelper.deleteAllHikes();
                    Toast.makeText(getApplicationContext(),"All hikes have been deleted successfully!", Toast.LENGTH_LONG).show();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();

            return true;
        }
        else if (R.id.itemSearch==item.getItemId()) {
            Intent searchIntent= new Intent(MainActivity.this, SearchActivity.class);
            startActivity(searchIntent);
            return true;
            
        } else return super.onOptionsItemSelected(item);
    }
    // ---  Menu Bar --- //

}