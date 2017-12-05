package com.example.timotej.whatdo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    MyApplication app;
    TextView title;
    TextView date;
    TextView time;
    TextView description;
    TextView location;
    MyAction action;

    Button saveButton;
    Button backButton;
    ImageButton mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (MyApplication) getApplication();
        title = (TextView) findViewById(R.id.textViewName);
        date = (TextView) findViewById(R.id.textViewDate);
        time = (TextView) findViewById(R.id.textViewTime);
        description = (TextView) findViewById(R.id.textViewDescription);
        location = (TextView) findViewById(R.id.textViewLocation);

        saveButton = (Button) findViewById(R.id.saveButton);
        backButton = (Button) findViewById(R.id.backButton);
        mapButton = (ImageButton) findViewById(R.id.mapButton);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Save activity
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(false);

                dialog.setMessage("Delete this entry?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        app.myRef.child(action.idAction).removeValue();
                        startActivity(new Intent(MainActivity.this, ListActivity.class));
                    }
                })
                        .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Action for "Cancel".
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();

            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Load menu
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                Bundle extras = new Bundle();
                extras.putDouble("lang",action.getLoc().getX());
                extras.putDouble("lat",action.getLoc().getY());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    void setAction(String id) {
        action = app.getActionById(id);
        System.out.println("Action: " + action.getName());
        update(action);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            System.out.println("setaction: " + extras.getString("neki"));
            setAction(extras.getString("neki"));
        } else {
            System.out.println("Nič ni v extras!");
        }
    }

    private void update(MyAction action) {
        title.setText("" + action.getName());
        date.setText("Date: " + MyMain.getStringDate(action.getStartDate()));
        time.setText("Time: " + action.getStartTime());
        description.setText("" + action.getDescription());
        location.setText("Lokacija: "+action.getLoc().name);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(MainActivity.this, ListActivity.class));
    }
}
