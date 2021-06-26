package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointmentBook;
import java.util.Collection;


public class AppointmentBook<T extends AbstractAppointmentBook>{

    String ownerName;
    Collection<T> appointments;

    public AppointmentBook(String Owner)
    {
        ownerName = Owner;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void addAppointment(T appt)
    {
        appointments.add(appt);
    }

    public Collection<T> getAppointments()
    {

        return appointments;
    }
}
