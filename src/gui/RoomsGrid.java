package gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import schedule.DbHelper;
import schedule.Group;
import schedule.GroupDAO;
import schedule.Room;
import schedule.RoomDAO;

public class RoomsGrid extends JPanel implements ToolBarInteface{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4421827934709464229L;
	private JTable table;
	private JPanel toolBar;

	/**
	 * Create the panel.
	 */
	public RoomsGrid() {

		setLayout(new  BorderLayout());
		//Массив названий столбцов
		  String[] columnNames = {"Name", "Value", "Capacity"};
		  
		  DbHelper db;
		  List<Room> rooms = null;
		  
		try {
			db = new DbHelper();
			RoomDAO roomDAO = new RoomDAO(db.connection);
			rooms = roomDAO.getRoomList();
			Object[] [] dataT = new Object[rooms.size()][3];
			for(int i=0; i<rooms.size(); i++)
			{
				Room r = rooms.get(i);
				dataT[i][0] = r.getName();
				dataT[i][1] = r.getValue();
				dataT[i][2] = r.getCapacity();
			}
			table = new JTable(dataT, columnNames);
			add(new JScrollPane(table), BorderLayout.CENTER);
			setToolBar();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setToolBar()
	{
		JPanel toolBar = new JPanel();
		JButton btnNewButton = new JButton("Room Button");
		toolBar.add(btnNewButton);
		
		this.toolBar = toolBar;
	}
	
	public JPanel getToolbar()
	{
		return this.toolBar;
	}

}
