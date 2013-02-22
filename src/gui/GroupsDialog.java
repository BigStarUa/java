package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;

import schedule.DbHelper;
import schedule.Group;
import schedule.Level;
import schedule.LevelDAO;
import schedule.Teacher;
import schedule.TeacherDAO;

public class GroupsDialog extends JDialog {

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
		if(this.group != null)
		{
			//txtName.setText(this.group.getName());
			this.name = this.group.getName();
			this.level_id = this.group.getLevel();
			this.teacher_id = this.group.getTeacher().getId();
		}
		//cbLevel
	}
	
	private void addContent()
	{
		contentPanel.add(txtName);
		contentPanel.add(txtCapacity);
		contentPanel.add(cbLevel);
		contentPanel.add(cbStudentAge);
		contentPanel.add(cbTeacher);
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
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(10, 11, 86, 20);
		contentPanel.add(lblName);
		
		JLabel lblLevel = new JLabel("Level:");
		lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevel.setBounds(10, 40, 86, 20);
		contentPanel.add(lblLevel);
		{
			JLabel lblCapacity = new JLabel("Capacity:");
			lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCapacity.setBounds(10, 71, 86, 20);
			contentPanel.add(lblCapacity);
		}
		{
			JLabel lblStudentAge = new JLabel("Student Age:");
			lblStudentAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblStudentAge.setBounds(10, 102, 86, 20);
			contentPanel.add(lblStudentAge);
		}
		{
			JLabel lblTeacher = new JLabel("Teacher:");
			lblTeacher.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblTeacher.setBounds(10, 133, 86, 20);
			contentPanel.add(lblTeacher);
		}
		{
			JLabel lblSchedule = new JLabel("Schedule:");
			lblSchedule.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSchedule.setBounds(10, 164, 86, 20);
			contentPanel.add(lblSchedule);
		}
		
		txtName = new JTextField();
		txtName.setBounds(106, 13, 150, 20);
		txtName.setText(this.name);
		//contentPanel.add(txtName);
		txtName.setColumns(10);
		{
			txtCapacity = new JTextField();
			txtCapacity.setColumns(10);
			txtCapacity.setBounds(106, 73, 150, 20);
			//contentPanel.add(txtCapacity);
		}

			LevelDAO lDAO = new LevelDAO(db.connection);
			//cbLevel = new JComboBox((Object[])lDAO.getComboBoxInterfaceList().toArray());
			cbLevel = new JComboBox();
			
			List<Level> levelList = lDAO.getLevelList();
			for(Level l : levelList)
			{
				cbLevel.addItem( l );
				if(l.getId() == 2)
				{
					//cbLevel.setSelectedItem( l );
				}
			}
			cbLevel.setRenderer(new ComboBoxRenderer());
			cbLevel.setBounds(106, 42, 150, 20);
			contentPanel.add(cbLevel);
		
		
		
		cbStudentAge = new JComboBox();
		cbStudentAge.setBounds(106, 104, 150, 20);
		//contentPanel.add(cbStudentAge);
		
		TeacherDAO tDAO = new TeacherDAO(db.connection);
		cbTeacher = new JComboBox();
		List<Teacher> teacherList = tDAO.getTeachersList();
		for(Teacher t : teacherList)
		{
			cbTeacher.addItem( t );
			if(t.getId() == this.teacher_id)
			{
				cbTeacher.setSelectedItem( t );
			}
		}
		cbTeacher.setRenderer(new ComboBoxRenderer());
		cbTeacher.setBounds(106, 135, 150, 20);
		//contentPanel.add(cbTeacher);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
		
}
