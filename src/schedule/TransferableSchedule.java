package schedule;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class TransferableSchedule implements Transferable {
	  protected static DataFlavor scheduleFlavor = new DataFlavor(Schedule.class, "A Schedule Object");
	  protected static DataFlavor[] supportedFlavors = { scheduleFlavor };
	  Schedule schedule;
	  public TransferableSchedule(Schedule schedule) {
	    this.schedule = schedule;
	  }

	  public DataFlavor[] getTransferDataFlavors() {
	    return supportedFlavors;
	  }

	  public boolean isDataFlavorSupported(DataFlavor flavor) {
	    if (flavor.equals(scheduleFlavor) || flavor.equals(DataFlavor.stringFlavor))
	      return true;
	    return false;
	  }

	  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
	    if (flavor.equals(scheduleFlavor))
	      return schedule;
	    else if (flavor.equals(DataFlavor.stringFlavor))
	      return schedule.toString();
	    else
	      throw new UnsupportedFlavorException(flavor);
	  }
	}
