package gui;

import exceptions.CapacityException;
import gui.SummaryGridShort.Group_scheduleComparator;
import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreePath;

import schedule.DbHelper;
import schedule.Group;
import schedule.GroupDAO;
import schedule.Group_schedule;
import schedule.Group_scheduleDAO;
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

public class SummaryGrid extends JPanel implements ToolBarInteface{
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
//				scheduleList = scheduleDAO.getScheduleByDayList(day, 0, 0);
//				Summary sum = new Summary();
//				sum.setScheduleList(scheduleList);
//				sum.setWeekDay(day);
//				summaryList.add(sum);
				
				summaryList.addAll(getList(day));
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
						if(value != null)
						{
							label.setText(value.toString());
							label.setBorder(paddingBorder);
						}
					    return label;
					  }
			}
					);
			
			table = new JTable(model);
			table.setRowSelectionAllowed(false);
			table.getTableHeader().setReorderingAllowed(false);
			table.setDragEnabled(true);  
			table.setDropMode(DropMode.USE_SELECTION); 
			table.setTransferHandler(new TS());
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setPreferredWidth(17);
			table.setRowHeight(20);
			
			table.setDefaultRenderer(Object.class, tableRenderer);
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//			table.addMouseListener(new java.awt.event.MouseAdapter() {
//
//				
//			});
			
		
			
			add(new JScrollPane(table));
			
			setToolBar();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(CapacityException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
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
	
	
	class Group_scheduleComparator implements Comparator<Group_schedule> {
	    public int compare(Group_schedule gs1, Group_schedule gs2) {
	        return gs2.getRoom().getValue() - gs1.getRoom().getValue();
	    }
	}
	
	
	private List<Summary> getList(String weekDay) throws CapacityException
	{
	
	Group_scheduleDAO gsDAO = new Group_scheduleDAO(db.connection);
	ScheduleDAO sDAO = new ScheduleDAO(db.connection);
	List<Schedule> scheduleList = new ArrayList<Schedule>();
	List<Schedule> sList = scheduleDAO.getScheduleByDayList(weekDay, 0, 0);
	scheduleList.addAll(sList);
	
	List<Summary> sumList = new ArrayList<Summary>();
	try{
	for(Schedule schedule : scheduleList)
	{
		List<Group_schedule> gs = gsDAO.getGroup_scheduleListByScheduleId(schedule.getId());
		//List<Group_schedule> groups = fillRooms(db, schedule.getId(), gs);
		List<Group_schedule> groups = fillRoom(schedule.getId());
		Collections.sort(groups, new Group_scheduleComparator());
		Summary s = new Summary();
		s.setSchedule(schedule);
		s.setGSList(groups);
		sumList.add(s);
	}
	}catch(CapacityException e)
	{
		throw new CapacityException(e.getMessage());
	}
	return sumList;
	}
	
	private List<Group_schedule> fillRoom(int schedule_id) throws CapacityException
	{
		GroupDAO groupDAO = new GroupDAO(db.connection);
		Group_scheduleDAO gsDAO = new Group_scheduleDAO(db.connection);
		List<Group_schedule> gs = gsDAO.getGroup_scheduleListByScheduleId(schedule_id);

		for(int i = 0; i < gs.size(); i++)
		{
			gs.get(i).setGroupObject(groupDAO.getGroup(gs.get(i).getGroup()));
		}
		Collections.sort(gs);
		
		RoomDAO roomDAO = new RoomDAO(db.connection);
		List<Room> rooms = roomDAO.getRoomList();
		Collections.sort(rooms);
		
		int count = Math.max(gs.size(), rooms.size());
		try{
		for (int i = 0; i < count; i++)
		{
			
			if(i == rooms.size())
			{
				Group_schedule g = gs.get(i);
//				Room virtRoom = new Room();
//				virtRoom.setCapacity(99);
//				virtRoom.setValue(0);
				//rooms.add(virtRoom);
				throw new CapacityException("Error: not enough rooms (rooms: " + rooms.size() + ", groups: " + gs.size() + ") in "+ g.getSchedule().getName());
			}
			else if(i == gs.size())
			{
				Group virtGroup = new Group();
				virtGroup.setCapacity(0);
				virtGroup.setValue(0);
				Group_schedule newGS = new Group_schedule();
				newGS.setGroupObject(virtGroup);
				gs.add(newGS);
			}
			
			Group_schedule g = gs.get(i);
			Room r = rooms.get(i);
			if(g.getGroupObject().getCapacity() > r.getCapacity()) throw new CapacityException("Error: not enough room capacity in " + g.getSchedule().getName());
			
			g.setRoom(r);
		}
		}catch(CapacityException e)
		{
			throw new CapacityException(e.getMessage());		
		}
		boolean flag = true;
		while(flag)
		{
			flag = false;
		for(int i = 0; i < gs.size(); i++)
		{
			Group_schedule r = gs.get(i);
			
			for (int n = 0; n < gs.size(); n++)
			{
				if(i==n) continue;
				Group_schedule com = gs.get(n);
				if(r.getGroupObject().getValue() < com.getGroupObject().getValue() 
						//&& r.getRoom().getValue() > com.getRoom().getValue()
						&& r.getRoom().getCapacity() >= com.getGroupObject().getCapacity() 
						&& com.getRoom().getCapacity() >= r.getGroupObject().getCapacity())
				{
					if(r.getRoom().getValue() > com.getRoom().getValue())
					{
						Room temp = r.getRoom();
						r.setRoom(com.getRoom());
						com.setRoom(temp);
						flag = true;
						
					}else if(r.getRoom().getValue() == com.getRoom().getValue()
							&& r.getRoom().getCapacity() > com.getRoom().getCapacity()
							&& r.getGroupObject().getCapacity() < com.getGroupObject().getCapacity())
					{
						Room temp = r.getRoom();
						r.setRoom(com.getRoom());
						com.setRoom(temp);
						flag = true;
					}
					
				}else if(r.getGroupObject().getValue() == com.getGroupObject().getValue()
						&& r.getRoom().getCapacity() >= com.getGroupObject().getCapacity() 
						&& com.getRoom().getCapacity() >= r.getGroupObject().getCapacity())
				{
					if(r.getRoom().getValue() < com.getRoom().getValue()
							&& r.getGroupObject().getCapacity() > com.getGroupObject().getCapacity())
					{
						Room temp = r.getRoom();
						r.setRoom(com.getRoom());
						com.setRoom(temp);
						flag = true;
						
					}else if(r.getRoom().getValue() == com.getRoom().getValue()
							&& r.getRoom().getCapacity() > com.getRoom().getCapacity()
							&& r.getGroupObject().getCapacity() < com.getGroupObject().getCapacity())
					{
						Room temp = r.getRoom();
						r.setRoom(com.getRoom());
						com.setRoom(temp);
						flag = true;
					}					

				}
			}
		}
		}
		return gs;
	}
	
	
}