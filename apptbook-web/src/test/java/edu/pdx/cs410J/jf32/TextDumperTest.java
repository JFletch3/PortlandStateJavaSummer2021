package edu.pdx.cs410J.jf32;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextDumperTest
{
//    @Test
//    void dumperDumpsAppointmentBookOwner() throws IOException {
//        String owner = "Owner";
//        AppointmentBook book = new AppointmentBook();
//        book.setOwnerName(owner);
//
//        StringWriter sw = new StringWriter();
//        TextDumper dumper = new TextDumper();
//        dumper.setWriter(sw);
//        dumper.setFileDir("testfile");
//        dumper.dump(book);
//
//        sw.flush();
//        String dumpedText = sw.toString();
//        assertThat(dumpedText, containsString(owner));
//    }

    @Test
    void dumperSetFileDirReturnsCorrectDir()
    {
        String fileDir = "TestFileDir";
        TextDumper dumper = new TextDumper();
        dumper.setFileDir(fileDir);
        assertEquals(fileDir, dumper.FileDir);
    }

    @Test
    void serverDump() throws IOException
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
        TextDumper dump = new TextDumper();
        String fileDir = "TestFileDir";

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);
        when(response.getWriter()).thenReturn(pw);

        assertEquals(dump.serverDump(pw, newbook), true);
    }
}
