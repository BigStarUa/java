package schedule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDAO {

	private final Connection con;
	
	public RoomDAO(Connection con)
	{
		this.con = con;
	}
	
	private Room getGroupFromRS(ResultSet rs) throws SQLException 
	{
		Room room = new Room();
		room.setId(rs.getInt("id"));
		room.setName(rs.getString("name"));
		room.setValue(rs.getInt("value"));
		room.setCapacity(rs.getInt("capacity"));
		
		return room;
		
	}
}
