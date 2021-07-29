package edu.pdx.cs410J.jf32;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    /**
     * method to format books count
     * @param count
     *      count of the appointment book list size.
     */
    public static String formatBOOKSCount(int count )
    {
        return String.format( "Appointment Book on server contains %d books", count );
    }

    /**
     * method format dictionary entry.
     * @param word
     *      work for dictionary.
     * @param definition
     *      definition of the work.
     */
    public static String formatDictionaryEntry(String word, String definition )
    {
        return String.format("  %s : %s", word, definition);
    }

    /**
     * method format for missing parameter.
     * @param parameterName
     *      parameter that is missing.
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * method define appointment
     * @param Owner
     *      Book owner
     * @param desc
     *      appointment description.
     * @param start
     *      appointment start date
     * @param end
     *      appointment end date
     */
    public static String definedAppointment(String Owner, String desc, String start, String end )
    {
        return String.format( "Created Appointment: Owner: %s Desc: %s Start: %s End: %s",
                Owner, desc, start, end );
    }

    /**
     * method define appointmens As
     * @param book
     *      newly created book.
     * @param appointment
     *      new appointment
     */
    public static String defineAppointmentAs(AppointmentBook book, Appointment appointment)
    {
        return String.format( "Created Appointment: Owner: %s" +
                                " Desc: %s" +
                                " Start: %s" +
                                " End: %s",
                                book.getOwnerName(),
                                appointment.getDescription(),
                                appointment.getThisStartDate() + " " + appointment.getThisStartTime(),
                                appointment.getThisEndDate() + " " + appointment.getThisEndTime());
    }

    /**
     * method for dictionary enteries deleted.
     */
    public static String allDictionaryEntriesDeleted() {
        return "All dictionary entries have been deleted";
    }

    /**
     * method parse dictionary entries.
     * @param content
     *      response content
     */
    public static Map.Entry<String, String> parseDictionaryEntry(String content) {
        Pattern pattern = Pattern.compile("\\s*(.*) : (.*)");
        Matcher matcher = pattern.matcher(content);

        if (!matcher.find()) {
            return null;
        }

        return new Map.Entry<>() {
            @Override
            public String getKey() {
                return matcher.group(1);
            }

            @Override
            public String getValue() {
                String value = matcher.group(2);
                if ("null".equals(value)) {
                    value = null;
                }
                return value;
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException("This method is not implemented yet");
            }
        };
    }

    /**
     * method to format appointment book entries.
     * @param pw
     *      Print writer for broswer
     * @param BOOKS list of Appointment books/
     *
     */
    public static void formatAppointmentEntries(PrintWriter pw, List<AppointmentBook> BOOKS) {

        PrettyPrint printer = new PrettyPrint(null, null);

        for (AppointmentBook book : BOOKS)
        {
            printer.ServletPrinter(pw, book);
        }

    }

    /**
     * method to print all books.
     * @param pw
     *      Print writer for broswer
     * @param BOOK list of Appointment books/
     *
     */
    public static void printallBooks(PrintWriter pw, List<AppointmentBook> BOOK)
    {
        pw.println(Messages.formatBOOKSCount(BOOK.size()));
        TextDumper dumper = new TextDumper();

        for (AppointmentBook thisbook : BOOK)
        {
            try
            {
                dumper.serverDump(pw, thisbook);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * method to format searched appointment
     * @param pw
     *      Print writer for browser
     * @param BOOK list of Appointment books/
     *
     */
    public static void formatSearchedAppointment(PrintWriter pw, AppointmentBook BOOK)
    {
        TextDumper printer = new TextDumper();
        try
        {
            printer.serverDump(pw, BOOK);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * method to format appointment book
     * @param content response content.
     *
     */
    public static AppointmentBook parseAppointmentBook(String content) {
        AppointmentBook book = new AppointmentBook();

        String[] lines = content.split("\n\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (line.contains("app_book"))
            {
                book.setOwnerName(line.substring(line.lastIndexOf("=")+1));
            }
            else if (line.contains("--NEW APPOINTMENT--"))
            {
                Appointment newAppointment = new Appointment();
                newAppointment.setDescription(lines[i+1].substring(lines[i+1].lastIndexOf("=")+1));
                newAppointment.setStartDate(lines[i+2].substring(lines[i+2].lastIndexOf("=")+1));
                newAppointment.setStartTime(lines[i+3].substring(lines[i+3].lastIndexOf("=")+1));
                newAppointment.setEndDate(lines[i+4].substring(lines[i+4].lastIndexOf("=")+1));
                newAppointment.setEndTime(lines[i+5].substring(lines[i+5].lastIndexOf("=")+1));
                book.addAppointment(newAppointment);
            }
        }
        return book;
    }

    /**
     * method to print all appointment books if no param is entered in http request.
     * @param content response content.
     */
    public static List<AppointmentBook> parseALLappointmentBooks(String content) {
        List<AppointmentBook> bookList = new ArrayList<>();
        AppointmentBook book = new AppointmentBook();

        String[] lines = content.split("\n\n");
        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            //  Map.Entry<String, String> entry = parseDictionaryEntry(line);
            //  book.setOwnerName(line.substring(line.lastIndexOf("=")+1));

            if (line.contains("app_book"))
            {
                book.setOwnerName(line.substring(line.lastIndexOf("=")+1));
                bookList.add(book);
            }
            else if (line.contains("--NEW APPOINTMENT--"))
            {
                Appointment newAppointment = new Appointment();
                newAppointment.setDescription(lines[i+1].substring(lines[i+1].lastIndexOf("=")+1));
                newAppointment.setStartDate(lines[i+2].substring(lines[i+2].lastIndexOf("=")+1));
                newAppointment.setStartTime(lines[i+3].substring(lines[i+3].lastIndexOf("=")+1));
                newAppointment.setEndDate(lines[i+4].substring(lines[i+4].lastIndexOf("=")+1));
                newAppointment.setEndTime(lines[i+5].substring(lines[i+5].lastIndexOf("=")+1));
                book.addAppointment(newAppointment);
            }
        }
        return bookList;
    }
}
