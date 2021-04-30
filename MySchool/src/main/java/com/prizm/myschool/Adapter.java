package com.prizm.myschool;

public class Adapter {
    private String date;
    private String schedule;

    public Adapter(String _date, String _schedule) {
        this.date = _date;
        this.schedule = _schedule;
    }

    public String getdate() {
        return date;
    }

    public String getschedule() {
        return schedule;
    }


}