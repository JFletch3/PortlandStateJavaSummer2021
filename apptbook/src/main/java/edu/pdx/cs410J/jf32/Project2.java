package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

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
    public static void checkCLArgCount(String[] args)
    {
        if (args.length == 0)
        {
            System.err.println("Missing command line arguments.");
            System.exit(1);
        }
        else if (args.length < 6)
        {
            System.err.println("Insufficient number of arguments.");
            System.exit(1);
        }
    }

    /**
     * Method to check the if the -print option was entered in the arguments
     * @param args
     *      String array of the command line arguments
     */
    public static int checkForPrintOption(String [] args)
    {
        if(!args[0].toUpperCase().contains("-PRINT"))
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
        else
        {
            return 1;
        }
        return 0;
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
        AppointmentBook newAppointmentBook = new AppointmentBook();
        Appointment appointment = new Appointment();
        int printOption = 0;

        TextParser Text = new TextParser();
        TextDumper TextDump = new TextDumper();
        Text.setFileDir("C:\\Users\\Joe\\Desktop\\CS410\\PortlandStateJavaSummer2021\\apptbook\\AppointmentBook.txt"); //Later to be changed to the file dir from command line.
        TextDump.setFileDir("C:\\Users\\Joe\\Desktop\\CS410\\PortlandStateJavaSummer2021\\apptbook\\AppointmentBook.txt");  //Later to be changed to the file dir from command line.

        try
        {
            newAppointmentBook = Text.parse();
        }
        catch (ParserException e)
        {
            //System.out.println("Parser Error");
        }


        for(String ap : args)
        {
            if (ap.toUpperCase().contains("-README"))
            {
                readME();
                System.exit(1);
            }
        }

        checkCLArgCount(args);
        printOption = checkForPrintOption(args);

        if(!args[0].toUpperCase().contains("-PRINT"))
        {
            for (String ap : args)
            {
                if (ap.toUpperCase().contains("-PRINT"))
                {
                    System.err.println("Option -PRINT is in the wrong location in the argument list.");
                    System.exit(1);
                }
            }

            checkDateFormat(args[2]);
            checkDateFormat(args[4]);

//            newAppointmentBook.setOwnerName(args[0]);
//            appointment.setDescription(args[1]);
//            appointment.setStartTime(args[2], args[3]);
//            appointment.setEndTime(args[4], args[5]);
        }
        else if (args[0].toUpperCase().contains("-PRINT"))
        {
            printOption = 1;
//            checkDateFormat(args[3]);
//            checkDateFormat(args[5]);
//            newAppointmentBook.setOwnerName(args[1]);
//            appointment.setDescription(args[2]);
//            appointment.setStartTime(args[3], args[4]);
//            appointment.setEndTime(args[5], args[6]);
        }

//        newAppointmentBook.addAppointment(appointment);
        appts = newAppointmentBook.getAppointments();


        if (printOption == 1)
        {
            System.out.println(newAppointmentBook);
            for(Appointment ap : appts)
            {
                System.out.println(ap.toString());
            }
        }

        System.exit(1);
    }

}
