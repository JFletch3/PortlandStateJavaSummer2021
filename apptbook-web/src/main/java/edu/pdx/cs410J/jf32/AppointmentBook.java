package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;

/**
 * This class is represents a <code>AppointmentBook</code>.
 */
public class AppointmentBook extends AbstractAppointmentBook<Appointment>
{
    /**
     * Appointment Book Owner name
     */
    String ownerName;
    /**
     * Appointment book collection of appointments.
     */
    ArrayList<Appointment> appointments;

    /**
     * Sets the <code>AppointmentBook</code> Owner name
     *
     * @param ownerName
     *        AppointmentBook Owner name.
     */
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        appointments = new ArrayList<Appointment>();
    }

    /**
     * Gets the <code>AppointmentBook</code> Owner name
     *
     * @return ownerName
     *          AppointmentBook Owner name
     */
    @Override
    public String getOwnerName()
    {
        if (ownerName == null)
        {
            throw new UnsupportedOperationException("Owner Name is null.");
        }
        return ownerName;
    }

    /**
     * Gets the <code>AppointmentBook</code> Collection of Appointments
     *
     * @return appointments
     *          AppointmentBook Collection of Appointments.
     */
    @Override
    public ArrayList<Appointment> getAppointments()
    {
        if (appointments == null)
        {
            throw new UnsupportedOperationException("Appointments is null.");
        }
        return appointments;
    }

    /**
     *  Adds an Appointment to the  <code>AppointmentBook</code> Collection
     *
     * @param  appt
     *         An Appointment.
     */
    public void addAppointment(Appointment appt)
    {
        appointments.add(appt);
    }
}