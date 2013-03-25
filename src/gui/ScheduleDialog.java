package gui;

import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.MaskFormatter;

import schedule.DbHelper;
import schedule.ResultListener;
import schedule.Schedule;
import schedule.ScheduleDAO;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

public class ScheduleDialog extends JDialog implements ActionListener{

	private DbHelper db;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblName;
	private JLabel lblWeekDay;
	private JTextField txtName;
	private JComboBox cbWeekDay;
	private String selected_tab;
	private JFormattedTextField frmtdtxtfldTime;
	private ResultListener listener;
	private Schedule schedule;
	private JTextField txtDuration;

	public ScheduleDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initContent();
	}
	
	/**
	 * Create the dialog.
	 * @throws ClassNotFoundException 
	 */
	public ScheduleDialog(Window owner, String title, ModalityType modalityType, Schedule schedule, String selected_tab, ResultListener listener) throws ClassNotFoundException {
		super(owner, title, modalityType);
		db = new DbHelper();
		this.selected_tab = selected_tab;
		this.listener = listener;
		this.schedule = schedule;
		//populateForm();
		initContent();
		fillContent();
		//addContent();
	}
	
	private void initContent()
	{
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(21, 22, 86, 20);
		contentPanel.add(lblName);
		
		lblWeekDay = new JLabel("Week day:");
		lblWeekDay.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWeekDay.setBounds(21, 51, 86, 20);
		contentPanel.add(lblWeekDay);
		
		txtName = new JTextField();
		txtName.setBounds(96, 24, 164, 20);
		contentPanel.add(txtName);
		txtName.setColumns(10);
		
		cbWeekDay = new JComboBox();
		cbWeekDay.setBounds(96, 53, 164, 20);
		List<String> day_list = StaticRes.WEEK_DAY_LIST;
		cbWeekDay.setModel(dayModel(day_list.toArray()));
		cbWeekDay.setRenderer(dayRenderer());
		cbWeekDay.setSelectedItem(selected_tab);
		contentPanel.add(cbWeekDay);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTime.setBounds(21, 82, 46, 14);
		contentPanel.add(lblTime);
		
		
		frmtdtxtfldTime = new JFormattedTextField(createFormatter("##:##"));
		//frmtdtxtfldTime.setValue("10:00");
		frmtdtxtfldTime.setBounds(96, 81, 164, 20);
		contentPanel.add(frmtdtxtfldTime);
		
		JLabel lblDuration = new JLabel("Duration:");
		lblDuration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDuration.setBounds(21, 108, 65, 14);
		contentPanel.add(lblDuration);
		
		txtDuration = new JTextField();
		txtDuration.setBounds(96, 107, 86, 20);
		txtDuration.addKeyListener(new KeyAdapter()
		{
		public void keyTyped(KeyEvent ke)
		{
			char c = ke.getKeyChar();
			if (!Character.isDigit(c))
			ke.consume(); // prevent event propagation
		}
		}); 
		contentPanel.add(txtDuration);
		
		JLabel lblMinutes = new JLabel("minutes (60 = 1 hour)");
		lblMinutes.setBounds(192, 110, 123, 14);
		contentPanel.add(lblMinutes);
		
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
						
						String h = ((String)frmtdtxtfldTime.getValue()).substring(0, 2);				
						String m = ((String)frmtdtxtfldTime.getValue()).substring(3, 5);

						if(Integer.valueOf(h) > 23 || Integer.valueOf(m) > 59)
						{
							System.out.println("Error");
						}
						else
						{
							if(fillAndSaveSchedule())
							{
								dispose();
								listener.returnObject(schedule);
							}
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
	
	private void fillContent()
	{
		if(this.schedule.getId() > 0)
		{
			txtName.setText(this.schedule.getName());
			frmtdtxtfldTime.setValue(this.schedule.getTime());
			txtDuration.setText(String.valueOf(this.schedule.getDuration()));
		}
	}
	
	private boolean fillAndSaveSchedule()
	{
		boolean correct = true;
		try{
			
			schedule.setName(txtName.getText());
			schedule.setTime((String)frmtdtxtfldTime.getValue());
			schedule.setWeekDay(cbWeekDay.getSelectedItem().toString());
			schedule.setDuration(Integer.parseInt(txtDuration.getText()));
			
			ScheduleDAO scheduleDAO = new ScheduleDAO(db.connection);
			int id = scheduleDAO.updateSchedule(schedule);
			if(schedule.getId() == 0)
			{
				schedule.setId(id);
				schedule.setStatus(Schedule.STATUS_NEW);
			}
		}catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Erorr generated: \n" + e.getMessage());
			correct = false;
		}
		return correct;
	}
	private DefaultComboBoxModel dayModel(Object[] list)
	{
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel(list)
		{
			
		};
		return comboModel;
	}
	
	private BasicComboBoxRenderer dayRenderer()
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
			            String item = (String)value;
			            setText( item );
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	protected MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;

	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
}
