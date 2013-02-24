package gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Window;

import javax.swing.JButton;
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
import java.util.List;

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
		//������ �������� ��������
		  String[] columnNames = {"Name", "Level", "Schedule"};
		  
		  DbHelper db;
		  groupList = null;
		  
		try {
			db = new DbHelper();
			GroupDAO groupDAO = new GroupDAO(db.connection);
			groupList = groupDAO.getGroupList(1);
			Object[] [] dataT = new Object[groupList.size()][3];
			for(int i=0; i<groupList.size(); i++)
			{
				Group g = groupList.get(i);
				dataT[i][0] = g.getName();
				dataT[i][1] = g.getLevel();
				dataT[i][2] = g.getSchedule();
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
		JButton btnNewButton = new JButton("Add Group");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GroupsDialog gd;
				try {
					gd = new GroupsDialog((Window)GroupsGrid.this.getRootPane().getParent(), "", Dialog.ModalityType.DOCUMENT_MODAL, groupList.get(3));
					gd.setVisible(true);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
