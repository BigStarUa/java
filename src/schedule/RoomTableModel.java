package schedule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class RoomTableModel extends AbstractTableModel {

	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

	private List<Room> roomList;

	public RoomTableModel(List<Room> roomList) {
		this.roomList = roomList;
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
			return "Capacity";
		case 2:
			return "Value";
		}
		return "";
	}

	public int getRowCount() {
		return roomList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Room room = roomList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return room.getName();
		case 1:
			return room.getCapacity();
		case 2:
			return room.getValue();		
		case -1:
			return room;
		}
		return "";
	}
	
	public void addObject(Room room)
	{
		roomList.add(room);
		int size = roomList.size();  
	    this.fireTableRowsInserted(size-1, size-1);
	}
	
	public Room getObjectAt(int rowIndex)
	{
		Room room = roomList.get(rowIndex);
		return room;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener listener) {
		listeners.remove(listener);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Room room = (Room)value;
		roomList.set(rowIndex, room);
		
	}
	public void setObjectAt(Object value, int rowIndex, int columnIndex) {
		Room room = (Room)value;
		roomList.set(rowIndex, room);
		
	}
	
	public void removeObjectAt(int rowIndex)
	{
		roomList.remove(rowIndex);
		this.fireTableRowsDeleted(rowIndex, rowIndex);
	}

}