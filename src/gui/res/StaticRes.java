package gui.res;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class StaticRes {

	public static final Icon CLOSE_TAB_ICON = new ImageIcon(StaticRes.class.getResource("icon/closeTabButton.png"));
	public static final Icon CLOSE_TAB_ICON_HOVER = new ImageIcon(StaticRes.class.getResource("icon/closeTabButtonHover.png"));
	public static final Icon PAGE_ICON = new ImageIcon(StaticRes.class.getResource("icon/1361500543_page_edit.png"));
	public static final Icon ADD_ICON = new ImageIcon(StaticRes.class.getResource("icon/add.png"));
	public static final Icon OK_ICON = new ImageIcon(StaticRes.class.getResource("icon/ok.png"));
	public static final Icon CANCEL_ICON = new ImageIcon(StaticRes.class.getResource("icon/cancel.png"));
	public static final Icon GROUP_ICON = new ImageIcon(StaticRes.class.getResource("icon/group.png"));
	public static final Icon TEACHER_ICON = new ImageIcon(StaticRes.class.getResource("icon/teacher.png"));
	public static final Icon CLASS_ICON = new ImageIcon(StaticRes.class.getResource("icon/class.png"));
	public static final Icon SCHEDULE_ICON = new ImageIcon(StaticRes.class.getResource("icon/schedule.png"));
	
	public static final Icon ADD16_ICON = new ImageIcon(StaticRes.class.getResource("icon/add16.png"));
	public static final Icon DELETE16_ICON = new ImageIcon(StaticRes.class.getResource("icon/delete16.png"));
	
	public static final Icon SCHEDULE48_ICON = new ImageIcon(StaticRes.class.getResource("icon/schedule48.png"));
	public static final Icon TEACHER48_ICON = new ImageIcon(StaticRes.class.getResource("icon/teacher48.png"));
	public static final Icon CLASS48_ICON = new ImageIcon(StaticRes.class.getResource("icon/class48.png"));
	public static final Icon GROUP48_ICON = new ImageIcon(StaticRes.class.getResource("icon/group48.png"));
	
	public static final List<String> WEEK_DAY_LIST = new ArrayList<String>(
		    Arrays.asList("Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
}
