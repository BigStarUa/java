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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


import javax.swing.JComboBox;

import schedule.DbHelper;
import schedule.Group;
import schedule.GroupDAO;
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
	private int teacher_id;
	private int capacity;
	private List<Schedule> schedule_list = new ArrayList<Schedule>();
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
			this.teacher_id = this.group.getTeacher().getId();
			this.capacity = this.group.getCapacity();
			this.schedule_list = this.group.getSchedule();
			
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
		setBounds(100, 100, 450, 306);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(21, 22, 86, 20);
		contentPanel.add(lblName);
		
		lblLevel = new JLabel("Level:");
		lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevel.setBounds(21, 51, 86, 20);
		contentPanel.add(lblLevel);
		{
			lblCapacity = new JLabel("Capacity:");
			lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCapacity.setBounds(21, 82, 86, 20);
			contentPanel.add(lblCapacity);
		}
		{
			lblStudentAge = new JLabel("Student Age:");
			lblStudentAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblStudentAge.setBounds(21, 113, 86, 20);
			contentPanel.add(lblStudentAge);
		}
		{
			lblTeacher = new JLabel("Teacher:");
			lblTeacher.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblTeacher.setBounds(21, 144, 86, 20);
			contentPanel.add(lblTeacher);
		}
		{
			panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Schedule", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_1.setBounds(277, 6, 155, 158);
			contentPanel.add(panel_1);
			panel_1.setLayout(null);
			
			JButton btnAdd = new JButton("Add");
			btnAdd.setFocusTraversalKeysEnabled(false);
			btnAdd.setFocusable(false);
			btnAdd.setFocusPainted(false);
			btnAdd.setPreferredSize(new Dimension(30, 23));
			btnAdd.setMargin(new Insets(2, 2, 2, 2));
			btnAdd.setMaximumSize(new Dimension(40, 23));
			btnAdd.setMinimumSize(new Dimension(30, 23));
			btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnAdd.setBounds(10, 13, 67, 23);
			btnAdd.setIcon(StaticRes.ADD16_ICON);
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	ScheduleSelectDialog ssd;
					try {
						ssd = new ScheduleSelectDialog(null, "Schedule for " + group.getName(), ModalityType.DOCUMENT_MODAL, GroupsDialog.this);
						ssd.setVisible(true);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
			panel_1.add(btnAdd);
			
			DefaultListModel listModel = new DefaultListModel();

			for(Schedule obj : this.schedule_list)
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
					
	                Schedule schedule = (Schedule) o;
	                label.setText(schedule.getName());
	                
	                return label;
				}
			});
			list.setBounds(10, 40, 135, 107);
			panel_1.add(list);
			
			JButton btnRemove = new JButton("Remove");
			btnRemove.setBounds(78, 13, 67, 23);
			btnRemove.setFocusTraversalKeysEnabled(false);
			btnRemove.setFocusable(false);
			btnRemove.setFocusPainted(false);
			btnRemove.setPreferredSize(new Dimension(30, 23));
			btnRemove.setMargin(new Insets(2, 2, 2, 2));
			btnRemove.setMaximumSize(new Dimension(40, 23));
			btnRemove.setMinimumSize(new Dimension(30, 23));
			btnRemove.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnRemove.setIcon(StaticRes.DELETE16_ICON);
			btnRemove.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	int index = list.getSelectedIndex();
	            	Schedule schedule = (Schedule)list.getSelectedValue();
	            	if(index >= 0)
	            	{
	            		((DefaultListModel) list.getModel()).removeElement(schedule);
	            	}
	            }
	        });
			panel_1.add(btnRemove);
		}
		
		txtName = new JTextField();
		txtName.setBounds(117, 24, 150, 20);
		txtName.setText(this.name);
		contentPanel.add(txtName);
		txtName.setColumns(10);
		{
			txtCapacity = new JTextField();
			txtCapacity.setColumns(10);
			txtCapacity.setText(String.valueOf(this.capacity));
			txtCapacity.setBounds(117, 84, 150, 20);
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
			cbLevel.setBounds(117, 53, 150, 20);
			contentPanel.add(cbLevel);
		
		
		
		cbStudentAge = new JComboBox();
		cbStudentAge.setBounds(117, 115, 150, 20);
		contentPanel.add(cbStudentAge);
		
		TeacherDAO tDAO = new TeacherDAO(db.connection);
		cbTeacher = new JComboBox();
		if(this.teacher_id < 1)
		{
			Teacher t = new Teacher();
			t.setName("Select teacher");
			t.setId(0);
			cbTeacher.addItem(t);
		}
		List<Teacher> teacherList = tDAO.getTeachersList();
		for(Teacher teacher : teacherList)
		{
			cbTeacher.addItem( teacher );
			if(teacher.getId() == this.teacher_id)
			{
				cbTeacher.setSelectedItem( teacher );
			}
		}
		cbTeacher.setRenderer(new ComboBoxRenderer());
		cbTeacher.setBounds(117, 146, 150, 20);
		contentPanel.add(cbTeacher);
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
						fillGroup();
						if(saveGroup())
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
	
	
	private boolean saveGroup()
	{
		boolean check = true;
		if(this.group.getName().length() < 1)
		{
			lblName.setForeground(Color.RED);
			check = false;
		}else lblName.setForeground(Color.BLACK);
		if(this.group.getCapacity() < 1)
		{
			lblCapacity.setForeground(Color.RED);
			check = false;
		}else lblCapacity.setForeground(Color.BLACK);
		if(this.group.getLevel().getId() < 1)
		{
			lblLevel.setForeground(Color.RED);
			check = false;
		}else lblLevel.setForeground(Color.BLACK);
		if(this.group.getTeacher().getId() < 1)
		{
			lblTeacher.setForeground(Color.RED);
			check = false;
		}else lblTeacher.setForeground(Color.BLACK);
		
		if(check)
		{
			GroupDAO groupDAO = new GroupDAO(db.connection);
			groupDAO.updateGroup(this.group, this.schedule_list);
		}
		return check;
	}
	
	private void fillGroup()
	{
		this.group.setName(txtName.getText());
		this.group.setCapacity(Integer.parseInt(txtCapacity.getText()));
		this.group.setTeacher((Teacher)cbTeacher.getSelectedItem());
		this.group.setLevel((Level)cbLevel.getSelectedItem());
		
		List<Schedule> newList = new ArrayList<Schedule>();
		for(int i=0; i < list.getModel().getSize(); i++)
		{
			Schedule schedule = (Schedule)((DefaultListModel) list.getModel()).getElementAt(i);
			newList.add(schedule);
			if(this.schedule_list.contains(schedule)){
				this.schedule_list.remove(schedule);
			}else{
				schedule.setStatus(1);
				this.schedule_list.add(schedule);
			}
		}

		
		this.group.setSchedule(newList);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void returnSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		((DefaultListModel) list.getModel()).addElement(schedule);
		System.out.println(schedule.getName());
	}
}
