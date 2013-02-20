package schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
	
	private final Connection con;

	public RoomDAO(Connection con)
	{
		this.con = con;
	}
	
	private Room getRoomFromRS(ResultSet rs) throws SQLException
	{
		Room  room = new Room();
		room.setId(rs.getInt("id"));
		room.setName(rs.getString("name"));
		room.setValue(rs.getInt("value"));
		room.setCapacity(rs.getInt("capacity"));
		return room;
	}
	
	public Room getGroup(int id)
	{
		Room room = null;
		
		try
	     {
	       PreparedStatement ps = con.prepareStatement( "SELECT * FROM rooms WHERE id=?" );
	       ps.setInt( 1, id );
	 
	       ResultSet rs = ps.executeQuery();
	       if( rs.next() )
	       {
	         room = getRoomFromRS( rs );
	       }
	       ps.close();
	     }
	     catch( SQLException e )
	     { 
	       e.printStackTrace(); 
	     }
		
		return room;
	}
	
	public List<Room> getGroupList()
	{
		List<Room> list = new ArrayList<Room>();
		
		try {
			ResultSet rs = con.createStatement().executeQuery( "SELECT * FROM rooms" );
			while(rs.next())
			{
				list.add(getRoomFromRS(rs));				
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;		
	}
	
	public void updateGroup(Room room)
	{
		try
	    {
	       PreparedStatement ps;
			if(room.getId()>0)
			{
				ps = con.prepareStatement( "UPDATE groups SET name=?, level=?, capacity=?," +
						" stud_age=?, teacher=?, schedule=? WHERE id=?" );
		    	ps.setInt( 7, room.getId() );
				
			}else{
				ps = con.prepareStatement( "INSERT INTO groups (name, level, capacity," +
						" stud_age, teacher, schedule) VALUES (?,?,?,?,?,?)" );	
			}
//			ps.setString( 1, room.getName());
//		    ps.setInt( 2, room.getLevel() );
//		    ps.setInt( 3, room.getCapacity() );
//		    ps.setInt( 4, room.getStudentAge() );
//		    ps.setInt( 5, room.getTeacher() );
//		    ps.setInt( 6, room.getSchedule() );
		    ps.executeUpdate();
		    ps.close();
	    }
		catch( SQLException e )
	    { 
	       e.printStackTrace(); 
	    }
		
	}
}
