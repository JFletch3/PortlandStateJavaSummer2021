package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class help_message_activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_message);

        String helpmessage = "Welcome to JABS (Joes Appointment Books)." +
                            "JABS allows the user to create a new Appointment book, " +
                            "create new Appointments, and search for Appointments.\n\n" +
                            "You should select one of the following options on the main screen drop down window:\n" +
                            "-Create new Appointment Book\n" +
                            "-Create new Appointment\n" +
                            "-Search for an Appointment\n" +
                            "---------------------------------------\n" +
                            "Create new appointment will create a new appointment book for the name specified.\n" +
                            "Create new Appointment will create a new appointment for the book provide.\n" +
                            "-NOTE: If a book does not exists, a new one will be created for you and the new appointment will be added.\n" +
                            "Search for an Appointment will search for appointments inside the book owned by the name entered.";

        EditText helpmsg = findViewById(R.id.helpmesageText);
        helpmsg.setText(helpmessage);
        helpmsg.setEnabled(false);

    }
}