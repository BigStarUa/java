package schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Map;

public class DbHelper {

	public Connection connection = null;
	private static final String DATABASE_NAME = "sample.db";
    
	public DbHelper() throws ClassNotFoundException {
		
		Class.forName("org.sqlite.JDBC");
		
		try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.

	    }
	    catch(SQLException e)
	    {
	      // if the error message is "out of memory", 
	      // it probably means no database file is found
	      System.err.println(e.getMessage());
	    }
//		finally
//	    {
//	      try
//	      {
//	        if(connection != null)
//	          connection.close();
//	      }
//	      catch(SQLException e)
//	      {
//	        // connection close failed.
//	        System.err.println(e);
//	      }
//	    }
    
	}
}
