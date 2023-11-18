package com.example.hikermanagementapp;

public class Hike {
    public Integer hike_id;
    public String hike_name;
    public String hike_location;
    public String hike_country;
    public String hike_date;
    public String hike_description;

    public String hike_length;
    public String parking_availability;
    
    public String hike_difficulty;
    public String hike_type;

    public String getHike_country() {
        return hike_country;
    }

    public String getHike_type() {
        return hike_type;
    }

    public Hike(Integer hike_id,String hike_name, String hike_location, String hike_date, String hike_length) {
        this.hike_id= hike_id;
        this.hike_name = hike_name;
        this.hike_location = hike_location;
        this.hike_date = hike_date;
        this.hike_length = hike_length;
    }

    public Hike(String hike_name, String hike_location, String hike_country, String hike_date, String hike_description, String hike_length, String parking_availability, String hike_difficulty, String hike_type) {
        this.hike_name = hike_name;
        this.hike_location = hike_location;
        this.hike_country = hike_country;
        this.hike_date = hike_date;
        this.hike_description = hike_description;
        this.hike_length = hike_length;
        this.parking_availability = parking_availability;
        this.hike_difficulty = hike_difficulty;
        this.hike_type = hike_type;
    }

    public Hike(Integer hike_id, String hike_name, String hike_location, String hike_country, String hike_date, String hike_description, String hike_length, String parking_availability, String hike_difficulty, String hike_type) {
        this.hike_id = hike_id;
        this.hike_name = hike_name;
        this.hike_location = hike_location;
        this.hike_country = hike_country;
        this.hike_date = hike_date;
        this.hike_description = hike_description;
        this.hike_length = hike_length;
        this.parking_availability = parking_availability;
        this.hike_type = hike_type;
        this.hike_difficulty = hike_difficulty;
    }

    public Integer getHike_id() {
        return hike_id;
    }

    public String getHike_name() {
        return hike_name;
    }

    public String getHike_location() {
        return hike_location;
    }

    public String getHike_date() {
        return hike_date;
    }

    public String getHike_description() {
        return hike_description;
    }

    public String getHike_length() {
        return hike_length;
    }

    public String getParking_availability() {
        return parking_availability;
    }

    public String getHike_difficulty() {
        return hike_difficulty;
    }
}
