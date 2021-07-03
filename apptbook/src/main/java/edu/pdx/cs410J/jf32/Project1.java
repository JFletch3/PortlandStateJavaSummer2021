package edu.pdx.cs410J.jf32;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {


  public static void readME()
  {
    System.out.println("Project 1 - CS410 - Joe Fletcher\n" +
                      "This is an Appointment book program which creates an Appointment book and an Appointment based on arguments.\n" +
                      "Command line arguments:\n" +
                      "\tOwner, Appointment Description (wrapped in quotes), Start date, Start time, End date, End time.\n" +
                      "\tEX: Joe \"First appointment\" 06/01/2021 1:00 06/01/2021 2:00\n" +
                      "NOTE: AM / PM are not needed for this program.\n" +
                      "Along with the command line arguments you may enter two optional arguments at the front of the command line\n" +
                      "\t-README  -PRINT\n" +
                      "\t-README\t\tPrints out this statement.\n" +
                      "\t-PRINT\t\tWill print out the details of your appointment book and appointment.\n" +
                      "Its worth noting that if you have -README as an option, the program will ONLY print this out.\n" +
                      "Leave -README off the argument list if you would like the program to run fully.");
  }

  public static void checkDateFormat(String date)
  {
    DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy");
    dFormat.setLenient(false);

    try
    {
      dFormat.parse(date);
    }
    catch (ParseException e)
    {
      System.err.println("Date format not correct: " + date + " --- Should be mm/dd/yyyy");
      System.exit(1);
    }
  }

  public static void main(String[] args) {

    Collection<Appointment> appts;
    AppointmentBook newAppointmentBook = new AppointmentBook();
    Appointment appointment = new Appointment();
    int printOption = 0;


    if (args.length == 0)
    {
      System.err.println("Missing command line arguments.");
      System.exit(1);
    }
    else if (args.length < 6)
    {
      System.err.println("Insufficient number of arguments.");
      System.exit(1);
    }

    for(String ap : args)
    {
      if (ap.toUpperCase().contains("-README"))
      {
        readME();
        System.exit(1);
      }
    }

    if(!args[0].toUpperCase().contains("-PRINT"))
    {
      for (String ap : args)
      {
        if (ap.toUpperCase().contains("-PRINT"))
        {
          System.err.println("Option -PRINT is in the wrong location in the argument list.");
          System.exit(1);
        }
      }

      checkDateFormat(args[2]);
      checkDateFormat(args[4]);

      newAppointmentBook.setOwnerName(args[0]);
      appointment.setDescription(args[1]);
      appointment.setStartTime(args[2], args[3]);
      appointment.setEndTime(args[4], args[5]);
    }
    else if (args[0].toUpperCase().contains("-PRINT"))
    {
      printOption = 1;
      checkDateFormat(args[3]);
      checkDateFormat(args[5]);
      newAppointmentBook.setOwnerName(args[1]);
      appointment.setDescription(args[2]);
      appointment.setStartTime(args[3], args[4]);
      appointment.setEndTime(args[5], args[6]);
    }

    newAppointmentBook.addAppointment(appointment);
    appts = newAppointmentBook.getAppointments();

    System.out.println(newAppointmentBook);

    if (printOption == 1)
    {
      for(Appointment ap : appts)
      {
        System.out.println(ap.toString());
      }
    }

    System.exit(1);
  }

}