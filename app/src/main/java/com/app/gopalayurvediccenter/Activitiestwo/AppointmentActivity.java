package com.app.gopalayurvediccenter.Activitiestwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.gopalayurvediccenter.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppointmentActivity extends AppCompatActivity {
    private static final String TAG = "AppCompatActivity";
    private static AppCompatTextView tvMonth;
    private static ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
//        long dAt = Long.parseLong(orderDataClass.getOrderTime());
//        int month = getDate(dAt).get(Calendar.MONTH);
//        String dayToday = DateFormat.format("EEEE", getDate(dAt)).toString();
//        String am_pm = null;
        tvMonth = findViewById(R.id.tvMonth);
        ivBack = findViewById(R.id.ivBack);
        Date c = Calendar.getInstance().getTime();

        Long dAt = System.currentTimeMillis();
        String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int month = getDate(dAt).get(Calendar.MONTH);
        int date = getDate(dAt).get(Calendar.DATE);
        Log.e(TAG, "Current Date"+months[month]+ " " +date);

        tvMonth.setText(months[month]);
        ivBack.setOnClickListener(view -> {
            onBackPressed();

        });
    }

    private Calendar getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        return cal;
    }
}