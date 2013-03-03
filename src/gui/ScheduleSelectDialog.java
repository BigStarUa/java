package gui;

import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import schedule.DbHelper;
import schedule.Group_schedule;
import schedule.Room;
import schedule.Schedule;
import schedule.ScheduleDAO;
import schedule.ScheduleTableModel;
import schedule.Teacher;
import schedule.TeacherDAO;

import javax.swing.JTabbedPane;
import java.awt.Component;
import javax.swing.JComboBox;
import java.awt.Dimension;

public class ScheduleSelectDialog extends JDialog implements ActionListener{

	private DbHelper db;
	private final JPanel contentPanel = new JPanel();
	private JTabbedPane tabbedPane;
	private JTable table;
	private ScheduleResultListener listener;
	private JComboBox cbTeacher;
	private Group_schedule group_schedule = null;

	public ScheduleSelectDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initContent();
	}
	
	/**
	 * Create the dialog.
	 * @throws ClassNotFoundException 
	 */
	public ScheduleSelectDialog(Window owner, String title, ModalityType modalityType,
			ScheduleResultListener listener, Group_schedule group_schedule) throws ClassNotFoundException {
		super(owner, title, modalityType);
		db = new DbHelper();
		this.listener = listener;
		this.group_schedule = group_schedule;
		//populateForm();
		initContent();
		addContent();
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
			        	Schedule schedule = (Schedule)((JTable) evt.getSource()).getValueAt(row, -1);
			        	Teacher teacher = (Teacher)cbTeacher.getSelectedItem();
			        	submit(schedule, teacher);
			        }
		    	 }
		    }
		});
		tabbedPane.addTab(day,new JScrollPane(table));
		}
		
	}
	
	private void initContent()
	{
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

		
		contentPanel.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("MenuBar.border"));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblSchedule = new JLabel("Schedule");
		lblSchedule.setHorizontalAlignment(SwingConstants.LEFT);
		lblSchedule.setIcon(StaticRes.SCHEDULE48_ICON);
		lblSchedule.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(lblSchedule);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblTeacher = new JLabel("Teacher:");
		lblTeacher.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTeacher.setBorder(new EmptyBorder(0, 40, 0, 0));
		panel_1.add(lblTeacher);
		
		cbTeacher = new JComboBox();
		cbTeacher.setPreferredSize(new Dimension(150, 20));
		cbTeacher.setMinimumSize(new Dimension(100, 20));
		
		TeacherDAO teacherDAO = new TeacherDAO(db.connection);
		List<Teacher> teachersList = teacherDAO.getTeachersList();
		
		cbTeacher.setModel(teacherModel(teachersList.toArray()));
		cbTeacher.setRenderer(teacherRenderer());
		cbTeacher.setSelectedIndex(0);
		panel_1.add(cbTeacher);
		
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
						
						JScrollPane js = (JScrollPane)tabbedPane.getSelectedComponent();
						JTable t = (JTable)js.getViewport().getComponent(0);
						Schedule schedule = null;
						if(t.getSelectedRow() >= 0){
							schedule = (Schedule)t.getValueAt(t.getSelectedRow(), -1);
						}
						
						Teacher teacher = (Teacher)cbTeacher.getSelectedItem();
						
						submit(schedule, teacher);
						
						//fillGroup();
						//if(saveGroup())
						//{
						//	dispose();
						//}else{
						//	
						//}
						
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
	private DefaultComboBoxModel teacherModel(Object[] list)
	{
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel(list)
		{
			
		};
		Teacher t = new Teacher();
		t.setName("Select teacher");
		t.setId(0);
		comboModel.insertElementAt(t, 0);
		return comboModel;
	}
	
	private BasicComboBoxRenderer teacherRenderer()
	{
		BasicComboBoxRenderer renderer = 
		new BasicComboBoxRenderer(){
			public Component getListCellRendererComponent(
			        JList list, Object value, int index,
			        boolean isSelected, boolean cellHasFocus)
			    {
			        super.getListCellRendererComponent(list, value, index,
			            isSelected, cellHasFocus);

			        if (value != null)
			        {
			            Teacher item = (Teacher)value;
			            setText( item.getName() );
			        }

			        if (index == -1)
			        {
			        	//ComboBoxInterface item = (ComboBoxInterface)value;
			            //setText( "None" );
			        }


			        return this;
			    }
		};
		return renderer;
	}
	
	private void submit(Schedule schedule, Teacher teacher)
	{
		String error = "";
		if(schedule == null)
		{
			error += " - schedule \n";
		}
		if(teacher.getId() <= 0)
		{
			error += " - teacher \n";
		}
		if(schedule != null && teacher.getId() > 0){
			//Component c = tabbedPane.getSelectedComponent();
			this.group_schedule.setSchedule(schedule);
			this.group_schedule.setTeacher(teacher);
			ScheduleSelectDialog.this.dispose();
	    	listener.returnGroup_schedule(this.group_schedule);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Please select: \n" + error);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
