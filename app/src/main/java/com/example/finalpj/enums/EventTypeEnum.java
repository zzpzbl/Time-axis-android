package com.example.finalpj.enums;

public enum EventTypeEnum {

    ORDINARY(0, "123"),
    YEAR(1, "123"),
    MONTH(2, "234");

    int type;
    String desc;

    EventTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
