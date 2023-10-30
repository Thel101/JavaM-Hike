package com.example.hikermanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_Name = "Hikes";
    public static final String HIKE_TABLE_NAME = "hikes";
    public static final String HIKE_ID = "_id";
    public static final String HIKE_NAME = "hike_name";
    public static final String HIKE_LOCATION = "hike_location";
    public static final String HIKE_LENGTH = "hike_length";
    public static final String HIKE_DATE = "hike_date";
    public static final String HIKE_PARKING ="hike_parking";
    public static final String HIKE_DIFFICULTY = "hike_difficulty";
    public static final String HIKE_DESCRIPTION = "hike_description";

    public static final String OBSERVATION_TABLE= "observations";
    public static final String OBSERVATION_ID= "observation_id";
    public static final String OBSERVATION_TITLE= "observation_title";
    public static final String OBSERVATION_TIME= "observation_time";
    public static final String OBSERVATION_COMMENTS= "observation_comments";

    SQLiteDatabase database;
    public DataBaseHelper(@Nullable Context context) {
        super( context, DB_Name,  null,2);
        database= getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "create table " + HIKE_TABLE_NAME + "(" + HIKE_ID + " integer primary key autoincrement, "
                + HIKE_NAME + " text," + HIKE_LOCATION + " text," + HIKE_LENGTH + " text,"
                + HIKE_DATE + " text," + HIKE_PARKING + " text,"
                + HIKE_DIFFICULTY+ " text," + HIKE_DESCRIPTION + " text)";
        sqLiteDatabase.execSQL(createTable);

        String createTable2 = "create table " + OBSERVATION_TABLE + "(" + OBSERVATION_ID + " integer primary key autoincrement, "
                + OBSERVATION_TITLE + " text," + OBSERVATION_TIME + " text," + OBSERVATION_COMMENTS + " text,"
                + HIKE_ID + " integer,"
                + " FOREIGN KEY (" + HIKE_ID + ")"
                + " REFERENCES " + HIKE_TABLE_NAME + "(" + HIKE_ID + ")"
                + " ON DELETE CASCADE)";

        sqLiteDatabase.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTable = "drop table if exists " + HIKE_TABLE_NAME;
        sqLiteDatabase.execSQL(dropTable);
        String dropTable2 = "drop table if exists " + OBSERVATION_TABLE;
        sqLiteDatabase.execSQL(dropTable2);
    }

    public void saveHikeDetails(Hike hike){
        ContentValues values = new ContentValues();
        values.put(HIKE_NAME, hike.getHike_name());
        values.put(HIKE_LOCATION, hike.getHike_location());
        values.put(HIKE_LENGTH, hike.getHike_length());
        values.put(HIKE_DATE, hike.getHike_date());
        values.put(HIKE_PARKING, hike.getParking_availability());
        values.put(HIKE_DIFFICULTY, hike.getHike_difficulty());
        values.put(HIKE_DESCRIPTION, hike.getHike_description());
        database.insertOrThrow(HIKE_TABLE_NAME, null, values);

    }
    public ArrayList<Hike> getAllHikeInfo() {

        database = getReadableDatabase();
        ArrayList<Hike> hikeArrayList = new ArrayList<>();
        String[] columns = {HIKE_ID, HIKE_NAME, HIKE_LOCATION, HIKE_LENGTH, HIKE_DATE, HIKE_PARKING, HIKE_DIFFICULTY, HIKE_DESCRIPTION};
        Cursor record = database.query(HIKE_TABLE_NAME, columns, null, null, null, null, null);
        while (record.moveToNext()) {
            int id = record.getInt(0);
            String hike_name = record.getString(1);
            String hike_location = record.getString(2);
            String hike_length = record.getString(3);
            String hike_date = record.getString(4);
            String hike_parking = record.getString(5);
            String hike_difficulty = record.getString(6);
            String hike_description = record.getString(7);
            Hike hike = new Hike(id,hike_name, hike_location,hike_date, hike_description,hike_length, hike_parking,hike_difficulty);
            hikeArrayList.add(hike);
        }

        // Close the cursor when done
        record.close();

        return hikeArrayList;
    }
    public  void updateHike(Hike hike){
        ContentValues values= new ContentValues();
        values.put(HIKE_NAME,hike.getHike_name());
        values.put(HIKE_LOCATION, hike.getHike_location());
        values.put(HIKE_LENGTH, hike.getHike_length());
        values.put(HIKE_DATE, hike.getHike_date());
        values.put(HIKE_PARKING, hike.getParking_availability());
        values.put(HIKE_DIFFICULTY, hike.getHike_difficulty());
        values.put(HIKE_DESCRIPTION, hike.getHike_description());
        database.update(HIKE_TABLE_NAME, values,HIKE_ID+"=?", new String[]{String.valueOf(hike.getHike_id())});

    }
    public long deleteHike(long hike_id){

        database.delete(HIKE_TABLE_NAME, HIKE_ID + "=?", new String[]{String.valueOf(hike_id)});
        return (hike_id);

    }

    public void saveObservation(Observation observation){
        ContentValues values= new ContentValues();
        values.put(OBSERVATION_TITLE, observation.getObservation_title());
        values.put(OBSERVATION_TIME, observation.getObservation_time());
        values.put(OBSERVATION_COMMENTS, observation.getObservation_comments());
        values.put(HIKE_ID, observation.getHike_id());
        database.insertOrThrow(OBSERVATION_TABLE, null, values);
    }

    public ArrayList<Observation> getAllObservations(int hike_id){
        database = getReadableDatabase();

        Cursor results = database.query(OBSERVATION_TABLE,
                new String[]{OBSERVATION_TITLE,OBSERVATION_TIME,OBSERVATION_COMMENTS,HIKE_ID},
                HIKE_ID+"=?",new String[]{String.valueOf(hike_id)},
                null,null,null);

        ArrayList<Observation> observationArrayList=new ArrayList<>();

        results.moveToFirst();

        while(!results.isAfterLast()){
            int id = results.getInt(0);
            String title = results.getString(1);
            String time = results.getString(2);
            String comments= results.getString(3);
            int hikeID = results.getInt(4);

            Observation ob = new Observation(title,time,comments,hikeID);//observation record

            observationArrayList.add(ob);// qualification object array

            results.moveToNext();//next row
        } //end of the loop

        return observationArrayList;
    }//end of getAllQualification for each user

}
