package gui;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import schedule.Teacher;

public class ComboBoxRenderer extends BasicComboBoxRenderer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4836479417640063838L;

	public Component getListCellRendererComponent(
        JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index,
            isSelected, cellHasFocus);

        if (value != null)
        {
            ComboBoxInterface item = (ComboBoxInterface)value;
            setText( item.getName() );
        }

        if (index == -1)
        {
        	ComboBoxInterface item = (ComboBoxInterface)value;
            //setText( "None" );
        }


        return this;
    }

}
