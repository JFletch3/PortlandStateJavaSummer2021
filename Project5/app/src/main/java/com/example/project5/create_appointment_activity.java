package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class create_appointment_activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);
    }


    public void CreateNewAppointmentBook(View view)
    {
        AppointmentBook newBook = new AppointmentBook();
        EditText OwnerTxt =  (EditText) findViewById(R.id.AppointmentBookOwner);
        String owner = (String) OwnerTxt.getText().toString();
        newBook.setOwnerName(owner);
        newBook.addAppointment(null);

        if (confirmBookDoesNotExists(owner))
        {
            //Create new appointment book into system and write to it.
            File file = new File(this.getFilesDir(), owner);
            TextDumper textdumper = new TextDumper();
            textdumper.setFileDir(owner);

            try
            {
                textdumper.dumpWithOwnerOnly(newBook, file);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("File exists");
            //popup message saying the book already exists.
            //Create popup message function that you can pass the error to.
        }
    }

    public boolean confirmBookDoesNotExists(String Owner)
    {
        boolean ret = false;
        File file = new File(this.getFilesDir(), Owner);

        if (!file.exists())
        {
            ret = true;
        }

        return ret;
    }
}