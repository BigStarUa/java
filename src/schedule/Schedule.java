package schedule;

import gui.ComboBoxInterface;

public class Schedule implements ComboBoxInterface{

	int id;
	String name;	
	String week_day;
	
	public Schedule() {

	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setWeekDay(String week_day)
	{
		this.week_day = week_day;
	}

	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getWeekDay()
	{
		return this.week_day;
	}
}