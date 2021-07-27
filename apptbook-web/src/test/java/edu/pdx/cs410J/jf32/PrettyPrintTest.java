package edu.pdx.cs410J.jf32;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrettyPrintTest
{
    @Test
    void PrettyPrintFileNameIsSetCorrectly() {
        String filename = "testFileName";
        PrettyPrint pPrint = new PrettyPrint(filename, null);
        assertEquals(pPrint.getFileDir(), filename);
    }

    @Test
    void PrettyPrintWriterIsSetCorrectly() {
        String filename = "testFileName";
        PrettyPrint pPrint = new PrettyPrint(filename, null);
        assertEquals(pPrint.getWriter(), null);
    }

    @Test
    void teststandardDump() throws IOException
    {
        AppointmentBook newbook = new AppointmentBook();
        Appointment newapp = new Appointment();
        newbook.setOwnerName("owner");
        newapp.setDescription("descrip");
        newapp.setStartDate("1/1/2021");
        newapp.setStartTime("1:00 PM");
        newapp.setEndDate("1/1/2021");
        newapp.setEndTime("2:00 PM");
        newbook.addAppointment(newapp);
        PrettyPrint pPrint = new PrettyPrint(null, null);

        assertEquals(pPrint.stdDump(newbook), true);
    }

}
