package schedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class SummaryTableModel extends AbstractTableModel {

	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

	private List<Summary> summaryList;
	private List<Room> roomsList;
	private List<Schedule> headersList = new ArrayList<Schedule>();
	

	public SummaryTableModel(List<Summary> summaryList, List<Room> roomsList) {
		this.summaryList = summaryList;
		this.roomsList = roomsList;
	}

	public void addTableModelListener(TableModelListener listener) {
		listeners.add(listener);
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		int i = 0;
		for(Summary sum : summaryList)
		{
			for(Schedule sched : sum.getScheduleList())
			{
				i += 1;
				headersList.add(sched);
			}
		}
		
		return i+1;
	}

	public String getColumnName(int columnIndex) {
		
		String sum;
		if(columnIndex == 0){
			sum = "Classes";
		}else{
		
			sum = headersList.get(columnIndex-1).getName();

		}
		return sum;
		
//		switch (columnIndex) {
//		case 0:
//			return "Name";
//		case 1:
//			return "Week Day";
//		case 2:
//			return "Time";
//		}
//		return "";
	}

	public int getRowCount() {
		return roomsList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(columnIndex == 0)
		{
			return roomsList.get(rowIndex).getName();
		}else{
		
		if(rowIndex < summaryList.size()){
			Summary summary = summaryList.get(rowIndex);
			if(columnIndex < summary.getScheduleList().size()+1){
				return summary.getScheduleList().get(columnIndex-1).getName();
			}else{
				return "";	
			}
		}else{
			return "";
		}
		}
//		switch (columnIndex) {
//		case 0:
//			return roomsList.get(rowIndex).getName();
//		case 1:
//			return summary.getWeekDay();
//		case 2:
//			return "Testt";
//		case -1:
//			return summary;
//		}
//		return "";
	}
	
	public Summary getObjectAt(int rowIndex)
	{
		Summary summary = summaryList.get(rowIndex);
		return summary;
	}
	
	public void addObject(Summary summary)
	{
		summaryList.add(summary);
		int size = summaryList.size();  
	    this.fireTableRowsInserted(size-1, size-1);
	}
	
	public void removeRowAt(int row) {
		summaryList.remove(row);
        //this.fireTableDataChanged();
        this.fireTableRowsDeleted(row - 1, summaryList.size() - 1);
    }

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener listener) {
		listeners.remove(listener);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		
		
		if(columnIndex == 0)
		{
			//return roomsList.get(rowIndex).getName();
		}else{
		
		if(rowIndex < summaryList.size()){
			Summary summary = summaryList.get(rowIndex);
			if(columnIndex < summary.getScheduleList().size()+1){
				summary.getScheduleList().set(columnIndex-1,(Schedule)value);
			}else{
				//return "";	
			}
		}else{
			//return "";
		}
		}
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public void removeObjectAt(int rowIndex)
	{
		summaryList.remove(rowIndex);
		this.fireTableRowsDeleted(rowIndex, rowIndex);
	}

}