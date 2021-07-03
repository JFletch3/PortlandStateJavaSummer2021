package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;


public class AppointmentBook extends AbstractAppointmentBook<Appointment>
{
    String ownerName;
    Collection<Appointment> appointments;

//    public AppointmentBook(String Owner)
//    {
//        ownerName = Owner;
//        appointments = new ArrayList<Appointment>();
//    }

    //SetOwner - Along with initializing the appointments collection to a new arraylist.
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        appointments = new ArrayList<Appointment>();
    }

//    public void setAppointments(Collection<Appointment> appointments)
//    {
//        this.appointments = appointments;
//    }

    @Override
    public String getOwnerName()
    {
        if (ownerName == null)
        {
            throw new UnsupportedOperationException("Owner Name is null.");
        }
        return ownerName;
    }

    @Override
    public Collection<Appointment> getAppointments()
    {
        if (appointments == null)
        {
            throw new UnsupportedOperationException("Appointments is null.");
        }
        return appointments;
    }


    public void addAppointment(Appointment appt)
    {
        appointments.add(appt);
    }
}