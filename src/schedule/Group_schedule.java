package schedule;

public class Group_schedule {

	public static final int STATUS_NEW = 1;
	public static final int STATUS_OLD = 2;
	public static final int STATUS_DELETE = 3;
	int id;
	int group;	
	Schedule schedule;
	Teacher teacher;
	int status;
	
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
		return this.schedule.getName() + " " + this.teacher.getName();
	}
}
