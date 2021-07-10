package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextParser implements AppointmentBookParser<AppointmentBook> {

    private String fileName;
    private String fileOwner;

    /**
     * Method to set the file name.
     * @param Name
     *      file nmame.
     */
    public void setFileName(String Name)
    {
        this.fileName = Name;
    }

    /**
     * Method to set the file owner name.
     * @param Name
     *      Owner Name.
     */
    public void setFileOwner(String Name)
    {
        this.fileOwner = Name;
    }

    /**
     * Method to check if the file is the right owner.
     * @param FileName
     *      Name of the file being passed in from CL
     * @return ret
     *      return true / false.
     */
    public boolean FileIsRightOwner(String FileName)
    {
        boolean ret = false;
        File appBookFile = new File(fileName);
        String PassedInFileOwner = "";
        String line;

        if(appBookFile.exists())
        {
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(appBookFile));
                while ((line = reader.readLine()) != null)
                {
                    if (line.contains("app_book"))
                    {
                        PassedInFileOwner = line.substring(line.lastIndexOf("=")+1);
                        break;
                    }
                }
            }
            catch (IOException e)
            {
                System.out.println("no file found");
            }
        }


        if (PassedInFileOwner.equals(fileOwner))
        {
            ret = true;
        }

        return ret;
    }

    /**
     * Method to create a new empty apointment book if the book doenst exists.
     */
    public AppointmentBook CreateEmptyBook ()
    {
        AppointmentBook newEmptyBook = new AppointmentBook();
        File appBookFile = new File(fileName);

        try
        {
            appBookFile.createNewFile();
        }
       catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("No appointment book - Sending empty appointment book back. file name: " + fileName);

        return newEmptyBook;
    }

    /**
     * Method to Parse a file if it exists and create an
     * appointment book based on the details from file.
     */
    @Override
    public AppointmentBook parse() throws ParserException
    {
        String line;
        AppointmentBook newAppointmentBook = new AppointmentBook();
        Appointment newAppointment = new Appointment();
        File appBookFile = new File(fileName);
        List<String> FileInfo = new ArrayList<String>();

        System.out.println("Appointment book exists - Creating appointment book with apts.");

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(appBookFile));

            while ((line = reader.readLine()) != null)
            {
                FileInfo.add(line);
            }
        }
        catch (IOException e)
        {
            System.out.println("no file found");
        }

        for (int i = 0; i < FileInfo.size(); i++)
        {
            if (FileInfo.get(i).contains("app_book"))
            {
                //System.out.println(line.substring(line.lastIndexOf("=")+1));
                newAppointmentBook.setOwnerName(FileInfo.get(i).substring(FileInfo.get(i).lastIndexOf("=")+1));
            }
            else if (FileInfo.get(i).contains("--NEW APPOINTMENT--"))
            {
                newAppointment = new Appointment();
                newAppointment.setDescription(FileInfo.get(i+1).substring(FileInfo.get(i+1).lastIndexOf("=")+1));
                newAppointment.setStartTime(FileInfo.get(i+2).substring(FileInfo.get(i+2).lastIndexOf("=")+1), "");
                newAppointment.setEndTime(FileInfo.get(i+3).substring(FileInfo.get(i+3).lastIndexOf("=")+1), "");
                newAppointmentBook.addAppointment(newAppointment);
            }
        }

        return newAppointmentBook;
    }
}
