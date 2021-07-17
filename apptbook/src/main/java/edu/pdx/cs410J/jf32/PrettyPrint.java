package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

public class PrettyPrint implements AppointmentBookDumper<AppointmentBook>
{
    /**
     * String file Dir variable for text dumper class.
     */
    private String fileDir;
    /**
     * Write object for text dumper class.
     */
    private Writer writer;


    /**
     * Constructor method for prettyprint class.
     * @param file
     *      File directory passed in from command line.
     * @param write
     *         file writer object.
     */
    public PrettyPrint(String file, Writer write)
    {
        this.fileDir = file;
        this.writer = write;
    }

    /**
     * getter method for testing purpose
     * @return fileDir
     *      Returns the file directory.
     */
    public String getFileDir()
    {
        return this.fileDir;
    }

    /**
     * getter method for testing purpose
     * @return writer
     *      returns the writer for the file dump.
     */
    public Writer getWriter()
    {
        return this.writer;
    }

    /**
     * This Method pretty print an appointment book
     * to the file passed in from the command line after the
     * -pretty command line option.
     * @param book
     *      File directory passed in from command line.
     */
    @Override
    public void dump(AppointmentBook book) throws IOException
    {
        try
        {
            if (writer == null)
            {
                writer = new FileWriter(fileDir);
            }
            File prettyPrintFile = new File(fileDir);
            prettyPrintFile.createNewFile();
            Collection<Appointment> appointments;
            appointments = book.getAppointments();
            writer.write("|===================================================\n");
            writer.write("| Appointment Book Owner:  " + book.getOwnerName() + "\n");
            writer.write("| Number of Appointments:  " + book.getAppointments().size() + "\n");
            writer.write("|===================================================\n");

            for (Appointment ap : appointments)
            {
                long TimeDifference = ap.getEndTime().getTime() - ap.getBeginTime().getTime();
                TimeDifference = (TimeDifference / (1000 * 60));
                writer.write("| Appointment = " + ap.getDescription() + "\n");
                writer.write("| Start Time  = " + ap.getBeginTime()  + "\n");
                writer.write("| End Time    = " + ap.getEndTime() + "\n");
                writer.write("| Duration    = " + TimeDifference + " Minutes\n");
                writer.write("|---------------------------------------------------\n");

            }
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
