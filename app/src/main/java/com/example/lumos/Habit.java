package com.example.lumos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Habit {

    private String name;
    private int days;
    private String des;
    private Date cday;
    private Date lday;
    private Date sday;
    private int state;

    public String getName() {
        return name;
    }
    public String getDes() {
        return des;
    }
    public int getMax() {
        return days;
    }
    public int getState(){ return state;}
    public int getProgress(){
        Calendar c1=Calendar.getInstance();
        c1.setTime(sday);//把获取的入住时间年月日放入Calendar中
        Calendar c2=Calendar.getInstance();
        c2.setTime(lday);//把获取的退房时间年月日放入Calendar中

        int progress = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) + 1;
        return progress;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return name + ", " + phone + ", " + salary;
//    }

    public Habit(String name, int days, String des,Date cday,Date lday,Date sday,int state) {
        super();
        this.name = name;
        this.days = days;
        this.cday = cday;
        this.lday = lday;
        this.sday = sday;
        this.des = des;
        this.state = state;


    }

    public String getLday(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        String str=sdf.format(this.lday);
        return str;
    }

}
