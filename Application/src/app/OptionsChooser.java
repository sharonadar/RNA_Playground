package app;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import Options.AlignmentOptionsPane;
import Options.ComparisonOptionsPane;
import Options.GeneAlignmentOptionsPane;
import Options.GraphOptionsPane;
import Options.PiesOptionsPane;
import Options.TypedPieOptionsPane;

public class OptionsChooser implements ActionListener{

	private ApplicationMain parent;
	private MenuBar menuBar;
	private Map<String, Class<? extends GraphOptionsPane>> options;
	
	public OptionsChooser(ApplicationMain parent) {
		this.parent = parent;
		
		options = new HashMap<String, Class<? extends GraphOptionsPane>>();
		options.put("Alignment Bin" , AlignmentOptionsPane.class);
		options.put("Comparison graph", ComparisonOptionsPane.class);
		options.put("Gene alignment", GeneAlignmentOptionsPane.class);
		options.put("Pies", PiesOptionsPane.class);
		options.put("Typed pies", TypedPieOptionsPane.class);
		
		// ---
		menuBar = new MenuBar();
		Menu graphsMenu = new Menu("Choose graph...");
		menuBar.add(graphsMenu);
		
		for (String itr : options.keySet()) {
			MenuItem menuItem1 = new MenuItem(itr);
			menuItem1.addActionListener(this);
			graphsMenu.add(menuItem1);
		}
	}

	public MenuBar getMenu(){
		return this.menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Class<? extends GraphOptionsPane> clz = options.get(e.getActionCommand());
		if(clz == null)
			return;
		
		try {
			Constructor<? extends GraphOptionsPane> ctr = clz.getConstructor(ApplicationMain.class);
			GraphOptionsPane optionPane = ctr.newInstance(parent);
			parent.setOptionPane(optionPane);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
