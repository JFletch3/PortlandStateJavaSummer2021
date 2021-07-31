package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMainSpinner();
    }

    
    /** Called when the user taps the Send button */
    public void changeForm(View view)
    {

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        String spinnerOption = mySpinner.getSelectedItem().toString();
        Intent intent;
        switch (spinnerOption)
        {
            case "Create an Appointment Book":
                intent = new Intent(this, create_appointment_book_activity.class);
                startActivity(intent);
                break;
            case "Create an Appointment":
                intent = new Intent(this, create_appointment_activity.class);
                startActivity(intent);
                //Show new appointment boxes
                break;
            case "Search Appointments":
                intent = new Intent(this, search_activity.class);
                startActivity(intent);
                break;
            default:
        }


    }

    public void createMainSpinner()
    {
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayList<String> spinnerArray = getSpinnerArray();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String SpinnerSelection = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + SpinnerSelection, Toast.LENGTH_LONG).show();



            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});
    }

    public ArrayList<String> getSpinnerArray()
    {
        ArrayList<String> spinlist = new ArrayList<>();

        spinlist.add("--select option--");
        spinlist.add("Create an Appointment Book");
        spinlist.add("Create an Appointment");
        spinlist.add("Search Appointments");

        return spinlist;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.helpmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showHelp()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //AlertDialog alert = new AlertDialog(this);
        dialog.setMessage(readmeMessage());

//        new DialogInterface.OnClickListener()
//        {
//            public void onClick(DialogInterface dialog,  int which)
//            {
//                Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
//            }
//        };
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG);
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }

    private String readmeMessage()
    {
        return "Welcome to JABS (Joes Appointment Books)." +
                "JABS allows the user to create a new Appointment book, " +
                "create new Appointments, and search for Appointments.\n\n" +
                "You should select one of the following options:\n" +
                "-Create new Appointment Book\n" +
                "-Create new Appointment\n" +
                "-Search for an Appointment";
    }
}