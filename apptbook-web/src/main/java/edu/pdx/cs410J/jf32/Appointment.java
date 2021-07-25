package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class is represents a <code>Appointment</code>.
 */
public class Appointment extends AbstractAppointment implements Comparable<Appointment>
{

  /**
   * Appointment description
   */
  private String description;
  /**
   * Appointment Start Time
   */
  private String startTime;
  /**
   * Appointment Start date
   */
  private String startDate;
  /**
   * Appointment End Time.
   */
  private String endTime;
  /**
   * Appointment Start date
   */
  private String endDate;

  /**
   * Sets the <code>Appointment</code> Description
   *
   * @param description
   *        Appointment Description
   */
  public void setDescription(String description)
  {
    this.description = description;
  }

  /**
   * Sets the <code>Appointment</code> Start Date.
   * Concatenates Start Date and Start Time to make one String.
   * @param startDate
   *        Appointment Start Date from Command line argument.
   */
  public void setStartDate(String startDate)
  {
    this.startDate = startDate;
  }

  /**
   * Sets the <code>Appointment</code> Start time.
   * Concatenates Start Date and Start Time to make one String.
   * @param startTime
   *        Appointment Start Time from Command line argument.
   */
  public void setStartTime(String startTime)
  {
      this.startTime = startTime;
  }

  /**
   * Sets the <code>Appointment</code> End Date
   *  Concatenates End Date and End Time to make one String.
   * @param endDate
   *        Appointment End Date from Command line argument.
   */
  public void setEndDate(String endDate)
  {
    this.endDate = endDate;
  }

  /**
   * Sets the <code>Appointment</code> End Time
   *  Concatenates End Date and End Time to make one String.
   * @param endTime
   *        Appointment End Time from Command line argument.
   */
  public void setEndTime(String endTime)
  {
      this.endTime = endTime;
  }

  /**
   * Getter for start Date
   * @return startDate
   *        Returns start Date
   */
  public String getThisStartDate()
  {
    return this.startDate;
  }

  /**
   * Getter for start time
   * @return startTime
   *        Returns start Time
   */
  public String getThisStartTime()
  {
    return this.startTime;
  }

  /**
   * Getter for end Date
   * @return endDate
   *        Returns end Date
   */
  public String getThisEndDate()
  {
    return this.endDate;
  }

  /**
   * Getter for end Time
   * @return endTime
   *        Returns end Time
   */
  public String getThisEndTime()
  {
    return this.endTime;
  }

  /**
   * Get the <code>Appointment</code> Begin Time String.
   * @return startTime
   *        startTime is a concatenated string for the Start Date / Time
   */
  @Override
  public String getBeginTimeString()
  {
    if (startTime == null)
    {
      throw new UnsupportedOperationException("StartTime is null.");
    }

    Date date1 = getBeginTime();
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date1);
  }

  /**
   * Gets the <code>Appointment</code> End Time String.
   *
   * @return endTime
   *         endTime is the concatenated string from the End Date / Time
   */
  @Override
  public String getEndTimeString()
  {
    if (endTime == null)
    {
      throw new UnsupportedOperationException("StartTime is null.");
    }

    Date date1 = getEndTime();
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date1);
  }

  /**
   * Gets the <code>Appointment</code> Description
   *
   * @return description
   *         Appointment Description
   */
  @Override
  public String getDescription()
  {
    if (description == null)
    {
      return "Description is not implemented.";
    }
    return description;
  }

  /**
   * Get the <code>Appointment</code> Begin Time date.
   * @return date1
   *        date1 is a date object of the being date + time
   */
  @Override
  public Date getBeginTime()
  {
    String start = this.startDate + " " + this.startTime;
    DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    dFormat.setLenient(false);
    Date date1 = null;
    try
    {
      date1 = dFormat.parse(start);
    }
    catch (ParseException e)
    {
      System.err.println(e.getMessage() + " Date " + start) ;
      System.exit(1);
    }
    return date1;
  }

  /**
   * Get the <code>Appointment</code> End Time date.
   * @return date1
   *        date1 is a date object of the being date + time
   */
  @Override
  public Date getEndTime()
  {
    String end = this.endDate + " " + this.endTime;
    DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    dFormat.setLenient(false);
    Date date1 = null;
    try
    {
      date1 = dFormat.parse(end);
    }
    catch (ParseException e)
    {
      System.err.println(e.getMessage() + " Date " + end) ;
      System.exit(1);
    }

    return date1;
  }

  @Override
  public int compareTo(Appointment appointment)
  {

    if (this.getBeginTime().equals(appointment.getBeginTime())
        && this.getEndTime().equals(appointment.getEndTime()))
    {
      return this.getDescription().compareTo(appointment.getDescription());
    }
    else if (this.getBeginTime().equals(appointment.getBeginTime()))
    {
      return this.getEndTime().compareTo(appointment.getEndTime());

    }

    return this.getBeginTime().compareTo(appointment.getBeginTime());
  }
}
