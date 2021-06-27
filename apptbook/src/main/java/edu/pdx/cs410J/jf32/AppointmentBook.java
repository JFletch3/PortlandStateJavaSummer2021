package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;


public class AppointmentBook extends AbstractAppointmentBook<Appointment>
{
    String ownerName;
    Collection<Appointment> appointments;

    public AppointmentBook(String Owner)
    {
        ownerName = Owner;
        appointments = new ArrayList<Appointment>();
    }

    @Override
    public String getOwnerName()
    {
        return ownerName;
    }

    @Override
    public Collection<Appointment> getAppointments()
    {
        return appointments;
    }


    public void addAppointment(Appointment appt)
    {
        appointments.add(appt);
    }
}