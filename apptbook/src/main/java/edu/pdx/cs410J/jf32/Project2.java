package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Project2 {

    /**
     * Readme function to print a simple readme about the program
     * to the standard out.
     */
    public static void readME()
    {
        System.out.println("\n\nProject 2 - CS410 - Joe Fletcher\n" +
                "This is an Appointment book program which creates an Appointment book and an Appointment based on arguments.\n" +
                "Command line arguments:\n" +
                "\tOwner, Appointment Description (wrapped in quotes), Start date, Start time, End date, End time.\n" +
                "\tEX: Joe \"First appointment\" 06/01/2021 1:00 06/01/2021 2:00\n" +
                "NOTE: AM / PM are not needed for this program.\n" +
                "Along with the command line arguments you may enter two optional arguments at the front of the command line\n" +
                "\t-README  -PRINT\n" +
                "\t-README\t\tPrints out this statement.\n" +
                "\t-PRINT\t\tWill print out the details of your appointment book and appointment.\n" +
                "Its worth noting that if you have -README as an option, the program will ONLY print this out.\n" +
                "Leave -README off the argument list if you would like the program to run fully.\n\n");
    }

    /**
     * Method to check the date format to make sure the date is valid.
     * @param date
     *      The date passed in from the commandline arguments.
     */
    public static void checkDateFormat(String date)
    {
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy");
        dFormat.setLenient(false);

        try
        {
            dFormat.parse(date);
        }
        catch (ParseException e)
        {
            System.err.println("Date format and/or Date is not valid: " + date + " --- Format Should be mm/dd/yyyy" +
                    "and date should be a real date.");
            System.exit(1);
        }
    }

    /**
     * Method to check the length of arguments and error if the length is 0 or less then min.
     * @param args
     *      String array of the command line arguments
     */
    public static void checkCLArgCount(List<String> args)
    {
        if (args.size() == 0)
        {
            System.err.println("Missing command line arguments.");
            System.exit(1);
        }
        else if (args.size() < 6) //TODO Need to update this so it captures the correct amount of argumtns.
        {
            System.err.println("Insufficient number of arguments.");
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
        if(args[0].toUpperCase().contains("-PRINT"))
        {
            return 1;
        }
        else
        {

            for (String ap : args)
            {
                if (ap.toUpperCase().contains("-PRINT"))
                {
                    System.err.println("Option -PRINT is in the wrong location in the argument list.");
                    System.exit(1);
                }
            }
        }

        return 0;
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

        checkDateFormat(args.get(2));
        checkDateFormat(args.get(4));

        newAppointment.setDescription(args.get(1));
        newAppointment.setStartTime(args.get(2), args.get(3));
        newAppointment.setEndTime(args.get(4), args.get(5));

        return newAppointment;
    }

    /**
     * Method to get the file name if -testFile is used as an option
     * @param args
     *      String array of the command line arguments
     * @return fileName
     *      name of the file passed in on the command line arguments
     */
    public static String getFileName(String[] args)
    {
        String fileName = "";

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].toUpperCase().contains("-TEXTFILE"))
            {

                fileName = args[i+1];
                System.out.println("text file name: " + args[i+1]);
            }
        }

        return fileName;
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

        List<String> slicedArgs = new ArrayList<String>();
        int printop = 0;
        int textop = 0;
        int increment = 0;

        for (String arg : args)
        {
            if (arg.toUpperCase().contains("-PRINT"))
            {
                printop = 1;
            } else if (arg.toUpperCase().contains("-TEXTFILE"))
            {
                textop = 1;;
            }
        }

        if (printop == 1 && textop == 1)
        {
            increment = 3;
        }
        else if (printop == 1)
        {
            increment = 1;
        }
        else if (textop == 1)
        {
            increment = 2;
        }

        slicedArgs.addAll(Arrays.asList(args).subList(increment, args.length));

        return slicedArgs;
    }

    /**
     * Main method that parses Command line arguments and
     * creates an <code>AppointmentBook</code> based on the Owner argument
     * Followed by creating an Appointment and loading the Appointment
     * into the newly created AppointmentBook.
     * @param args
     *        Command line arguments.
     */
    public static void main(String[] args){

        Collection<Appointment> appts;
        AppointmentBook newAppointmentBook;
        Appointment appointment ;
        List<String> CLArguments;
        int printOption = 0;
        int textOption = 0;
        String fileName = "";
        TextParser TextRead = new TextParser();
        TextDumper TextDump = new TextDumper();

        //------------------------------------------------------------
        //-README check - leave this here, its just a print and exit
        // This needs to be the first thing checked.
        for(String ap : args)
        {
            if (ap.toUpperCase().contains("-README"))
            {
                readME();
                System.exit(1);
            }
        }
        //-----------------------------------------------------------


        //-----------------------------------------------------------
        printOption = checkForPrintOption(args);    //Get Print option - 1 print 0 no print
        fileName = getFileName(args);               //Get File name
        CLArguments =  argumentSlicer(args);        //Sliced arguments without the options
        checkCLArgCount(CLArguments);

        //If file name IS NOT entered - Proceed like normal.
        if (fileName.equals(""))
        {
            newAppointmentBook = makeAppointmentBook(CLArguments);
            appointment = makeAppointment(CLArguments);

            newAppointmentBook.addAppointment(appointment);
            appts = newAppointmentBook.getAppointments();

            //Create new file and write to it
        }
        //If file name IS entered -- Parse or create new file
        else
        {
            //Try to get the appointment book from the passed in fileName.
            try
            {
                TextRead.setFileDir(fileName); //Later to be changed to the file dir from command line.
                newAppointmentBook = TextRead.parse();
            }
            catch (ParserException e){}
        }

        newAppointmentBook = makeAppointmentBook(CLArguments);
        appointment = makeAppointment(CLArguments);

        newAppointmentBook.addAppointment(appointment);
        appts = newAppointmentBook.getAppointments();





        //-----------------------------------------------------------

        //Write to file / printout based on options.

        try
        {
            TextDump.dump(newAppointmentBook);
        }
        catch(IOException e)
        {

        }

        if (printOption == 1)
        {
            System.out.println(newAppointmentBook);
            for(Appointment ap : appts)
            {
                System.out.println(ap.toString());
            }
        }
        //-----------------------------------------------------------

        System.exit(1);
    }

}
