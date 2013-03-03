package schedule;

import gui.ComboBoxInterface;

public class Teacher implements ComboBoxInterface{

	int id;
	String name;	
	int value;
	
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
}
