package edu.pdx.cs410J.jf32;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        //------------------------------------------------------------
        //-README check - leave this here, its just a print and exit
        // This needs to be the first thing checked.
        if (args.length == 0)
        {
            System.err.println("Missing command line arguments.");
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
        checkCLArgCount(args);
        hostName        = getArgumentValue(args, "-HOST");
        portString      = getArgumentValue(args, "-PORT");
        searchString    = getArgumentValue(args, "-SEARCH");
        printOption     = checkForPrintOption(args); //TODO write code to do something with this
        CLArguments     = argumentSlicer(args);

        if (!searchString.equals(""))
        {
            searchDetails = getSearchInfo(args, searchString)
        }
        
        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);

        String message;
        try {
            if (word == null) {
                // Print all word/definition pairs
                Map<String, String> dictionary = client.getAllDictionaryEntries();
                StringWriter sw = new StringWriter();
                Messages.formatDictionaryEntries(new PrintWriter(sw, true), dictionary);
                message = sw.toString();

            } else if (definition == null) {
                // Print all dictionary entries
                message = Messages.formatDictionaryEntry(word, client.getDefinition(word));

            } else {
                // Post the word/definition pair
                client.addDictionaryEntry(word, definition);
                message = Messages.definedWordAs(word, definition);
            }

        } catch ( IOException ex ) {
            error("While contacting server: " + ex);
            return;
        }

        System.out.println(message);

        System.exit(0);
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
        else if (args.length < 8)
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

    public static List<String> getSearchInfo(String [] args, String owner)
    {
        List<String> searchDetails = new ArrayList<>();

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].toUpperCase().contains("-SEARCH"))
            {
                searchDetails.add(owner) // owner
                searchDetails.add(args[i+2]);
                searchDetails.add(args[i+3]);
                searchDetails.add(args[i+4]);
                searchDetails.add(args[i+5]);
                searchDetails.add(args[i+6]);
                searchDetails.add(args[i+7]);
                return searchDetails;
            }
        }


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

//        if (printop == 1 && searchOp == 1 && hostOp == 1 && portop == 1)
//        {
//            increment = 7;
//        }
//        else if (hostOp == 1 && portop == 1 && searchOp == 1)
//        {
//            increment = 6;
//        }
//        else if (hostOp == 1 && portop == 1 && printop == 1)
//        {
//            increment = 5;
//        }
//        else if (hostOp == 1 && portop == 1)
//        {
//            increment = 4;
//        }
//        else if (printop == 1)
//        {
//            increment = 1;
//        }


        slicedArgs.addAll(Arrays.asList(args).subList(increment, args.length));

        if (slicedArgs.size() != 8)
        {
            System.err.println("Number of arguments is incorrect. Please check command line arguments.");
            System.exit(1);
        }
        return slicedArgs;
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
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