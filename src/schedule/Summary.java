package schedule;

import java.util.List;

public class Summary {

	List<Schedule> scheduleList;
	String weekDay;
	
	
	public Summary()
	{
		
	}
	
	public void setScheduleList(List<Schedule> scheduleList)
	{
		this.scheduleList = scheduleList;
	}
	
	public void setWeekDay(String weekDay)
	{
		this.weekDay = weekDay;
	}
	
	public List<Schedule> getScheduleList()
	{
		return scheduleList;
	}
	
	public String getWeekDay()
	{
		return weekDay;
	}
}
