package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class search_activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);
    }


    public void searchAppointmentBooks(View view)
    {
        String ownerName = (EditText) findViewById(R.id.searchOwnerName).toString();
        EditText searchDataText =  (EditText) findViewById(R.id.searchInfo);
        BufferedReader reader = new FileReader(ownerName);
        String line;

        while (line = reader.readLine())
        {

        }


        searchDataText.setText();
    }


}