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

public class create_appointment_activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);
    }


    public void CreateNewAppointment(View view)
    {
        //Search for appointment book
        // if it does not exists
        // create a new appointment book
        // notify user of a new appointment book (popup)
        // Write all info to the file.
        //
        // If file exists, open it and then write new information to the file.

        EditText ownerNameTXT = findViewById(R.id.createAppOwnerName);
        String ownerName = ownerNameTXT.getText().toString();

        File file = new File (this.getFilesDir(), ownerName);

        if (!file.exists())
        {
            //create new appointmentbook i guess
            // show popup indicating new appointbook created.


        }
        else
        {
            // Capture appointment information,
            // check date / time for validity
            // Create appointment and add it to the book.
            //Create new appointment book into system and write to it.

            AppointmentBook book = getExistingAppointmentBook(ownerName);

            TextDumper textdumper = new TextDumper();
            textdumper.setFileDir(ownerName);
            System.out.println(file.getAbsolutePath());

//            try
//            {
//                textdumper.dumpWithOwnerOnly(newBook, file);
//            } catch (IOException e)
//            {
//                e.printStackTrace();
//            }

        }


    }

    public AppointmentBook getExistingAppointmentBook(String Owner)
    {
        String line;
        AppointmentBook newAppointmentBook = new AppointmentBook();
        Appointment newAppointment = new Appointment();
        File file = new File (this.getFilesDir(), Owner);
        BufferedReader reader = null;
        List<String> FileInfo = new ArrayList<>();

        try
        {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null)
            {
                FileInfo.add(line);
            }

        }
        catch (FileNotFoundException e | IOException e)
        {
            System.out.println("This exception was thrown.");
            e.printStackTrace();
        }

        for (int i = 0; i < FileInfo.size(); i++)
        {
            if (FileInfo.get(i).contains("app_book"))
            {
                newAppointmentBook.setOwnerName(FileInfo.get(i).substring(FileInfo.get(i).lastIndexOf("=")+1));
            }
            else if (FileInfo.get(i).contains("--NEW APPOINTMENT--"))
            {
                newAppointment = new Appointment();
                newAppointment.setDescription(FileInfo.get(i+1).substring(FileInfo.get(i+1).lastIndexOf("=")+1));
                newAppointment.setStartDate(FileInfo.get(i+2).substring(FileInfo.get(i+2).lastIndexOf("=")+1));
                newAppointment.setStartTime(FileInfo.get(i+3).substring(FileInfo.get(i+3).lastIndexOf("=")+1));
                newAppointment.setEndDate(FileInfo.get(i+4).substring(FileInfo.get(i+4).lastIndexOf("=")+1));
                newAppointment.setEndTime(FileInfo.get(i+5).substring(FileInfo.get(i+5).lastIndexOf("=")+1));
                newAppointmentBook.addAppointment(newAppointment);
            }
        }

        return newAppointmentBook;
    }

}