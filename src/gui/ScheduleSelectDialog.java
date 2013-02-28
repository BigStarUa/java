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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import schedule.DbHelper;
import schedule.Room;
import schedule.Schedule;
import schedule.ScheduleDAO;
import schedule.ScheduleTableModel;

import javax.swing.JTabbedPane;

public class ScheduleSelectDialog extends JDialog implements ActionListener{

	private DbHelper db;
	private final JPanel contentPanel = new JPanel();
	private JTabbedPane tabbedPane;
	private JTable table;
	private ScheduleResultListener listener;

	public ScheduleSelectDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initContent();
	}
	
	/**
	 * Create the dialog.
	 * @throws ClassNotFoundException 
	 */
	public ScheduleSelectDialog(Window owner, String title, ModalityType modalityType, ScheduleResultListener listener) throws ClassNotFoundException {
		super(owner, title, modalityType);
		db = new DbHelper();
		this.listener = listener;
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
			        	ScheduleSelectDialog.this.dispose();
			        	listener.returnSchedule(schedule);
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
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		//tabbedPane.addTab("Monday", null);
//		tabbedPane.addTab("Tuesday", null);
//		tabbedPane.addTab("Wednesday", null);
//		tabbedPane.addTab("Thursday", null);
//		tabbedPane.addTab("Friday", null);
//		tabbedPane.addTab("Saturday", null);
//		tabbedPane.addTab("Sunday", null);
		
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
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
