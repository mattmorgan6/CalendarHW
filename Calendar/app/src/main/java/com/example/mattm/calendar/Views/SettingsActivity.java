package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mattm.calendar.Models.FileIO;
import com.example.mattm.calendar.R;

public class SettingsActivity extends AppCompatActivity implements OnCheckedChangeListener
{
    // Private Properties
    private boolean darkTheme;
    
    private Switch themeSwitch;
    
    private TextView dark;
    private TextView light;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_Dark);
        
        setContentView(R.layout.activity_settings);

        dark = findViewById(R.id.dark);
        light = findViewById(R.id.light);
        
        themeSwitch = findViewById(R.id.colorThemeSwitch);
        
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            themeSwitch.setChecked(true);
        
        themeSwitch.setOnCheckedChangeListener(this);
    }

    // Event Handlers
    public void aboutOnClick(View view)
    {
        // If the user taps on the text that says "about"
        Toast.makeText(this, "\n Developed by KMS \n", Toast.LENGTH_SHORT).show();
    }

    // Overridden Methods
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        FileIO fileIO = new FileIO();
        if (isChecked)
        {
            changeToDark();
            fileIO.writeToFile("true", this, "color_theme");
        }
        else
        {
            changeToLight();
            fileIO.writeToFile("false", this, "color_theme");
        }
        
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
    public void onClickLogOut(View view)
    {
        // TODO: Get this to logout
    }
    
    // Public Methods
    public void changeToDark()
    {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
    
    public void changeToLight()
    {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    
    // Private Methods
    private void adjustText(boolean isChecked)
    {
        // TODO: Fix this (Mateo)
        darkTheme = isChecked;
        (isChecked ? dark : light).setTextColor(getResources().getColor(R.color.colorFontDark));
        (isChecked ? light : dark).setTextColor(getResources().getColor(R.color.colorFontReg));
        dark.setTypeface(null, isChecked ? Typeface.BOLD : Typeface.NORMAL);
    }
}