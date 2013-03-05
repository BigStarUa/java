package schedule;

import gui.ComboBoxInterface;

public class Teacher implements ComboBoxInterface{

	public static final int STATUS_NEW = 1;
	public static final int STATUS_OLD = 2;
	public static final int STATUS_DELETE = 3;
	
	int id;
	String name;	
	int value;
	int status;
	
	public Teacher() {

	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setValue(int value)
	{
		this.value = value;
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
	
	public int getValue()
	{
		return this.value;
	}
	
	public int getStatus()
	{
		return this.status;
	}
}
