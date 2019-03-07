package com.example.mattm.calendar.Views;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddEventActivity extends AppCompatActivity
{
    // Private Properties
    private AWSConnection awsConnection;
    
    private Date dateObject;
    
    private OnDateSetListener mDateSetListener;

    private String day;
    private String month;
    private String year;
    
    private TextView mDisplayDate;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_Dark);
        setContentView(R.layout.activity_add_event);

        initializeDate();
        showDate();
        setUpDatePicker();

        try
        {
            awsConnection = AWSConnection.getCurrentInstance(null);
        }
        catch (Exception e)
        {
            // TODO: UI - Show error message to User in a way they will understand for different error messages

            // Temporary solution
            e.printStackTrace();
            Toast.makeText(this, "Unable to connect to network", Toast.LENGTH_LONG).show();
        }

    }
    
    // Event Handlers
    public void classButton_Clicked(View view)
    {
        // Get Information
        awsConnection.storeAssignment(
                getIntent().getStringExtra("ClassName"),
                String.format("%s-%s-%s", GetYear(), GetMonth(), GetDay()),
                GetEventName(),
                GetDescription()
        );

        Intent intentHome = new Intent(this, MainActivity.class);
        startActivity(intentHome);
    }
    
    // Private Methods
    
    /**
     * Sets the initial date to current date
     */
    private void initializeDate()
    {
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DAY_OF_MONTH);

        year = String.valueOf(y);
        month = String.valueOf(m);
        day = String.valueOf(d);
    }
    
    /**
     * Sets up the ClickListener for the date
     */
    private void setUpDatePicker()
    {
        mDisplayDate.setOnClickListener(view ->
        {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    AddEventActivity.this,
                    android.R.style.Theme_Holo,     // Theme can be adjusted & HoloDialog is full screen
                    mDateSetListener,
                    year, month, day);
            
            // Sets the background transparent (unnecessary)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();
        });

        mDateSetListener = (datePicker, i_year, i_month, i_day) ->
        {
            // The month starts at 0
            i_month++;
            year = String.valueOf(i_year);
            month = String.valueOf(i_month);
            day = String.valueOf(i_day);
            showDate();
        };
    }
    
    /**
     * Shows the formatted date in the TextView for date
     */
    private void showDate()
    {
        mDisplayDate = findViewById(R.id.tvDate);
        String date = String.format("%s/%s/%s", month, day, year);
        mDisplayDate.setText(date);
        
        // Date object is also updated
        dateObject = new GregorianCalendar(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)).getTime();
    }
    
    // Accessors
    public Date GetDate()
    {
        return dateObject;
    }

    public String GetDay()
    {
        return day;
    }

    public String GetMonth()
    {
        return month;
    }

    public String GetYear()
    {
        return year;
    }
    
    public String GetDescription()
    {
        return ((EditText) findViewById(R.id.descriptionText)).getText().toString();
    }
    
    public String GetEventName()
    {
        return ((EditText) findViewById(R.id.eventName)).getText().toString();
    }
}