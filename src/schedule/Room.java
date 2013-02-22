package schedule;

import java.util.Comparator;
import java.util.HashMap;

public class Room implements Comparable<Room>, Comparator<Room>{

	int id;
	String name;
	int value;
	int capacity;
	int group_id;
	Group group = null;
	//HashMap<K, V> schedule;
	public Room(String name, int value, int capacity) {
		this.name = name;
		this.value = value;
		this.capacity = capacity;
	}
	
	    public Room() {
		// TODO Auto-generated constructor stub
	}

		public int compareTo(Room anotherInstance) {
	        return anotherInstance.capacity - this.capacity;
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
	
	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}
	
	public void setGroupId(int group_id)
	{
		this.group_id = group_id;
	}
	
	public void setGroup(Group g) {
		this.group = g;		
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
	
	public int getCapacity()
	{
		return this.capacity;
	}
	
	public int getGroupId()
	{
		return this.group_id;
	}
	
	public Group getGroup() {
		return this.group;
	}

	@Override
	public int compare(Room o1, Room o2) {
		int ret;
		
		if(o1.value > o2.value)
		{

			ret = -1;
			
		}else if(o1.value == o2.value)
		{
			ret = 0;
			
		}else
		{
			ret = 1;
			
		}
		
		
	
		return ret;
	    
	}
	
}

