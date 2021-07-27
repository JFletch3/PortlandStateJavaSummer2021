package edu.pdx.cs410J.jf32;

import org.junit.jupiter.api.Test;

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



}
