package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.File;
import java.io.IOException;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    String FileDir;

    public void setFileDir(String Dir)
    {
        FileDir = Dir;
    }

    @Override
    public void dump(AppointmentBook book) throws IOException
    {
        File ApptBookFile = new File(FileDir);


    }
}
