package edu.pdx.cs410J.jf32;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args)
    {
        String hostName = null;
        String portString = null;
        String searchString = null;
        String word = null;
        String definition = null;
        int printOption = 0;
        List<String> CLArguments = null;
        List<String> searchDetails = null;
        AppointmentBook newBook = new AppointmentBook();
        Appointment newApt      = new Appointment();

        //------------------------------------------------------------
        //-README check - leave this here, its just a print and exit
        // This needs to be the first thing checked.
        if (args.length == 0)
        {
            System.err.println(MISSING_ARGS);
            System.exit(1);
        }
        for(String ap : args)
        {
            if (ap.toUpperCase().contains("-README"))
            {
                usage("Read Me");
                System.exit(1);
            }
        }
        //-----------------------------------------------------------
        // Parse arguments
        checkCLArgCount(args);
        hostName        = getArgumentValue(args, "-HOST");
        portString      = getArgumentValue(args, "-PORT");
        searchString    = getArgumentValue(args, "-SEARCH");
        printOption     = checkForPrintOption(args); //TODO write code to do something with this
        CLArguments     = argumentSlicer(args);
        //-----------------------------------------------------------

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);

        // Add appointment book / appointment to the server.
        String message = "";

        //If Search is entered I want to search, print and end.
        if (!searchString.equals(""))
        {
            try
            {
                searchDetails = getSearchInfo(args, searchString);
                AppointmentBook searchBook = client.getSearchedAppointmentBook(searchDetails);
                Collections.sort(searchBook.getAppointments());
                SearchAndPrettyPrint(searchBook, searchDetails.get(1), searchDetails.get(2));
                System.exit(1);
            } catch (IOException | ParseException e)
            {
                System.err.println("Invalid Host or Port. Please check argument inputs.");
                System.exit(0);
            }
        }

        if (searchString.equals("") && printOption == 0)
        {
            if (CLArguments.size() == 1)
            {
                try
                {
                    AppointmentBook searchBook = client.getSearchedAppointmentBookOwnerOnly(CLArguments.get(0));
                    PrettyPrint print = new PrettyPrint(null, null);
                    Collections.sort(searchBook.getAppointments());
                    print.stdDump(searchBook);
                    System.exit(0);
                } catch (IOException e)
                {
                    System.err.println("Invalid Host or Port. Please check argument inputs.");
                    System.exit(0);
                }
            }
        }

        //-----------------------------------------------------------
        // Create Appointment book.
        newBook = makeAppointmentBook(CLArguments);
        newApt = makeAppointment(CLArguments);
        newBook.addAppointment(newApt);
        //-----------------------------------------------------------

        //-----------------------------------------------------------
        //check for the print option. if set to 1, print out the information.
        if (printOption == 1)
        {
            List<Appointment> appts = newBook.getAppointments();
            System.out.println(newBook);
            for(Appointment ap : appts)
            {
                System.out.println(ap.toString());
            }
        }
        //-----------------------------------------------------------


        client.addAppointmentEntry(newApt, CLArguments.get(0));

        System.out.println(message);
        System.exit(0);
    }

    /**
     * Method to check the if the -print option was entered in the arguments
     * @param BOOK
     *      String array of the command line arguments
     * @param start
     *      Start date to search for.
     * @param end
     *      End date for searching.
     *
     */
    public static void SearchAndPrettyPrint(AppointmentBook BOOK, String start, String end) throws ParseException
    {
        AppointmentBook retBook = new AppointmentBook();
        retBook.setOwnerName(BOOK.getOwnerName());
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        dFormat.setLenient(false);
        Date searchStart = dFormat.parse(start);
        Date searchEnd = dFormat.parse(end);
        int appointmentCount = 0;

        for (Appointment ap : BOOK.getAppointments())
        {
            String appointmentStart = ap.getThisStartDate() + " " + ap.getThisStartTime();
            Date appointmentStartDate = dFormat.parse(appointmentStart);

            if (start != null && end != null)
            {
                if (appointmentStartDate.getTime() >= searchStart.getTime() && appointmentStartDate.getTime() <= searchEnd.getTime())
                {
                    appointmentCount += 1;
                    retBook.addAppointment(ap);
                }
            }
        }
        if (appointmentCount > 0)
        {
            PrettyPrint print = new PrettyPrint(null, null);
            try
            {
                print.stdDump(retBook);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }else
        {
            System.err.println("No Appointments found that start between " + start + " and " + end);
        }
    }

    /**
     * Method to check the if the -print option was entered in the arguments
     * @param args
     *      String array of the command line arguments
     * @return newAppointmentBook
     *      Newly created appointment book.
     */
    public static AppointmentBook makeAppointmentBook(List<String> args)
    {
        AppointmentBook newAppointmentBook = new AppointmentBook();
        newAppointmentBook.setOwnerName(args.get(0));
        return newAppointmentBook;
    }

    /**
     * Method to check the if the -print option was entered in the arguments
     * @param args
     *      String array of the command line arguments
     * @return newAppointmentBook
     *      Newly created appointment.
     */
    public static Appointment makeAppointment(List<String> args)
    {
        Appointment newAppointment = new Appointment();

        String startTime = args.get(3) + " " + args.get(4);
        String endTime = args.get(6) + " " + args.get(7);

        newAppointment.setDescription(args.get(1));
        newAppointment.setStartDate(args.get(2));
        newAppointment.setStartTime(startTime);
        newAppointment.setEndDate(args.get(5));
        newAppointment.setEndTime(endTime);

        return newAppointment;
    }

    /**
     * Method to get the file name if -testFile is used as an option
     * @param args
     *      String array of the command line arguments
     * @param key
     *      String variable passed in to search for. This should be a command line argument.
     * @return fileName
     *      name of the file passed in on the command line arguments
     */
    public static String getArgumentValue(String[] args, String key)
    {
        String argValue = "";

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].toUpperCase().contains(key))
            {
                argValue = args[i+1];
            }
        }

        return argValue;
    }

    /**
     * Method to check the length of arguments and error if the length is 0 or less then min.
     * @param args
     *      String array of the command line arguments
     */
    public static void checkCLArgCount(String [] args)
    {
        if (args.length == 0)
        {
            System.err.println("Missing command line arguments.");
            System.exit(1);
        }
        else if (args.length < 5)
        {
            System.err.println("Number of arguments is incorrect. Please check command line arguments.");
            System.exit(1);
        }
    }

    /**
     * Method to check the if the -print option was entered in the arguments
     * @param args
     *      String array of the command line arguments
     * @return 1/0
     *          1 = -PRINT is set properly.
     *          0 = -PRINT is not set.
     */
    public static int checkForPrintOption(String [] args)
    {
        for (String ap : args)
        {
            if (ap.toUpperCase().contains("-PRINT"))
            {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Method to get the search information.
     * @param args
     *      String array of the command line arguments
     * @return owner
     *          owner to search for.
     */
    public static List<String> getSearchInfo(String [] args, String owner) throws IOException
    {
        List<String> searchDetails = new ArrayList<>();

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equalsIgnoreCase("-SEARCH"))
            {
                searchDetails.add(owner); // owner
                checkDateFormat(args[i+2] + " " + args[i+3] + " " + args[i+4]);
                checkDateFormat(args[i+5] + " " + args[i+6] + " " + args[i+7]);
                searchDetails.add(args[i+2] + " " + args[i+3] + " " + args[i+4]);
                searchDetails.add(args[i+5] + " " + args[i+6] + " " + args[i+7]);
                return searchDetails;
            }
        }
        return null;
    }

    /**
     * Method to create an array list of the command line arguments
     * slicing off the options.
     * @param args
     *      String array of the command line arguments
     * @return slicedArgs
     *      Array list of just the arguments without the options.
     */
    public static List<String> argumentSlicer(String [] args)
    {
        String []  ValidArguments = new String [] {"-PRINT", "-HOST", "-PORT", "-SEARCH"};
        List<String> ValidArgList = new ArrayList<>(Arrays.asList(ValidArguments));
        List<String> slicedArgs = new ArrayList<>();
        int printop = 0;
        int textop = 0;
        int searchOp = 0;
        int increment = 0;
        int hostOp = 0;
        int portop = 0;

        for (String arg : args)
        {
            if (arg.toUpperCase().contains("-"))
            {
                if (!ValidArgList.contains(arg.toUpperCase()))
                {
                    System.err.println("Invalid Option: " + arg);
                    System.exit(1);
                }
            }
        }

        for (String arg : args)
        {
            if (arg.toUpperCase().contains("-PRINT"))
            {
                printop = 1;
            }
            else if (arg.toUpperCase().contains("-SEARCH"))
            {
                searchOp = 2;
            }
            else if (arg.toUpperCase().contains("-HOST"))
            {
                hostOp = 2;
            }
            else if (arg.toUpperCase().contains("-PORT"))
            {
                portop = 2;
            }
        }

        if ((hostOp == 1 && portop == 0) || (hostOp == 0 && portop == 1))
        {
            System.err.println("BOTH Host and Port must be provided.");
            System.exit(1);
        }

        increment = (printop + searchOp + hostOp + portop);

        slicedArgs.addAll(Arrays.asList(args).subList(increment, args.length));

//        if (slicedArgs.size() != 8)
//        {
//            System.err.println("Number of arguments is incorrect. Please check command line arguments.");
//            System.exit(1);
//        }
        return slicedArgs;
    }

    /**
     * Method to check the date format to make sure the date is valid.
     * @param date
     *      The date passed in from the commandline arguments.
     */
    public static void checkDateFormat(String date) throws IOException
    {
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        dFormat.setLenient(false);

        try
        {
            Date retDate = dFormat.parse(date);
        }
        catch (ParseException e)
        {

            System.err.println("Date format and/or Date is not valid: " + date + " --- Format Should be mm/dd/yyyy" +
                    "and date should be a real date.");
            System.exit(1);
        }

    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();

        System.exit(1);
    }
}