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

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project2IT</code> which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */
class Project2Test extends InvokeMainTestCase{



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
        assertEquals(1, Project2.checkForPrintOption(args));
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
        assertEquals(0, Project2.checkForPrintOption(args));
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
        assertEquals(newApp.getClass(), Project2.makeAppointmentBook(args).getClass());
        assertEquals(newApp.getOwnerName(), Project2.makeAppointmentBook(args).getOwnerName());
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
        assertEquals("TestFileName", Project2.getFileName(args));
    }

    @Test
    void CheckArgumentSlicerReturnsCorrectList() {
        String [] args = new String[9];
        args[0] = "-print";
        args[1] = "-textFile";
        args[2] = "TestFileName";
        args[3] = "Joe";
        args[4] = "\"test\"";
        args[5] = "6/1/2021";
        args[6] = "1:00";
        args[7] = "6/2/2021";
        args[8] = "2:00";
        assertEquals(6, Project2.argumentSlicer(args).size());
        assertEquals("Joe", Project2.argumentSlicer(args).get(0));
        assertEquals("\"test\"", Project2.argumentSlicer(args).get(1));
        assertEquals("6/1/2021", Project2.argumentSlicer(args).get(2));
        assertEquals("1:00", Project2.argumentSlicer(args).get(3));
        assertEquals("6/2/2021", Project2.argumentSlicer(args).get(4));
        assertEquals("2:00", Project2.argumentSlicer(args).get(5));
    }
}
