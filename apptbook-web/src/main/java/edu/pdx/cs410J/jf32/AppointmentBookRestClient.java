package edu.pdx.cs410J.jf32;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AppointmentBookRestClient extends HttpRequestHelper {
  private static final String WEB_APP = "apptbook";
  private static final String SERVLET = "appointments";

  private final String url;


  /**
   * Creates a client to the appointment book REST service running on the given host and port
   *
   * @param hostName The name of the host
   * @param port     The port
   */
  public AppointmentBookRestClient(String hostName, int port) {
    this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
  }

  /**
   * Returns all dictionary entries from the server
   */
  public Map<String, String> getAllDictionaryEntries() throws IOException {
    Response response = get(this.url, Map.of());
    return Messages.parseDictionary(response.getContent());
  }

  public AppointmentBook getSearchedAppointmentBook(List<String> SearchCriteria) throws IOException
  {
    Response response = get(this.url, Map.of("owner", SearchCriteria.get(0),
                                              "start", SearchCriteria.get(1),
                                              "end", SearchCriteria.get(2)));
    return Messages.parseAppointmentBook(response.getContent());
  }

  public AppointmentBook getSearchedAppointmentBookOwnerOnly(String Owner) throws IOException
  {
    Response response = get(this.url, Map.of("owner", Owner));
    return Messages.parseAppointmentBook(response.getContent());
  }

  /**
   * Returns the definition for the given word
   */
  public String getDefinition(String word) throws IOException {
    Response response = get(this.url, Map.of("word", word));
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();
    return Messages.parseDictionaryEntry(content).getValue();
  }

  public void addDictionaryEntry(String word, String definition) throws IOException {
    Response response = postToMyURL(Map.of("word", word, "definition", definition));
    throwExceptionIfNotOkayHttpStatus(response);
  }

  public void addAppointmentEntry(Appointment appointment, String owner) {
    String desctiption = appointment.getDescription();
    String start = appointment.getThisStartDate() + " " + appointment.getThisStartTime();
    String end = appointment.getThisEndDate() + " " + appointment.getThisEndTime();
    try
    {
      Response response = postToMyURL(Map.of("owner", owner,
                                             "description", desctiption,
                                             "start", start,
                                             "end", end));
      throwExceptionIfNotOkayHttpStatus(response);
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @VisibleForTesting
  Response postToMyURL(Map<String, String> appointmentEntries) throws IOException {
    return post(this.url, appointmentEntries);
  }

  public void removeAllDictionaryEntries() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
    return response;
  }




}
