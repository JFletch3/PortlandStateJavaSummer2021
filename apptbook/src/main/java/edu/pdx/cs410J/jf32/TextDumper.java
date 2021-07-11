package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import javax.swing.plaf.synth.SynthSpinnerUI;
import java.io.*;
import java.util.Collection;

/**
 * This class is represents a <code>TextDumper</code>.
 * This class will write the appointbook and apointments
 * to a text file.
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    String FileDir;
    Writer ApptBookFile;

    /**
     * This Method sets the file directory.
     * @param Dir
     *      File directory passed in from command line.
     */
    public void setFileDir(String Dir)
    {
        FileDir = Dir;
    }

    /**
     * This Method sets the appointment book buffered writer.
     * The purpose of this is for testing purposes.
     * @param write
     *      File directory passed in from command line.
     */
    public void setWriter(Writer write)
    {
        ApptBookFile = write;
    }

    /**
     * This Method writes the information of an Appointment Book
     * to a file determined from the directory passed in at the
     * command line.
     * @param book
     *      File directory passed in from command line.
     */
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
