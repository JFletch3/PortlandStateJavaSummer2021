package edu.pdx.cs410J.jf32;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {

  private String description;
  private String startTime;
  private String endTime;

//  public Appointment(String desc, String start, String end)
//  {
//    description = desc;
//    startTime = start;
//    endTime = end;
//  }
  public void setDescription(String description)
  {
    this.description = description;
  }

  public void setStartTime(String startTime)
  {
    this.startTime = startTime;
  }

  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }

  @Override
  public String getBeginTimeString()
  {
    return startTime;
  }

  @Override
  public String getEndTimeString()
  {
    //throw new UnsupportedOperationException("This method is not implemented yet");
    return endTime;
  }

  @Override
  public String getDescription()
  {
    return description;
  }
}
