package edu.pdx.cs410J.jf32;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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



}
