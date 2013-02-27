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
import schedule.Room;
import schedule.RoomDAO;
import schedule.Schedule;
import schedule.ScheduleDAO;

public class ScheduleGrid extends JPanel implements ToolBarInteface{

	private JTable table;
	private JToolBar toolBar;
	private List<Schedule> scheduleList;

	public ScheduleGrid() {

		setLayout(new  BorderLayout());
		//Массив названий столбцов
		  String[] columnNames = {"Name", "Week Day", "Value"};
		  
		  DbHelper db;
		  scheduleList = null;
		  
		try {
			db = new DbHelper();
			ScheduleDAO scheduleDAO = new ScheduleDAO(db.connection);
			scheduleList = scheduleDAO.getScheduleList();
			Object[] [] dataT = new Object[scheduleList.size()][3];
			for(int i=0; i<scheduleList.size(); i++)
			{
				Schedule s = scheduleList.get(i);
				dataT[i][0] = " " + s.getName();
				dataT[i][1] = " " + s.getWeekDay();
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
				        	Schedule r = scheduleList.get(table.getSelectedRow());
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
	
	private void getDialog(Schedule schedule)
	{
		ScheduleDialog sd;
		try {
			sd = new ScheduleDialog((Window)ScheduleGrid.this.getRootPane().getParent(), "Edit Schedule", Dialog.ModalityType.DOCUMENT_MODAL, schedule);
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
				getDialog(new Schedule());
			}
		});
		toolBar.add(addButton);
		
		this.toolBar = toolBar;
	}
	
	@Override
	public JToolBar getToolbar() {
		// TODO Auto-generated method stub
		return this.toolBar;
	}

}
