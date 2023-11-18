package com.example.hikermanagementapp;

public class Observation {
    public Integer observation_id;
    public String observation_title;
    public String observation_time;
    public String observation_comments;
    public byte [] observation_photo;

    public int hike_id;

    public byte[] getObservation_photo() {
        return observation_photo;
    }

    public Observation(String observation_title, String observation_time, String observation_comments, byte[] observation_photo, int hike_id) {

        this.observation_title = observation_title;
        this.observation_time = observation_time;
        this.observation_comments = observation_comments;
        this.observation_photo = observation_photo;
        this.hike_id = hike_id;
    }


    public Observation(Integer observation_id, String observation_title, String observation_time, String observation_comments) {
        this.observation_id = observation_id;
        this.observation_title = observation_title;
        this.observation_time = observation_time;
        this.observation_comments = observation_comments;
    }

    public Observation(Integer observation_id, String observation_title, String observation_time, String observation_comments, byte[] observation_photo ) {
        this.observation_id = observation_id;
        this.observation_title = observation_title;
        this.observation_time = observation_time;
        this.observation_comments = observation_comments;
        this.observation_photo = observation_photo;
    }

    public Observation(String observation_title, String observation_time, String observation_comments, int hike_id) {
        this.observation_title = observation_title;
        this.observation_time = observation_time;
        this.observation_comments = observation_comments;
        this.hike_id = hike_id;
    }

    public Integer getObservation_id() {
        return observation_id;
    }

    public String getObservation_title() {
        return observation_title;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public String getObservation_comments() {
        return observation_comments;
    }

    public int getHike_id() {
        return hike_id;
    }
}
