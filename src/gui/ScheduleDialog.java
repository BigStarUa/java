package gui;

import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import schedule.DbHelper;
import schedule.Schedule;
import schedule.Teacher;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JComboBox;

public class ScheduleDialog extends JDialog implements ActionListener{

	private DbHelper db;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblName;
	private JLabel lblWeekDay;
	private JTextField txtName;
	private JComboBox cbWeekDay;
	private String selected_tab;

	public ScheduleDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initContent();
	}
	
	/**
	 * Create the dialog.
	 * @throws ClassNotFoundException 
	 */
	public ScheduleDialog(Window owner, String title, ModalityType modalityType, Schedule schedule, String selected_tab) throws ClassNotFoundException {
		super(owner, title, modalityType);
		db = new DbHelper();
		this.selected_tab = selected_tab;
		System.out.println(selected_tab);
		//populateForm();
		initContent();
		
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
						System.out.println("OK Clicked!");
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
}
