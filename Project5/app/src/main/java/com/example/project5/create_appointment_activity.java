package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class create_appointment_activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);
    }


    public void CreateNewAppointmentBook()
    {
        AppointmentBook newBook = new AppointmentBook();
        EditText OwnerTxt =  (EditText) findViewById(R.id.AppointmentBookOwner);
        String owner = (String) OwnerTxt.getText().toString();
        System.out.println(owner);
    }
}