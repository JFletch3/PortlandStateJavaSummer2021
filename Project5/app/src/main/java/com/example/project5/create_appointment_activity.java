package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.security.acl.Owner;
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
        setOnClickListenerForAMPMSwitch();

    }

    public void CreateNewAppointment(View view)
    {

        EditText ownerNameTXT =(EditText) findViewById(R.id.appointmentOwnerName);
        String ownerName = (String) ownerNameTXT.getText().toString();
        File file = new File (this.getFilesDir(), ownerName);
        AppointmentBook book = null;

        if (!file.exists())
        {
            book = new AppointmentBook();
            showPOPupMessage(newAppointmentBookMessage(ownerName));
        }
        else
        {
            book = getExistingAppointmentBook(ownerName);
        }

        Appointment newAppointment = buildNewAppointment();
        TextDumper textdumper = new TextDumper();
        textdumper.setFileDir(ownerName);
        book.addAppointment(newAppointment);

        try
        {
            textdumper.newAppointmentFileDump(book, file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        showPOPupMessage(newAppointmentMessage());
        clearFields();
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
            System.out.println("This exception was thrown1234.");
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

    public Appointment buildNewAppointment()
    {
        Appointment newApp = new Appointment();

        Switch sw1 = findViewById(R.id.AMPMswitch);
        Switch sw2 = findViewById(R.id.AMPMswitch2);
        String sw1SwitchAMPM = getAMPM(sw1);
        String sw2SwitchAMPM = getAMPM(sw2);

        EditText appDesction = findViewById(R.id.AppointmentDescription);
        String appDesc = appDesction.getText().toString();

        EditText appStartD = findViewById(R.id.appointmentStartDate);
        String appStartDate = appStartD.getText().toString();

        EditText appStartT = findViewById(R.id.appointmentStartTime);
        String appStartTime = appStartT.getText().toString();

        EditText appEndD = findViewById(R.id.appointmentEndDate);
        String appEndDate = appEndD.getText().toString();

        EditText appEndT = findViewById(R.id.appointmentEndTime);
        String appEndTime = appEndT.getText().toString();

        try
        {
            checkDateFormat(appStartDate + " " + appStartTime + " " + sw1SwitchAMPM);
            checkDateFormat(appEndDate + " " + appEndTime + " " + sw2SwitchAMPM);

        } catch (IOException e)
        {
            exceptionPopupHandling(e, "test");
        }

        newApp.setDescription(appDesc);
        newApp.setStartDate(appStartDate);
        newApp.setStartTime(appStartTime + " " + sw1SwitchAMPM);
        newApp.setEndDate(appEndDate);
        newApp.setEndTime(appEndTime + " " + sw2SwitchAMPM);

        return newApp;

    }

    /**
     * Method to check the date format to make sure the date is valid.
     * @param date
     *      The date passed in from the commandline arguments.
     */
    public static void checkDateFormat(String date) throws IOException
    {
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        dFormat.setLenient(false);

        try
        {
            Date retDate = dFormat.parse(date);
        }
        catch (ParseException e)
        {

            System.err.println("Date format and/or Date is not valid: " + date + " --- Format Should be mm/dd/yyyy" +
                    "and date should be a real date.");
            System.exit(0);
        }

    }

    public void setOnClickListenerForAMPMSwitch()
    {
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

    public String getAMPM(Switch sw)
    {
        if (sw.isChecked())
        {
            return "PM";
        }

        return "AM";
    }

    public void exceptionPopupHandling(Exception e, String message)
    {
        System.out.println("An exception was thrown: " + message);
    }

    public void showPOPupMessage(String message)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setMessage(message);
        dialog.setNegativeButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}});

        AlertDialog alert = dialog.create();
        alert.show();
    }

    public String newAppointmentBookMessage(String Owner)
    {
        return "Appointment Book for " + Owner + " did not exists. New Appointment Book Created.";
    }

    public String newAppointmentMessage()
    {
        return "New Appointment Created.";
    }

    public void clearFields()
    {
        Switch sw1 = findViewById(R.id.AMPMswitch);
        Switch sw2 = findViewById(R.id.AMPMswitch2);
        sw1.setChecked(false);
        sw2.setChecked(false);

        EditText appDesction = findViewById(R.id.AppointmentDescription);
        appDesction.setText("");

        EditText appStartD = findViewById(R.id.appointmentStartDate);
        appStartD.setText("");

        EditText appStartT = findViewById(R.id.appointmentStartTime);
        appStartT.setText("");

        EditText appEndD = findViewById(R.id.appointmentEndDate);
        appEndD.setText("");

        EditText appEndT = findViewById(R.id.appointmentEndTime);
        appEndT.setText("");
    }

}