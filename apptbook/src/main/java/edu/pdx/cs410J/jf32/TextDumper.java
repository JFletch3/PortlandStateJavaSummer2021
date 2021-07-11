package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import javax.swing.plaf.synth.SynthSpinnerUI;
import java.io.*;
import java.util.Collection;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    String FileDir;
    Writer ApptBookFile;


    public void setFileDir(String Dir)
    {
        FileDir = Dir;
    }

    public void setWriter(Writer write)
    {
        ApptBookFile = write;
    }

    @Override
    public void dump(AppointmentBook book) throws IOException
    {
        try
        {
            Collection<Appointment> appointments;
            //FileWriter ApptBookFile = new FileWriter(FileDir);
            ApptBookFile = new FileWriter(FileDir);
            appointments = book.getAppointments();
            ApptBookFile.write("app_book_owner=" + book.getOwnerName() + "\n");

            for (Appointment ap : appointments)
            {
                ApptBookFile.write("--NEW APPOINTMENT--\n");
                ApptBookFile.write("app_desc=" + ap.getDescription() + "\n");
                ApptBookFile.write("app_start=" + ap.getBeginTimeString() +"\n");
                ApptBookFile.write("app_end=" + ap.getEndTimeString() + "\n");
            }
            ApptBookFile.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
