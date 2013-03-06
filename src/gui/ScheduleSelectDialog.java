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
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import schedule.DbHelper;
import schedule.Group_schedule;
import schedule.Group_scheduleDAO;
import schedule.Room;
import schedule.RoomDAO;
import schedule.Schedule;
import schedule.ScheduleDAO;
import schedule.ScheduleTableModel;
import schedule.Teacher;
import schedule.TeacherDAO;

import javax.swing.JTabbedPane;
import java.awt.Component;
import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.swing.JCheckBox;

public class ScheduleSelectDialog extends JDialog implements ActionListener{

	private DbHelper db;
	private final JPanel contentPanel = new JPanel();
	private JTabbedPane tabbedPane;
	private JTable table;
	private ScheduleResultListener listener;
	private JComboBox cbTeacher;
	private Group_schedule group_schedule = null;
	private JCheckBox chckbxFixedRoom;
	private JComboBox cbRooms;
	private ScheduleDAO sdao;
	private Group_scheduleDAO gsDAO;

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
		
		
		ChangeListener changeListener = new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
		        int index = sourceTabbedPane.getSelectedIndex();
		        
		        
		        JScrollPane js = (JScrollPane)sourceTabbedPane.getSelectedComponent();
				JTable t = (JTable)js.getViewport().getComponent(0);
				int i = t.getSelectedRow();
		       if(t.getSelectedRow() >= 0)
		       {
		    	   Schedule schedule = (Schedule)t.getValueAt(t.getSelectedRow(), -1);
		    	   cbRooms.setModel(comboModel(updateRoomModel(schedule.getId()).toArray(), Room.class));
		       }else{
		    	   cbRooms.setModel(comboModel(updateRoomModel(-1).toArray(), Room.class));
		       }
		     }
		};
		tabbedPane.addChangeListener(changeListener);
		
		sdao = new ScheduleDAO(db.connection);
		gsDAO = new Group_scheduleDAO(db.connection);
		
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
		    	 }else if(evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1)
		    	 {
		    		 int row = ((JTable) evt.getSource()).rowAtPoint(evt.getPoint());;
				        if (row >= 0 ) {
				        	Schedule schedule = (Schedule)((JTable) evt.getSource()).getValueAt(row, -1);
				        	cbRooms.setModel(comboModel(updateRoomModel(schedule.getId()).toArray(), Room.class));
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
		
		cbTeacher.setModel(comboModel(teachersList.toArray(), Teacher.class));
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
				
				JPanel panel_2 = new JPanel();
				panel_2.setBorder(new EmptyBorder(0, 0, 0, 30));
				buttonPane.add(panel_2);
				
				chckbxFixedRoom = new JCheckBox("fixed room");
				chckbxFixedRoom.setIconTextGap(2);
				chckbxFixedRoom.setSelected(group_schedule.getIsFixed());
				chckbxFixedRoom.addActionListener(new ActionListener() {
				      public void actionPerformed(ActionEvent actionEvent) {
				          AbstractButton abstractButton = (AbstractButton)actionEvent.getSource();
				          boolean selected = abstractButton.getModel().isSelected();				     
				          if(selected){
				        	  cbRooms.setEnabled(true);
				          }else{
				        	  cbRooms.setEnabled(false);
				          }
				        }
				      });
				panel_2.add(chckbxFixedRoom);
				
				
				
				cbRooms = new JComboBox();
				cbRooms.setPreferredSize(new Dimension(100, 20));
				cbRooms.setRenderer(roomRenderer());
				cbRooms.setEnabled(group_schedule.getIsFixed());
				panel_2.add(cbRooms);
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
	private DefaultComboBoxModel comboModel(Object[] list, Class clss)
	{
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel(list)
		{
			
		};
		if(clss == Teacher.class){
			Teacher t = new Teacher();
			t.setName("Select teacher");
			t.setId(0);
			comboModel.insertElementAt(t, 0);
		}else if(clss == Room.class)
		{
//			Room r = new Room();
//			r.setName("Select room");
//			r.setId(0);
//			comboModel.insertElementAt(r, 0);
		}
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
			        return this;
			    }
		};
		return renderer;
	}
	
	private BasicComboBoxRenderer roomRenderer()
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
			            Room item = (Room)value;
			            setText( item.getName() );
			        }
			        return this;
			    }
		};
		return renderer;
	}
	
	private void submit(Schedule schedule, Teacher teacher)
	{
		String errorMSG = "";
		boolean error = false;
		if(schedule == null)
		{
			errorMSG += " - schedule \n";
		}
		if(teacher.getId() <= 0)
		{
			errorMSG += " - teacher \n";
		}
		if(chckbxFixedRoom.isSelected() && ((Room)cbRooms.getSelectedItem()).getId() < 1)
		{
			errorMSG += " - room or unset fixed room \n";
			error = true;
		}
		if(schedule != null && teacher.getId() > 0 && !error){
			//Component c = tabbedPane.getSelectedComponent();
			group_schedule.setSchedule(schedule);
			group_schedule.setTeacher(teacher);
			group_schedule.setIsFixed(chckbxFixedRoom.isSelected());
			if(chckbxFixedRoom.isSelected())
			{
				group_schedule.setRoom((Room)cbRooms.getSelectedItem());
			}
			
			ScheduleSelectDialog.this.dispose();
	    	listener.returnGroup_schedule(group_schedule);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Please select: \n" + errorMSG);
		}
	}
	
	private List<Room> updateRoomModel(int schedule_id)
	{
		List<Room> roomsList;
		if(schedule_id > 0)
		{
			RoomDAO roomDAO = new RoomDAO(db.connection);
			roomsList = roomDAO.getNotFixedRoomList(schedule_id);
		}else{
			roomsList = new ArrayList<Room>();
		}
		return roomsList;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
