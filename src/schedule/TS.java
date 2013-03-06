package schedule;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

public class TS extends TransferHandler{  
  
	public static DataFlavor FLAVOR = new DataFlavor(Schedule.class,
            "Schedule");
	private Schedule sourceSchedule;
	private int sourceIndexColumn;
	private boolean isImported = false;
	
    public TS() {  
    }  
  
    @Override  
    public int getSourceActions(JComponent c) {  
        return MOVE;  
    }  
  
    @Override  
    protected Transferable createTransferable(JComponent source) {
    	if(((JTable) source).getSelectedColumn() > 0){
    	Schedule s = (Schedule) ((SummaryTableModel)((JTable) source).getModel()).getObjectAt(((JTable) source).getSelectedRow(), ((JTable) source).getSelectedColumn()-1);
    	sourceIndexColumn = ((JTable) source).getSelectedColumn();
    	
    	return new TransferableSchedule(s);  
    	}
    	return null;
//        return new StringSelection((String) ((JTable) source).getModel().getValueAt(((JTable) source).getSelectedRow(), ((JTable) source).getSelectedColumn()));  
    }  
  
    @Override  
    protected void exportDone(JComponent source, Transferable data, int action) {  
  
    	if(isImported)
    	{
    		((JTable) source).getModel().setValueAt(sourceSchedule, ((JTable) source).getSelectedRow(), ((JTable) source).getSelectedColumn());  
    		isImported = false;
    	}
    }  
  
    @Override
	public void exportAsDrag(JComponent comp, InputEvent e, int action) {
		// TODO Auto-generated method stub
    	
		super.exportAsDrag(comp, e, action);
	}

	@Override  
    public boolean canImport(TransferSupport support) { 
		if(!support.isDrop()){
			return false;
		}
		
		if (!support.isDataFlavorSupported(FLAVOR)) {
	        return false;
	    }
		
    	JTable.DropLocation dl = (JTable.DropLocation)support.getDropLocation();
    	if(dl.getColumn() != sourceIndexColumn)
    	{
    		return false;
    	}
    	
    	return true; 
    }  
  
    @Override  
    public boolean importData(TransferSupport support) {  
    	if(canImport(support)){
	        JTable jt = (JTable) support.getComponent(); 
	        JTable.DropLocation dl = (JTable.DropLocation)support.getDropLocation();
	        sourceSchedule = ((SummaryTableModel)jt.getModel()).getObjectAt(dl.getRow(), dl.getColumn()-1);
	//        Schedule s = new Schedule();
	//        	s.setName("Test2");
	//        	jt.setValueAt(s, jt.getSelectedRow(), jt.getSelectedColumn());  
	        try {  
	        	
	            jt.setValueAt(support.getTransferable().getTransferData(FLAVOR), jt.getSelectedRow(), jt.getSelectedColumn());  
	            jt.repaint();
	            jt.revalidate();
	            isImported = true;
	        } catch (UnsupportedFlavorException ex) {  
	           // Logger.getLogger(TS.class.getName()).log(Level.SEVERE, null, ex);  
	        } catch (IOException ex) {  
	           // Logger.getLogger(TS.class.getName()).log(Level.SEVERE, null, ex);  
	        }  
	        return super.importData(support);  
    	}
    	return false;
    }

}  