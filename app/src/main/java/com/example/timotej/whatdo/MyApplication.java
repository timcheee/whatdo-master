package com.example.timotej.whatdo;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Timotej on 4. 03. 2017.
 */

public class MyApplication extends Application {
    MyActionList sez;
    MyActionList pametniSez;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private Classifier mClassifier = null;

    MyAction tmpAction;

    private static final String DATA_MAP = "actions";
    private static final String FILE_NAME = "actions.json";

    LocationManager lm;
    android.location.Location location;
    double myLongitute = 0;
    double myLatitude = 0;

    public MyApplication() throws IOException {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("activities/");
        myRef.keepSynced(true);

        tmpAction = new MyAction();
        sez = new MyActionList();
        pametniSez = new MyActionList();

        AssetManager assetManager = getAssets();
        try {
            mClassifier = (Classifier) weka.core.SerializationHelper.read(assetManager.open("bayes.model"));
            Log.i("Classifier","Classifier uspešno serializiran");
        }catch (Exception e){
            Log.i("Classifier","Napaka pri serializaciji classifierja");
        }

         final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLongitute = location.getLongitude();
                myLatitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        try {
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                //location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                myLongitute = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();
                myLatitude = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
            }
        }
        catch (Exception e){
            Log.i("Napaka","Napaka pri pridobivanju lokacije");
        }
            if(sez.activities != null)
            napolniPametniSeznam();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                sez.activities.clear();

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {

                    tmpAction = messageSnapshot.getValue(MyAction.class);
                    sez.activities.add(tmpAction);
                }
                preveriVeljavnost();
                napolniPametniSeznam();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void preveriVeljavnost() {
        for(MyAction mA : sez.activities)
        {
            long currentDate = System.currentTimeMillis();
            String maDate = MyMain.getStringDate(mA.getStartDate());
            String maTime = mA.getStartTime();

            long actionMillis = MyMain.getActionMillis(maDate,maTime);
            if(actionMillis < currentDate)
            {
                myRef.child(mA.getIdAction()).removeValue();
            }
        }

    }

    private  void napolniPametniSeznam() {
        pametniSez.activities.clear();
        
        final Attribute atributOddaljenost = new Attribute("Oddaljenost");
        final Attribute atributTrajanje = new Attribute("Trajanje");
        final Attribute atributDanVTednu = new Attribute("DanVTednu");
        final Attribute atributcas = new Attribute("cas");
        final Attribute atributtag = new Attribute("tag");
        final Attribute atributCenaVstopnice = new Attribute("cenaVstopnice");
        final List<String> classes = new ArrayList<String>() {
            {
                add("N"); // cls nr 1
                add("DP"); // cls nr 2
                add("P"); // cls nr 3
            }
        };

        ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2) {
            {
                add(atributOddaljenost);
                add(atributTrajanje);
                add(atributDanVTednu);
                add(atributcas);
                add(atributtag);
                add(atributCenaVstopnice);
                Attribute attributeClass = new Attribute("@@class@@", classes);
                add(attributeClass);
            }
        };

        Instances dataUnpredicted = new Instances("TestInstances",
                attributeList, 1);
        // last feature is target variable
        dataUnpredicted.setClassIndex(dataUnpredicted.numAttributes() - 1);

        Random r = new Random(); //za oddaljenost in trajanje, za testiranje
        Calendar c = Calendar.getInstance();

        for (MyAction currAction: sez.activities) {
            c.setTimeInMillis(currAction.getStartDate());
            String tmpCas = currAction.getStartTime();
            tmpCas = tmpCas.substring(0,2);

            //Računanle oddaljenosti v km za vsako aktivnost posebej
            Location a = new Location("a");
            Location b = new Location("b");
            a.setLatitude(myLatitude);
            a.setLongitude(myLongitute);
            b.setLatitude(currAction.getLoc().getY());
            b.setLongitude(currAction.getLoc().getX());
            float oddaljenostFLOAT = a.distanceTo(b)/1000;
            int oddaljenost = (int) oddaljenostFLOAT;
            int trajanjeRandom = r.nextInt(180); // TODO
            int casInt = Integer.parseInt(tmpCas); //Da dobim integer 0-24 iz časa
            int danVTednuInt = c.get(Calendar.DAY_OF_WEEK); //Da dobim dan v tednu iz datuma v int
            final ActionForWeka tmpAction = new ActionForWeka(oddaljenost,trajanjeRandom,danVTednuInt,casInt,0,0);

            DenseInstance newInstance = new DenseInstance(dataUnpredicted.numAttributes()) {
                {
                    setValue(atributOddaljenost, tmpAction.oddaljenost);
                    setValue(atributTrajanje, tmpAction.trajanje);
                    setValue(atributDanVTednu, tmpAction.danVTednu);
                    setValue(atributcas, tmpAction.cas);
                    setValue(atributtag, tmpAction.tag);
                    setValue(atributCenaVstopnice, tmpAction.cenaVstopnice);
                }
            };

            newInstance.setDataset(dataUnpredicted);

            try {
                double result = mClassifier.classifyInstance(newInstance);
                Log.i("RezultatKlasifikacije",""+result);
                if(result == 2.0)
                    pametniSez.activities.add(currAction);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
