package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;

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

        System.out.println("Appointment book exists - Creating appointment book with apts.");

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(appBookFile));

            while ((line = reader.readLine()) != null)
            {
                if (line.contains("app_book"))
                {
                    //System.out.println(line.substring(line.lastIndexOf("=")+1));
                    newAppointmentBook.setOwnerName(line.substring(line.lastIndexOf("=")+1));
                }

                if (line.contains("--NEW APPOINTMENT--"))
                {
                    newAppointment = new Appointment();
                }
                else
                {
                    if (line.contains("app_desc"))
                    {
                        //System.out.println(line.substring(line.lastIndexOf("=")+1));
                        newAppointment.setDescription(line.substring(line.lastIndexOf("=")+1));
                    }
                    else if (line.contains("app_start"))
                    {
                        //System.out.println(line.substring(line.lastIndexOf("=")+1));
                        newAppointment.setStartTime(line.substring(line.lastIndexOf("=")+1),"");
                    }
                    else if (line.contains("app_end"))
                    {
                        //System.out.println(line.substring(line.lastIndexOf("=")+1));
                        newAppointment.setEndTime(line.substring(line.lastIndexOf("=")+1),"");
                    }
                    newAppointmentBook.addAppointment(newAppointment);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("no file found");
        }
        return newAppointmentBook;
    }
}
