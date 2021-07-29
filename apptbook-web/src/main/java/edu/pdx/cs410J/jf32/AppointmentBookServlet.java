package edu.pdx.cs410J.jf32;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AppointmentBookServlet extends HttpServlet
{
    /**
     * Owner parameter
     */
    static final String OWNER_PARAMETER     = "owner";
    /**
     * Description parameter
     */
    static final String DESC_PARAMETER      = "description";
    /**
     * start parameter
     */
    static final String START_PARAMETER     = "start";
    /**
     * end parameter
     */
    static final String END_PARAMETER       = "end";
    /**
     * Old dictionary.
     */
    private final Map<String, String> dictionary = new HashMap<>();
    /**
     * List of appointment books.
     */
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

        String owner    = getParameter( OWNER_PARAMETER, request );
        String start    = getParameter( START_PARAMETER, request );
        String end      = getParameter( END_PARAMETER, request );
        String startDate = null;
        String endDate = null;
        if (start != null)
        {
            checkDateFormat(response, start);
            startDate =  start;
        }
        if (end != null)
        {
           checkDateFormat(response, end);
           endDate = end;
        }

        if (start != null && end != null && owner != null)
        {
            try
            {
                writeAllAppointments(response, owner, startDate, endDate);
            } catch (ParseException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else if (owner != null)
        {
            try
            {
                writeAllAppointmentsForOwner(response, owner);
            }
            catch (ParseException e)
            {
                System.err.println("Owner does not exists. ");
                System.out.println(e.getMessage());
            }
        }
        else
        {
            try
            {
                ReadAllAppointments(response);
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
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
        String [] startTimeSplit = start.split(" ");
        String [] endTimeSplit = end.split(" ");
        int ownedBook = 0;

        for (AppointmentBook ab : BOOKS)
        {
            if (ab.getOwnerName().equals(owner))
            {
                ownedBook = 1;
                appointment = new Appointment();
                appointment.setDescription(description);
                appointment.setStartDate(startTimeSplit[0]);
                appointment.setStartTime(startTimeSplit[1] + " " + startTimeSplit[2]);
                appointment.setEndDate(endTimeSplit[0]);
                appointment.setEndTime(endTimeSplit[1] + " " + endTimeSplit[2]);
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
            appointment.setStartTime(startTimeSplit[1] + " " + startTimeSplit[2]);
            appointment.setEndDate(endTimeSplit[0]);
            appointment.setEndTime(endTimeSplit[1] + " " + endTimeSplit[2]);
            newbook.addAppointment(appointment);
            BOOKS.add(newbook);
        }


        PrintWriter pw = response.getWriter();
        pw.println(Messages.defineAppointmentAs(newbook, appointment));
        pw.flush();
        response.setStatus( HttpServletResponse.SC_OK);
        System.out.println("Created Appointment: Owner: " + owner + " Desc: " + description + " Start: " + start + " End: " + end);
        System.out.println("======================");
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
    public void missingRequiredParameter( HttpServletResponse response, String parameterName ) throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes all of the appointment book entries to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    public void writeAllAppointments(HttpServletResponse response, String owner, String start, String end ) throws IOException, ParseException
    {
        PrintWriter pw = response.getWriter();

        if (owner != null)
        {
            for (AppointmentBook book : BOOKS)
            {
                if (owner.equalsIgnoreCase(book.getOwnerName()))
                {
                    AppointmentBook tempBook = getSearchedAppointments(book, start, end);
                    Messages.formatSearchedAppointment(pw, tempBook);
                }
            }
        }
        else
        {
            Messages.formatAppointmentEntries(pw, BOOKS);
        }

        pw.flush();
        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Writes all of the appointment book entries to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    public void writeAllAppointmentsForOwner(HttpServletResponse response, String owner) throws IOException, ParseException
    {
        PrintWriter pw = response.getWriter();
        int ownerExists = 0;
        if (owner != null)
        {
            for (AppointmentBook book : BOOKS)
            {
                if (owner.equalsIgnoreCase(book.getOwnerName()))
                {
                    ownerExists = 1;
                    Messages.formatSearchedAppointment(pw, book);
                }
            }
        }
        else
        {
            Messages.formatAppointmentEntries(pw, BOOKS);
        }

        pw.flush();
        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Writes all of the appointment book entries to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    public void ReadAllAppointments(HttpServletResponse response) throws IOException, ParseException
    {
        PrintWriter pw = response.getWriter();

        Messages.printallBooks(pw, BOOKS);

        pw.flush();
        response.setStatus( HttpServletResponse.SC_OK );
    }


    /**
     * Returns the value of the HTTP request parameter with the given name.
     * @param BOOK
     *      Appointment book
     * @param start
     *      Start time
     * @param end
     *      End time
     * @return retBook
     *      Returned appointmentbook for the searched parameters.
     */
    public AppointmentBook getSearchedAppointments(AppointmentBook BOOK, String start, String end) throws ParseException
    {
        AppointmentBook retBook = new AppointmentBook();
        retBook.setOwnerName(BOOK.getOwnerName());
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        dFormat.setLenient(false);
        Date searchStart = dFormat.parse(start);
        Date searchEnd = dFormat.parse(end);

        for (Appointment ap : BOOK.getAppointments())
        {
            String appointmentStart = ap.getThisStartDate() + " " + ap.getThisStartTime();
            Date appointmentStartDate = dFormat.parse(appointmentStart);

            if (appointmentStartDate.getTime() >= searchStart.getTime() && appointmentStartDate.getTime() <= searchEnd.getTime())
            {
                retBook.addAppointment(ap);
            }
        }
        return retBook;
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

    /**
     * Method to check the date format to make sure the date is valid.
     * @param date
     *      The date passed in from the commandline arguments.
     */
    public boolean checkDateFormat(HttpServletResponse response, String date) throws IOException
    {
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        dFormat.setLenient(false);
        PrintWriter pw = response.getWriter();
        try
        {
            Date retDate = dFormat.parse(date);
        }
        catch (ParseException e)
        {
            pw.println("Date format and/or Date is not valid: " + date + " --- Format Should be mm/dd/yyyy" +
                    "and date should be a real date.");
            return false;
        }

        return true;
    }

    /**
     * Method to check for appointmentbook owner - used for test
     * @param owner
     *      Owner of the appointment book
     * @return book
     *      Book what matches the owner.
     */
    @VisibleForTesting
    String getAppointmentBookOwner(String owner) {

        for (AppointmentBook book : this.BOOKS)
        {
            if (book.getOwnerName().equals(owner))
            {
                return  book.getOwnerName();
            }
        }
        return null;
    }
}
