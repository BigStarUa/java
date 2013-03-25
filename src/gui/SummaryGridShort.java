package gui;

import exceptions.CapacityException;
import gui.SummaryGrid.Group_scheduleComparator;
import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneLayout;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import schedule.DbHelper;
import schedule.Group;
import schedule.GroupDAO;
import schedule.Group_schedule;
import schedule.Group_scheduleDAO;
import schedule.Room;
import schedule.RoomDAO;
import schedule.Schedule;
import schedule.ScheduleDAO;
import schedule.ScheduleTableModel;
import schedule.Summary;
import schedule.SummaryJTextPane;
import schedule.SummaryTableModel;
import schedule.TS;

public class SummaryGridShort extends JPanel implements ToolBarInteface {

	private ToolBarInteface listener;
	private DbHelper db;
	private JTable table;
	private JTabbedPane tabbedPane;
	private ScheduleDAO scheduleDAO;
	private RoomDAO roomDAO;
	private SummaryTableModel model;
	private JToolBar toolBar;
	
	public SummaryGridShort(ToolBarInteface listener)
	{
		setLayout(new  BorderLayout());
		this.listener = listener;
		
		try {
			db = new DbHelper();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initContent();
		addContent();
		setToolBar();
	}
	
	private void addContent()
	{		
		scheduleDAO = new ScheduleDAO(db.connection);
		roomDAO = new RoomDAO(db.connection);
		List<Room> roomsList = roomDAO.getRoomList();
		try{
		for(String day : StaticRes.WEEK_DAY_LIST)
		{
			
			model = new SummaryTableModel(getList(day), roomsList, db.connection);
			
			TableCellRenderer tableRenderer = (new TableCellRenderer()
			{
				//protected DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
				
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					      boolean hasFocus, int row, int column) {

						//JLabel label = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);
						Border border = BorderFactory.createEmptyBorder(1,3,1,1);
					
						SummaryJTextPane panel = new SummaryJTextPane();
						if(value != null)
						{
							if(column > 0)
							{
								panel = (SummaryJTextPane)value;
							}else{
								
								JLabel label = new JLabel();
								label.setText(value.toString());
								label.setBorder(border);
								//label.setForeground(Color.gray);
								label.setBackground(Color.LIGHT_GRAY);
								label.setOpaque(true);
								return label;
							}
						}
						if(hasFocus && column > 0)
						{
							panel.setBorder(BorderFactory.createCompoundBorder(UIManager.getBorder("Table.focusCellHighlightBorder"), border));
							//panel.setForeground(UIManager.getColor("Table.focusCellForeground"));
							//panel.setBackground(Color.DARK_GRAY);
						}

					    return panel;
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
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			//table.getColumnModel().getColumn(0).setCellRenderer(new TextAreaRenderer());
			table.setRowHeight(40);
			table.setDefaultRenderer(Object.class, tableRenderer);
			//setLayout(new BoxLayout(this, BoxLayout.X_AXIS));	

		tabbedPane.addTab(day, new JScrollPane(table));
		}
		}catch(CapacityException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
	}
	
	private void initContent()
	{
		setBounds(100, 100, 400, 300);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

		
		add(tabbedPane, BorderLayout.CENTER);

	}
	
	class Group_scheduleComparator implements Comparator<Group_schedule> {
	    public int compare(Group_schedule gs1, Group_schedule gs2) {
	        return gs1.getRoom().getOrder() - gs2.getRoom().getOrder();
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
	
	private List<Group_schedule> fillGroupObject(GroupDAO groupDAO, List<Group_schedule> gs)
	{
		for(int i = 0; i < gs.size(); i++)
		{
			gs.get(i).setGroupObject(groupDAO.getGroup(gs.get(i).getGroup()));
		}
		return gs;
	}
	
	private List<Group_schedule> fillRoom(int schedule_id) throws CapacityException
	{
		GroupDAO groupDAO = new GroupDAO(db.connection);
		Group_scheduleDAO gsDAO = new Group_scheduleDAO(db.connection);
		//List<Group_schedule> gs = gsDAO.getGroup_scheduleListByScheduleId(schedule_id);
		List<Group_schedule> gsIsFixed = gsDAO.getGroup_scheduleListByScheduleIdRoomFixed(schedule_id, true);
		List<Group_schedule> gs = gsDAO.getGroup_scheduleListByScheduleIdRoomFixed(schedule_id, false);
		fillGroupObject(groupDAO, gs);
		fillGroupObject(groupDAO, gsIsFixed);
		Collections.sort(gs);
		
		RoomDAO roomDAO = new RoomDAO(db.connection);
		List<Room> rooms = roomDAO.getNotFixedRoomList(schedule_id, 0);
		//List<Room> rooms = roomDAO.getRoomList();
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
		gs.addAll(gsIsFixed);
		return gs;
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
}
