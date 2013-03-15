package schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group_scheduleDAO{

	Connection con;
	
	public Group_scheduleDAO(Connection con)
	{
		this.con = con;
	}
	
	private Group_schedule getGroup_scheduleFromRS(ResultSet rs) throws SQLException
	{
		Group_schedule gschedule = new Group_schedule();
		gschedule.setId(rs.getInt("id"));
		gschedule.setGroup(rs.getInt("group_id"));
		gschedule.setSchedule(getSchedule(rs.getInt("schedule_id")));	
		gschedule.setTeacher(getTeacher(rs.getInt("teacher_id")));	
		gschedule.setStatus(Group_schedule.STATUS_OLD);
		gschedule.setIsFixed(rs.getBoolean("room_fixed"));
		if(rs.getBoolean("room_fixed"))
		{
			gschedule.setRoom(getRoom(rs.getInt("room_id")));
		}
		return gschedule;
	}
	
	private Group getGroup(int id)
	{
		GroupDAO groupDAO = new GroupDAO(con);
		Group group = groupDAO.getGroup(id);
		return group;
	}
	
	private Room getRoom(int id)
	{
		RoomDAO roomDAO = new RoomDAO(con);
		Room room = roomDAO.getRoom(id);
		return room;
	}
	
	private Schedule getSchedule(int id)
	{
		ScheduleDAO scheduleDAO = new ScheduleDAO(con);
		Schedule schedule = scheduleDAO.getSchedule(id);
		return schedule;
	}
	
	private Teacher getTeacher(int id)
	{
		TeacherDAO teacherDAO = new TeacherDAO(con);
		Teacher teacher = teacherDAO.getTeacher(id);
		return teacher;
	}
	
	public Group_schedule getGroup_schedule(int id)
	{
		Group_schedule gschedule = null;
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM group_schedule WHERE id=?");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				gschedule = getGroup_scheduleFromRS(rs);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gschedule;
	}
	
	public List<Group_schedule> getGroup_scheduleListByGroupId(int group_id)
	{
		List<Group_schedule> list = new ArrayList<Group_schedule>();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM group_schedule WHERE group_id=?");
			ps.setInt(1, group_id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				list.add(getGroup_scheduleFromRS(rs));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Group_schedule> getGroup_scheduleListByScheduleId(int schedule_id)
	{
		List<Group_schedule> list = new ArrayList<Group_schedule>();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM group_schedule WHERE schedule_id=?");
			ps.setInt(1, schedule_id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				list.add(getGroup_scheduleFromRS(rs));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Group_schedule> getGroup_scheduleListByScheduleIdRoomFixed(int schedule_id, boolean isFixed)
	{
		List<Group_schedule> list = new ArrayList<Group_schedule>();
		try {
			PreparedStatement ps;
			if(isFixed)
			{
				ps = con.prepareStatement("SELECT * FROM group_schedule WHERE schedule_id=? AND room_fixed=1 ORDER BY room_id");
			}else{
				ps = con.prepareStatement("SELECT * FROM group_schedule WHERE schedule_id=? AND room_fixed=0 ORDER BY room_id");
			}
			ps.setInt(1, schedule_id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				list.add(getGroup_scheduleFromRS(rs));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean isRoomOccupied(int schedule_id, int room_id, int id)
	{
		boolean isOccupied = false;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM group_schedule WHERE schedule_id=? AND room_fixed=1 AND room_id=? AND id<>?");
			ps.setInt(1, schedule_id);
			ps.setInt(2, room_id);
			ps.setInt(3, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				isOccupied = true;
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isOccupied;	
	}
	
	
	public void updateGroup_schedule(Group_schedule gschedule)
	{
		PreparedStatement ps;
		try {
			if(gschedule.getId() > 0)
			{
				ps = con.prepareStatement("UPDATE group_schedule SET" +
						" group_id=?, schedule_id=?, teacher_id=?, room_id=?, room_fixed=? WHERE id=?");
				ps.setInt(6, gschedule.getId());
			}
			else
			{
				ps = con.prepareStatement("INSERT INTO group_schedule" +
						" (group_id, schedule_id, teacher_id, room_id, room_fixed) VALUES (?,?,?,?,?)");
			}
			ps.setInt(1, gschedule.getGroup());
			ps.setString(2, String.valueOf(gschedule.getSchedule().getId()));
			ps.setString(3, String.valueOf(gschedule.getTeacher().getId()));
			ps.setInt(4, gschedule.getRoom().getId());
			ps.setBoolean(5, gschedule.getIsFixed());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteGroup_schedule(int id)
	{
		try
	     {
	       PreparedStatement ps = con.prepareStatement( "DELETE FROM group_schedule WHERE id=?" );
	       ps.setInt( 1, id );
	       ps.executeUpdate();
	       ps.close();
	     }
	     catch( SQLException e )
	     { 
	       e.printStackTrace(); 
	     }
	}

}
