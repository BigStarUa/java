package gui;

import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import schedule.DbHelper;
import schedule.Group;
import schedule.GroupDAO;
import schedule.GroupTableModel;
import schedule.ResultListener;
import schedule.Room;
import schedule.RoomDAO;
import schedule.RoomTableModel;
import schedule.Schedule;

public class RoomsGrid extends JPanel implements ToolBarInteface, ResultListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4421827934709464229L;
	private JTable table;
	private JToolBar toolBar;
	private List<Room> rooms;
	private RoomTableModel model;
	private ToolBarInteface listener;

	/**
	 * Create the panel.
	 */
	public RoomsGrid(ToolBarInteface listener) {

		setLayout(new  BorderLayout());
		//Массив названий столбцов
		  String[] columnNames = {"Name", "Capacity", "Value"};
		  this.listener = listener;
		  DbHelper db;
		  rooms = null;
		  
		try {
			db = new DbHelper();
			RoomDAO roomDAO = new RoomDAO(db.connection);
			rooms = roomDAO.getRoomList();
			model = new RoomTableModel(rooms);
			
			TableCellRenderer tableRenderer = (new TableCellRenderer()
			{
				protected DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
				
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					      boolean hasFocus, int row, int column) {
						JLabel label = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected,
							      hasFocus, row, column);
						Border paddingBorder = BorderFactory.createEmptyBorder(3,5,3,3);
						label.setText(value.toString());
						label.setBorder(paddingBorder);
					    return label;
					  }
			}
					);
			
			table = new JTable(model);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setPreferredWidth(17);
			table.setRowHeight(20);
			table.setDefaultRenderer(Object.class, tableRenderer);
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
			    	 }else if(evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1)
			    	 {
			    		 setToolBar();
					     RoomsGrid.this.listener.pushToolbar(toolBar);
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
			rd = new RoomDialog((Window)RoomsGrid.this.getRootPane().getParent(),"Edit Class", Dialog.ModalityType.DOCUMENT_MODAL, room, this);
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
		
		JButton editButton = new JButton();
		editButton.setIcon(StaticRes.EDIT32_ICON);
		editButton.setToolTipText("Edit schedule");
		editButton.setFocusable(false);
		editButton.setEnabled(false);

		if(table.getSelectedRow() > -1)
		{
			editButton.setEnabled(true);
			final Room room = (Room)table.getValueAt(table.getSelectedRow(), -1);
			editButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getDialog(room);
				}
			});
		}
		toolBar.add(editButton);
		
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

	@Override
	public void returnObject(Object o) {
		// TODO Auto-generated method stub
		
	}

}
