package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Project3Test extends InvokeMainTestCase
{

    @Test
    void checkForPrintOptionReturns1() {
        String [] args = new String[7];
        args[0] = "-print";
        args[1] = "Joe";
        args[2] = "\"test\"";
        args[3] = "6/1/2021";
        args[4] = "1:00";
        args[5] = "6/2/2021";
        args[6] = "2:00";
        assertEquals(1, Project3.checkForPrintOption(args));
    }

    @Test
    void checkForPrintOptionReturns0() {
        String [] args = new String[6];
        args[0] = "Joe";
        args[1] = "\"test\"";
        args[2] = "6/1/2021";
        args[3] = "1:00";
        args[4] = "6/2/2021";
        args[5] = "2:00";
        assertEquals(0, Project3.checkForPrintOption(args));
    }

    @Test
    void checkForPrettyOptionReturns1() {
        String [] args = new String[7];
        args[0] = "-pretty";
        args[1] = "Joe";
        args[2] = "\"test\"";
        args[3] = "6/1/2021";
        args[4] = "1:00";
        args[5] = "6/2/2021";
        args[6] = "2:00";
        assertEquals(1, Project3.checkforPrettyOption(args));
    }

    @Test
    void checkForPrettyOptionReturns0() {
        String [] args = new String[6];
        args[0] = "Joe";
        args[1] = "\"test\"";
        args[2] = "6/1/2021";
        args[3] = "1:00";
        args[4] = "6/2/2021";
        args[5] = "2:00";
        assertEquals(0, Project3.checkforPrettyOption(args));
    }

    @Test
    void CheckMakeAppointmentReturnsCorrectAppointment() {
        Appointment newApp = new Appointment();
        List<String> args = new ArrayList<String>();
        args.add("Joe");
        args.add("\"test\"");
        args.add("6/1/2021");
        args.add("1:00");
        args.add("6/2/2021");
        args.add("2:00");
        newApp.setDescription(args.get(1));
        newApp.setStartDate(args.get(2));
        newApp.setStartTime(args.get(3));
        newApp.setEndDate(args.get(4));
        newApp.setEndTime(args.get(5));
        assertEquals(newApp.getClass(), Project2.makeAppointment(args).getClass());
        assertEquals(newApp.getDescription(), Project2.makeAppointment(args).getDescription());
        assertEquals(newApp.getBeginTimeString(), Project2.makeAppointment(args).getBeginTimeString());
        assertEquals(newApp.getEndTimeString(), Project2.makeAppointment(args).getEndTimeString());
    }

    @Test
    void CheckMakeAppointmentBookReturnsCorrectAppointment() {
        AppointmentBook newApp = new AppointmentBook();
        List<String> args = new ArrayList<String>();
        args.add("Joe");
        newApp.setOwnerName(args.get(0));
        assertEquals(newApp.getClass(), Project3.makeAppointmentBook(args).getClass());
        assertEquals(newApp.getOwnerName(), Project3.makeAppointmentBook(args).getOwnerName());
    }

    @Test
    void CheckFileNameReturnIsCorrect() {
        String [] args = new String[8];
        args[0] = "-textFile";
        args[1] = "TestFileName";
        args[2] = "Joe";
        args[3] = "\"test\"";
        args[4] = "6/1/2021";
        args[5] = "1:00";
        args[6] = "6/2/2021";
        args[7] = "2:00";
        assertEquals("TestFileName", Project3.getFileName(args, "-TEXTFILE"));
    }

    @Test
    void CheckArgumentSlicerReturnsCorrectList() {
        String [] args = new String[11];
        args[0] = "-print";
        args[1] = "-textFile";
        args[2] = "TestFileName";
        args[3] = "Joe";
        args[4] = "\"test\"";
        args[5] = "6/1/2021";
        args[6] = "1:00";
        args[7] = "PM";
        args[8] = "6/2/2021";
        args[9] = "2:00";
        args[10] = "PM";
        assertEquals(8, Project3.argumentSlicer(args).size());
        assertEquals("Joe", Project3.argumentSlicer(args).get(0));
        assertEquals("\"test\"", Project3.argumentSlicer(args).get(1));
        assertEquals("6/1/2021", Project3.argumentSlicer(args).get(2));
        assertEquals("1:00", Project3.argumentSlicer(args).get(3));
        assertEquals("PM", Project3.argumentSlicer(args).get(4));
        assertEquals("6/2/2021", Project3.argumentSlicer(args).get(5));
        assertEquals("2:00", Project3.argumentSlicer(args).get(6));
        assertEquals("PM", Project3.argumentSlicer(args).get(7));
    }
}
