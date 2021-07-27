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
    public static String formatBOOKSCount(int count )
    {
        return String.format( "Appointment Book on server contains %d books", count );
    }

    public static String formatDictionaryEntry(String word, String definition )
    {
        return String.format("  %s : %s", word, definition);
    }

    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String definedAppointment(String Owner, String desc, String start, String end )
    {
        return String.format( "Created Appointment: Owner: %s Desc: %s Start: %s End: %s",
                Owner, desc, start, end );
    }

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

    public static String allDictionaryEntriesDeleted() {
        return "All dictionary entries have been deleted";
    }

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

    public static void formatAppointmentEntries(PrintWriter pw, List<AppointmentBook> BOOKS) {

        PrettyPrint printer = new PrettyPrint(null, null);

        for (AppointmentBook book : BOOKS)
        {
            printer.ServletPrinter(pw, book);
        }

    }

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

    public static void formatSearchedAppointmentCommandLine(PrintWriter pw, AppointmentBook BOOK)
    {
        PrettyPrint printer = new PrettyPrint(null, null);
        printer.ServletPrinter(pw, BOOK);
    }

    public static void formatDictionaryEntries(PrintWriter pw, Map<String, String> dictionary) {
        pw.println(Messages.formatBOOKSCount(dictionary.size()));

        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            pw.println(Messages.formatDictionaryEntry(entry.getKey(), entry.getValue()));
        }
    }

    public static Map<String, String> parseDictionary(String content) {
        Map<String, String> map = new HashMap<>();

        String[] lines = content.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            Map.Entry<String, String> entry = parseDictionaryEntry(line);
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static AppointmentBook parseAppointmentBook(String content) {
        AppointmentBook book = new AppointmentBook();

        String[] lines = content.split("\n\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
          //  Map.Entry<String, String> entry = parseDictionaryEntry(line);
          //  book.setOwnerName(line.substring(line.lastIndexOf("=")+1));

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
