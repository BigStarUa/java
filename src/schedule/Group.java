package schedule;

import java.util.List;

public class Group implements Comparable<Group>{
	
	private int id;
	private String name;
	private Level level;
	private int capacity;
	private int value;
	private int studentAge;
	//private Teacher teacher;
	private String teacher;
	private List<Group_schedule> group_schedule;
	private Room room;
	
	public Group(String name, int level, int capacity,
			int studentAge, int teacher, int schedule, int value) {
		
		this.name = name;
		//this.level = level;
		this.capacity = capacity;
		this.studentAge = studentAge;
		//this.teacher = teacher;
		//this.schedule = schedule;
		this.value = value;
		//countValue();
	}

	public Group() {
		// TODO Auto-generated constructor stub
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setLevel(Level level)
	{
		this.level = level;		
	}
	
	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}
	
	public void setStudentAge(int studentAge)
	{
		this.studentAge = studentAge;
	}
	
	public void setTeacher(String teacher)
	{
		this.teacher = teacher;
	}
	
	public void setSchedule(List<Group_schedule> list)
	{
		this.group_schedule = list;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public void setRoom(Room room)
	{
		this.room = room;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Level getLevel()
	{
		return this.level;		
	}
	
	public int getCapacity()
	{
		return this.capacity;
	}
	
	public int getStudentAge()
	{
		return this.studentAge;
	}
	
	public String getTeacher()
	{
		return this.teacher;
	}
	
	public List<Group_schedule> getSchedule()
	{
		return this.group_schedule;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public Room getRoom()
	{
		return this.room;
	}

	@Override
	public int compareTo(Group g) {
		// TODO Auto-generated method stub
		
		int thisG = countValue(this);
		int thatG = countValue(g);
		//this.value = thisG;
		
		
		return g.capacity - this.capacity;
	}
	
	private int countValue(Group g) {
		
		//int value = g.level + (g.capacity*3) + g.studentAge + g.teacher;
		
		return value;
	}
	
	private void countValue() {
		
		//int value = this.level.getValue() + (this.capacity*3) + this.studentAge + this.teacher.getValue();
		
		//this.value = value;
	}
	
}


