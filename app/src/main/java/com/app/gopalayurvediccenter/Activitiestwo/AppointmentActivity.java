package com.app.gopalayurvediccenter.Activitiestwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.gopalayurvediccenter.Dataclass.AppointmentDto;
import com.app.gopalayurvediccenter.R;
import com.app.gopalayurvediccenter.Utils.Constants;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class AppointmentActivity extends AppCompatActivity {
    private static final String TAG = "AppCompatActivity";
    private static AppCompatTextView tvMonth;
    private static ImageView ivBack;
    private SmartMaterialSpinner timeSpinner;
    private DatePicker date_picker;
    private EditText etFullName, etContactNumber;
    private Button btBookAppointment;
    private String strFullName, strContactNumber, strSelectedTime, strSelectedDate, phNumber;
    private DatabaseReference dbRefAppointment;

//    private DatePicker date_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        timeSpinner = findViewById(R.id.timeSpinner);
        tvMonth = findViewById(R.id.tvMonth);
        ivBack = findViewById(R.id.ivBack);
        date_picker = findViewById(R.id.date_picker);
        btBookAppointment = findViewById(R.id.btBookAppointment);
        etFullName = findViewById(R.id.etFullName);
        etContactNumber = findViewById(R.id.etContactNumber);
        phNumber = getSharedPreferences(Constants.ACCESS_PREFS, Context.MODE_PRIVATE).getString(Constants.PH_NUMBER, "nophNumberfound");
        etContactNumber.setText("+91"+phNumber);
        dbRefAppointment = FirebaseDatabase.getInstance().getReference("appointment_table");

        Date c = Calendar.getInstance().getTime();
        Long dAt = System.currentTimeMillis();
        String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int month = getDate(dAt).get(Calendar.MONTH);
        int date = getDate(dAt).get(Calendar.DATE);
        //setting minimum date for the date picker
        date_picker.setMinDate(System.currentTimeMillis() - 1000);

        tvMonth.setText(months[month]);
        initSpinner();
        ivBack.setOnClickListener(view -> {
            onBackPressed();
        });

        btBookAppointment.setOnClickListener(view -> {
            bookAppointment();
        });
    }


    private void printToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void bookAppointment() {
//        strFullName = etFullName.getText().toString();
//        strContactNumber = etContactNumber.getText().toString();
        int month = date_picker.getMonth() + 1;
        strSelectedDate = date_picker.getDayOfMonth() + "_"+month+"_"+date_picker.getYear();
            strSelectedTime = timeSpinner.getSelectedItem().toString();

        Intent i = new Intent(AppointmentActivity.this, AppointmentBookedActivity.class);
        i.putExtra(Constants.time, strSelectedTime);
        i.putExtra(Constants.date, strSelectedDate);
        startActivity(i);
//        if (strFullName.isEmpty()) {
//            printToast("Please enter your Full name");
//        } else if (strContactNumber.isEmpty()) {
//            printToast("Please enter your Contact number");
//        } else if(timeSpinner.getSelectedItem() == null)
//        {
//            printToast("Please select a time for Appointment");
//        }
//        else {
//            strSelectedDate = date_picker.getDayOfMonth() + "_"+month+"_"+date_picker.getYear();
//            strSelectedTime = timeSpinner.getSelectedItem().toString();
//            printToast("Full name: " + strFullName + " \n" + "Contact number: " + strContactNumber + "\n" +"Selected time: "+strSelectedTime);
//            AppointmentDto appointmentDto = new AppointmentDto();
//            appointmentDto.setDate(strSelectedDate);
//            appointmentDto.setTime(strSelectedTime);
//            appointmentDto.setFullName(strFullName);
//            appointmentDto.setContactNumber(strContactNumber);
//            dbRefAppointment.child(phNumber).push().setValue(appointmentDto);
//        }
    }

    private void initSpinner() {
        ArrayList<String> timeArray;
        timeArray = new ArrayList<>();
        timeArray.add("Select time");
        timeArray.add("10:00AM");
        timeArray.add("11:00AM");
        timeArray.add("12:00PM");
        timeArray.add("01:00PM");
        timeArray.add("01:30PM");
        timeArray.add("02:00PM");
        timeArray.add("02:30PM");
        timeArray.add("03:00PM");
        timeArray.add("04:00PM");
        timeSpinner.setItem(timeArray);
    }

    private Calendar getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        return cal;
    }
}