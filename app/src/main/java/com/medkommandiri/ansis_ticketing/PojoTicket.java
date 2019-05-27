package com.medkommandiri.ansis_ticketing;


public class PojoTicket {
    private String realname, title, description, date, time, status, id_ticket;


    public PojoTicket() {

    }

    public PojoTicket(String realname, String title, String description, String date, String time, String status, String id_ticket) {
        this.realname = realname;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.status = status;
        this.id_ticket = id_ticket;
    }

    public String getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(String id_ticket) {
        this.id_ticket = id_ticket;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
