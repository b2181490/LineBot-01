package com.example.linebot;

public class PreExam {
    private int weekKey;
    private String week;
    private int time;
    private String subject;

    public PreExam(int weekKey,String week,int time,String subject){
        this.weekKey=weekKey;
        this.subject=subject;
        this.time=time;
        this.week=week;
    }

    public void print(){
        System.out.println(week+time+subject);
    }

    public int getWeekKey(){return weekKey;}
    public String getWeek(){
        return week;
    }
    public int getTime(){
        return time;
    }
    public String getSubject(){
        return subject;
    }
}
