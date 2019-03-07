package com.example.mattm.calendar.Views;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.Subject;

import java.util.ArrayList;

public class ChoosePeriodDialogFragment extends DialogFragment
{
    // Private Properties
    private ArrayList<Subject> subjects;
    
    private AWSConnection awsConnection;
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        try
        {
            awsConnection = AWSConnection.getCurrentInstance(null);
            subjects = awsConnection.getSubjects().execute().get();
        }
        catch (Exception e)
        {
            // TODO: UI - Show error message to User in a way they will understand for different error messages
        }
        
        String subjectNameArgument = getArguments().get("SubjectName").toString();
        
        subjects = getCorrectSubjects(subjects, subjectNameArgument);
        ArrayList<String> periods = getPeriods(subjects);
    
        ArrayAdapter<String> periodList = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, periods);
        
        // Using Builder class for convenient dialog construction
        Builder builder = new Builder(getActivity());
        
        builder.setTitle("Select a Period")
                .setAdapter(periodList, (dialog, which) ->
                        awsConnection.addSubject(subjects.get(which)).execute())
                .setPositiveButton("Create Class", (dialog, which) ->
                {
                    Intent intent = new Intent(getActivity(), AddSubjectActivity.class);
                    intent.putExtra("SubjectName", subjectNameArgument);
                    intent.putExtra("TeacherName", getArguments().get("TeacherName").toString());
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> { });
        return builder.create();
    }
    
    // Private Properties
    private ArrayList<Subject> getCorrectSubjects(ArrayList<Subject> subjects, String subjectName)
    {
        ArrayList<Subject> result = new ArrayList<>();
        
        for (Subject subject : subjects)
        {
            if (subjectName.equals(subject.getSubject()))
                result.add(subject);
        }
        return result;
    }
    
    private ArrayList<String> getPeriods(ArrayList<Subject> subjects)
    {
        ArrayList<String> result = new ArrayList<>();
    
        for (Subject subject : subjects)
            result.add(subject.getPeriod());
        
        return result;
    }
}