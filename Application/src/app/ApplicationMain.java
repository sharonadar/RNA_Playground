package app;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuBar;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import Options.GraphOptionsPane;


public class ApplicationMain {

	private JFrame mainComponent;
	private JSplitPane splitPane;
	private JTabbedPane tabbedPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationMain window = new ApplicationMain();
					window.mainComponent.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApplicationMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainComponent = new JFrame();
		mainComponent.setTitle("RNA Playground");
		mainComponent.setBounds(100, 100, 800, 500);
		mainComponent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{434, 0};
		gridBagLayout.rowHeights = new int[]{262, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		mainComponent.getContentPane().setLayout(gridBagLayout);
		OptionsChooser oc = new OptionsChooser(this);
		MenuBar mb = oc.getMenu();
		mainComponent.setMenuBar(mb);
		
		splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		mainComponent.getContentPane().add(splitPane, gbc_splitPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);
		
		ImagePanel panel = new ImagePanel();
		panel.setPreferredSize(new Dimension(300, 500));
		splitPane.setLeftComponent(panel);
	}

	public JTabbedPane getTabsPane() {
		return tabbedPane;
	}

	public void setOptionPane(GraphOptionsPane optionPane) {
		optionPane.init();
		splitPane.setLeftComponent(optionPane);
	}
	
}
