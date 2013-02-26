package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import schedule.DbHelper;
import schedule.Group;
import schedule.GroupDAO;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class GroupsGrid extends JPanel implements ToolBarInteface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1370998440094794537L;
	private JTable table;
	private JPanel toolBar;
	private List<Group> groupList;

	/**
	 * Create the panel.
	 */
	public GroupsGrid() {
		//FlowLayout flowLayout = (FlowLayout) getLayout();
		setLayout(new  BorderLayout());
		//Массив названий столбцов
		  String[] columnNames = {"Name", "Level", "Teacher", "Quantity", "Schedule"};
		  
		  DbHelper db;
		  groupList = null;
		  
		try {
			db = new DbHelper();
			GroupDAO groupDAO = new GroupDAO(db.connection);
			groupList = groupDAO.getGroupList(1);
			Object[] [] dataT = new Object[groupList.size()][5];
			for(int i=0; i<groupList.size(); i++)
			{
				JLabel SkillLabel = new JLabel();
				
				Group g = groupList.get(i);
				dataT[i][0] = " " + g.getName();
				dataT[i][1] = " " + g.getLevel().getName();
				dataT[i][2] = " " + g.getTeacher().getName();
				dataT[i][3] = g.getCapacity();
				dataT[i][4] = " " + g.getSchedule();
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
			table.getColumnModel().getColumn(1).setPreferredWidth(50);
			table.getColumnModel().getColumn(2).setPreferredWidth(50);
			table.getColumnModel().getColumn(3).setPreferredWidth(17);
			table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
			table.setRowHeight(20);
			table.addMouseListener(new java.awt.event.MouseAdapter() {
				
				
			    @Override
			    public void mouseClicked(java.awt.event.MouseEvent evt) {
			    	 if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1){
				        int row = table.rowAtPoint(evt.getPoint());
				        if (row >= 0 ) {
				        	Group g = groupList.get(table.getSelectedRow());
				        	getDialog(g);
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
	
	private void getDialog(Group group)
	{
		GroupsDialog gd;
		try {
			gd = new GroupsDialog((Window)GroupsGrid.this.getRootPane().getParent(), "Edit Group", Dialog.ModalityType.DOCUMENT_MODAL, group);
			gd.setVisible(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void setToolBar()
	{
		JPanel toolBar = new JPanel();
		JButton btnNewButton = new JButton("Add Group");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getDialog(new Group());
			}
		});
		toolBar.add(btnNewButton);
		
		this.toolBar = toolBar;
	}
	
	public JPanel getToolbar()
	{
		return this.toolBar;
	}

}
