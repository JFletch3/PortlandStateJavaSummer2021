package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Text;

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
        else if (args.length < 6) //TODO Need to update this so it captures the correct amount of arguemtns.
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
     * @param PrintOp
     *      Print option variable returned from checkForPrintOption method.
     * @return newAppointmentBook
     *      Newly created appointment book.
     */
    public static AppointmentBook makeAppointmentBook(String [] args, int PrintOp)
    {
        //TODO  Need to check this for when there are more then one option entered
        AppointmentBook newAppointmentBook = new AppointmentBook();

        if (PrintOp == 0)
        {
            newAppointmentBook.setOwnerName(args[0]);
        }
        else
        {
            newAppointmentBook.setOwnerName(args[1]);
        }

        return newAppointmentBook;
    }


    /**
     * Method to check the if the -print option was entered in the arguments
     * @param args
     *      String array of the command line arguments
     * @param PrintOp
     *      Print option variable returned from checkForPrintOption method.
     * @return newAppointmentBook
     *      Newly created appointment.
     */
    public static Appointment makeAppointment(String [] args, int PrintOp)
    {
        //TODO  Need to check this for when there are more then one option entered
        Appointment newAppointment = new Appointment();

        if (PrintOp == 0)
        {
            checkDateFormat(args[2]);
            checkDateFormat(args[4]);

            newAppointment.setDescription(args[1]);
            newAppointment.setStartTime(args[2], args[3]);
            newAppointment.setEndTime(args[4], args[5]);
        }
        else
        {
            checkDateFormat(args[3]);
            checkDateFormat(args[5]);
            newAppointment.setDescription(args[2]);
            newAppointment.setStartTime(args[3], args[4]);
            newAppointment.setEndTime(args[5], args[6]);
        }

        return newAppointment;
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

        TextParser TextRead = new TextParser();
        TextDumper TextDump = new TextDumper();


        TextDump.setFileDir("C:\\Users\\Joe\\Desktop\\CS410\\PortlandStateJavaSummer2021\\apptbook\\AppointmentBook.txt");  //Later to be changed to the file dir from command line.

        //File does not exist - Create new file, write new appointment to file.
        //File exists - Take in new appoointment and write it out to the file

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
        checkCLArgCount(args);
        //-----------------------------------------------------------

        //-----------------------------------------------------------
        //TODO this needs to only run if the otption -textFile is set.
        // If -textFile is set, the next variable will be the directory.
        // setFileDir needs to be the file directory.
        // This will read in the appointment book
        try
        {
            TextRead.setFileDir("C:\\Users\\Joe\\Desktop\\CS410\\PortlandStateJavaSummer2021\\apptbook\\AppointmentBook.txt"); //Later to be changed to the file dir from command line.
            newAppointmentBook = TextRead.parse();
        }
        catch (ParserException e){}
        //-----------------------------------------------------------


        //--
        checkCLArgCount(args);
        printOption = checkForPrintOption(args);
        newAppointmentBook = makeAppointmentBook(args, printOption);
        appointment = makeAppointment(args, printOption);

        newAppointmentBook.addAppointment(appointment);
        appts = newAppointmentBook.getAppointments();

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

        System.exit(1);
    }

}
