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
	private static final String TABLE_GROUPS = "Groups";
	//private static final int SCHEMA_VERSION = 1;
	
	private static final String COLUMN_ID = "_id";
	private static final int ID_COLUMN = 0;
	public static final String COLUMN_NAME = "name";
	private static final int NAME_COLUMN = 1;
	
	
	public String tableName;
    
	public DbHelper() throws ClassNotFoundException {
		
		Class.forName("org.sqlite.JDBC");
		
		try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.

	      //statement.executeUpdate("drop table if exists person");
	      //statement.executeUpdate("create table person (id integer, name string)");
	      //statement.executeUpdate("insert into person values(1, 'leo')");
	      //statement.executeUpdate("insert into person values(2, 'yui')");
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
	
	public ResultSet query() {
		ResultSet result = null;
		
		
		try
	    {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			result = statement.executeQuery("select * from groups");
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
		return result;
	}
	
	public void insert(String tableName, Map<String, String> keyValues) {
		
			Statement statement;
			String keys, values;
			String[] keysArray, valuesArray;
			
			keys = keyValues.keySet().toString();
			keys = keys.substring(1, keys.length()-1);
			values = keyValues.values().toString();
			values = values.substring(1, values.length()-1);
			
			System.out.println(keys);
			System.out.println(values);
//			for (Map.Entry<String, String> entry: keyValues.entrySet()){
//				
//			    System.out.println(entry.getKey() + " = " + entry.getValue());
//			}
			
			try {
				statement = connection.createStatement();
				statement.setQueryTimeout(30);  // set timeout to 30 sec.
			    statement.executeUpdate("insert into " + tableName + "(" + keys + ") values(" + values + ")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
		
	}
}
