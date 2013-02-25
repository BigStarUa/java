package schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {

	private final Connection con;

	public GroupDAO(Connection con) {
		this.con = con;
	}
	
	private Group getGroupFromRS(ResultSet rs) throws SQLException 
	{
		Group group = new Group();
		group.setId(rs.getInt("id"));
		group.setName(rs.getString("name"));
		group.setLevel(getLevel(rs.getInt("level")));
		group.setCapacity(rs.getInt("capacity"));
		group.setStudentAge(rs.getInt("stud_age"));
		group.setTeacher(getTeacher(rs.getInt("teacher")));
		group.setSchedule(rs.getInt("schedule"));
		group.setValue(rs.getInt("value"));
		return group;
		
	}
	
	private Level getLevel(int id)
	{
		LevelDAO levelDAO = new LevelDAO(con);
		Level level = levelDAO.getLevel(id);
		return level;
	}
	
	private Teacher getTeacher(int id)
	{
		TeacherDAO teacherDAO = new TeacherDAO(con);
		Teacher teacher = teacherDAO.getTeacher(id);
		return teacher;
	}
	
	public Group getGroup(int id)
	{
		Group group = null;
		
		try
	     {
	       PreparedStatement ps = con.prepareStatement( "SELECT * FROM groups WHERE id=?" );
	       ps.setInt( 1, id );
	 
	       ResultSet rs = ps.executeQuery();
	       if( rs.next() )
	       {
	         group = getGroupFromRS( rs );
	       }
	       ps.close();
	     }
	     catch( SQLException e )
	     { 
	       e.printStackTrace(); 
	     }
		
		return group;
	}
	
	public List<Group> getGroupList(int schedule)
	{
		List<Group> list = new ArrayList<Group>();
		
		try {
			ResultSet rs = con.createStatement().executeQuery( "SELECT * FROM groups WHERE schedule=" + schedule );
			while(rs.next())
			{
				list.add(getGroupFromRS(rs));				
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;		
	}
	
	public void updateGroup(Group group)
	{
		try
	    {
	       PreparedStatement ps;
			if(group.getId()>0)
			{
				ps = con.prepareStatement( "UPDATE groups SET name=?, level=?, capacity=?," +
						" stud_age=?, teacher=?, schedule=? WHERE id=?" );
		    	ps.setInt( 7, group.getId() );
				
			}else{
				ps = con.prepareStatement( "INSERT INTO groups (name, level, capacity," +
						" stud_age, teacher, schedule) VALUES (?,?,?,?,?,?)" );	
			}
			ps.setString( 1, group.getName());
		    ps.setInt( 2, group.getLevel().getId() );
		    ps.setInt( 3, group.getCapacity() );
		    ps.setInt( 4, group.getStudentAge() );
		    ps.setInt( 5, group.getTeacher().getId() );
		    ps.setInt( 6, group.getSchedule() );
		    ps.executeUpdate();
		    ps.close();
	    }
		catch( SQLException e )
	    { 
	       e.printStackTrace(); 
	    }
		
	}
	
	public void deleteGroup( int id )
	   {
	     try
	     {
	       PreparedStatement ps = con.prepareStatement( "DELETE FROM groups WHERE id=?" );
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
