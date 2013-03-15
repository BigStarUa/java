package schedule;

import java.awt.Rectangle;
import javax.swing.JTextPane;

public class SummaryJTextPane extends JTextPane
{

	public SummaryJTextPane() {
	super();
	}
	
	@Override
	public boolean isOpaque() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void repaint(long arg0, int arg1, int arg2,
			int arg3, int arg4) {
		// TODO Auto-generated method stub
		super.repaint(arg0, arg1, arg2, arg3, arg4);
	}
	
	@Override
	public void repaint(Rectangle arg0) {
		// TODO Auto-generated method stub
		super.repaint(arg0);
	}
	
	@Override
	public void revalidate() {
		// TODO Auto-generated method stub
		super.revalidate();
	}
	
	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		super.invalidate();
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		//super.validate();
	}

}