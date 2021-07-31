package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class search_activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }


    public void searchAppointmentBooks(View view)
    {
        String line;
        EditText ownerNameTXT = (EditText)  findViewById(R.id.searchOwnerName);
        String ownerName = (String) ownerNameTXT.getText().toString();
        File file = new File (this.getFilesDir(), ownerName);

        EditText searchDataText =  (EditText) findViewById(R.id.searchInfo);
        BufferedReader reader = null;
        List<String> FileInfo = new ArrayList<String>();
        System.out.println("Owner name: " + ownerName);
        try
        {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null)
            {
                FileInfo.add(line);
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("This exception was thrown.");
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < FileInfo.size(); i++)
        {
            searchDataText.setText(FileInfo.get(i));
            searchDataText.append(System.getProperty("line.separator"));
            searchDataText.append(FileInfo.get(i));

        }
    }

}