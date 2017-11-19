package com.example.timotej.whatdo;

import java.util.ArrayList;

/**
 * Created by Timotej on 4. 03. 2017.
 */


public class MyActionList {
    //public static final String LOKACIJA_ID = "lokacija_idXX";
    ArrayList<MyAction> activities;

    MyActionList() {/*
        long date1 = MyMain.getMillis("18.03.2017");
        long date2 = MyMain.getMillis("19.03.2017");
        long date3 = MyMain.getMillis("20.03.2017");

        activities = new ArrayList<>();
        activities.add(new MyAction("Kino","Ogled filma Pr Hostarju",Location.RandomLocation(),new TagList(),MyMain.getRandomDate(),2374232,"19:00","21:00"));
        activities.add(new MyAction("Sprehod","Sprehod ob Dravi",Location.RandomLocation(),new TagList(),MyMain.getRandomDate(),8795795,"15:00",""));
        activities.add(new MyAction("Učenje","Učenje programiranja",Location.RandomLocation(),new TagList(),MyMain.getRandomDate(),46864757,"20:30",""));
        activities.add(new MyAction("Kavica","Kavica  u luftu",Location.RandomLocation(),new TagList(),MyMain.getRandomDate(),1489764264995L,"16:00",""));
        activities.add(new MyAction("Čiščenje ob Dravi","Čiščenje šume ob Dravi",Location.RandomLocation(),new TagList(),MyMain.getRandomDate(),6546546,"16:00",""));
        activities.add(new MyAction("Nogomet","Nogomet na igrišču pri štuku",Location.RandomLocation(),new TagList(),MyMain.getRandomDate(),6546546,"15:00",""));
        /*
        for(int i=0;i<30;i++)
        {
            activities.add(new MyAction("Kavica","Kavica  u luftu",Location.RandomLocation(),new TagList(),MyMain.getRandomDate(),763573567,"13:00",""));
            activities.add(new MyAction("Nogomet","Nogomet na igrišču pri štuku",Location.RandomLocation(),new TagList(),MyMain.getRandomDate(),6546546,"16:00",""));
            activities.add(new MyAction("Kino","Ogled filma Pr Hostarju",Location.RandomLocation(),new TagList(),MyMain.getRandomDate(),2374232,"19:00","21:00"));
        }
        */

    }

    @Override
    public String toString() {
        return "MyActionList{" +
                "activities=" + activities +
                '}';
    }
}
