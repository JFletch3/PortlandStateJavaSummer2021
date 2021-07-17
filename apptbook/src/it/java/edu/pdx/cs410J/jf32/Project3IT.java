package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the {@link Project3} main class.
 */
public class Project3IT extends InvokeMainTestCase
{
    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private InvokeMainTestCase.MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

    @Test
    void testNoCommandLineArguments() {
        InvokeMainTestCase.MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    void testDateFormatReturn() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("Joe", "\"test\"", "fakeDate", "1:00","am", "6/2/2021", "2:00","am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Date format and/or Date is not valid:"));
    }

    @Test
    void testNumberOfArgumentsLessThenMin() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("Joe");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Number of arguments is incorrect. Please check command line arguments."));
    }

    @Test
    void testNumberOfArgumentsMoreThenMAx() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "-textfile","file","-pretty","prettyFile1","Joe", "\"test\"", "6/2/2021", "1:00","AM", "6/2/2021", "2:00","AM", "additional argument");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Number of arguments is incorrect. Please check command line arguments."));
    }

    @Test
    void DoesNotThrowWithInput (){
        assertDoesNotThrow(() -> {
            String [] args = {"-textfile", "test.txt", "Owner",
                    "Description", "1/1/2021", "1:00", "am",
                    "1/1/2021", "1:05", "am"};

            MainMethodResult result = invokeMain(args);
        });
    }

    @Test
    void CompareDatesTest()
    {
        InvokeMainTestCase.MainMethodResult result = invokeMain("Joe", "\"test\"", "6/7/2021", "1:00","AM", "6/2/2021", "2:00","AM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Date sequence is out of order. Please evaluate dates."));
    }

}
