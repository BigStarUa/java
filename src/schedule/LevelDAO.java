package schedule;

import gui.ComboBoxInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LevelDAO {

Connection con;
	
	public LevelDAO(Connection con)
	{
		this.con = con;
	}
	
	private Level getLevelFromRS(ResultSet rs) throws SQLException
	{
		Level level = new Level();
		level.setId(rs.getInt("id"));
		level.setName(rs.getString("name"));
		level.setValue(rs.getInt("value"));		
		return level;
	}
	
	public Level getLevel(int id)
	{
		Level level = null;
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM level WHERE id=?");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				level = getLevelFromRS(rs);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return level;
	}
	
	public List<Level> getLevelList()
	{
		List<Level> list = new ArrayList<Level>();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM level");
			
			while(rs.next())
			{
				list.add(getLevelFromRS(rs));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	

	public List<ComboBoxInterface> getComboBoxInterfaceList()
	{
		List<ComboBoxInterface> list = new ArrayList<ComboBoxInterface>();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM level");
			
			while(rs.next())
			{
				list.add(getLevelFromRS(rs));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void updateLevel(Level level)
	{
		PreparedStatement ps;
		try {
			if(level.getId() > 0)
			{
				ps = con.prepareStatement("UPDATE teacher SET name=?, value=? WHERE id=?");
				ps.setInt(3, level.getId());
			}
			else
			{
				ps = con.prepareStatement("INSERT INTO teacher (name, value) VALUES (?, ?)");
			}
			ps.setString(1, level.getName());
			ps.setInt(2, level.getValue());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteLevel(int id)
	{
		try
	     {
	       PreparedStatement ps = con.prepareStatement( "DELETE FROM teacher WHERE id=?" );
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
