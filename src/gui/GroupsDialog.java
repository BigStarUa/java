package gui;

import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


import javax.swing.JComboBox;

import schedule.DbHelper;
import schedule.Group;
import schedule.GroupDAO;
import schedule.Group_schedule;
import schedule.Group_scheduleDAO;
import schedule.Level;
import schedule.LevelDAO;
import schedule.ResultListener;
import schedule.Schedule;
import schedule.ScheduleDAO;
import schedule.Teacher;
import schedule.TeacherDAO;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

public class GroupsDialog extends JDialog implements ActionListener, ScheduleResultListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5257679186923943723L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JTextField txtCapacity;
	private JComboBox cbLevel;
	private JComboBox cbStudentAge;
	private JComboBox cbTeacher;
	private DbHelper db;
	private Group group;
	private String name;
	private int level_id;
	private String teacher;
	private int capacity;
	private int value;
	private List<String> teacher_list = new ArrayList<String>();
	private List<Group_schedule> schedule_list = new ArrayList<Group_schedule>();
	private JLabel lblName;
	private JLabel lblLevel;
	private JLabel lblCapacity;
	private JLabel lblStudentAge;
	private JLabel lblTeacher;
	private JPanel panel;
	private JLabel lblGroup;
	private JPanel panel_1;
	private JList list;
	private ResultListener result;
	private JLabel lblDispTeacher;
	private JButton btnEdit;
	private JButton btnRemove;
	private JButton btnAdd;
	private JTextField txtValue;
	private GroupDAO groupDAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GroupsDialog dialog = new GroupsDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public GroupsDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initContent();
	}
	
	/**
	 * Create the dialog.
	 * @throws ClassNotFoundException 
	 */
	public GroupsDialog(Window owner, String title, ModalityType modalityType, Group group, ResultListener result) throws ClassNotFoundException {
		super(owner, title, modalityType);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.result = result;
		db = new DbHelper();
		this.group = group;
		populateForm();
		initContent();
		
		addContent();
	}
	
	private void populateForm()
	{
		if(this.group.getId() > 0)
		{
			//txtName.setText(this.group.getName());
			this.name = this.group.getName();
			this.level_id = this.group.getLevel().getId();
			this.teacher = this.group.getTeacher();
			this.capacity = this.group.getCapacity();
			this.schedule_list = this.group.getSchedule();
			this.value = this.group.getValue();
//			ScheduleDAO sDAO = new ScheduleDAO(db.connection);
//			for(Integer id : this.group.getSchedule())
//			{
//				Schedule s = sDAO.getSchedule(id);
//				this.schedule_list.add(s);
//			}
		}
		//cbLevel
	}
	
	private void addContent()
	{
//		contentPanel.add(txtName);
//		contentPanel.add(txtCapacity);
//		contentPanel.add(cbLevel);
//		contentPanel.add(cbStudentAge);
//		contentPanel.add(cbTeacher);
	}

	/**
	 * Create the dialog.
	 */
	private void initContent() {
		setBounds(100, 100, 450, 310);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(10, 6, 86, 20);
		contentPanel.add(lblName);
		
		lblLevel = new JLabel("Level:");
		lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevel.setBounds(10, 35, 86, 20);
		contentPanel.add(lblLevel);
		{
			lblCapacity = new JLabel("Capacity:");
			lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCapacity.setBounds(10, 66, 86, 20);
			contentPanel.add(lblCapacity);
		}
		{
			lblStudentAge = new JLabel("Student Age:");
			lblStudentAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblStudentAge.setBounds(10, 97, 86, 20);
			contentPanel.add(lblStudentAge);
		}
		{
			lblTeacher = new JLabel("Teacher(s):");
			lblTeacher.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblTeacher.setBounds(10, 128, 86, 20);
			contentPanel.add(lblTeacher);
		}
		{
			panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Schedule", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_1.setBounds(246, 6, 186, 175);
			contentPanel.add(panel_1);
			panel_1.setLayout(null);

			DefaultListModel listModel = new DefaultListModel();

			for(Group_schedule obj : this.schedule_list)
			{
				listModel.addElement(obj);
			}
			list = new JList(listModel);
			list.setVisibleRowCount(5);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setCellRenderer(new ListCellRenderer() {
				
				protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
				@Override
				public Component getListCellRendererComponent(JList list, Object o,
						int i, boolean selected, boolean cellHasFocus) {
					// TODO Auto-generated method stub
					
					//JLabel label = new JLabel();
					JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, o, i,
							selected, cellHasFocus);
					
					Group_schedule gschedule = (Group_schedule) o;
	                label.setText(gschedule.getName());
	                
	                return label;
				}
			});
			list.setBounds(10, 44, 166, 120);
			list.addMouseListener(new java.awt.event.MouseAdapter() {
			
		    @Override
		    public void mouseClicked(MouseEvent evt) {
		    	
		    	 if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1){
		    		 
			        int row = ((JList) evt.getSource()).getSelectedIndex();
			        if (row >= 0 ) {
			        	Group_schedule group_schedule = (Group_schedule)((JList) evt.getSource()).getSelectedValue();
			        	try {
							group_schedule.setGroup(group.getId());
							DefaultListModel listModel = (DefaultListModel)list.getModel();
							ScheduleSelectDialog ssd = new ScheduleSelectDialog(null, "Schedule for " + group.getName(), ModalityType.DOCUMENT_MODAL, GroupsDialog.this, group_schedule, listModel);
							ssd.setVisible(true);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
		    	 }else if(evt.getButton() == MouseEvent.BUTTON1){
		    		 btnRemove.setEnabled(true);
		    		 btnEdit.setEnabled(true);
		    	 }
		    }
		});
			
			panel_1.add(list);
			
			btnAdd = new JButton("Add");
			btnAdd.setFocusTraversalKeysEnabled(false);
			btnAdd.setFocusable(false);
			btnAdd.setFocusPainted(false);
			btnAdd.setPreferredSize(new Dimension(30, 23));
			btnAdd.setMargin(new Insets(2, 2, 2, 2));
			btnAdd.setMaximumSize(new Dimension(40, 23));
			btnAdd.setMinimumSize(new Dimension(30, 23));
			btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnAdd.setBounds(10, 15, 52, 23);
			btnAdd.setIcon(StaticRes.ADD16_ICON);
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	ScheduleSelectDialog ssd;
					try {
						Group_schedule group_schedule = new Group_schedule();
						group_schedule.setGroup(group.getId());
						DefaultListModel listModel = (DefaultListModel)list.getModel();
						ssd = new ScheduleSelectDialog(null, "Schedule for " + group.getName(), 
								ModalityType.DOCUMENT_MODAL, GroupsDialog.this, group_schedule, listModel);
						ssd.setVisible(true);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
			panel_1.add(btnAdd);
			
			btnRemove = new JButton("Del");
			btnRemove.setBounds(125, 15, 52, 23);
			btnRemove.setFocusTraversalKeysEnabled(false);
			btnRemove.setFocusable(false);
			btnRemove.setFocusPainted(false);
			btnRemove.setPreferredSize(new Dimension(30, 23));
			btnRemove.setMargin(new Insets(2, 2, 2, 2));
			btnRemove.setMaximumSize(new Dimension(40, 23));
			btnRemove.setMinimumSize(new Dimension(30, 23));
			btnRemove.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnRemove.setIcon(StaticRes.DELETE16_ICON);
			btnRemove.setEnabled(false);
			btnRemove.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	int index = list.getSelectedIndex();
	            	Group_schedule gschedule = (Group_schedule)list.getSelectedValue();
	            	if(index >= 0)
	            	{
	            		((DefaultListModel) list.getModel()).removeElement(gschedule);
	            		lblDispTeacher.setText(getTeacher(getTeacherFromList()));
	            	}
	            }
	        });
			panel_1.add(btnRemove);
			
			btnEdit = new JButton("Edit");
			btnEdit.setMargin(new Insets(2, 4, 2, 4));
			btnEdit.setIconTextGap(2);
			btnEdit.setFocusable(false);
			btnEdit.setFocusPainted(false);
			btnEdit.setIcon(StaticRes.EDIT16_ICON);
			btnEdit.setBounds(67, 15, 52, 23);
			btnEdit.setEnabled(false);
			btnEdit.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	int index = list.getSelectedIndex();
	            	if(index >= 0)
	            	{
	            		Group_schedule gschedule = (Group_schedule)list.getSelectedValue();
	            		DefaultListModel listModel = (DefaultListModel)list.getModel();
						try {
							ScheduleSelectDialog ssd = new ScheduleSelectDialog((Window)GroupsDialog.this.getRootPane().getParent(), "Schedule for " + group.getName(), ModalityType.DOCUMENT_MODAL, GroupsDialog.this, gschedule, listModel);
							ssd.setVisible(true);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
	            	}
	            }
	        });
			panel_1.add(btnEdit);
		}
		
		txtName = new JTextField();
		txtName.setBounds(106, 8, 130, 20);
		txtName.setText(this.name);
		contentPanel.add(txtName);
		txtName.setColumns(10);
		{
			txtCapacity = new JTextField();
			txtCapacity.setColumns(10);
			txtCapacity.setText(String.valueOf(this.capacity));
			txtCapacity.setBounds(106, 68, 130, 20);
			txtCapacity.addKeyListener(new KeyAdapter()
			{
			public void keyTyped(KeyEvent ke)
			{
				char c = ke.getKeyChar();
				if (!Character.isDigit(c))
				ke.consume(); // prevent event propagation
			}
			}); 
			contentPanel.add(txtCapacity);
		}

			LevelDAO lDAO = new LevelDAO(db.connection);
			cbLevel = new JComboBox();
			//cbLevel.setBorder(BorderFactory.createMatteBorder(0,0,1,1,Color.BLACK));
			if(this.level_id < 1)
			{
				Level l = new Level();
				l.setName("Select level");
				l.setId(0);
				cbLevel.addItem(l);
			}
			List<Level> levelList = lDAO.getLevelList();
			for(Level level : levelList)
			{
				cbLevel.addItem( level );
				if(level.getId() == this.level_id)
				{
					cbLevel.setSelectedItem( level );
				}
			}
			cbLevel.setRenderer(new ComboBoxRenderer());
			cbLevel.setBounds(106, 37, 130, 20);
			contentPanel.add(cbLevel);
		
		
		
		cbStudentAge = new JComboBox();
		cbStudentAge.setBounds(106, 99, 130, 20);
		contentPanel.add(cbStudentAge);
		
		lblDispTeacher = new JLabel(getTeacher(getTeacherFromList()));
		lblDispTeacher.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDispTeacher.setBounds(106, 128, 130, 20);
		contentPanel.add(lblDispTeacher);
		
		JLabel lblAddValue = new JLabel("Add value:");
		lblAddValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddValue.setBounds(10, 159, 108, 14);
		contentPanel.add(lblAddValue);
		
		txtValue = new JTextField();
		txtValue.setBounds(106, 158, 130, 20);
		txtValue.setText(String.valueOf(value));
		txtValue.addKeyListener(new KeyAdapter()
		{
		public void keyTyped(KeyEvent ke)
		{
			char c = ke.getKeyChar();
			if (!Character.isDigit(c))
			ke.consume(); // prevent event propagation
		}
		}); 
		contentPanel.add(txtValue);
		txtValue.setColumns(10);
		
		{
			panel = new JPanel();
			panel.setBorder(UIManager.getBorder("MenuBar.border"));
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			getContentPane().add(panel, BorderLayout.NORTH);
			{
				lblGroup = new JLabel("Group");
				lblGroup.setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblGroup.setIcon(StaticRes.GROUP48_ICON);
				panel.add(lblGroup);
			}
		}
						
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setIcon(StaticRes.OK_ICON);
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("OK Clicked!");
						//fillGroup();
						if(checkAndSaveGroup())
						{
							dispose();
							result.returnObject(GroupsDialog.this.group);
						}else{
							//AboutDialog dlg = new AboutDialog(null, "Error", "Error");
						}
						
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.setIcon(StaticRes.CANCEL_ICON);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Cancel Clicked!");
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
	
	private boolean checkAndSaveGroup()
	{
		boolean check = true;
		String error = "";
		if(txtName.getText().length() < 1)
		{
			lblName.setForeground(Color.RED);
			error += " - Name \n";
			check = false;
		}else lblName.setForeground(Color.BLACK);
		if(txtCapacity.getText().length() < 1)
		{
			lblCapacity.setForeground(Color.RED);
			error += " - Capacity \n";
			check = false;
		}else lblCapacity.setForeground(Color.BLACK);
		if(((Level)cbLevel.getSelectedItem()).getId() < 1)
		{
			lblLevel.setForeground(Color.RED);
			error += " - Level \n";
			check = false;
		}else lblLevel.setForeground(Color.BLACK);
		if(isRoomOccupiedColision() != null)
		{
			error += " - Room " + isRoomOccupiedColision().getRoom().getName() + " in (" + isRoomOccupiedColision().getSchedule().getName() + ") has already been occupied \n";
			check = false;
		}
		if(check)
		{
			
			fillGroup();
			groupDAO = new GroupDAO(db.connection);
			groupDAO.updateGroup(this.group, this.schedule_list);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Please select: \n" + error);
		}
		return check;
	}
	
	private void fillGroup()
	{
		this.group.setName(txtName.getText());
		this.group.setCapacity(Integer.parseInt(txtCapacity.getText()));
		//this.group.setTeacher((Teacher)cbTeacher.getSelectedItem());
		this.group.setTeacher(lblDispTeacher.getText());
		this.group.setLevel((Level)cbLevel.getSelectedItem());
		if(txtValue.getText().length() > 0){
			this.group.setValue(Integer.parseInt(txtValue.getText()));
		}else{
			this.group.setValue(0);
		}
		
		
		List<Group_schedule> newList = new ArrayList<Group_schedule>();
		for(int i=0; i < list.getModel().getSize(); i++)
		{
			
			Group_schedule gschedule = (Group_schedule)((DefaultListModel) list.getModel()).getElementAt(i);		
			newList.add(gschedule);
			if(this.schedule_list.contains(gschedule) && gschedule.getStatus() != Group_schedule.STATUS_CHANGED){
				this.schedule_list.remove(gschedule);
			}else{
				//gschedule.setStatus(Group_schedule.STATUS_NEW);
				this.schedule_list.add(gschedule);
			}
		}

		
		this.group.setSchedule(newList);
	}

	private Group_schedule isRoomOccupiedColision() {
		// TODO Auto-generated method stub
		Group_scheduleDAO gsDAO = new Group_scheduleDAO(db.connection);
		for(int i=0; i < list.getModel().getSize(); i++)
		{
			Group_schedule gschedule = (Group_schedule)((DefaultListModel) list.getModel()).getElementAt(i);
			if(gschedule.getIsFixed() && gschedule.getRoom() != null)
			{
				if(gsDAO.isRoomOccupied(gschedule.getSchedule().getId(), gschedule.getRoom().getId(), gschedule.getId()))
				{
					return gschedule;
				}
			}
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void returnGroup_schedule(Group_schedule group_schedule) {
		// TODO Auto-generated method stub
		if(group_schedule.getStatus() == Group_schedule.STATUS_NEW)
		{
			((DefaultListModel) list.getModel()).addElement(group_schedule);
		}
		lblDispTeacher.setText(getTeacher(getTeacherFromList()));
	}
	
	private List<String> getTeacherFromList()
	{
		List<String> teachers_list = new ArrayList<String>();
		int size = list.getModel().getSize();
			for(int i=0; i < size; i++)
			{
				String teacher_name = ((Group_schedule)list.getModel().getElementAt(i)).getTeacher().getName();
				if(!teachers_list.contains(teacher_name))
				{
					teachers_list.add(teacher_name);
				}
			}
			//this.teacher_list = teachers_list;
		return teachers_list;
	}
	
	private String getTeacher(List<String> teachers)
	{
		String s = "";
		for(String t : teachers){
			s += t + ", ";
		}
		if(s.length() > 0)
		{
			s = s.substring(0, s.length()-2);
		}
		else
		{
			s = "None";
		}
		return s;
	}
}
