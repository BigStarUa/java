package gui;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;

public class GUIGroups extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public GUIGroups() {
		
		JLabel lblNewLabel = new JLabel("New label");
		add(lblNewLabel);
		
		table = new JTable();
		add(table);

	}

}
