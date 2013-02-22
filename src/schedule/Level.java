package schedule;

import gui.ComboBoxInterface;

public class Level implements ComboBoxInterface{

	private int id;
	private String name;
	private int value;
	
	public Level()
	{
		
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
