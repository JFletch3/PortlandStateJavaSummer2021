package edu.pdx.cs410J.jf32;
import org.junit.jupiter.api.Test;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class TextParserTest
{
    @Test
    void emptyFileCannotBeParsed() {

        InputStream resource = getClass().getResourceAsStream("emptyFile.txt");
        File file = new File (Objects.requireNonNull(getClass().getResource("emptyFile.txt")).getFile());
        String path = file.getPath();
        assertNotNull(resource);
        TextParser parser = new TextParser();
        parser.setFileName(path);
        assertThrows(ParserException.class, parser::parse);
    }

    @Test
    void appointmentBookOwnerCanBeDumpedAndParsed() throws IOException, ParserException {
        String owner = "Owner";
        String fileName = "TestFileWithThisUnitTest";
        AppointmentBook book;
        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper();
        TextParser parser = new TextParser();
        File appBookFile = new File(fileName);
        parser.setFileName(fileName);
        book = parser.CreateEmptyBook();
        book.setOwnerName(owner);
        dumper.setFileDir(fileName);
        dumper.dump(book);
        book = parser.parse();
        assertThat(book.getOwnerName(), equalTo(owner));
    }

    @Test
    void TestingSetters()
    {
        String fileName = "test File name";
        String fileOwner = "owner";
        TextParser parser = new TextParser();
        parser.setFileName(fileName);
        parser.setFileOwner(fileOwner);
        assertEquals(parser.getFileOwner(), fileOwner);
        assertEquals(parser.getFileName(), fileName);
    }

}
