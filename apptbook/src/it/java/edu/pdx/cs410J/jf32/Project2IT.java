package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the {@link Project1} main class.
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

    @Test
    void testDateFormatReturn() {
        MainMethodResult result = invokeMain("Joe", "\"test\"", "fakeDate", "1:00", "6/2/2021", "2:00");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Date format and/or Date is not valid:"));
    }

    @Test
    void testNumberOfArgumentsLessThenMin() {
        MainMethodResult result = invokeMain("Joe");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Number of arguments is incorrect. Please check command line arguments."));
    }

    @Test
    void testNumberOfArgumentsMoreThenMAx() {
        MainMethodResult result = invokeMain("-print", "-textfile","file","Joe", "\"test\"", "6/2/2021", "1:00", "6/2/2021", "2:00", "additional argument");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Number of arguments is incorrect. Please check command line arguments."));
    }

}