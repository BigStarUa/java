package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;

public class GroupsGrid extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public GroupsGrid() {
		//FlowLayout flowLayout = (FlowLayout) getLayout();
		setLayout(new  BorderLayout());
		//������ �������� ��������
		  String[] columnNames = {"������ �������", "������ �������", "������ �������"};
		  //������ ����� �������
		  Object[] [] dataTable = { 
		  {"������ ������� ������ 1", "������ ������� ������ 1", "������ ������� ������ 1"},
		  {"������ ������� ������ 2", "������ ������� ������ 2", "������ ������� ������ 2"},
		  {"������ ������� ������ 3", "������ ������� ������ 3", "������ ������� ������ 3"},
		  {"������ ������� ������ 4", "������ ������� ������ 4", "������ ������� ������ 4"},
		  {"������ ������� ������ 5", "������ ������� ������ 5", "������ ������� ������ 5"},
		  {"������ ������� ������ 6", "������ ������� ������ 6", "������ ������� ������ 6"},
		  {"������ ������� ������ 7", "������ ������� ������ 7", "������ ������� ������ 7"},
		  {"������ ������� ������ 8", "������ ������� ������ 8", "������ ������� ������ 8"},
		                          };
		  
		  
		table = new JTable(dataTable, columnNames);
		add(table, BorderLayout.CENTER);

	}

}
