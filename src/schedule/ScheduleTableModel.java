package schedule;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

public class ScheduleTableModel extends AbstractTableModel {

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
		return 3;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Name";
		case 1:
			return "Week Day";
		case 2:
			return "Time";
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
		case 2:
			
			/*DateFormat sdf = new SimpleDateFormat("hh:mm");
			Date date;
			try {
				date = sdf.parse(String.format("%04d", 1810));
				System.out.println("Date and Time: " + date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} */
			//System.out.println("Date and Time: " + date);
			return schedule.getTime();
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
	
	public void addObject(Schedule schedule)
	{
		this.scheduleList.add(schedule);
		int size = scheduleList.size();  
	    this.fireTableRowsInserted(size-1, size-1);
	}
	
	public void removeRowAt(int row) {
        this.scheduleList.remove(row);
        //this.fireTableDataChanged();
        this.fireTableRowsDeleted(row - 1, scheduleList.size() - 1);
    }

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener listener) {
		listeners.remove(listener);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}

}