package edu.pdx.cs410J.jf32;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AppointmentBookServlet extends HttpServlet
{
    static final String WORD_PARAMETER = "word";
    static final String DEFINITION_PARAMETER = "definition";
    static final String OWNER_PARAMETER     = "owner";
    static final String DESC_PARAMETER      = "description";
    static final String START_PARAMETER     = "start";
    static final String END_PARAMETER       = "end";

    private final Map<String, String> dictionary = new HashMap<>();

    private final List<AppointmentBook> BOOKS = new ArrayList<AppointmentBook>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String owner = getParameter( OWNER_PARAMETER, request );

        writeAllAppointments(response);
//        if (word != null) {
//            writeDefinition(word, response);
//
//        } else {
//            writeAllDictionaryEntries(response);
//        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {

        response.setContentType( "text/plain" );

        String owner = getParameter(OWNER_PARAMETER, request );
        if (owner == null) {
            missingRequiredParameter(response, OWNER_PARAMETER);
            return;
        }

        String description = getParameter(DESC_PARAMETER, request );
        if ( description == null) {
            missingRequiredParameter( response, DESC_PARAMETER );
            return;
        }

        String start = getParameter(START_PARAMETER, request );
        if ( start == null) {
            missingRequiredParameter( response, START_PARAMETER );
            return;
        }

        String end = getParameter(END_PARAMETER, request );
        if ( end == null) {
            missingRequiredParameter( response, END_PARAMETER );
            return;
        }

        AppointmentBook newbook = null;
        Appointment appointment = null;
        String [] startTimeSplit = start.split(", ");
        String [] endTimeSplit = end.split(", ");
        int ownedBook = 0;

        System.out.println("Adding Appointment:");
        System.out.println("Owner: " +owner);
        System.out.println("Start Time: " + startTimeSplit[0] + " " + startTimeSplit[1]);
        System.out.println("End Time: " + endTimeSplit[0] + " " + endTimeSplit[1]);

        System.out.println(description);
        System.out.println(start);
        System.out.println(end);

        for (AppointmentBook ab : BOOKS)
        {
            if (ab.getOwnerName().equals(owner))
            {
                ownedBook = 1;
                appointment = new Appointment();
                appointment.setDescription(description);
                appointment.setStartDate(startTimeSplit[0]);
                appointment.setStartTime(startTimeSplit[1]);
                appointment.setEndDate(endTimeSplit[0]);
                appointment.setEndTime(endTimeSplit[1]);
                ab.addAppointment(appointment);
                newbook = ab;
            }
        }

        if (ownedBook == 0)
        {
            newbook = new AppointmentBook();
            newbook.setOwnerName(owner);
            appointment = new Appointment();
            appointment.setDescription(description);
            appointment.setStartDate(startTimeSplit[0]);
            appointment.setStartTime(startTimeSplit[1]);
            appointment.setEndDate(endTimeSplit[0]);
            appointment.setEndTime(endTimeSplit[1]);
            newbook.addAppointment(appointment);
            BOOKS.add(newbook);
        }

        //this.dictionary.put(owner, description, start, end);
        PrintWriter pw = response.getWriter();
        pw.println(Messages.defineAppointmentAs(newbook, appointment));
        pw.flush();
        response.setStatus( HttpServletResponse.SC_OK);
        System.out.println("Appointment Added.");
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.dictionary.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the definition of the given word to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    private void writeDefinition(String word, HttpServletResponse response) throws IOException {
        String definition = this.dictionary.get(word);

        if (definition == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();
            pw.println(Messages.formatDictionaryEntry(word, definition));

            pw.flush();

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Writes all of the dictionary entries to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        Messages.formatAppointmentEntries(pw, BOOKS);

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Writes all of the appointment book entries to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    private void writeAllAppointments(HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        Messages.formatAppointmentEntries(pw, BOOKS);

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }


    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    @VisibleForTesting
    String getDefinition(String word) {
        return this.dictionary.get(word);
    }
}
