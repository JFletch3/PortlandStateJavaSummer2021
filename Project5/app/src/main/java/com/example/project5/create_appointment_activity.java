package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class create_appointment_activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);


        Switch sw1 = findViewById(R.id.AMPMswitch);
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    sw1.setText("PM");
                }else
                {
                    sw1.setText("AM");
                }
            }
        });
        Switch sw2 = findViewById(R.id.AMPMswitch2);
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    sw2.setText("PM");
                }else
                {
                    sw2.setText("AM");
                }
            }
        });

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
            //create new appointmentbook i guess.
            // show popup indicating new appointbook created.


        }
        else
        {

            AppointmentBook book = getExistingAppointmentBook(ownerName);

            TextDumper textdumper = new TextDumper();
            textdumper.setFileDir(ownerName);
            System.out.println(file.getAbsolutePath());

            EditText appStartD = findViewById(R.id.appointmentStartDate);
            String appStartDate = appStartD.getText().toString();

            EditText appStartT = findViewById(R.id.appointmentStartTime);
            String appStartTime = appStartT.getText().toString();

            EditText appEndD = findViewById(R.id.appointmentEndDate);
            String appEndDate = appEndD.getText().toString();

            EditText appEndT = findViewById(R.id.appointmentEndTime);
            String appEndTime = appEndT.getText().toString();

//            try
//            {
//                checkDateFormat(appStartDate);
//            } catch (IOException e)
//            {
//                e.printStackTrace();
//            }


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
        catch (FileNotFoundException e) {
            System.out.println("This exception was thrown.");
            e.printStackTrace();
        }
        catch (IOException f) {
            System.out.println("This exception was thrown.");
            f.printStackTrace();
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

    /**
     * Method to check the date format to make sure the date is valid.
     * @param date
     *      The date passed in from the commandline arguments.
     */
    public static boolean checkDateFormat(String date) throws IOException
    {
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        dFormat.setLenient(false);

        try
        {
            Date retDate = dFormat.parse(date);
            return true;
        }
        catch (ParseException e)
        {

            System.err.println("Date format and/or Date is not valid: " + date + " --- Format Should be mm/dd/yyyy" +
                    "and date should be a real date.");
            System.exit(0);
        }
        return false;

    }

}