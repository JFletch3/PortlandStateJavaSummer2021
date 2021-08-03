package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class appointment_book_view_activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_book_view);
        AppointmentBook book = (AppointmentBook) getIntent().getSerializableExtra("book");
        ArrayList<Appointment> appList = getAppointments(book);
        createRecyclerView(appList);
    }

    public ArrayList<Appointment>  getAppointments(AppointmentBook book)
    {
       return book.getAppointments();
    }

    public void createRecyclerView(ArrayList<Appointment> appList)
    {
        RecyclerView rc = findViewById(R.id.appBookView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(appList);
        rc.setAdapter(adapter);
        rc.setLayoutManager(new LinearLayoutManager(this));
    }
}