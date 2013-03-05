package gui;

import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import schedule.DbHelper;
import schedule.Group;
import schedule.GroupDAO;
import schedule.Group_schedule;
import schedule.Group_scheduleDAO;
import schedule.ResultListener;
import schedule.Room;
import schedule.RoomDAO;
import schedule.Schedule;
import schedule.ScheduleDAO;
import schedule.ScheduleTableModel;
import schedule.Teacher;
import schedule.TeacherDAO;

public class ScheduleGrid extends JPanel implements ToolBarInteface, ResultListener{

	private JTable table;
	private JToolBar toolBar;
	private List<Schedule> scheduleList;
	private DbHelper db;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private ToolBarInteface listener;
	private ScheduleDAO sdao;
	private JTable tbl;


	public ScheduleGrid(ToolBarInteface listener) {

		this.listener = listener;
		setLayout(new  BorderLayout());
		//Массив названий столбцов
		  String[] columnNames = {"Name", "Week Day", "Value"};
		  try {
			db = new DbHelper();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  scheduleList = null;
		  panel = new JPanel();
		  tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		  addContent();
		  initContent();
		  //panel.add(tabbedPane, BorderLayout.CENTER);
		  add(tabbedPane);
		  setToolBar();
		  
	}
	
	private void initContent()
	{
		
		setBounds(100, 100, 400, 300);
		panel.setLayout(new BorderLayout());
		//panel.add(tabbedPane, BorderLayout.CENTER);
		
		ChangeListener changeListener = new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
		        int index = sourceTabbedPane.getSelectedIndex();
		        Component comp = sourceTabbedPane;
		        setToolBar();
		        listener.pushToolbar(toolBar);
		      }
		    };
		tabbedPane.addChangeListener(changeListener);
		

	}
	
	private void addContent()
	{
		
		sdao = new ScheduleDAO(db.connection);
		
		for(String day : StaticRes.WEEK_DAY_LIST)
		{
		List<Schedule> list = sdao.getScheduleByDayList(day);
				
		TableModel model = new ScheduleTableModel(list);
		
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
		table.setDefaultRenderer(Object.class, tableRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(17);
		//table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		table.setRowHeight(20);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			
			
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	
		    	 if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1){
		    		 
			        int row = ((JTable) evt.getSource()).rowAtPoint(evt.getPoint());;
			        if (row >= 0 ) {
			        	Schedule schedule = (Schedule)((JTable) evt.getSource()).getValueAt(row, -1);
			        	//Teacher teacher = (Teacher)cbTeacher.getSelectedItem();
			        	getDialog(schedule, schedule.getWeekDay());
			        }
		    	 }else if(evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1)
		    	 {
		    		 setToolBar();
				     listener.pushToolbar(toolBar);
		    	 }
		    }
		});
		tabbedPane.addTab(day,new JScrollPane(table));
		}
		
	}
	
	private void getDialog(Schedule schedule, String selected_tab)
	{
		ScheduleDialog sd;
		try {
			sd = new ScheduleDialog((Window)ScheduleGrid.this.getRootPane().getParent(),
					"Edit Schedule", Dialog.ModalityType.DOCUMENT_MODAL, schedule, selected_tab, ScheduleGrid.this);
			sd.setVisible(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setToolBar()
	{
		tbl = getTableByTabIndex(tabbedPane.getSelectedIndex());
		
		JToolBar toolBar = new JToolBar();
		JButton addButton = new JButton();
		addButton.setIcon(StaticRes.ADD32_ICON);
		addButton.setToolTipText("Add schedule");
		addButton.setFocusable(false);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getDialog(new Schedule(), ScheduleGrid.this.getTabTitle());
			}
		});
		toolBar.add(addButton);
		
		JButton editButton = new JButton();
		editButton.setIcon(StaticRes.EDIT32_ICON);
		editButton.setToolTipText("Edit schedule");
		editButton.setFocusable(false);
		editButton.setEnabled(false);

		if(tbl.getSelectedRow() > -1)
		{
			editButton.setEnabled(true);
			final Schedule schedule = (Schedule)tbl.getValueAt(tbl.getSelectedRow(), -1);
			editButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getDialog(schedule, ScheduleGrid.this.getTabTitle());
				}
			});
		}
		toolBar.add(editButton);
		
		JButton deleteButton = new JButton();
		deleteButton.setIcon(StaticRes.DELETE32_ICON);
		deleteButton.setToolTipText("Delete group");
		deleteButton.setFocusable(false);
		deleteButton.setEnabled(false);
		if(tbl.getSelectedRow() > -1)
		{
			deleteButton.setEnabled(true);
			final Schedule schedule = (Schedule)tbl.getValueAt(tbl.getSelectedRow(), -1);
			final int index = tbl.getSelectedRow();
			deleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String message = " Really delete ? ";
	                String title = "Delete";
	                int reply = JOptionPane.showConfirmDialog(ScheduleGrid.this.getParent(), message, title, JOptionPane.YES_NO_OPTION);
	                if (reply == JOptionPane.YES_OPTION)
	                {
	                    try
	                    {
	                    	Group_scheduleDAO group_scheduleDAO = new Group_scheduleDAO(db.connection);
	                    	List<Group_schedule> list = group_scheduleDAO.getGroup_scheduleListByScheduleId(schedule.getId());
	                    	if(list.size() < 1){
		                    	sdao.deleteSchedule(schedule.getId());
		                    	((ScheduleTableModel)tbl.getModel()).removeObjectAt(index);
			                    tbl.repaint();
			                    tbl.revalidate();
			                    tbl.clearSelection();
			                    setToolBar();
		                    	listener.pushToolbar(ScheduleGrid.this.toolBar);
	                    	}
	                    	else
	                    	{
	                    		message = "Schedule: " + schedule.getName() + " still present in group(s):\n";
	                    		GroupDAO groupDAO = new GroupDAO(db.connection);
	                    		for(Group_schedule gs : list)
	                    		{
	                    			Group group = groupDAO.getGroup(gs.getGroup());
	                    			message += "Group: " + group.getName() + "\n";
	                    		}
	                    		message += "Please delete it from group(s) first";
	                    		JOptionPane.showMessageDialog(ScheduleGrid.this.getParent(), message);
	                    	}
		                    
	                    }
	                    catch(NullPointerException e)
	                    {
	                    	
	                    }
	                    
	                }
				}
			});
		}
		toolBar.add(deleteButton);
		
		this.toolBar = toolBar;
		
	}
	
	private String getTabTitle()
	{
		return tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
	}
	
	private JTable getTableByTabTitle(String title)
	{
		JTable table = null;
		for(int i=0; i < tabbedPane.getTabCount(); i++)
		{
			if(tabbedPane.getTitleAt(i) == title)
			{
				JScrollPane js = (JScrollPane)tabbedPane.getComponentAt(i);
				JTable t = (JTable)js.getViewport().getComponent(0);
				return t;
			}
		}
		return table;
	}
	
	private JTable getTableByTabIndex(int index)
	{
		JScrollPane js = (JScrollPane)tabbedPane.getComponentAt(index);
		JTable t = (JTable)js.getViewport().getComponent(0);
		return t;
	}
	
	@Override
	public JToolBar getToolbar() {
		// TODO Auto-generated method stub
		return this.toolBar;
	}

	@Override
	public void pushToolbar(JToolBar toolBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void returnObject(Object o) {
		// TODO Auto-generated method stub
		JTable tbl = getTableByTabTitle(((Schedule)o).getWeekDay());
		if(((Schedule)o).getStatus() == Schedule.STATUS_NEW)
		{
			((Schedule)o).setStatus(Schedule.STATUS_OLD);
			((ScheduleTableModel)tbl.getModel()).addObject((Schedule)o);
			((ScheduleTableModel)tbl.getModel()).fireTableDataChanged();
		}
		tbl.repaint();
		tbl.revalidate();
	}

}
