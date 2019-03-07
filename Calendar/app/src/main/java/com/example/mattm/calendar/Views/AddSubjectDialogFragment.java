package com.example.mattm.calendar.Views;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.Subject;
import com.example.mattm.calendar.R;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.mattm.calendar.Models.Subject.ConvertListToReadable;

public class AddSubjectDialogFragment extends DialogFragment
{
    // Private Properties
    private ArrayList<String> readableSubjects;
    
    private ArrayList<Subject> showSubjects;
    private ArrayList<Subject> subjects;
    
    private AWSConnection awsConnection;

    private EditText searchEditText;
    
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
            e.printStackTrace();
        }
        
        // Using Builder class for convenient dialog construction
        Builder builder = new Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        View view = inflater.inflate(R.layout.fragment_add_subject_dialog, null);
        
        searchEditText = view.findViewById(R.id.SearchEditText);
        searchEditText.setHint("Search Classes");
        
        // Temporary arrayList to show in the dialog
        showSubjects = subjects;

        createLists();
        searchTextEntered();

        // TODO(Sandeep): Use the same adapter for both MainActivity and this fragment to save data
        final ArrayAdapter<String> subjectsAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, readableSubjects);

        builder.setTitle("Classes")
                .setView(view)
                .setAdapter(subjectsAdapter, (dialog, which) ->
                {
                    Bundle bundle = new Bundle();
                    
                    String subject = showSubjects.get(which).toString();
                    String[] split = subject.split("_");
                    
                    bundle.putString("SubjectName", split[2]);
                    bundle.putString("TeacherName", split[0]);

                    ChoosePeriodDialogFragment chooseDialog = new ChoosePeriodDialogFragment();
                    chooseDialog.setArguments(bundle);
                    chooseDialog.show(getActivity().getSupportFragmentManager(), "Dialog2");
                })
                .setPositiveButton("Create Class", (dialog, which) ->
                {
                    // TODO: use this to search for classes, recommend using a thread - kenneth
                    String search = searchEditText.getText().toString();

                    Intent intent = new Intent(getActivity(), AddSubjectActivity.class);
                    getActivity().startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> { });

        // Create the AlertDialog object and return it
        return builder.create();
    }
    
    // Public Methods
    /**
     * Converts lists to readable format
     */
    public void createLists()
    {
        readableSubjects = ConvertListToReadable(showSubjects);
        removePeriodDuplicates(readableSubjects, showSubjects);

        // TODO: make it so the listView updates!!!
    }
    
    /**
     * Algorithm that performs the search function
     * @param s The String that is used to look for results
     */
    public void searchLoop(String s)
    {
        int size = showSubjects.size();
    
        // Removes classes that are not searched for
        for (int i = 0; i < size; i++)
        {
            if (!showSubjects.get(i).getSubject().contains(s))
            {
                showSubjects.remove(i);
                i--;
                size--;
            }
        }
        
        // createLists();
    }
    
    /**
     * Method that detects changes in the search bar.
     */
    public void searchTextEntered()
    {
        searchEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // Goes to the search algorithm
                searchLoop(s.toString());

                if ("".equals(s.toString()))
                    initList();   // Resets ListView
                else
                    searchLoop(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    
    // TODO: refresh dialog / listView method needed
    
    // Private Methods
    /**
     * Resets the listView to all original/available subjects
     */
    private void initList()
    {
        showSubjects = subjects;
    }
    
    private void removePeriodDuplicates(ArrayList<String> duplicateStr, ArrayList<Subject> duplicateSub)
    {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (int i = 1; i < duplicateStr.size(); i++)
        {
            if (duplicateStr.get(i - 1).equals(duplicateStr.get(i)))
                toRemove.add(i);
        }
        
        Collections.reverse(toRemove);
        
        for (int index: toRemove)
        {
            duplicateStr.remove(index);
            duplicateSub.remove(index);
        }
    }
}