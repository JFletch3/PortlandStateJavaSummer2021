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
class Project1IT extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project1} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args );
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  @Test
  void testNotEnoughArguements() {
    MainMethodResult result = invokeMain("joe");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Insufficient number of arguments."));
  }

  @Test
  void testDateFormat() {
    MainMethodResult result = invokeMain("joe", "test app", "20-1-2021", "1:00", "6-2-2021", "2:00");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Date format and/or Date is not valid"));
  }

  @Test
  void testREADME() {
    MainMethodResult result = invokeMain("-readme","joe", "test app", "06-1-2021", "1:00", "6-2-2021", "2:00");
    assertThat(result.getTextWrittenToStandardOut(), containsString("Project 1 - CS410 - Joe Fletcher"));
  }

  @Test
  void testPrintInWrongLocation() {
    MainMethodResult result = invokeMain("joe","-print", "test app", "06-1-2021", "1:00", "6-2-2021", "2:00");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Option -PRINT is in the wrong location in the argument list."));
  }

}