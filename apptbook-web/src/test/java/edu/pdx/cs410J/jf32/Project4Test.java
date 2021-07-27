package edu.pdx.cs410J.jf32;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Project4Test extends InvokeMainTestCase
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
        assertEquals(1, Project4.checkForPrintOption(args));
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
        assertEquals(0, Project4.checkForPrintOption(args));
    }

    @Test
    void CheckMakeAppointmentReturnsCorrectAppointment() {
        Appointment newApp = new Appointment();
        List<String> args = new ArrayList<String>();
        args.add("Joe");
        args.add("\"test\"");
        args.add("6/1/2021");
        args.add("1:00");
        args.add("PM");
        args.add("6/2/2021");
        args.add("2:00");
        args.add("PM");
        String startTime = args.get(3) + " " + args.get(4);
        String endTime = args.get(6) + " " + args.get(7);

        newApp.setDescription(args.get(1));
        newApp.setStartDate(args.get(2));
        newApp.setStartTime(startTime);
        newApp.setEndDate(args.get(5));
        newApp.setEndTime(endTime);
        assertEquals(newApp.getClass(), Project4.makeAppointment(args).getClass());
        assertEquals(newApp.getDescription(), Project4.makeAppointment(args).getDescription());
        assertEquals(newApp.getBeginTimeString(), Project4.makeAppointment(args).getBeginTimeString());
        assertEquals(newApp.getEndTimeString(), Project4.makeAppointment(args).getEndTimeString());
    }

    @Test
    void CheckMakeAppointmentBookReturnsCorrectAppointment() {
        AppointmentBook newApp = new AppointmentBook();
        List<String> args = new ArrayList<String>();
        args.add("Joe");
        newApp.setOwnerName(args.get(0));
        assertEquals(newApp.getClass(), Project4.makeAppointmentBook(args).getClass());
        assertEquals(newApp.getOwnerName(), Project4.makeAppointmentBook(args).getOwnerName());
    }

}
