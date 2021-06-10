package com.example.finalpj.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

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
public class Event extends LitePalSupport implements Serializable {

    Integer id;
    Long date;
    String title;
    String intro;
    String details;
    int eventType;
    String image;

}
