package schedule;

import gui.ComboBoxInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

	Connection con;
	
	public ScheduleDAO(Connection con)
	{
		this.con = con;
	}
	
	private Schedule getScheduleFromRS(ResultSet rs) throws SQLException
	{
		Schedule schedule = new Schedule();
		schedule.setId(rs.getInt("id"));
		schedule.setName(rs.getString("name"));
		schedule.setWeekDay(rs.getString("week_day"));	
		schedule.setStatus(Schedule.STATUS_OLD);	
		schedule.setTime(rs.getString("time"));
		return schedule;
	}
	
	public Schedule getSchedule(int id)
	{
		Schedule schedule = null;
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM schedule WHERE id=?");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				schedule = getScheduleFromRS(rs);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return schedule;
	}
	
	public List<Schedule> getScheduleList()
	{
		List<Schedule> list = new ArrayList<Schedule>();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM schedule");
			
			while(rs.next())
			{
				list.add(getScheduleFromRS(rs));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	

	public List<Schedule> getScheduleByDayList(String day)
	{
		List<Schedule> list = new ArrayList<Schedule>();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM schedule WHERE week_day=? ORDER BY time ASC");
			ps.setString(1, day);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				list.add(getScheduleFromRS(rs));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int updateSchedule(Schedule schedule)
	{
		PreparedStatement ps;
		int id = 0;
		try {
			if(schedule.getId() > 0)
			{
				ps = con.prepareStatement("UPDATE schedule SET name=?, week_day=?, time=? WHERE id=?");
				ps.setInt(4, schedule.getId());
			}
			else
			{
				ps = con.prepareStatement("INSERT INTO schedule (name, week_day, time) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			}
			ps.setString(1, schedule.getName());
			ps.setString(2, schedule.getWeekDay());
			ps.setString(3, schedule.getTime());
			ps.executeUpdate();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            id = generatedKeys.getInt(1);
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
			
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public void deleteSchedule(int id)
	{
		try
	     {
	       PreparedStatement ps = con.prepareStatement( "DELETE FROM schedule WHERE id=?" );
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
