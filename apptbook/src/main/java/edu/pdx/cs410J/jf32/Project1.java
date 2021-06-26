package edu.pdx.cs410J.jf32;
import java.util.Collection;
/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) {

    Collection appts;
//    System.out.println(args[0]);
//    System.out.println(args[1]);
//    System.out.println(args[2]);
//    System.out.println(args[3]);

    AppointmentBook newAppointmentBook = new AppointmentBook(args[0]);

    Appointment appointment = new Appointment();
    appointment.setDescription(args[1]);
    appointment.setStartTime(args[2]);
    appointment.setEndTime(args[3]);

    newAppointmentBook.addAppointment(appointment);

    appts = newAppointmentBook.getAppointments();

    appts

    for (String arg : args) {
      System.out.println(arg);
    }


    System.exit(1);
  }

}