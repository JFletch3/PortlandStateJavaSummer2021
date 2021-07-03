package edu.pdx.cs410J.jf32;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppointmentBookTest {

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

//    @Test
//    void checkIfGetAppointmentsIsImplemented() {
//        AppointmentBook book = new AppointmentBook();
//        assertThrows(UnsupportedOperationException.class, book::getAppointments);
//    }

}
