package com.example.mattm.calendar.Views;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mattm.calendar.R;

public class SubjectDetailsDialogFragment extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Using Builder class for convenient dialog construction
        Builder builder = new Builder(getActivity());
    
        LayoutInflater inflater = getActivity().getLayoutInflater();
    
        View view = inflater.inflate(R.layout.fragment_subject_details_dialog, null);
        ((TextView) view.findViewById(R.id.subjectNameTextView))
                .setText(getArguments().getString("Subject"));
        ((TextView) view.findViewById(R.id.teacherNameTextView))
                .setText(getArguments().getString("TeacherName"));
        ((TextView) view.findViewById(R.id.periodTextView))
                .setText(getArguments().getString("Period"));
        
        builder.setTitle("Details")
                .setView(view)
                .setPositiveButton("Add Assignment", (dialog, which) ->
                {
                    Intent intent = new Intent(getActivity(), AddEventActivity.class);
                    intent.putExtra("ClassName", getArguments().getString("ClassName"));
                    startActivity(intent);
                })
                .setNegativeButton("Unsubscribe", (dialog, which) ->
                {
                    // TODO: (Kenneth) Remove this class from User's subjects
                });
    
        // Create the AlertDialog object and return it
        return builder.create();
    }
}