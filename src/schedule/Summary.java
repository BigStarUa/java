package schedule;

import java.util.List;

public class Summary {

	List<Schedule> scheduleList;
	List<Group_schedule> gsList;
	Schedule schedule;
	String weekDay;
	
	
	public Summary()
	{
		
	}
	
	public void setScheduleList(List<Schedule> scheduleList)
	{
		this.scheduleList = scheduleList;
	}
	
	public void setGSList(List<Group_schedule> gsList)
	{
		this.gsList = gsList;
	}
	
	public void setWeekDay(String weekDay)
	{
		this.weekDay = weekDay;
	}
	
	public void setSchedule(Schedule schedule)
	{
		this.schedule = schedule;
	}
	
	public List<Schedule> getScheduleList()
	{
		return scheduleList;
	}
	
	public List<Group_schedule> getGSList()
	{
		return gsList;
	}
	
	public String getWeekDay()
	{
		return weekDay;
	}
	
	public Schedule getSchedule()
	{
		return schedule;
	}
}
