package edu.pdx.cs410J.jf32;
import org.junit.jupiter.api.Test;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest
{
//    @Test
//    void emptyFileCannotBeParsed() {
//        InputStream resource = getClass().getResourceAsStream("emptyFile.txt");
//        File file = new File (Objects.requireNonNull(getClass().getResource("emptyFile.txt")).getFile());
//
//        assertNotNull(res);
//        TextParser parser = new TextParser();
//        parser.setFileName(res);
//        assertThrows(ParserException.class, parser::parse);
//    }

//    @Test
//    void appointmentBookOwnerCanBeDumpedAndParsed() throws IOException, ParserException {
//        String owner = "Owner";
//        AppointmentBook book = new AppointmentBook(owner);
//
//        StringWriter sw = new StringWriter();
//        TextDumper dumper = new TextDumper(sw);
//        dumper.dump(book);
//
//        TextParser parser = new TextParser(new StringReader(sw.toString()));
//        book = parser.parse();
//
//        assertThat(book.getOwnerName(), equalTo(owner));
//    }


}
