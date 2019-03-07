package com.example.mattm.calendar.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.SignInStateChangeListener;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.FileIO;
import com.example.mattm.calendar.R;

public class AuthenticatorActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        checkForTheme();

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_Dark);
        setContentView(R.layout.activity_authenticator);
        
        try
        {
            AWSConnection.getCurrentInstance(this);
        }
        catch (Exception e)
        {
            // TODO: UI - Show error message to User in a way they will understand for different error messages
            e.printStackTrace();
        }

        IdentityManager.getDefaultIdentityManager().addSignInStateChangeListener(new SignInStateChangeListener()
        {
            @Override
            public void onUserSignedIn()
            {
                try
                {
                    AWSConnection.getCurrentInstance(null).updateUserID().execute().get();
                }
                catch (Exception e)
                {
                    // TODO: UI - Show error message to User in a way they will understand for different error messages

                    // Temporary solution
                    e.printStackTrace();
                    Toast.makeText(AuthenticatorActivity.this, "Unable to connect to network", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUserSignedOut() { }
        });
        
        showSignIn();
    }

    // Public Methods
    public void checkForTheme()
    {
        SettingsActivity settingsActivity = new SettingsActivity();
        FileIO fileIO = new FileIO();
        String darkTheme = fileIO.readFromFile(this, "color_theme");
        if (Boolean.valueOf(darkTheme))
            settingsActivity.changeToDark();
    }

    // Private Method
    private void showSignIn()
    {
        SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(AuthenticatorActivity.this, SignInUI.class);
        signin.login(AuthenticatorActivity.this, MainActivity.class).execute();
    }
}