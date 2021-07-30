package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link AppointmentBookRestClient}
 */
@TestMethodOrder(MethodName.class)
class AppointmentBookRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AppointmentBookRestClient newAppointmentBookRestClient() {
    int port = Integer.parseInt(PORT);
    return new AppointmentBookRestClient(HOSTNAME, port);
  }

//  @Test
//  void test1EmptyServerContainsNoDictionaryEntries() throws IOException {
//    AppointmentBookRestClient client = newAppointmentBookRestClient();
//    List<AppointmentBook> books = client.getAllDictionaryEntries();
//    assertThat(books.size(), equalTo(0));
//  }

  @Test
  void testSearchForBookByOwnerOnly() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    String owner = "owner";
    String desc = "descrption";
    String start = "1/1/2021 1:00 PM";
    String end = "1/1/2021 2:00 PM";


    AppointmentBook newbook = new AppointmentBook();
    Appointment newapp = new Appointment();
    newbook.setOwnerName(owner);
    newapp.setDescription(desc);
    newapp.setStartDate("1/1/2021");
    newapp.setStartTime("1:00 PM");
    newapp.setEndDate("1/1/2021");
    newapp.setEndTime("2:00 PM");
    newbook.addAppointment(newapp);
    client.addAppointmentEntry(newapp, owner);

    AppointmentBook book = client.getSearchedAppointmentBookOwnerOnly(owner);
    assertThat(book.getOwnerName(), equalTo(owner));
  }

  @Test
  void testSearchingForBook() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    String owner = "owner";
    String desc = "descrption";
    String start = "1/1/2021";
    String startTime = "1:00";
    String startampm = "PM";
    String end = "1/1/2021";
    String endTime = "2:00";
    String endampm = "PM";
    List<String> args = new ArrayList<>();

    AppointmentBook newbook = new AppointmentBook();
    Appointment newapp = new Appointment();
    newbook.setOwnerName(owner);
    newapp.setDescription(desc);
    newapp.setStartDate("1/1/2021");
    newapp.setStartTime("1:00 PM");
    newapp.setEndDate("1/1/2021");
    newapp.setEndTime("2:00 PM");
    newbook.addAppointment(newapp);
    client.addAppointmentEntry(newapp, owner);

    args.add(owner);
    args.add(start);
    args.add(startTime);
    args.add(startampm);
    args.add(end);
    args.add(endTime);
    args.add(endampm);

    AppointmentBook book = client.getSearchedAppointmentBook(args);
    assertThat(book.getOwnerName(), equalTo(owner));
  }

}
