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
import schedule.Teacher;
import schedule.TeacherDAO;
import schedule.TeacherTableModel;

public class TeacherGrid extends JPanel implements ToolBarInteface, ResultListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4421827934709464229L;
	private JTable table;
	private JToolBar toolBar;
	private List<Teacher> teachersList;
	private TeacherTableModel model;
	private ToolBarInteface listener;
	private DbHelper db;
	private TeacherDAO teacherDAO;

	/**
	 * Create the panel.
	 */
	public TeacherGrid(ToolBarInteface listener) {

		setLayout(new  BorderLayout());
		  this.listener = listener;
		  teachersList = null;
		  
		try {
			db = new DbHelper();
			teacherDAO = new TeacherDAO(db.connection);
			teachersList = teacherDAO.getTeachersList();
			model = new TeacherTableModel(teachersList);
			
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
				        	Teacher t = teachersList.get(table.getSelectedRow());
				        	getDialog(t);
				        	System.out.println(row);
				        }
			    	 }else if(evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1)
			    	 {
			    		 setToolBar();
					     TeacherGrid.this.listener.pushToolbar(toolBar);
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
	
	private void getDialog(Teacher t)
	{
//		RoomDialog rd;
//		try {
//			rd = new TeacherDialog((Window)TeacherGrid.this.getRootPane().getParent(),"Edit Teacher", Dialog.ModalityType.DOCUMENT_MODAL, t, this);
//			rd.setVisible(true);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void setToolBar()
	{
		JToolBar toolBar = new JToolBar();
		JButton addButton = new JButton();
		addButton.setIcon(StaticRes.ADD32_ICON);
		addButton.setToolTipText("Add teacher");
		addButton.setFocusable(false);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getDialog(new Teacher());
			}
		});
		toolBar.add(addButton);
		
		JButton editButton = new JButton();
		editButton.setIcon(StaticRes.EDIT32_ICON);
		editButton.setToolTipText("Edit teacher");
		editButton.setFocusable(false);
		editButton.setEnabled(false);

		if(table.getSelectedRow() > -1)
		{
			editButton.setEnabled(true);
			editButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Teacher teacher = (Teacher)table.getValueAt(table.getSelectedRow(), -1);
					getDialog(teacher);
				}
			});
		}
		toolBar.add(editButton);
		
		JButton deleteButton = new JButton();
		deleteButton.setIcon(StaticRes.DELETE32_ICON);
		deleteButton.setToolTipText("Delete teacher");
		deleteButton.setFocusable(false);
		deleteButton.setEnabled(false);
		if(table.getSelectedRow() > -1)
		{
			deleteButton.setEnabled(true);
			deleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String message = " Really delete ? ";
	                String title = "Delete";
	                int reply = JOptionPane.showConfirmDialog(TeacherGrid.this.getParent(), message, title, JOptionPane.YES_NO_OPTION);
	                if (reply == JOptionPane.YES_OPTION)
	                {
	                    try
	                    {
	                    	Teacher teacher = (Teacher)table.getValueAt(table.getSelectedRow(), -1);
	                    	teacherDAO.deleteTeacher(teacher.getId());
	                    	((TeacherTableModel)table.getModel()).removeObjectAt(table.getSelectedRow());
	                    	table.repaint();
	                    	table.revalidate();
	                    	table.clearSelection();
	                    	setToolBar();
	                    	listener.pushToolbar(TeacherGrid.this.toolBar);
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
		if(((Teacher)o).getStatus() == Teacher.STATUS_NEW)
		{
			((Teacher)o).setStatus(Teacher.STATUS_OLD);
			((TeacherTableModel)table.getModel()).addObject((Teacher)o);
			((TeacherTableModel)table.getModel()).fireTableDataChanged();
		}
		table.repaint();
		table.revalidate();
	}

}
