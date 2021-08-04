package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

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

public class search_activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setOnClickListenerForAMPMSwitch();
    }


    public void searchAppointmentBooks(View view)
    {

        PrettyPrint print = new PrettyPrint(null, null);
        Switch sw1 = findViewById(R.id.AMPMSearchSwitch1);
        Switch sw2 = findViewById(R.id.AMPMSearchSwitch2);
        String sw1SwitchAMPM = getAMPM(sw1);
        String sw2SwitchAMPM = getAMPM(sw2);
        AppointmentBook searchedBook = null;

        EditText ownerNameTXT   = findViewById(R.id.createAppOwnerName);
        String ownerName        = ownerNameTXT.getText().toString();
        EditText appStartD      = findViewById(R.id.SearchStartDate);
        String appStartDate     = appStartD.getText().toString();
        EditText appStartT      = findViewById(R.id.SearchStartTime);
        String appStartTime     = appStartT.getText().toString();
        EditText appEndD        = findViewById(R.id.SearchEndDate);
        String appEndDate       = appEndD.getText().toString();
        EditText appEndT        = findViewById(R.id.SearchEndTime);
        String appEndTime       = appEndT.getText().toString();

        String start = appStartDate + " " + appStartTime +" " + sw1SwitchAMPM;
        String end = appEndDate + " " + appEndTime +" " + sw2SwitchAMPM;

        if (!checkTextValuesForDatesAndtimes())
        {
            Toast.makeText(this, "Some fields are missing - Searching for Book Owner only.", Toast.LENGTH_LONG).show();
            searchedBook = getBookByOwner(ownerName);
        }
        else
        {
            searchedBook = getSearchedBook(ownerName, start, end);
        }

        Intent intent = new Intent(this, appointment_book_view_activity.class);
        intent.putExtra("book", searchedBook);
        startActivity(intent);

    }

    public boolean checkTextValuesForDatesAndtimes()
    {
        boolean ret = true;
        EditText ownerNameTXT   = findViewById(R.id.createAppOwnerName);
        String ownerName        = ownerNameTXT.getText().toString();
        EditText appStartD      = findViewById(R.id.SearchStartDate);
        String appStartDate     = appStartD.getText().toString();
        EditText appStartT      = findViewById(R.id.SearchStartTime);
        String appStartTime     = appStartT.getText().toString();
        EditText appEndD        = findViewById(R.id.SearchEndDate);
        String appEndDate       = appEndD.getText().toString();
        EditText appEndT        = findViewById(R.id.SearchEndTime);
        String appEndTime       = appEndT.getText().toString();

        if (ownerName.length() == 0  ||
            appStartDate.length() == 0 ||
            appStartTime.length() == 0 ||
            appEndDate.length() == 0 ||
            appEndTime.length() == 0)
        {
            ret = false;
        }

        return ret;

    }

    /**
     * Method to check the date format to make sure the date is valid.
     * @param date
     *      The date passed in from the commandline arguments.
     */
    public Date checkDateFormat(String date)
    {
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        dFormat.setLenient(false);

        try
        {
            Date retDate = dFormat.parse(date);
            return retDate;
        }
        catch (ParseException e)
        {
            String errMessage = "Date format and/or Date is not valid: " + date + " --- Format Should be mm/dd/yyyy" +
                    "and date should be a real date.";
            showPOPupMessage(errMessage);
        }

        return null;
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

    public void setOnClickListenerForAMPMSwitch()
    {
        Switch sw1 = findViewById(R.id.AMPMSearchSwitch1);
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
        Switch sw2 = findViewById(R.id.AMPMSearchSwitch2);
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

    public AppointmentBook getBookByOwner(String Owner)
    {
        AppointmentBook newAppointmentBook;
        AppointmentBook returnBook = new AppointmentBook();
        Appointment newAppointment = new Appointment();

        returnBook.setOwnerName(Owner);
        newAppointmentBook = getAppointmentBook(Owner);



        for (Appointment ap : newAppointmentBook.getAppointments())
        {
            returnBook.addAppointment(ap);
        }
        return returnBook;
    }

    public AppointmentBook getSearchedBook(String Owner, String start, String end)
    {
        AppointmentBook newAppointmentBook;
        AppointmentBook returnBook = new AppointmentBook();
        Appointment newAppointment = new Appointment();
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        dFormat.setLenient(false);
        Date searchStart = null;
        Date searchEnd = null;

        searchStart = checkDateFormat(start);
        searchEnd = checkDateFormat(end);

        returnBook.setOwnerName(Owner);
        newAppointmentBook = getAppointmentBook(Owner);

        for (Appointment ap : newAppointmentBook.getAppointments())
        {
            String appointmentStart = ap.getThisStartDate() + " " + ap.getThisStartTime();
            Date appointmentStartDate = null;
            appointmentStartDate = checkDateFormat(appointmentStart);

            if (appointmentStartDate.getTime() >= searchStart.getTime() && appointmentStartDate.getTime() <= searchEnd.getTime())
            {
                returnBook.addAppointment(ap);
            }
        }
        return returnBook;
    }

    public AppointmentBook getAppointmentBook(String Owner)
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


}