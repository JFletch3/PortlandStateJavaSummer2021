package edu.pdx.cs410J.jf32;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextDumperTest
{
    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException {
        String owner = "Owner";
        AppointmentBook book = new AppointmentBook();
        book.setOwnerName(owner);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper();
        dumper.setWriter(sw);
        dumper.setFileDir("testfile");
        dumper.dump(book);

        sw.flush();
        String dumpedText = sw.toString();
        assertThat(dumpedText, containsString(owner));
    }

    @Test
    void dumperSetFileDirReturnsCorrectDir()
    {
        String fileDir = "TestFileDir";
        TextDumper dumper = new TextDumper();
        dumper.setFileDir(fileDir);
        assertEquals(fileDir, dumper.FileDir);
    }
}
