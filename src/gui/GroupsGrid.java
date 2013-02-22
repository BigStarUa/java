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
		//Массив названий столбцов
		  String[] columnNames = {"Первый столбец", "Второй столбец", "Третий столбец"};
		  //Массив ячеек таблицы
		  Object[] [] dataTable = { 
		  {"Первый столбец строка 1", "Второй столбец строка 1", "Третий столбец строка 1"},
		  {"Первый столбец строка 2", "Второй столбец строка 2", "Третий столбец строка 2"},
		  {"Первый столбец строка 3", "Второй столбец строка 3", "Третий столбец строка 3"},
		  {"Первый столбец строка 4", "Второй столбец строка 4", "Третий столбец строка 4"},
		  {"Первый столбец строка 5", "Второй столбец строка 5", "Третий столбец строка 5"},
		  {"Первый столбец строка 6", "Второй столбец строка 6", "Третий столбец строка 6"},
		  {"Первый столбец строка 7", "Второй столбец строка 7", "Третий столбец строка 7"},
		  {"Первый столбец строка 8", "Второй столбец строка 8", "Третий столбец строка 8"},
		                          };
		  
		  
		table = new JTable(dataTable, columnNames);
		add(table, BorderLayout.CENTER);

	}

}
