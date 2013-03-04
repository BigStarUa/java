package gui;

import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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
	private JToolBar toolBar;
	private List<Room> rooms;

	/**
	 * Create the panel.
	 */
	public RoomsGrid() {

		setLayout(new  BorderLayout());
		//Массив названий столбцов
		  String[] columnNames = {"Name", "Capacity", "Value"};
		  
		  DbHelper db;
		  rooms = null;
		  
		try {
			db = new DbHelper();
			RoomDAO roomDAO = new RoomDAO(db.connection);
			rooms = roomDAO.getRoomList();
			Object[] [] dataT = new Object[rooms.size()][3];
			for(int i=0; i<rooms.size(); i++)
			{
				Room r = rooms.get(i);
				dataT[i][0] = " " + r.getName();
				dataT[i][1] = r.getCapacity();
				dataT[i][2] = " " + r.getValue();
			}
			
			DefaultTableModel model = new DefaultTableModel(dataT, columnNames){
				
				 public boolean isCellEditable(int row, int column)
				 {
				     return false;
				 }
			};
			
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( JLabel.CENTER);
			
			table = new JTable(model);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setPreferredWidth(17);
			table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
			table.setRowHeight(20);
			table.addMouseListener(new java.awt.event.MouseAdapter() {
				
				
			    @Override
			    public void mouseClicked(java.awt.event.MouseEvent evt) {
			    	 if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1){
				        int row = table.rowAtPoint(evt.getPoint());
				        if (row >= 0 ) {
				        	Room r = rooms.get(table.getSelectedRow());
				        	getDialog(r);
				        	System.out.println(row);
				        }
			    	 }
			    }
			});
			add(new JScrollPane(table), BorderLayout.CENTER);
			setToolBar();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getDialog(Room room)
	{
		RoomDialog rd;
		try {
			rd = new RoomDialog((Window)RoomsGrid.this.getRootPane().getParent(), "Edit Class", Dialog.ModalityType.DOCUMENT_MODAL, room);
			rd.setVisible(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setToolBar()
	{
		JToolBar toolBar = new JToolBar();
		JButton addButton = new JButton();
		addButton.setIcon(StaticRes.ADD_ICON);
		addButton.setToolTipText("Add room");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getDialog(new Room());
			}
		});
		toolBar.add(addButton);
		
		this.toolBar = toolBar;
	}
	
	public JToolBar getToolbar()
	{
		return this.toolBar;
	}

	@Override
	public void pushToolbar(JToolBar toolBar) {
		// TODO Auto-generated method stub
		
	}

}
