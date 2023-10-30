package com.example.hikermanagementapp;

public class Observation {
    public long observation_id;
    public String observation_title;
    public String observation_time;
    public String observation_comments;

    public int hike_id;

    public Observation(long observation_id, String observation_title, String observation_time, String observation_comments) {
        this.observation_id = observation_id;
        this.observation_title = observation_title;
        this.observation_time = observation_time;
        this.observation_comments = observation_comments;
    }

    public Observation(String observation_title, String observation_time, String observation_comments, int hike_id) {
        this.observation_title = observation_title;
        this.observation_time = observation_time;
        this.observation_comments = observation_comments;
        this.hike_id = hike_id;
    }

    public long getObservation_id() {
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
