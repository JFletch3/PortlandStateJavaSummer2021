package edu.pdx.cs410J.jf32;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MessagesTest {

  @Test
  void malformedWordAndDefinitionReturnsNull() {
    assertThat(Messages.parseDictionaryEntry("blah"), nullValue());
  }

  @Test
  void canParseFormattedDictionaryEntryPair() {
    String word = "testWord";
    String definition = "testDefinition";
    String formatted = Messages.formatDictionaryEntry(word, definition);
    Map.Entry<String, String> parsed = Messages.parseDictionaryEntry(formatted);
    assertThat(parsed, notNullValue());
    assertThat(parsed.getKey(), equalTo(word));
    assertThat(parsed.getValue(), equalTo(definition));
  }

  @Test
  void canParseFormattedDictionaryEntryWithoutLeadingSpaces() {
    String word = "testWord";
    String definition = "testDefinition";
    String formatted = Messages.formatDictionaryEntry(word, definition);
    String trimmed = formatted.trim();
    Map.Entry<String, String> parsed = Messages.parseDictionaryEntry(trimmed);
    assertThat(parsed, notNullValue());
    assertThat(parsed.getKey(), equalTo(word));
    assertThat(parsed.getValue(), equalTo(definition));

  }

  @Test
  void nullDefinitionIsParsedAsNull() {
    String word = "testWord";
    String formatted = Messages.formatDictionaryEntry(word, null);
    Map.Entry<String, String> parsed = Messages.parseDictionaryEntry(formatted);
    assertThat(parsed, notNullValue());
    assertThat(parsed.getKey(), equalTo(word));
    assertThat(parsed.getValue(), equalTo(null));
  }

  @Test
  void canParseAppointmentBook() {
    String appointment = "app_book_owner=joe\n\n--NEW APPOINTMENT--\n\n" +
                        "app_desc=test\n\napp_start_date=7/1/2021\n\napp_start_time=1:00 PM\n\n" +
                        "app_end_date=7/1/2021\n\napp_end_time=2:00 PM";
   String owner = "joe";

    AppointmentBook parsed = Messages.parseAppointmentBook(appointment);
    assertThat(parsed, notNullValue());
    assertThat(parsed.getOwnerName(), equalTo(owner));

  }

  @Test
  void canParseAllBooks() {
    String appointment = "app_book_owner=joe\n\n--NEW APPOINTMENT--\n\n" +
            "app_desc=test\n\napp_start_date=7/1/2021\n\napp_start_time=1:00 PM\n\n" +
            "app_end_date=7/1/2021\n\napp_end_time=2:00 PM";
    String owner = "joe";

    List<AppointmentBook> parsed = Messages.parseALLappointmentBooks(appointment);
    assertThat(parsed, notNullValue());
    assertThat(parsed.size(), equalTo(1));
    assertThat(parsed.get(0).getOwnerName(), equalTo(owner));

  }
}
