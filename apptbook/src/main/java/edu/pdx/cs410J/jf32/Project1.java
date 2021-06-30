package edu.pdx.cs410J.jf32;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) {

    Collection<Appointment> appts;
    AppointmentBook newAppointmentBook = new AppointmentBook();
    Appointment appointment = new Appointment();

    if (args.length != 6)
    {
      throw new UnsupportedOperationException("Invalid amount of CL arguments. There must be 6 args " +
                                              "- Owner, Description, Start date, Start time" +
                                              ", End date and End time.");
    }

    newAppointmentBook.setOwnerName(args[0]);
    appointment.setDescription(args[1]);
    appointment.setStartTime(args[2], args[3]);
    appointment.setEndTime(args[4], args[5]);

    newAppointmentBook.addAppointment(appointment);

    appts = newAppointmentBook.getAppointments();

    System.out.println(newAppointmentBook);
    for(Appointment ap : appts)
    {
      System.out.println(ap.toString());
    }

    System.exit(1);
  }

}