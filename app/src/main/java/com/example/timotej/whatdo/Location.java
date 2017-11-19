package com.example.timotej.whatdo;

/**
 * Created by Timotej on 3. 03. 2017.
 */
import java.util.Random;

public class Location {
    double x,y;
    String name; //a lahka dobim naslov, če mam x in y?

    public Location(){};

    public Location(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public static Location RandomLocation() {
        Location temp = new Location(new Random().nextInt(100)+1,new Random().nextInt(100)+1,"");
        return temp;
    }


    public double getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "x: " + x +
                ", y:" + y;
    }
}
