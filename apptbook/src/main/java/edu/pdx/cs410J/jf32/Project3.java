package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.ParserException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The main class for the CS410J appointment book Project 3
 */
public class Project3
{
    /**
     * Readme function to print a simple readme about the program
     * to the standard out.
     */
    public static void readME()
    {
        System.out.println("\n\nProject 3 - CS410 - Joe Fletcher\n" +
                "This is an Appointment book program which creates an Appointment book and an Appointment based on arguments.\n" +
                "The program allows you to submit a file to create an appointment.\n" +
                "Command line arguments:\n" +
                "\tOwner, Appointment Description (wrapped in quotes), Start date, Start time, End date, End time.\n" +
                "\tEX: Joe \"First appointment\" 06/01/2021 1:00 PM 06/01/2021 2:00 PM\n" +
                "NOTE: AM / PM ARE required for this program.\n" +
                "Along with the command line arguments you may enter 4 optional arguments at the front of the command line\n" +
                "\t-README  -PRINT -textFile file -pretty file\n " +
                "\t-README\t\tPrints out this statement.\n" +
                "\t-PRINT\t\tWill print out the details of your appointment book and appointment.\n" +
                "\t-textFile\t\tIndicates that there is a file name being passed in with the 'file' argument after the option\n" +
                "\t-pretty\t\tIndicates that there is a file name being passed in with the 'file' argument after the option\n" +
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
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

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
     * Method to compare the two dates passed in to determine they are int he correct order.
     * @param d1
     *      String for the start date
     * @param d2
     *      String for the end date
     */
    public static void CompareDates(String d1, String d2)
    {
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        dFormat.setLenient(false);

        Date date1 = null;
        Date date2 = null;

        try
        {
            date1 = dFormat.parse(d1);
            date2 = dFormat.parse(d2);
        }
        catch (ParseException e)
        {
            System.err.println("Date format and/or Date is not valid format Should be mm/dd/yyyy " +
                    "and date should be a real date.");
            System.exit(1);
        }

        if (date1.after(date2))
        {
            System.err.println("Date sequence is out of order. Please evaluate dates.");
            System.exit(1);
        }

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

    /**
     * Method to check the if the -pretty option was entered in the arguments
     * @param args
     *      String array of the command line arguments
     * @return 1/0
     *          1 = -PRETTY is set properly.
     *          0 = -PRETTY is not set.
     */
    public static int checkforPrettyOption(String [] args)
    {

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].toUpperCase().contains("-PRETTY"))
            {
                if (args[i+1].contains("-"))
                {
                    return 2;
                }

                return 1;
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
    public static String getFileName(String[] args, String key)
    {
        String fileName = "";

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].toUpperCase().contains(key))
            {
                fileName = args[i+1];
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
        String []  ValidArguments = new String [] {"-PRINT", "-TEXTFILE", "-PRETTY", "-"};
        List<String> slicedArgs = new ArrayList<>();
        int printop = 0;
        int textop = 0;
        int prettyOp = 0;
        int increment = 0;

        for (String arg : args)
        {
            if (arg.toUpperCase().contains("-"))
            {
                if (!arg.toUpperCase().equals(ValidArguments[0]) && !arg.toUpperCase().equals(ValidArguments[1])
                        && !arg.toUpperCase().equals(ValidArguments[2])
                        && !arg.toUpperCase().equals(ValidArguments[3]))
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
            else if (arg.toUpperCase().contains("-TEXTFILE"))
            {
                textop = 1;
            }
            else if (arg.toUpperCase().contains("-PRETTY"))
            {
                prettyOp = 1;
            }
        }

        if (printop == 1 && textop == 1 && prettyOp == 1)
        {
            increment = 5;
        }
        else if (textop == 1 && prettyOp == 1)
        {
            increment = 4;
        }
        else if ((printop == 1 && textop == 1) || (printop == 1 && prettyOp == 1))
        {
            increment = 3;
        }
        else if (textop == 1 || prettyOp == 1)
        {
            increment = 2;
        }
        else if (printop == 1)
        {
            increment = 1;
        }


        slicedArgs.addAll(Arrays.asList(args).subList(increment, args.length));

        if (slicedArgs.size() != 8)
        {
            System.err.println("Number of arguments is incorrect. Please check command line arguments.");
            System.exit(1);
        }
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
    public static void main(String[] args)
    {

        ArrayList<Appointment> appts;
        List<String> CLArguments;
        AppointmentBook newAppointmentBook = new AppointmentBook();
        Appointment appointment;
        int printOption;
        int prettyOption;
        String fileName;
        String prettyFile;
        boolean CorrectFileOwner;
        TextParser TextRead = new TextParser();
        TextDumper TextDump = new TextDumper();

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
                readME();
                System.exit(1);
            }
        }
        //-----------------------------------------------------------

        //-----------------------------------------------------------
        checkCLArgCount(args);
        printOption = checkForPrintOption(args);    //Get Print option - 1 print 0 no print
        prettyOption = checkforPrettyOption(args);  //Get pretty option - 1 print 0 no print
        fileName = getFileName(args, "-TEXTFILE"); //Get File name
        prettyFile = getFileName(args, "-PRETTY"); //Get pretty file name
        CLArguments = argumentSlicer(args);         //Sliced arguments without the options
        checkDateFormat(CLArguments.get(2) + " " + CLArguments.get(3) + " " + CLArguments.get(4)); //check Date format
        checkDateFormat(CLArguments.get(5) + " " + CLArguments.get(6) + " " + CLArguments.get(7)); //Check date format
        CompareDates(CLArguments.get(2) + " " + CLArguments.get(3) + " " + CLArguments.get(4),
                     CLArguments.get(5) + " " + CLArguments.get(6) + " " + CLArguments.get(7));  //Compare dates
        if (prettyOption == 1)
        {
            if (prettyFile.equals(fileName))
            {
                System.err.println("Pretty File and Text File can not be the same.");
                System.exit(1);
            }
        }
        //-----------------------------------------------------------

        //-----------------------------------------------------------
        //If file name IS NOT entered - Proceed like normal - No text file.
        if (fileName.equals(""))
        {
            newAppointmentBook = makeAppointmentBook(CLArguments);
            appointment = makeAppointment(CLArguments);

            newAppointmentBook.addAppointment(appointment);
        }
        else
        {
            File appBookFile = new File(fileName);
            TextRead.setFileName(fileName);

            if(!appBookFile.exists())
            {
                newAppointmentBook = TextRead.CreateEmptyBook();
                newAppointmentBook.setOwnerName(CLArguments.get(0));
            }
            else
            {
                TextRead.setFileOwner(CLArguments.get(0));
                CorrectFileOwner = TextRead.FileIsRightOwner(fileName);
                if (CorrectFileOwner)
                {
                    try
                    {
                        newAppointmentBook = TextRead.parse();
                    }
                    catch (ParserException ignored){}
                }
                else
                {
                    System.out.println("The File owner and Owner passed in from the command line do not match.");
                    System.exit(1);
                }
            }
            appointment = makeAppointment(CLArguments);
            newAppointmentBook.addAppointment(appointment);
        }

        //-----------------------------------------------------------
        //Check if the fileName is null.
        // If it is not null - Dump out the info.
        // If it is null do nothing as they didnt pass in the -textFile option
        if (!fileName.equals(""))
        {
            try
            {
                TextDump.setFileDir(fileName);
                //   TextDump.setWriter(new StringWriter());
                TextDump.dump(newAppointmentBook);
            }
            catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
        //-----------------------------------------------------------

        //-----------------------------------------------------------
        //check for the print option. if set to 1, print out the information.
        if (printOption == 1)
        {
            appts = newAppointmentBook.getAppointments();
            System.out.println(newAppointmentBook);
            for(Appointment ap : appts)
            {
                System.out.println(ap.toString());
            }
        }
        //-----------------------------------------------------------

        //-----------------------------------------------------------
        if (prettyOption != 0)
        {
            appts = newAppointmentBook.getAppointments();
            Collections.sort(appts);

//            FileWriter sw = null;
//            try
//            {
//                sw = new FileWriter(prettyFile);
//            } catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//            PrettyPrint prettyprint = new PrettyPrint(prettyFile, sw);
            try
            {
                if (prettyOption == 1)
                {
                    FileWriter sw = null;
                    try
                    {
                        sw = new FileWriter(prettyFile);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    PrettyPrint prettyprint = new PrettyPrint(prettyFile, sw);
                    try
                    {
                        sw = new FileWriter(prettyFile);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    prettyprint.dump(newAppointmentBook);
                }
                else if (prettyOption == 2)
                {
                    PrettyPrint prettyprint = new PrettyPrint(prettyFile, null);
                    prettyprint.stdDump(newAppointmentBook);
                }

            }
            catch(IOException ignore)
            {}
        }
        //-----------------------------------------------------------

        System.exit(1);
    }
}
