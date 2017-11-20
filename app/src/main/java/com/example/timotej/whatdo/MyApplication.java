package com.example.timotej.whatdo;

import android.app.Application;
import android.app.DownloadManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Timotej on 4. 03. 2017.
 */

public class MyApplication extends Application {
    MyActionList sez;
    FirebaseDatabase database;
    DatabaseReference myRef;
    //DatabaseReference activitiesRef;
    //DatabaseReference myRef = database.getReference("activities/");

    MyAction tmpAction;

    private static final String DATA_MAP = "actions";
    private static final String FILE_NAME = "actions.json";
    @Override
    public void onCreate() {
        //myRef.setValue("bla");
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("activities/");
        myRef.keepSynced(true);

        //activitiesRef = FirebaseDatabase.getInstance().getReference("activities");
        //activitiesRef.keepSynced(true);

        tmpAction = new MyAction();
        sez = new MyActionList();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                sez.activities.clear();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    tmpAction = messageSnapshot.getValue(MyAction.class);
                    sez.activities.add(tmpAction);
                }
                //sez = dataSnapshot.getValue(MyActionList.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //app.save();
        //sez = new MyActionList();
        if(!load())
        {
            sez = new MyActionList();
            //sez =
        }
    }
    public void removeAction(MyAction a){
        for(int i=0;i<sez.activities.size();i++)
        {
            if(sez.activities.get(i).equals(a))
            {
                sez.activities.remove(i);
            }
        }
    }


    public MyAction getActionById(String id) {
        for (int i = 0; i < sez.activities.size(); i++) {
            if (sez.activities.get(i).getIdAction().equals(id)) {
                return sez.activities.get(i);
            }
        }
        return null;
    }

    public boolean save() {
        File file = new File(this.getExternalFilesDir(DATA_MAP), ""
                + FILE_NAME);

        return ApplicationJson.save(sez,file);
    }
    public boolean load(){
        File file = new File(this.getExternalFilesDir(DATA_MAP), ""
                + FILE_NAME);
        MyActionList tmp = ApplicationJson.load(file);
        if (tmp!=null) sez = tmp;
        else return false;
        return true;
    }
}
