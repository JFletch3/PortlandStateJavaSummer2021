package edu.pdx.cs410J.jf32;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppointmentBookTest
{

    @Test
    void checkIfGetOwnerNameIsImplemented() {
        AppointmentBook book = new AppointmentBook();
        assertThrows(UnsupportedOperationException.class, book::getOwnerName);
    }

    @Test
    void checkIfGetAppointmentsIsImplemented() {
        AppointmentBook book = new AppointmentBook();
        assertThrows(UnsupportedOperationException.class, book::getAppointments);
    }

    @Test
    void checkIfaddAppointmentIsImplemented() {
        AppointmentBook book = new AppointmentBook();
        Appointment testApp = new Appointment();
        book.setOwnerName("testOwner");
        book.addAppointment(testApp);
        Collection<Appointment> testAppList = book.getAppointments();
        assertEquals(testAppList.iterator().next(), testApp);
    }

}
