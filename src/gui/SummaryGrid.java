package gui;

import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import schedule.DbHelper;
import schedule.ResultListener;
import schedule.Room;
import schedule.RoomDAO;
import schedule.RoomTableModel;
import schedule.Schedule;
import schedule.ScheduleDAO;
import schedule.ScheduleTableModel;
import schedule.Summary;
import schedule.SummaryTableModel;
import schedule.TS;

import javax.swing.BoxLayout;

public class SummaryGrid extends JPanel implements ToolBarInteface, ResultListener{
	/**
	 * 
	 */
	private JTable table;
	private JToolBar toolBar;
	private List<Schedule> scheduleList;
	private SummaryTableModel model;
	private ToolBarInteface listener;
	private DbHelper db;
	private RoomDAO roomDAO;
	private ScheduleDAO scheduleDAO;
	private JTable table2;

	/**
	 * Create the panel.
	 */
	public SummaryGrid(ToolBarInteface listener) {
		  this.listener = listener;
		  scheduleList = null;
		  List<Summary> summaryList = new ArrayList<Summary>();
		  
		try {
			db = new DbHelper();
			scheduleDAO = new ScheduleDAO(db.connection);
			roomDAO = new RoomDAO(db.connection);
			List<Room> roomsList = roomDAO.getRoomList();
			for(String day : StaticRes.WEEK_DAY_LIST)
			{
				scheduleList = scheduleDAO.getScheduleByDayList(day);
				Summary sum = new Summary();
				sum.setScheduleList(scheduleList);
				sum.setWeekDay(day);
				summaryList.add(sum);
			}
			
			model = new SummaryTableModel(summaryList, roomsList);
			
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
			table.setRowSelectionAllowed(false);
			table.setDragEnabled(true);  
			table.setDropMode(DropMode.USE_SELECTION); 
			table.setTransferHandler(new TS());
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setPreferredWidth(17);
			table.setRowHeight(20);
			//table.setDefaultRenderer(Object.class, tableRenderer);
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//			table.addMouseListener(new java.awt.event.MouseAdapter() {
//				
//				
//			    @Override
//			    public void mouseClicked(java.awt.event.MouseEvent evt) {
//			    	 if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1){
//				        int row = table.rowAtPoint(evt.getPoint());
//				        if (row >= 0 ) {
//				        	Room r = rooms.get(table.getSelectedRow());
//				        	getDialog(r);
//				        	System.out.println(row);
//				        }
//			    	 }else if(evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1)
//			    	 {
//			    		 setToolBar();
//					     RoomsGrid.this.listener.pushToolbar(toolBar);
//			    	 }
//			    }
//			});
			add(new JScrollPane(table));
			
			setToolBar();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setToolBar()
	{
		JToolBar toolBar = new JToolBar();
		JButton addButton = new JButton();
		addButton.setIcon(StaticRes.ADD32_ICON);
		addButton.setToolTipText("Add room");
		addButton.setFocusable(false);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				getDialog(new Room());
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
			editButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Room room = (Room)table.getValueAt(table.getSelectedRow(), -1);
//					getDialog(room);
				}
			});
		}
		toolBar.add(editButton);
		
		JButton deleteButton = new JButton();
		deleteButton.setIcon(StaticRes.DELETE32_ICON);
		deleteButton.setToolTipText("Delete class");
		deleteButton.setFocusable(false);
		deleteButton.setEnabled(false);
//		if(table.getSelectedRow() > -1)
//		{
//			deleteButton.setEnabled(true);
//			deleteButton.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {
//					String message = " Really delete ? ";
//	                String title = "Delete";
//	                int reply = JOptionPane.showConfirmDialog(RoomsGrid.this.getParent(), message, title, JOptionPane.YES_NO_OPTION);
//	                if (reply == JOptionPane.YES_OPTION)
//	                {
//	                    try
//	                    {
//	                    	Room room = (Room)table.getValueAt(table.getSelectedRow(), -1);
//	                    	roomDAO.deleteRoom(room.getId());
//	                    	((RoomTableModel)table.getModel()).removeObjectAt(table.getSelectedRow());
//	                    	table.repaint();
//	                    	table.revalidate();
//	                    	table.clearSelection();
//	                    	setToolBar();
//	                    	listener.pushToolbar(RoomsGrid.this.toolBar);
//	                    }
//	                    catch(NullPointerException e)
//	                    {
//	                    	
//	                    }
//	                    
//	                }
//				}
//			});
//		}
		toolBar.add(deleteButton);
		
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
		if(((Room)o).getStatus() == Room.STATUS_NEW)
		{
			((Room)o).setStatus(Room.STATUS_OLD);
			((RoomTableModel)table.getModel()).addObject((Room)o);
			((RoomTableModel)table.getModel()).fireTableDataChanged();
		}
		table.repaint();
		table.revalidate();
	}
}