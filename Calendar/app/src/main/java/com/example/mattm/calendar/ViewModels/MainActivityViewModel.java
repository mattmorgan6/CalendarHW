package com.example.mattm.calendar.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel
{
    // Public Properties
    public MutableLiveData<String> UserName;
    
    public MutableLiveData<ArrayList<String>> UserSubjects;
    
    // Constructors
    public MainActivityViewModel()
    {
        UserName = new MutableLiveData<>();
        UserName.setValue("Mateo");
        
        UserSubjects = new MutableLiveData<>();
        UserSubjects.setValue(new ArrayList<>());
    }
}