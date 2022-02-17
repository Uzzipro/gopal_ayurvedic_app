package com.app.gopalayurvediccenter.Activitiestwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.app.gopalayurvediccenter.R;
import com.app.gopalayurvediccenter.Utils.Constants;

public class AppointmentBookedActivity extends AppCompatActivity {
    private static final String TAG = "AppointmentBooked";
    private String time, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booked);
        if(getIntent() != null &&  getIntent().hasExtra(Constants.time) || getIntent().hasExtra(Constants.date))
        {
            Intent intent = getIntent();
            time = intent.getStringExtra(Constants.time);
            date = intent.getStringExtra(Constants.date);
            Log.e(TAG, "onCreate: "+time+"\n"+date);
        }
        else {
            Log.e(TAG, "onCreate: no intent found");
        }
    }
}