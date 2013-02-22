package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.JToolBar;

import schedule.Group;

public class MainFrame extends JFrame implements ToolBarInteface{

	private JPanel contentPane;
	
	private static final Icon CLOSE_TAB_ICON = new ImageIcon("res/gui/closeTabButton.png");
	private static final Icon CLOSE_TAB_ICON_HOVER = new ImageIcon("res/gui/closeTabButtonHover.png");
	private static final Icon PAGE_ICON = new ImageIcon("res/gui/1361500543_page_edit.png");
	private int tabCount = 0;
	
	// Variables declaration - do not modify                    
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton createTabButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration   

	private JPanel toolBarPanel;
	JFrame mainFrame = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		initComponent();	
	}
	
	
	/**
	   * Adds a component to a JTabbedPane with a little "close tab" button on the
	   * right side of the tab.
	   *
	   * @param tabbedPane the JTabbedPane
	   * @param c any JComponent
	   * @param title the title for the tab
	   * @param icon the icon for the tab, if desired
	   */
	  public static void addClosableTab(final JTabbedPane tabbedPane, final JComponent c, final String title,
	          final Icon icon) {
	    // Add the tab to the pane without any label
	    tabbedPane.addTab(null, c);
	    int pos = tabbedPane.indexOfComponent(c);
	 
	    // Create a FlowLayout that will space things 5px apart
	    FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
	 
	    // Make a small JPanel with the layout and make it non-opaque
	    JPanel pnlTab = new JPanel(f);
	    pnlTab.setOpaque(false);
	 
	    // Add a JLabel with title and the left-side tab icon
	    JLabel lblTitle = new JLabel(title);
	    lblTitle.setIcon(icon);
	 
	    // Create a JButton for the close tab button
	    JButton btnClose = new JButton();
	    btnClose.setOpaque(false);
	 
	    // Configure icon and rollover icon for button
	    btnClose.setRolloverIcon(CLOSE_TAB_ICON_HOVER);
	    btnClose.setRolloverEnabled(true);
	    btnClose.setIcon(CLOSE_TAB_ICON);
	 
	    // Set border null so the button doesn't make the tab too big
	    btnClose.setBorder(null);
	 
	    // Make sure the button can't get focus, otherwise it looks funny
	    btnClose.setFocusable(false);
	 
	    // Put the panel together
	    pnlTab.add(lblTitle);
	    pnlTab.add(btnClose);
	    // Add a thin border to keep the image below the top edge of the tab
	    // when the tab is selected
	    pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	 
	    // Now assign the component for the tab
	    tabbedPane.setTabComponentAt(pos, pnlTab);
	 
	    // Add the listener that removes the tab
	    ActionListener listener = new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	        // The component parameter must be declared "final" so that it can be
	        // referenced in the anonymous listener class like this.
	        tabbedPane.remove(c);
	      }
	    };
	    btnClose.addActionListener(listener);
	 
	    // Optionally bring the new tab to the front
	    tabbedPane.setSelectedComponent(c);
	 
	    //-------------------------------------------------------------
	    // Bonus: Adding a <Ctrl-W> keystroke binding to close the tab
	    //-------------------------------------------------------------
	    AbstractAction closeTabAction = new AbstractAction() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	        tabbedPane.remove(c);
	      }
	    };
	 
	    // Create a keystroke
	    KeyStroke controlW = KeyStroke.getKeyStroke("control W");
	 
	    // Get the appropriate input map using the JComponent constants.
	    // This one works well when the component is a container.
	    InputMap inputMap = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	 
	    // Add the key binding for the keystroke to the action name
	    inputMap.put(controlW, "closeTab");
	 
	    // Now add a single binding for the action name to the anonymous action
	    c.getActionMap().put("closeTab", closeTabAction);
	  }
	  
	  private void createTabButtonActionPerformed(java.awt.event.ActionEvent evt, JComponent newclass, String name) {                                                
		    System.out.println("Add new tab!");
		    //tabCount++;   
		    //JScrollPane scrollPane = new JScrollPane(new GroupsGrid());	    
		    Icon icon = PAGE_ICON;
		    addClosableTab(tabbedPane,  newclass, name, icon);
		    
		  } 
	  
	  private boolean checkExistingTab(String className)
	  {
		  boolean exist = false;
		    int count = tabbedPane.getTabCount();
		    for (int i = 0; i < count; i++) {
		    	Component comp = tabbedPane.getComponentAt(i);
			    if(className == comp.getClass().getName())
			    {
			    	tabbedPane.setSelectedIndex(i);
			    	exist = true;
			    	//reBuildJToolBar(comp);
			    	break;
			    }	
		    }
		    
		  return exist;
	  }
	  
	 private void reBuildJToolBar(Component comp)
	 {
		 ToolBarInteface tbf = (ToolBarInteface)comp;
		 toolBar.removeAll();
		 toolBar.add(tbf.getToolbar());
		 toolBar.repaint();
		 toolBar.revalidate();
	 }
	
	
	private void initComponent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 430);
		
		ChangeListener changeListener = new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
		        int index = sourceTabbedPane.getSelectedIndex();
		        Component comp = null;
		        if(index >= 0)
		        {
		        	comp = sourceTabbedPane.getComponentAt(index);
		        }
		        else
		        {
		        	comp = MainFrame.this;
		        System.out.println("Tab changed to: " + comp.toString());
		        }
		        reBuildJToolBar(comp);
		      }
		    };
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnGridview = new JMenu("GridView");
		menuBar.add(mnGridview);
		
		JMenuItem mntmGroups = new JMenuItem("Groups");
		mntmGroups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(!checkExistingTab(GroupsGrid.class.getName()))
            	{
            		createTabButtonActionPerformed(evt, new GroupsGrid(), "Groups");
            	}
            }
        });
		mnGridview.add(mntmGroups);
		
		JMenuItem mntmRooms = new JMenuItem("Rooms");
		mntmRooms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(!checkExistingTab(RoomsGrid.class.getName()))
            	{
            		createTabButtonActionPerformed(evt, new RoomsGrid(), "Rooms");
            	}
            }
        });
		mnGridview.add(mntmRooms);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//JPanel panel = new JPanel();
		//contentPane.add(panel, BorderLayout.NORTH);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.addChangeListener(changeListener);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		setToolBar();
		toolBar.add(this.toolBarPanel);
	}

	private void setToolBar()
	{
		JPanel toolBar = new JPanel();
		JButton btnNewButton = new JButton("Main Button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GroupsDialog gd;
				try {
					gd = new GroupsDialog(MainFrame.this, "", Dialog.ModalityType.DOCUMENT_MODAL, new Group());
					gd.setVisible(true);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		toolBar.add(btnNewButton);
		
		this.toolBarPanel = toolBar;
	}
	
	@Override
	public JPanel getToolbar() {
		// TODO Auto-generated method stub
		return this.toolBarPanel;
	}

}
