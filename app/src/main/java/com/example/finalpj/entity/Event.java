package com.example.finalpj.entity;

import lombok.Data;

@Data
public class Event {

    Integer id;

    String date;

    String title;

    String details;

    public Event(Integer id, String date, String title, String details) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.details = details;
    }

    String image;



}
