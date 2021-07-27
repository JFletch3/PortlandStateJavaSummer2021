package edu.pdx.cs410J.jf32;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AppointmentBookServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AppointmentBookServletTest {

//  @Test
//  void initiallyServletContainsNoDictionaryEntries() throws ServletException, IOException {
//    AppointmentBookServlet servlet = new AppointmentBookServlet();
//
//    HttpServletRequest request = mock(HttpServletRequest.class);
//    HttpServletResponse response = mock(HttpServletResponse.class);
//    PrintWriter pw = mock(PrintWriter.class);
//
//    when(response.getWriter()).thenReturn(pw);
//
//    servlet.doGet(request, response);
//
//    int expectedWords = 0;
//    verify(pw).println(Messages.formatWordCount(expectedWords));
//    verify(response).setStatus(HttpServletResponse.SC_OK);
//  }

  @Test
  void AddAppointment() throws ServletException, IOException {
    AppointmentBookServlet servlet = new AppointmentBookServlet();

    String owner = "joe";
    String description = "TEST Description";
    String start  = "8/1/2021 10:00 AM";
    String end = "8/10/2021 10:00 PM";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("owner")).thenReturn(owner);
    when(request.getParameter("description")).thenReturn(description);
    when(request.getParameter("start")).thenReturn(start);
    when(request.getParameter("end")).thenReturn(end);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    assertThat(stringWriter.toString(), containsString(Messages.definedAppointment(owner, description, start, end)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());
    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));
    assertThat(servlet.getAppointmentBookOwner(owner), equalTo(owner));
  }

    @Test
    void testReadAllAppointments() throws ServletException, IOException, ParseException
    {
        AppointmentBookServlet servlet = new AppointmentBookServlet();

        String owner = "joe";
        String description = "TEST Description";
        String start  = "8/1/2021 10:00 AM";
        String end = "8/10/2021 10:00 PM";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("owner")).thenReturn(owner);
        when(request.getParameter("description")).thenReturn(description);
        when(request.getParameter("start")).thenReturn(start);
        when(request.getParameter("end")).thenReturn(end);

        HttpServletResponse response = mock(HttpServletResponse.class);

        // Use a StringWriter to gather the text from multiple calls to println()
        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);

        when(response.getWriter()).thenReturn(pw);


        servlet.ReadAllAppointments(response);

        // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
        ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(statusCode.capture());
        assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));
    }

    @Test
    void testWriteAllappointmentsForOwner() throws ServletException, IOException, ParseException
    {
        AppointmentBookServlet servlet = new AppointmentBookServlet();

        String owner = "joe";
        String description = "TEST Description";
        String start  = "8/1/2021 10:00 AM";
        String end = "8/10/2021 10:00 PM";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("owner")).thenReturn(owner);
        when(request.getParameter("description")).thenReturn(description);
        when(request.getParameter("start")).thenReturn(start);
        when(request.getParameter("end")).thenReturn(end);

        HttpServletResponse response = mock(HttpServletResponse.class);

        // Use a StringWriter to gather the text from multiple calls to println()
        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);

        when(response.getWriter()).thenReturn(pw);


        servlet.writeAllAppointmentsForOwner(response, owner);

        // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
        ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(statusCode.capture());
        assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));
    }

    @Test
    void testWriteAllappointments() throws ServletException, IOException, ParseException
    {
        AppointmentBookServlet servlet = new AppointmentBookServlet();

        String owner = "joe";
        String description = "TEST Description";
        String start  = "8/1/2021 10:00 AM";
        String end = "8/10/2021 10:00 PM";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("owner")).thenReturn(owner);
        when(request.getParameter("description")).thenReturn(description);
        when(request.getParameter("start")).thenReturn(start);
        when(request.getParameter("end")).thenReturn(end);

        HttpServletResponse response = mock(HttpServletResponse.class);

        // Use a StringWriter to gather the text from multiple calls to println()
        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);

        when(response.getWriter()).thenReturn(pw);


        servlet.writeAllAppointments(response, owner, start, end);

        // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
        ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(statusCode.capture());
        assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));
    }


    @Test
    void CheckDateFormatReturnsFalseForBadDate() throws ServletException, IOException {
        AppointmentBookServlet servlet = new AppointmentBookServlet();

        String owner = "joe";
        String description = "TEST Description";
        String start  = "Fake Date";
        String end = "8/10/2021 10:00 PM";

        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);
        when(response.getWriter()).thenReturn(pw);

        assertThat(servlet.checkDateFormat(response, start), equalTo(false));
    }

    @Test
    void CheckDateFormatReturnsTrueForValidDate() throws ServletException, IOException {
        AppointmentBookServlet servlet = new AppointmentBookServlet();

        String owner = "joe";
        String description = "TEST Description";
        String start  = "8/10/2021 10:00 PM";
        String end = "8/10/2021 10:00 PM";

        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);
        when(response.getWriter()).thenReturn(pw);

        assertThat(servlet.checkDateFormat(response, start), equalTo(true));
    }

}
