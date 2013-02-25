package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultDesktopManager;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;

import schedule.DbHelper;
import schedule.Group;
import schedule.GroupDAO;
import schedule.Level;
import schedule.LevelDAO;
import schedule.Teacher;
import schedule.TeacherDAO;

public class GroupsDialog extends JDialog implements ActionListener{

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
	private JLabel lblName;
	private JLabel lblLevel;
	private JLabel lblCapacity;
	private JLabel lblStudentAge;
	private JLabel lblTeacher;
	private JLabel lblSchedule;

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
		initContent();
	}
	
	/**
	 * Create the dialog.
	 * @throws ClassNotFoundException 
	 */
	public GroupsDialog(Window owner, String title, ModalityType modalityType, Group group) throws ClassNotFoundException {
		super(owner, title, modalityType);
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
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(10, 11, 86, 20);
		contentPanel.add(lblName);
		
		lblLevel = new JLabel("Level:");
		lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevel.setBounds(10, 40, 86, 20);
		contentPanel.add(lblLevel);
		{
			lblCapacity = new JLabel("Capacity:");
			lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCapacity.setBounds(10, 71, 86, 20);
			contentPanel.add(lblCapacity);
		}
		{
			lblStudentAge = new JLabel("Student Age:");
			lblStudentAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblStudentAge.setBounds(10, 102, 86, 20);
			contentPanel.add(lblStudentAge);
		}
		{
			lblTeacher = new JLabel("Teacher:");
			lblTeacher.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblTeacher.setBounds(10, 133, 86, 20);
			contentPanel.add(lblTeacher);
		}
		{
			lblSchedule = new JLabel("Schedule:");
			lblSchedule.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSchedule.setBounds(10, 164, 86, 20);
			contentPanel.add(lblSchedule);
		}
		
		txtName = new JTextField();
		txtName.setBounds(106, 13, 150, 20);
		txtName.setText(this.name);
		contentPanel.add(txtName);
		txtName.setColumns(10);
		{
			txtCapacity = new JTextField();
			txtCapacity.setColumns(10);
			txtCapacity.setText(String.valueOf(this.capacity));
			txtCapacity.setBounds(106, 73, 150, 20);
			contentPanel.add(txtCapacity);
		}

			LevelDAO lDAO = new LevelDAO(db.connection);
			cbLevel = new JComboBox();
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
			cbLevel.setBounds(106, 42, 150, 20);
			contentPanel.add(cbLevel);
		
		
		
		cbStudentAge = new JComboBox();
		cbStudentAge.setBounds(106, 104, 150, 20);
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
		cbTeacher.setBounds(106, 135, 150, 20);
		contentPanel.add(cbTeacher);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("OK Clicked!");
						fillGroup();
						if(saveGroup())
						{
							dispose();
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
			groupDAO.updateGroup(this.group);
		}
		return check;
	}
	
	private void fillGroup()
	{
		this.group.setName(txtName.getText());
		this.group.setCapacity(Integer.parseInt(txtCapacity.getText()));
		this.group.setTeacher((Teacher)cbTeacher.getSelectedItem());
		this.group.setLevel((Level)cbLevel.getSelectedItem());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
	}
		
}
