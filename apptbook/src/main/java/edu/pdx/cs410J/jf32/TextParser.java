package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;

public class TextParser implements AppointmentBookParser<AppointmentBook> {

    private String fileDir;

    public void setFileDir(String dir)
    {
        this.fileDir = dir;
    }

    @Override
    public AppointmentBook parse() throws ParserException
    {
        String line;
        AppointmentBook newAppointmentBook = new AppointmentBook();
        Appointment newAppointment = new Appointment();

        try
        {
            File appBookFile = new File(fileDir);
            BufferedReader reader = new BufferedReader(new FileReader(appBookFile));

            while ((line = reader.readLine()) != null)
            {
                if (line.contains("app_book"))
                {
                    newAppointmentBook.setOwnerName(line.substring(line.lastIndexOf("=")+1));
                }
                else if (line.contains("app_desc"))
                {
                    newAppointment.setDescription(line.substring(line.lastIndexOf("=")+1));
                }
                else if (line.contains("app_start"))
                {
                    newAppointment.setStartTime(line.substring(line.lastIndexOf("=")+1),line.substring(line.lastIndexOf(" ")+1));
                }
                else if (line.contains("app_end"))
                {
                    newAppointment.setEndTime(line.substring(line.lastIndexOf("=")+1),line.substring(line.lastIndexOf(" ")+1));
                }
                //System.out.println(line);
                System.out.println(line.substring(line.lastIndexOf("=")+1));
                // Need to check the line of code -
                //
            }
        }
        catch (IOException e)
        {
           System.out.println("no file found");
        }

        return newAppointmentBook;
    }
}
