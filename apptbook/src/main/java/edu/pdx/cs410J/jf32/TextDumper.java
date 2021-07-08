package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import javax.swing.plaf.synth.SynthSpinnerUI;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    String FileDir;

    public void setFileDir(String Dir)
    {
        FileDir = Dir;
    }

    @Override
    public void dump(AppointmentBook book) throws IOException
    {

     //   File ApptBookFile = new File(FileDir);

//        try
//        {
//            if (ApptBookFile.createNewFile())
//            {
//                System.out.println("Created new file at:" + ApptBookFile.getName());
//            }
//        }
//        catch (IOException e)
//        {
//            System.out.println("File already exists.");
//        }
        try
        {
            Collection<Appointment> appointments;
            FileWriter ApptBookFile = new FileWriter(FileDir);
            appointments = book.getAppointments();
            ApptBookFile.write("app_book_owner=" + book.getOwnerName() + "\n");

            for (Appointment ap : appointments)
            {
                ApptBookFile.write("app_des=" + ap.getDescription() + "\n");
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
