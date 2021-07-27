package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * An integration test for {@link Project4} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

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

    @Test
    void DoesNotThrowWithInput (){
        assertDoesNotThrow(() -> {
            String [] args = {"-host", "localhost","-port", "8080", "Owner",
                    "Description", "1/1/2021", "1:00", "am",
                    "1/1/2021", "1:05", "am"};

            MainMethodResult result = invokeMain(Project4.class, args);
        });
    }

    @Test
    void testHelpMesage() {
        MainMethodResult result = invokeMain( Project4.class, "-README" );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("usage: java Project4 host port"));
    }

//    @Test
//    void testSearchAndPrettyPrint() throws IOException
//    {
//        MainMethodResult result = invokeMain( Project4.class, "-HOST", "localhost",
//                                                                    "-PORT", "8080",
//                                                                    "-search", "Joe",
//                                                                    "1/1/2021", "1:00", "am",
//                                                                    "1/1/2021", "1:05", "am");
//        assertThat(result.getTextWrittenToStandardError(), containsString("usage: java Project4 host port"));
//    }

}