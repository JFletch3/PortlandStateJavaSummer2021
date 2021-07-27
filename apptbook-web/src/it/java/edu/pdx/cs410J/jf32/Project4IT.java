package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * An integration test for {@link Project4} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

//    @Test
//    void test0RemoveAllMappings() throws IOException {
//      AppointmentBookRestClient client = new AppointmentBookRestClient(HOSTNAME, Integer.parseInt(PORT));
//      client.removeAllDictionaryEntries();
//    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
//        String out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(Messages.formatBOOKSCount(0)));
    }

//    @Test
//    void test3NoDefinitionsThrowsAppointmentBookRestException() {
//        String owner = "WORD";
//        try {
//            invokeMain(Project4.class, HOSTNAME, PORT, owner);
//            fail("Expected a RestException to be thrown");
//
//        } catch (UncaughtExceptionInMain ex) {
//            RestException cause = (RestException) ex.getCause();
//            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
//        }
//    }

//    @Test
//    void test4AddDefinition() {
//        String owner = "owner";
//        String desc = "descrption";
//        String start = "1/1/2021 1:00 PM";
//        String end = "1/1/2021 2:00 PM";
//
//
//        AppointmentBook newbook = new AppointmentBook();
//        Appointment newapp = new Appointment();
//        newbook.setOwnerName(owner);
//        newapp.setDescription(desc);
//        newapp.setStartDate("1/1/2021");
//        newapp.setStartTime("1:00 PM");
//        newapp.setEndDate("1/1/2021");
//        newapp.setEndTime("2:00 PM");
//        newbook.addAppointment(newapp);
//
//
//        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, owner, desc, start, end );
//        String out = result.getTextWrittenToStandardOut();
//       assertThat(out, out, containsString(Messages.definedAppointment(owner, desc, start,end)));
//
//        result = invokeMain( Project4.class, HOSTNAME, PORT, owner );
//        out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(Messages.formatDictionaryEntry(owner, desc)));
//
//        result = invokeMain( Project4.class, HOSTNAME, PORT );
//        out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(Messages.formatDictionaryEntry(owner, desc)));
//    }
}