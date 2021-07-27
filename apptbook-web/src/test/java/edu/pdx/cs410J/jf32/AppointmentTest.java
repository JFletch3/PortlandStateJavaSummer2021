package edu.pdx.cs410J.jf32;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Appointment} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class AppointmentTest
{

  @Test
  void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment();
    assertThrows(UnsupportedOperationException.class, appointment::getBeginTimeString);
  }

  @Test
  void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getDescription(), containsString("not implemented"));
  }

  @Test
  void forProject3ItIsOkayIfGetBeginTimeReturnsNull() {
    Appointment appointment = new Appointment();
    appointment.setStartDate("6/1/2021");
    appointment.setStartTime("1:00 PM");
    assertEquals(appointment.getBeginTime().getClass(), Date.class);
  }

  @Test
  void forProject3ItIsOkayIfGetEndTimeReturnsNull() {
    Appointment appointment = new Appointment();
    appointment.setEndDate("6/1/2021");
    appointment.setEndTime("1:00 PM");
    assertEquals(appointment.getEndTime().getClass(), Date.class);
  }

  //Test that setting the description via setDescription sets the description correctly.
  @Test
  void setDescriptionSetsTheDescription() {
    Appointment appointment = new Appointment();
    String desc = "test description";
    appointment.setDescription(desc);
    assertEquals(appointment.getDescription(), desc);
  }

  //Test that setting the set Start time function sets the start date/time correctly.
  @Test
  void setStartTimeAndDate() {
    Appointment appointment = new Appointment();
    String date = "1/1/2021";
    String time = "8:00 PM";
    String start = date + " " + time;
    DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    dFormat.setLenient(false);
    Date date1 = null;
    try
    {
      date1 = dFormat.parse(start);
    }
    catch (ParseException e)
    {
      System.err.println(e.getMessage() + " Date " + start) ;
      System.exit(1);
    }
    appointment.setStartTime(time);
    appointment.setStartDate(date);

    assertEquals(appointment.getBeginTimeString(), DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date1));
  }

  //Test that setting the set End time function sets the End date/time correctly.
  @Test
  void setEndTimeAndDate() {
    Appointment appointment = new Appointment();
    String date = "1/1/2021";
    String time = "8:00 PM";
    String start = date + " " + time;
    DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    dFormat.setLenient(false);
    Date date1 = null;
    try
    {
      date1 = dFormat.parse(start);
    }
    catch (ParseException e)
    {
      System.err.println(e.getMessage() + " Date " + start) ;
      System.exit(1);
    }
    appointment.setEndTime(time);
    appointment.setEndDate(date);
    assertEquals(appointment.getEndTimeString(), DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date1));
  }

  @Test
  void TestStartDateSort() {
    AppointmentBook book = new AppointmentBook();
    Appointment appointment = new Appointment();
    assertThat(appointment.getDescription(), containsString("not implemented"));
  }

}
