package schedule;


import gui.ComboBoxInterface;

public class Schedule implements ComboBoxInterface{

	public static final int STATUS_NEW = 1;
	public static final int STATUS_OLD = 2;
	public static final int STATUS_DELETE = 3;
	int id;
	String name;	
	String week_day;
	int status;
	
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
	
	public void setStatus(int status)
	{
		this.status = status;
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
	
	public int getStatus()
	{
		return this.status;
	}
}