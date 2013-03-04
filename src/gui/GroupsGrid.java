package gui;

import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import schedule.DbHelper;
import schedule.Group;
import schedule.GroupDAO;
import schedule.GroupTableModel;
import schedule.ResultListener;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class GroupsGrid extends JPanel implements ToolBarInteface, ResultListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1370998440094794537L;
	private JTable table;
	private JToolBar toolBar;
	private List<Group> groupList;
	private GroupTableModel model;

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
			model = new GroupTableModel(groupList);
			
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
			table.getColumnModel().getColumn(1).setPreferredWidth(50);
			table.getColumnModel().getColumn(2).setPreferredWidth(50);
			table.getColumnModel().getColumn(3).setMaxWidth(70);
			table.getColumnModel().getColumn(3).setPreferredWidth(55);
			//table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
			table.setRowHeight(20);
			table.setDefaultRenderer(Object.class, tableRenderer);
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
			gd = new GroupsDialog((Window)GroupsGrid.this.getRootPane().getParent(),
					"Edit Group", Dialog.ModalityType.DOCUMENT_MODAL, group, GroupsGrid.this);
			gd.setVisible(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void setToolBar()
	{
		JToolBar toolBar = new JToolBar();
		JButton btnNewButton = new JButton();
		btnNewButton.setIcon(StaticRes.ADD_ICON);
		btnNewButton.setToolTipText("Add Group");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getDialog(new Group());
			}
		});
		toolBar.add(btnNewButton);
		
		this.toolBar = toolBar;
	}
	
	public JToolBar getToolbar()
	{
		return this.toolBar;
	}

	@Override
	public void returnObject(Object object) {
		// TODO Auto-generated method stub
		for(int i=0; i< model.getRowCount(); i++)
		{
			if(model.getValueAt(i, -1) == (Group)object)
			{
				model.setObjectAt(((Group)object), i, -1);
				model.fireTableDataChanged();
				table.repaint();
			}
		}
		
	}

	@Override
	public void pushToolbar(JToolBar toolBar) {
		// TODO Auto-generated method stub
		
	}

}
