package com.example.timotej.whatdo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;
import java.text.*;

/**
 * Created by Timotej on 3. 03. 2017.
 */

public class MyMain{


    public static void main(String args[]){

        String string_date = "12.02.2012";

        MyAction activity = new MyAction("Tek","Tek okoli maribora",new Location(10,20,""),new TagList(),getMillis(string_date),getMillis(string_date),"16:00","19:00");
        System.out.print(activity.toString());

        MyActionList seznamAktivnosti;
        seznamAktivnosti = new MyActionList();
        System.out.print("\n"+seznamAktivnosti);
    }

    //Funkcije
    public static long getMillis(String date) {
        long millis = 0;
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date d = f.parse(date);
            millis = d.getTime();
        } catch (ParseException e) {
            System.out.print("Eksepšn");
            e.printStackTrace();
        }
        return  millis;
    }
    public static String getStringDate(long millis) {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        Date date=new Date(millis);
        String dateText = f.format(date);

        return dateText;
    }
    public static long getRandomDate() {
        String randomDatum = "";
        int dan = 1 + (int)(Math.random() * ((31 - 1) + 1));
        if(dan<10)
        { randomDatum += "0"+dan+"."; }
        else { randomDatum += dan+"."; }
        int mesec = 1 + (int)(Math.random() * ((12 - 1) + 1));
        if(mesec<10)
        { randomDatum += "0"+mesec; }
        else { randomDatum += mesec; }
        randomDatum += ".2017";

        return getMillis(randomDatum);
    }
    public static long getActionMillis(String date, String time)
    {
        long actionMillis = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        formatter.setLenient(false);

        String oldTime = date+", "+time;
        Date oldDate = null;
        try {
            oldDate = formatter.parse(oldTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        actionMillis = oldDate.getTime();

        return actionMillis;
    }

}
