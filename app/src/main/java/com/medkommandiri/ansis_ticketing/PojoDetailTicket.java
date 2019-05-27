package com.medkommandiri.ansis_ticketing;

public class PojoDetailTicket {
    String description, realname, datetime;

    public PojoDetailTicket(String description, String realname, String datetime) {
        this.description = description;
        this.realname = realname;
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
