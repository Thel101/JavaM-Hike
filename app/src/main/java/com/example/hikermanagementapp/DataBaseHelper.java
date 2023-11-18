package com.example.hikermanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    public static final String HIKE_COUNTRY = "hike_country";
    public static final String HIKE_LENGTH = "hike_length";
    public static final String HIKE_DATE = "hike_date";
    public static final String HIKE_PARKING ="hike_parking";
    public static final String HIKE_DIFFICULTY = "hike_difficulty";
    public static final String HIKE_TYPE= "hike_type";
    public static final String HIKE_DESCRIPTION = "hike_description";


    public static final String OBSERVATION_TABLE= "observations";
    public static final String OBSERVATION_ID= "observation_id";
    public static final String OBSERVATION_TITLE= "observation_title";
    public static final String OBSERVATION_TIME= "observation_time";
    public static final String OBSERVATION_COMMENTS= "observation_comments";
    public static final String OBSERVATION_PHOTO = "observation_photo";

    SQLiteDatabase database;
    public DataBaseHelper(@Nullable Context context) {
        super( context, DB_Name,  null,2);
        database= getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "create table " + HIKE_TABLE_NAME + "(" + HIKE_ID + " integer primary key autoincrement, "
                + HIKE_NAME + " text," + HIKE_LOCATION + " text," + HIKE_COUNTRY+ " text," + HIKE_LENGTH + " text,"
                + HIKE_DATE + " text," + HIKE_PARKING + " text,"
                + HIKE_DIFFICULTY+ " text," + HIKE_TYPE+ " text," + HIKE_DESCRIPTION + " text)";
        sqLiteDatabase.execSQL(createTable);

        String createTable2 = "create table " + OBSERVATION_TABLE + "(" + OBSERVATION_ID + " integer primary key autoincrement, "
                + OBSERVATION_TITLE + " text," + OBSERVATION_TIME + " text," + OBSERVATION_COMMENTS + " text," + OBSERVATION_PHOTO + " blob,"
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
        values.put(HIKE_COUNTRY, hike.getHike_country());
        values.put(HIKE_LENGTH, hike.getHike_length());
        values.put(HIKE_DATE, hike.getHike_date());
        values.put(HIKE_PARKING, hike.getParking_availability());
        values.put(HIKE_DIFFICULTY, hike.getHike_difficulty());
        values.put(HIKE_TYPE, hike.getHike_type());
        values.put(HIKE_DESCRIPTION, hike.getHike_description());
        database.insertOrThrow(HIKE_TABLE_NAME, null, values);

    }
    public ArrayList<Hike> getAllHikeInfo() {

        database = getReadableDatabase();
        ArrayList<Hike> hikeArrayList = new ArrayList<>();
        String[] columns = {HIKE_ID, HIKE_NAME, HIKE_LOCATION, HIKE_COUNTRY,HIKE_LENGTH, HIKE_DATE, HIKE_PARKING, HIKE_DIFFICULTY, HIKE_TYPE, HIKE_DESCRIPTION};
        Cursor record = database.query(HIKE_TABLE_NAME, columns, null, null, null, null, null);
        while (record.moveToNext()) {
            int id = record.getInt(0);
            String hike_name = record.getString(1);
            String hike_location = record.getString(2);
            String hike_country = record.getString(3);
            String hike_length = record.getString(4);
            String hike_date = record.getString(5);
            String hike_parking = record.getString(6);
            String hike_difficulty = record.getString(7);
            String hike_type = record.getString(8);
            String hike_description = record.getString(9);
            Hike hike = new Hike(id,hike_name, hike_location,hike_country,hike_date, hike_description,hike_length, hike_parking,hike_difficulty, hike_type);
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
        values.put(HIKE_COUNTRY, hike.getHike_country());
        values.put(HIKE_LENGTH, hike.getHike_length());
        values.put(HIKE_DATE, hike.getHike_date());
        values.put(HIKE_PARKING, hike.getParking_availability());
        values.put(HIKE_DIFFICULTY, hike.getHike_difficulty());
        values.put(HIKE_TYPE, hike.getHike_type());
        values.put(HIKE_DESCRIPTION, hike.getHike_description());
        database.update(HIKE_TABLE_NAME, values,HIKE_ID+"=?", new String[]{String.valueOf(hike.getHike_id())});

    }
    public long deleteHike(int hike_id){

        database.delete(HIKE_TABLE_NAME, HIKE_ID + "=?", new String[]{String.valueOf(hike_id)});
        return (hike_id);

    }
    public void  deleteAllHikes(){
        database.delete(HIKE_TABLE_NAME, null, null);
    }
    //search Hike
    public ArrayList<Hike> searchHike(String key){
        database = getReadableDatabase();
        ArrayList<Hike> hikeArrayList= new ArrayList<>();
        String[] columns = {HIKE_ID, HIKE_NAME, HIKE_LOCATION, HIKE_COUNTRY,HIKE_LENGTH, HIKE_DATE, HIKE_PARKING, HIKE_DIFFICULTY, HIKE_TYPE, HIKE_DESCRIPTION};
        Cursor results = database.query(HIKE_TABLE_NAME,columns,
                HIKE_NAME+" LIKE ? OR "+HIKE_LOCATION+" LIKE ? OR "+HIKE_DATE+ " LIKE ?",
                new String[]{"%"+key+"%","%"+key+"%","%"+key+"%"},
                null,null, null);


        results.moveToFirst();

        while(!results.isAfterLast()){
            Integer id= results.getInt(0);
            String name = results.getString(1);
            String location = results.getString(2);
            String country = results.getString(3);
            String length = results.getString(4);
            String date = results.getString(5);
            String parking = results.getString(6);
            String difficulty = results.getString(7);
            String type = results.getString(8);
            String description = results.getString(9);

            Hike hike= new Hike(id,name, location,country,date, length, parking, difficulty,type, description);
            hikeArrayList.add(hike);
            results.moveToNext();//next row
        }//end of the loop

        return hikeArrayList;
    }
    public ArrayList<Hike> searchSpecificHike(String key, String key1){
        database = getReadableDatabase();
        ArrayList<Hike> hikeArrayList= new ArrayList<>();
        String[] columns = {HIKE_ID, HIKE_NAME, HIKE_LOCATION, HIKE_COUNTRY,HIKE_LENGTH, HIKE_DATE, HIKE_PARKING, HIKE_DIFFICULTY, HIKE_TYPE, HIKE_DESCRIPTION};

        Cursor results = database.query(HIKE_TABLE_NAME,columns,
                HIKE_LOCATION+" LIKE ? AND "+HIKE_DATE+" LIKE ? ",
                new String[]{"%"+key+"%","%"+key1+"%"},
                null,null, null);


        results.moveToFirst();

        while(!results.isAfterLast()){

            int id= results.getInt(0);
            String hike_name = results.getString(1);
            String hike_location = results.getString(2);
            String hike_country = results.getString(3);
            String hike_length = results.getString(4);
            String hike_date = results.getString(5);
            String hike_parking = results.getString(6);
            String hike_difficulty = results.getString(7);
            String hike_type = results.getString(8);
            String hike_description = results.getString(9);
            Hike hike = new Hike(id,hike_name, hike_location,hike_country,hike_date, hike_description,hike_length, hike_parking,hike_difficulty, hike_type);
            hikeArrayList.add(hike);
            results.moveToNext();//next row
        }//end of the loop
        results.close();
        return hikeArrayList;
    }

    public void saveObservation(Observation observation){
        ContentValues values= new ContentValues();
        values.put(OBSERVATION_TITLE, observation.getObservation_title());
        values.put(OBSERVATION_TIME, observation.getObservation_time());
        values.put(OBSERVATION_COMMENTS, observation.getObservation_comments());
        values.put(OBSERVATION_PHOTO, observation.getObservation_photo().toString());
        values.put(HIKE_ID, observation.getHike_id());
        database.insertOrThrow(OBSERVATION_TABLE, null, values);
    }

    public ArrayList<Observation> getAllObservations(int hike_id){
        database = getReadableDatabase();

        Cursor observation_results = database.query(OBSERVATION_TABLE,
                new String[]{OBSERVATION_ID,OBSERVATION_TITLE,OBSERVATION_TIME,OBSERVATION_COMMENTS, OBSERVATION_PHOTO},
                HIKE_ID+"=?",new String[]{String.valueOf(hike_id)},
                null,null,null);

        ArrayList<Observation> observationArrayList=new ArrayList<>();

       observation_results.moveToFirst();

        while(!observation_results.isAfterLast()){
            int observationID= observation_results.getInt(0);
            String title = observation_results.getString(1);
            String time = observation_results.getString(2);
            String comments= observation_results.getString(3);
            byte[] byteArray = observation_results.getBlob(4);

            Observation ob = new Observation(observationID,title,time,comments, byteArray);//observation record

            observationArrayList.add(ob);// qualification object array

            observation_results.moveToNext();//next row
        } //end of the loop
        observation_results.close();
        return observationArrayList;
    }//end of getAllQualification for each user
    public long deleteObservation(long obs_id){

        database.delete(OBSERVATION_TABLE, OBSERVATION_ID + "=?", new String[]{String.valueOf(obs_id)});
        return (obs_id);

    }
    public  void updateObservation(Observation observation){
        ContentValues values= new ContentValues();
        values.put(OBSERVATION_TITLE,observation.getObservation_title());
        values.put(OBSERVATION_TIME, observation.getObservation_time());
        values.put(OBSERVATION_COMMENTS, observation.getObservation_comments());
        database.update(OBSERVATION_TABLE, values,OBSERVATION_ID+"=?", new String[]{String.valueOf(observation.getObservation_id())});

    }

}
