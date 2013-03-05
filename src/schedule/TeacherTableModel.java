package schedule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class TeacherTableModel extends AbstractTableModel {

	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

	private List<Teacher> teachersList;

	public TeacherTableModel(List<Teacher> teachersList) {
		this.teachersList = teachersList;
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
			return "Value";
		}
		return "";
	}

	public int getRowCount() {
		return teachersList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Teacher teacher = teachersList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return teacher.getName();
		case 1:
			return teacher.getValue();		
		case -1:
			return teacher;
		}
		return "";
	}
	
	public void addObject(Teacher teacher)
	{
		teachersList.add(teacher);
		int size = teachersList.size();  
	    this.fireTableRowsInserted(size-1, size-1);
	}
	
	public Teacher getObjectAt(int rowIndex)
	{
		Teacher teacher = teachersList.get(rowIndex);
		return teacher;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener listener) {
		listeners.remove(listener);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Teacher teacher = (Teacher)value;
		teachersList.set(rowIndex, teacher);
		
	}
	public void setObjectAt(Object value, int rowIndex, int columnIndex) {
		Teacher teacher = (Teacher)value;
		teachersList.set(rowIndex, teacher);
		
	}
	
	public void removeObjectAt(int rowIndex)
	{
		teachersList.remove(rowIndex);
		this.fireTableRowsDeleted(rowIndex, rowIndex);
	}

}