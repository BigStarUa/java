package schedule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ScheduleTableModel implements TableModel {

	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

	private List<Schedule> scheduleList;

	public ScheduleTableModel(List<Schedule> scheduleList) {
		this.scheduleList = scheduleList;
	}

	public void addTableModelListener(TableModelListener listener) {
		listeners.add(listener);
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return 2;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Name";
		case 1:
			return "Week Day";
		}
		return "";
	}

	public int getRowCount() {
		return scheduleList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Schedule schedule = scheduleList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return schedule.getName();
		case 1:
			return schedule.getWeekDay();
		case -1:
			return schedule;
		}
		return "";
	}
	
	public Schedule getObjectAt(int rowIndex)
	{
		Schedule schedule = scheduleList.get(rowIndex);
		return schedule;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener listener) {
		listeners.remove(listener);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {

	}

}