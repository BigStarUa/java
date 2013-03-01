package schedule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class GroupTableModel extends AbstractTableModel {

	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

	private List<Group> groupList;

	public GroupTableModel(List<Group> groupList) {
		this.groupList = groupList;
	}

	public void addTableModelListener(TableModelListener listener) {
		listeners.add(listener);
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Name";
		case 1:
			return "Level";
		case 2:
			return "Teacher";
		case 3:
			return "Quantity";
		case 4:
			return "Schedule";
		}
		return "";
	}

	public int getRowCount() {
		return groupList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Group group = groupList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return group.getName();
		case 1:
			return group.getLevel().getName();
		case 2:
			return group.getTeacher().getName();
		case 3:
			return group.getCapacity();
		case 4:
			String scheduleText = "";
			for(Schedule s : group.getSchedule()){
				scheduleText += s.getName() + " | ";
			}
			return scheduleText;
			
		case -1:
			return group;
		}
		return "";
	}
	
	public Group getObjectAt(int rowIndex)
	{
		Group group = groupList.get(rowIndex);
		return group;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener listener) {
		listeners.remove(listener);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Group group = (Group)value;
		groupList.set(rowIndex, group);
		
	}
	public void setObjectAt(Object value, int rowIndex, int columnIndex) {
		Group group = (Group)value;
		groupList.set(rowIndex, group);
		
	}

}