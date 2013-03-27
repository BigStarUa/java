package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.BorderLayout;


public class SummaryTeacherGrid extends JPanel implements ToolBarInteface{

	private ToolBarInteface listeren;
	private JPanel panel;
	private JTable table;
	private JToolBar toolBar;
	private JTable table_1;
	private JLabel lblNewLabel;
	
	public SummaryTeacherGrid(ToolBarInteface listener)
	{
		this.listeren = listener;
		
		String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
		
		Object[][] data = {
			    {"Kathy", "Smith",
			     "Snowboarding", new Integer(5), new Boolean(false)},
			    {"John", "Doe",
			     "Rowing", new Integer(3), new Boolean(true)},
			    {"Sue", "Black",
			     "Knitting", new Integer(2), new Boolean(false)},
			    {"Jane", "White",
			     "Speed reading", new Integer(20), new Boolean(true)},
			    {"Joe", "Brown",
			     "Pool", new Integer(10), new Boolean(false)}
			};
		
		JButton button = new JButton("Button 1");
		this.toolBar = new JToolBar();
		
		panel = new JPanel();
		panel.setBackground(Color.white);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[]{1.0};
		panel.setLayout(gbl_panel);
		
		JLabel btnNewButton = new JLabel("John");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(5, 5, 0, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel.add(btnNewButton, gbc_btnNewButton);
		
		
		table = new JTable(data, columnNames);
		table.setRowSelectionAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(17);
		setLayout(new BorderLayout(0, 0));
		table.setRowHeight(20);
				
		
		JLabel lblBob = new JLabel("Bob");
		GridBagConstraints gbc_lblBob = new GridBagConstraints();
		gbc_lblBob.anchor = GridBagConstraints.WEST;
		gbc_lblBob.insets = new Insets(5, 5, 0, 0);
		gbc_lblBob.gridx = 0;
		gbc_lblBob.gridy = 2;
		panel.add(lblBob, gbc_lblBob);
		
		
		table_1 = new JTable(data, columnNames);
		GridBagConstraints gbc_table_1 = new GridBagConstraints();
		gbc_table_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_table_1.insets = new Insets(0, 0, 5, 0);
		gbc_table_1.gridx = 0;
		gbc_table_1.gridy = 3;
		panel.add(table_1, gbc_table_1);
		
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.ipadx = 10;
		gbc_table.fill = GridBagConstraints.HORIZONTAL;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		panel.add(table, gbc_table);
		
		
		add(new JScrollPane(panel), BorderLayout.CENTER);
		
		
		
	}

	@Override
	public JToolBar getToolbar() {
		// TODO Auto-generated method stub
		return this.toolBar;
	}

	@Override
	public void pushToolbar(JToolBar toolBar) {
		// TODO Auto-generated method stub
		
	}
}
