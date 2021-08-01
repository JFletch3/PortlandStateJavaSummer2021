package com.example.project5;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

/**
 * This class is represents a <code>TextDumper</code>.
 * This class will write the appointbook and apointments
 * to a text file.
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    /**
     * String file Dir variable for text dumper class.
     */
    String FileDir;
    /**
     * Write object for text dumper class.
     */
    Writer ApptBookFile;

    /**
     * This Method sets the file directory.
     * @param Dir
     *      File directory passed in from command line.
     */
    public void setFileDir(String Dir)
    {
        this.FileDir = Dir;
    }

    /**
     * This Method sets the appointment book buffered writer.
     * The purpose of this is for testing purposes.
     * @param write
     *      File directory passed in from command line.
     */
    public void setWriter(Writer write)
    {
        this.ApptBookFile = write;
    }


    /**
     * This Method writes the information of an Appointment Book
     * to a file determined from the directory passed in at the
     * command line.
     * @param book
     *      File directory passed in from command line.
     */
    public void dumpWithOwnerOnly(AppointmentBook book, File file) throws IOException
    {
        try
        {
            if (ApptBookFile == null)
            {
                ApptBookFile = new FileWriter(file);
            }
            ApptBookFile.write("app_book_owner=" + book.getOwnerName() + "\n");
            ApptBookFile.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This Method writes the information of an Appointment Book
     * to a file determined from the directory passed in at the
     * command line.
     * @param book
     *      File directory passed in from command line.
     */

    public void newAppointmentFileDump(AppointmentBook book, File file) throws IOException
    {
        try
        {
            if (ApptBookFile == null)
            {
                ApptBookFile = new FileWriter(file);
            }
            Collection<Appointment> appointments;
            appointments = book.getAppointments();
            ApptBookFile.write("app_book_owner=" + book.getOwnerName() + "\n");

            for (Appointment ap : appointments)
            {
                ApptBookFile.write("--NEW APPOINTMENT--\n");
                ApptBookFile.write("app_desc=" + ap.getDescription() + "\n");
                ApptBookFile.write("app_start_date=" + ap.getThisStartDate() +"\n");
                ApptBookFile.write("app_start_time=" + ap.getThisStartTime() +"\n");
                ApptBookFile.write("app_end_date=" + ap.getThisEndDate() + "\n");
                ApptBookFile.write("app_end_time=" + ap.getThisEndTime() + "\n");
            }
            ApptBookFile.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
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
            if (ApptBookFile == null)
            {
                ApptBookFile = new FileWriter(FileDir);
            }
            Collection<Appointment> appointments;
            appointments = book.getAppointments();
            ApptBookFile.write("app_book_owner=" + book.getOwnerName() + "\n");

            for (Appointment ap : appointments)
            {
                ApptBookFile.write("--NEW APPOINTMENT--\n");
                ApptBookFile.write("app_desc=" + ap.getDescription() + "\n");
                ApptBookFile.write("app_start_date=" + ap.getThisStartDate() +"\n");
                ApptBookFile.write("app_start_time=" + ap.getThisStartTime() +"\n");
                ApptBookFile.write("app_end_date=" + ap.getThisEndDate() + "\n");
                ApptBookFile.write("app_end_time=" + ap.getThisEndTime() + "\n");
            }
            ApptBookFile.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
