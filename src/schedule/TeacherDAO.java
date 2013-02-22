package schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

	Connection con;
	
	public TeacherDAO(Connection con)
	{
		this.con = con;
	}
	
	private Teacher getTeacherFromRS(ResultSet rs) throws SQLException
	{
		Teacher teacher = new Teacher();
		teacher.setId(rs.getInt("id"));
		teacher.setName(rs.getString("name"));
		teacher.setValue(rs.getInt("value"));		
		return teacher;
	}
	
	public Teacher getTeacher(int id)
	{
		Teacher teacher = null;
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM teacher WHERE id=?");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				teacher = getTeacherFromRS(rs);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teacher;
	}
	
	public List<Teacher> getTeachersList()
	{
		List<Teacher> list = new ArrayList<Teacher>();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM teacher");
			
			while(rs.next())
			{
				list.add(getTeacherFromRS(rs));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public void updateTeacher(Teacher teacher)
	{
		PreparedStatement ps;
		try {
			if(teacher.getId() > 0)
			{
				ps = con.prepareStatement("UPDATE teacher SET name=?, value=? WHERE id=?");
				ps.setInt(3, teacher.getId());
			}
			else
			{
				ps = con.prepareStatement("INSERT INTO teacher (name, value) VALUES (?, ?)");
			}
			ps.setString(1, teacher.getName());
			ps.setInt(2, teacher.getValue());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteTeacher(int id)
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
