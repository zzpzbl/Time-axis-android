package com.example.finalpj.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Event {

    Integer id;

    Date date;

    String title;

    String details;

    String imageUrl;

}
