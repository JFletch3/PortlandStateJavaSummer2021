package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import java.io.File;
import java.io.IOException;

public class create_appointment_book_activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment_book);
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

            showPOPupMessage(newBookCreatedMessage(owner));

            try
            {
                textdumper.dumpWithOwnerOnly(newBook, file);
            } catch (IOException e)
            {
                showPOPupMessage(exceptionPopupHandling(e, "Attempting to create new appointment book file."));
            }
        }
        else
        {
            showPOPupMessage(bookExistsMessage(owner));
        }
        clearFields();
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

    public String bookExistsMessage(String owner)
    {
        return "An appointment book already exists for " + owner + ".";
    }
    public String newBookCreatedMessage(String owner)
    {
        return "An appointment book has been created for " + owner + ".";
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

    public String exceptionPopupHandling(Exception e, String message)
    {
        return "An Error occurred. " + message;
    }

    public void clearFields()
    {
        EditText OwnerTxt =  (EditText) findViewById(R.id.AppointmentBookOwner);
        OwnerTxt.setText("");

    }
}