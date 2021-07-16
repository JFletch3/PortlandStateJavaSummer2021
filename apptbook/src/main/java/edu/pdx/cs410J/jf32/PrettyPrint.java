package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;

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



    public void PrettyPrint(String file, Writer write)
    {
        this.fileDir = file;
        this.writer = write;
    }


    @Override
    public void dump(AppointmentBook book) throws IOException
    {

    }
}
