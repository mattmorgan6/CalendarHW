package com.example.mattm.calendar.Models;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileIO
{
    // Public Methods
    
    /**
     * File I/O to save the theme to a text file
     * @param data // TODO: (Mateo) Fill in parameter description
     * @param context The Acivity which this method is being called.
     * @param fileName // TODO: (Mateo) Fill in parameter description
     */
    public void writeToFile(String data, Context context, String fileName)
    {
        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e)
        {
            Toast.makeText(context, "Data save failed", Toast.LENGTH_SHORT).show();
        }
    }


    public String readFromFile(Context context, String fileName)
    {
        String ret = "";

        try
        {
            InputStream inputStream = context.openFileInput(fileName);

            if (null != inputStream)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while (null != (receiveString = bufferedReader.readLine()))
                    stringBuilder.append(receiveString);

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (IOException e)
        {
            Toast.makeText(context, "Data not found", Toast.LENGTH_SHORT).show();
        }

        return ret;
    }
}