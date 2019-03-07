package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.Subject;
import com.example.mattm.calendar.R;

public class AddSubjectActivity extends AppCompatActivity
{
    // Private Properties
    private AWSConnection awsConnection;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_Dark);
        setContentView(R.layout.activity_add_class);
        
        String subjectName = getIntent().getStringExtra("SubjectName");
        ((EditText) findViewById(R.id.className)).setText(subjectName);
        
        String teacherName = getIntent().getStringExtra("TeacherName");
        ((EditText) findViewById(R.id.teacherName)).setText(teacherName);
        
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
    public void addClassButton_Clicked(View view)
    {
        final Subject subject = new Subject(GetClassName(), GetTeacher(), GetPeriod());
        awsConnection.addSubject(subject).execute();

        Intent intentHome = new Intent(this, MainActivity.class);
        startActivity(intentHome);
    }

    // Accessors
    public String GetClassName()
    {
        return ((EditText) findViewById(R.id.className)).getText().toString();
    }

    public String GetPeriod()
    {
        return ((EditText) findViewById(R.id.period)).getText().toString();
    }
    
    public String GetTeacher()
    {
        return ((EditText) findViewById(R.id.teacherName)).getText().toString();
    }
}