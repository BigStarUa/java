package schedule;

public class Group_schedule implements Comparable<Group_schedule>{

	public static final int STATUS_NEW = 1;
	public static final int STATUS_OLD = 2;
	public static final int STATUS_DELETE = 3;
	public static final int STATUS_CHANGED = 4;
	int id;
	int group;
	Group groupObject;
	Schedule schedule;
	Teacher teacher;
	Room room;
	int status;
	boolean isFixed;
	
	public Group_schedule() {

	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setGroup(int group)
	{
		this.group = group;
	}
	
	public void setSchedule(Schedule schedule)
	{
		this.schedule = schedule;
	}
	public void setTeacher(Teacher teacher)
	{
		this.teacher = teacher;
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public void setGroupObject(Group groupObject)
	{
		this.groupObject = groupObject;
	}
	
	public void setRoom(Room room)
	{
		this.room = room;
	}
	
	public void setIsFixed(boolean isFixed)
	{
		this.isFixed = isFixed;
	}

	public int getId()
	{
		return this.id;
	}
	
	public int getGroup()
	{
		return this.group;
	}
	
	public Schedule getSchedule()
	{
		return this.schedule;
	}
	
	public Teacher getTeacher()
	{
		return this.teacher;
	}
	
	public int getStatus()
	{
		return this.status;
	}
	
	public String getName()
	{
		String roomName = "";
		if(isFixed)
		{
			roomName = " " + room.getName(); 
		}
		return this.schedule.getName() + "-" + this.teacher.getName() + roomName;
	}
	
	public Group getGroupObject()
	{
		return this.groupObject;
	}
	
	public Room getRoom()
	{
		return this.room;
	}
	
	public boolean getIsFixed()
	{
		return this.isFixed;
	}
	
	@Override
	public int compareTo(Group_schedule g) {
		// TODO Auto-generated method stub
		
		return g.getGroupObject().getCapacity() - this.getGroupObject().getCapacity();
	}
}
