package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {

  private String description;
  private String startTime;
  private String endTime;

  public void setDescription(String description)
  {
    this.description = description;
  }

  public void setStartTime(String startDate,  String startTime)
  {
    this.startTime = startDate  + " " + startTime;
  }

  public void setEndTime(String endDate,  String endTime)
  {
    this.endTime = endDate + " " + endTime;
  }

  @Override
  public String getBeginTimeString()
  {
    if (startTime == null)
    {
      throw new UnsupportedOperationException("StartTime is null.");
    }
    return startTime;
  }

  @Override
  public String getEndTimeString()
  {
    if (endTime == null)
    {
      throw new UnsupportedOperationException("StartTime is null.");
    }
    return endTime;
  }

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
