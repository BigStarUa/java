package gui;

import gui.res.StaticRes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import schedule.DbHelper;
import schedule.Room;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class RoomDialog extends JDialog implements ActionListener{

	private DbHelper db;
	private Room room;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblName;
	private JLabel lblCapacity;
	private JLabel lblValue;
	private JTextField txtName;
	private JTextField txtCapacity;
	private JTextField txtValue;
	private JPanel panel;
	private JLabel lblClass;

	public RoomDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initContent();
	}
	
	/**
	 * Create the dialog.
	 * @throws ClassNotFoundException 
	 */
	public RoomDialog(Window owner, String title, ModalityType modalityType, Room room) throws ClassNotFoundException {
		super(owner, title, modalityType);
		db = new DbHelper();
		this.room = room;
		//populateForm();
		initContent();
		
		//addContent();
	}
	
	private void initContent()
	{
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBorder(UIManager.getBorder("MenuBar.border"));
		getContentPane().add(panel, BorderLayout.NORTH);
		
		lblClass = new JLabel("Class");
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblClass.setIcon(StaticRes.CLASS48_ICON);
		panel.add(lblClass);
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(21, 22, 86, 20);
		contentPanel.add(lblName);
		
		lblCapacity = new JLabel("Capacity:");
		lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCapacity.setBounds(21, 51, 86, 20);
		contentPanel.add(lblCapacity);
		
		lblValue = new JLabel("Value:");
		lblValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValue.setBounds(21, 82, 86, 20);
		contentPanel.add(lblValue);
		
		txtName = new JTextField();
		txtName.setBounds(96, 24, 164, 20);
		contentPanel.add(txtName);
		txtName.setColumns(10);
		
		txtCapacity = new JTextField();
		txtCapacity.setBounds(96, 53, 164, 20);
		contentPanel.add(txtCapacity);
		txtCapacity.setColumns(10);
		
		txtValue = new JTextField();
		txtValue.setBounds(96, 82, 164, 20);
		contentPanel.add(txtValue);
		txtValue.setColumns(10);
		
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
