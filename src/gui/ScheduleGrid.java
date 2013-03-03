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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import schedule.DbHelper;
import schedule.Room;
import schedule.RoomDAO;
import schedule.Schedule;
import schedule.ScheduleDAO;
import schedule.ScheduleTableModel;
import schedule.Teacher;
import schedule.TeacherDAO;

public class ScheduleGrid extends JPanel implements ToolBarInteface{

	private JTable table;
	private JToolBar toolBar;
	private List<Schedule> scheduleList;
	private DbHelper db;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private ToolBarInteface listener;


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
		        listener.pushToolbar(toolBar);
		      }
		    };
		tabbedPane.addChangeListener(changeListener);
		

	}
	
	private void addContent()
	{
		
		ScheduleDAO sdao = new ScheduleDAO(db.connection);
		
		for(String day : StaticRes.WEEK_DAY_LIST)
		{
		List<Schedule> list = sdao.getScheduleByDayList(day);
				
		TableModel model = new ScheduleTableModel(list);
		
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
//			        	Schedule schedule = (Schedule)((JTable) evt.getSource()).getValueAt(row, -1);
//			        	Teacher teacher = (Teacher)cbTeacher.getSelectedItem();
//			        	submit(schedule, teacher);
			        }
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
					"Edit Schedule", Dialog.ModalityType.DOCUMENT_MODAL, schedule, selected_tab);
			sd.setVisible(true);
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
		addButton.setToolTipText("Add schedule");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getDialog(new Schedule(), ScheduleGrid.this.getTabTitle());
			}
		});
		toolBar.add(addButton);
		
		this.toolBar = toolBar;
		
	}
	
	public String getTabTitle()
	{
		return tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
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

}
