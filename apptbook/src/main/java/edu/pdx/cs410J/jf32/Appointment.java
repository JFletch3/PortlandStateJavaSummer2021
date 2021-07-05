package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointment;

/**
 * This class is represents a <code>Appointment</code>.
 */
public class Appointment extends AbstractAppointment {

  private String description;
  private String startTime;
  private String endTime;

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
   * Sets the <code>Appointment</code> Start Date and Start Time.
   * Concatenates Start Date and Start Time to make one String.
   * @param startDate
   *        Appointment Start Date from Command line argument.
   * @param startTime
   *        Appointment Start Time from Command line argument.
   */
  public void setStartTime(String startDate,  String startTime)
  {
    this.startTime = startDate  + " " + startTime;
  }

  /**
   * Sets the <code>Appointment</code> End Date and End Time
   *  Concatenates End Date and End Time to make one String.
   * @param endDate
   *        Appointment End Date from Command line argument.
   * @param endTime
   *        Appointment End Time from Command line argument.
   */
  public void setEndTime(String endDate,  String endTime)
  {
    this.endTime = endDate + " " + endTime;
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
    return startTime;
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
    return endTime;
  }

  /**
   * Gets the <code>Appointment</code> Description
   *
   * @return description
   *         <code>Appointment</code> Description
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
}
