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
                    System.out.println(line.substring(line.lastIndexOf("=")+1));
                    newAppointmentBook.setOwnerName(line.substring(line.lastIndexOf("=")+1));
                }
                else if (line.contains("app_desc"))
                {
                    System.out.println(line.substring(line.lastIndexOf("=")+1));
                    newAppointment.setDescription(line.substring(line.lastIndexOf("=")+1));
                }
                else if (line.contains("app_start"))
                {
                    System.out.println(line.substring(line.lastIndexOf("=")+1));
                    newAppointment.setStartTime(line.substring(line.lastIndexOf("=")+1),"");
                }
                else if (line.contains("app_end"))
                {
                    System.out.println(line.substring(line.lastIndexOf("=")+1));
                    newAppointment.setEndTime(line.substring(line.lastIndexOf("=")+1),"");
                }

                newAppointmentBook.addAppointment(newAppointment);
            }
        }
        catch (IOException e)
        {
           System.out.println("no file found");
        }

        return newAppointmentBook;
    }
}
