package com.example.finalpj.entity;

import org.litepal.crud.LitePalSupport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Event extends LitePalSupport {

    Integer id;
    Long date;
    String title;
    String intro;
    String details;
    String image;

    public Event(Integer id, Long date, String title, String details) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.details = details;
    }
}
