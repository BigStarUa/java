package schedule;

import java.awt.Color;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class SummaryTableModel extends AbstractTableModel {

	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

	private List<Summary> summaryList;
	private List<Room> roomsList;
	private List<Schedule> headersList = new ArrayList<Schedule>();
	private Connection con;

	private Group_scheduleDAO gsDAO;
	

	public SummaryTableModel(List<Summary> summaryList, List<Room> roomsList, Connection con) {
		this.summaryList = summaryList;
		this.roomsList = roomsList;
		this.con = con;
	}

	public void addTableModelListener(TableModelListener listener) {
		listeners.add(listener);
	}

	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex > 0){
			return JTextPane.class;
		}else{
		return String.class;
		}
	}

	public int getColumnCount() {
		int i = 0;
		for(Summary sum : summaryList)
		{
			//for(Group_schedule sched : sum.getGSList())
			//{
				i += 1;
				headersList.add(sum.getSchedule());
			//}
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

	}

	public int getRowCount() {
		return roomsList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(columnIndex == 0)
		{
			return roomsList.get(rowIndex).getName() + " (" + roomsList.get(rowIndex).getCapacity() + ")";
		}else{
		
			Summary summary = summaryList.get(columnIndex-1);
			//if(rowIndex < summary.getGSList().size()+1){
			Group_schedule gs = summary.getGSList().get(rowIndex);
			String teacher = "";
			String scheduleName = "";
			if(gs.getTeacher() != null)
			{
					teacher = gs.getTeacher().getName();
			}
			if(gs.getGroupObject().getName() != null)
			{
				scheduleName = gs.getGroupObject().getName() + " (" + gs.getGroupObject().getCapacity() + ")";
			}
			SummaryJTextPane panel = new SummaryJTextPane();
			panel.setText(scheduleName);
			SimpleAttributeSet set = new SimpleAttributeSet();
		    //StyleConstants.setItalic(set, true);
		    StyleConstants.setBold(set, true);
		    StyleConstants.setForeground(set, Color.blue);
		    //StyleConstants.setBackground(set, Color.blue);
		    
		    Document doc = panel.getStyledDocument();
		    try {
				doc.insertString(doc.getLength(), "\n" + teacher, set);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				return panel;
		}

	}
	
	public Group_schedule getObjectAt(int rowIndex, int columnIndex)
	{
		Summary summary = summaryList.get(columnIndex);
		return summary.getGSList().get(rowIndex);
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
			
		}
		else
		{
			if(columnIndex-1 < summaryList.size()){
				Summary summary = summaryList.get(columnIndex-1);
				if(rowIndex < summary.getGSList().size()){
					Group_schedule gs = (Group_schedule)value;
					Room room = roomsList.get(rowIndex);
					gs.setRoom(room);
					summary.getGSList().set(rowIndex, gs);
					updateInBase(gs);
					
				}else{
					//return "";	
				}
			}else{
				//return "";
			}
		}
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	private void updateInBase(Group_schedule gs)
	{
		try{
			if(gsDAO == null)gsDAO = new Group_scheduleDAO(con);
			gsDAO = new Group_scheduleDAO(con);
			gsDAO.updateGroup_schedule(gs);
		}catch(Exception e)
		{
			
		}
		

	}
	
	public void removeObjectAt(int rowIndex)
	{
		summaryList.remove(rowIndex);
		this.fireTableRowsDeleted(rowIndex, rowIndex);
	}

}