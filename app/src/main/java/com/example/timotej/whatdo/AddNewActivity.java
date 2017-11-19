package com.example.timotej.whatdo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddNewActivity extends AppCompatActivity {
    EditText etTitle;
    TextView twTime;
    TextView twDate;
    MyApplication app;
    Button addButton;
    Button backButton;
    EditText etDescripion;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        Bundle extras = getIntent().getExtras();
        final Double lang = extras.getDouble("lang");
        final Double lat = extras.getDouble("lat");


        twTime = (TextView) findViewById(R.id.textView);
        //twTime.setText(lang.toString()+" - "+lat.toString());
        //twDate = (TextView) findViewById(R.id.twDate);
        app = (MyApplication) getApplication();
        addButton = (Button) findViewById(R.id.buttonAdd);
        backButton = (Button) findViewById(R.id.buttonBack);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescripion = (EditText) findViewById(R.id.etDescription);

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());


        try {
            addresses = geocoder.getFromLocation(lat, lang, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FAK");
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpD;
                String tmpM;
                String tmpY;
                String dateFormat;
                // Do something with the time chosen by the user
               if(dateD < 10)
                   tmpD = "0"+dateD;
                else
                    tmpD = ""+dateD;
                if(dateM < 10)
                    tmpM = "0"+(dateM+1);
                else
                    tmpM = ""+dateM;
                tmpY = ""+dateY;
                dateFormat = tmpD+"."+tmpM+"."+tmpY;
                //twDate.setText("Date "+dateFormat);
                String tmptH;
                String tmptM;
                if(timeH < 10)
                    tmptH = "0"+timeH;
                else
                    tmptH = ""+timeH;
                if(timeM < 10)
                    tmptM = "0"+timeM;
                else
                    tmptM = ""+timeM;
                String timeFormat;
                timeFormat = tmptH +":"+ tmptM;
                //twTime.setText("Time "+timeFormat);


                MyAction currentAction = new MyAction(etTitle.getText().toString(),etDescripion.getText().toString(),new Location(lang,lat,address),new TagList(),MyMain.getMillis(dateFormat),2374232,timeFormat,"");
                app.sez.activities.add(currentAction);
                app.save();

                Map<String,Object> activityMap = new HashMap<>();
                activityMap.put(currentAction.getIdAction(),currentAction);
                app.myRef.updateChildren(activityMap);

                startActivity(new Intent(AddNewActivity.this,ListActivity.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Load menu
                startActivity(new Intent(AddNewActivity.this, ListActivity.class));

            }
        });
    }
    static int timeH;
    static int timeM;
    static int dateD;
    static int dateM;
    static int dateY;


    //cas
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(),2, this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timeH = hourOfDay;
            timeM = minute;
        }
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        //newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light);
        newFragment.show(getFragmentManager(),"timePicker");
    }

    //datum
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            dateD = day;
            dateM = month;
            dateY = year;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    public void openMap(View v) {
        startActivity(new Intent(AddNewActivity.this, AddLocationActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(AddNewActivity.this, ListActivity.class));
    }
}
