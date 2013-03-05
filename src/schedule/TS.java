package schedule;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

public class TS extends TransferHandler {  
  
	public static DataFlavor FLAVOR = new DataFlavor(Schedule.class,
            "Schedule");
	
    public TS() {  
    }  
  
    @Override  
    public int getSourceActions(JComponent c) {  
        return MOVE;  
    }  
  
    @Override  
    protected Transferable createTransferable(JComponent source) {  
  
        return new StringSelection((String) ((JTable) source).getModel().getValueAt(((JTable) source).getSelectedRow(), ((JTable) source).getSelectedColumn()));  
    }  
  
    @Override  
    protected void exportDone(JComponent source, Transferable data, int action) {  
  
    	Schedule s = null;
		try {
			s = (Schedule)data.getTransferData(FLAVOR);
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//s.setName("Test");
        ((JTable) source).getModel().setValueAt(s, ((JTable) source).getSelectedRow(), ((JTable) source).getSelectedColumn());  
  
    }  
  
    @Override  
    public boolean canImport(TransferSupport support) {  
    	JTable jt = (JTable) support.getComponent();
    	if(jt.getSelectedColumn() != 2)
    	{
    		return false;
    	}
        return true;  
    }  
  
    @Override  
    public boolean importData(TransferSupport support) {  
        JTable jt = (JTable) support.getComponent(); 
//        Schedule s = new Schedule();
//        	s.setName("Test2");
//        	jt.setValueAt(s, jt.getSelectedRow(), jt.getSelectedColumn());  
        try {  
        	
            jt.setValueAt(support.getTransferable().getTransferData(FLAVOR), jt.getSelectedRow(), jt.getSelectedColumn());  
        } catch (UnsupportedFlavorException ex) {  
           // Logger.getLogger(TS.class.getName()).log(Level.SEVERE, null, ex);  
        } catch (IOException ex) {  
           // Logger.getLogger(TS.class.getName()).log(Level.SEVERE, null, ex);  
        }  
        return super.importData(support);  
    }  
}  