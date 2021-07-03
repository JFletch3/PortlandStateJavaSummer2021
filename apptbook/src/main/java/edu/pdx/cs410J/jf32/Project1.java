package edu.pdx.cs410J.jf32;
import java.util.ArrayList;
import java.util.Collection;

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


  public static void main(String[] args) {


    Collection<Appointment> appts;
    AppointmentBook newAppointmentBook = new AppointmentBook();
    Appointment appointment = new Appointment();
    int printOption = 0;

    for(String ap : args)
    {
      if (ap.toUpperCase().contains("-README"))
      {
        readME();
        System.exit(1);
      }
      else if (ap.toUpperCase().contains("-PRINT"))
      {
        printOption = 1;
      }
    }

    if (args.length == 0)
    {
      throw new UnsupportedOperationException("Invalid amount of CL arguments." +
                                              "Owner, Description, Start date, Start time, End date and End time.");
    }

    newAppointmentBook.setOwnerName(args[0]);
    appointment.setDescription(args[1]);
    appointment.setStartTime(args[2], args[3]);
    appointment.setEndTime(args[4], args[5]);

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