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
		Room room = new Room();
		room.setId(rs.getInt("id"));
		room.setName(rs.getString("name"));
		room.setValue(rs.getInt("value"));
		room.setCapacity(rs.getInt("capacity"));
		room.setGroupId(rs.getInt("group_id"));
		
		return room;
		
	}
	
	public Room getRoom(int id)
	{
		Room room = null;
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM rooms WHERE id=?");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if( rs.next() )
			{
				room = getRoomFromRS(rs);
			}
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return room;
	}
	
	public List<Room> getRoomList()
	{
		List<Room> list = new ArrayList<Room>();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM rooms");
			ResultSet rs = ps.executeQuery();
			while( rs.next() )
			{
				list.add(getRoomFromRS(rs));
			}
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public void updateRoom(Room room)
	{
		try {
			PreparedStatement ps;
			
			if(room.getId() > 0) // UPDATE existing room.
			{
				ps = con.prepareStatement("UPDATE rooms SET name=?, value=?," +
						" capacity=?, group_id=? WHERE id=? ");
				ps.setInt(5, room.getId());
			}
			else // INSERT a new room.
			{
				ps = con.prepareStatement("INSERT INTO rooms (name, value," +
						" capacity, group_id) VALUES (?, ?, ?, ?, ?)");
			}
			ps.setString(1, room.getName());
			ps.setInt(2, room.getValue());
			ps.setInt(3, room.getCapacity());
			ps.setInt(4, room.getGroupId());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteRoom(int id)
	{
		try
	     {
	       PreparedStatement ps = con.prepareStatement( "DELETE FROM rooms WHERE id=?" );
	       ps.setInt( 1, id );
	       ps.executeUpdate();
	     }
	     catch( SQLException e )
	     { 
	       e.printStackTrace(); 
	     }
	}
}
