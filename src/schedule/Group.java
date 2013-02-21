package schedule;

public class Group implements Comparable<Group>{
	
	private int id;
	private String name;
	private int level;
	private int capacity;
	private int value;
	private int studentAge;
	private int teacher;
	private int schedule;
	
	public Group(String name, int level, int capacity,
			int studentAge, int teacher, int schedule, int value) {
		
		this.name = name;
		this.level = level;
		this.capacity = capacity;
		this.studentAge = studentAge;
		this.teacher = teacher;
		this.schedule = schedule;
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
	
	public void setLevel(int level)
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
	
	public void setTeacher(int teacher)
	{
		this.teacher = teacher;
	}
	
	public void setSchedule(int schedule)
	{
		this.schedule = schedule;
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
	
	public int getLevel()
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
	
	public int getTeacher()
	{
		return this.teacher;
	}
	
	public int getSchedule()
	{
		return this.schedule;
	}
	
	public int getValue()
	{
		return this.value;
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
		
		int value = g.level + (g.capacity*3) + g.studentAge + g.teacher;
		
		return value;
	}
	
	private void countValue() {
		
		int value = this.level + (this.capacity*3) + this.studentAge + this.teacher;
		
		this.value = value;
	}
	
}


