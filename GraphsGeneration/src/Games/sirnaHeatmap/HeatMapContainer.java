package Games.sirnaHeatmap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HeatMapContainer extends JFrame {

	private static final long serialVersionUID = -4266517992127846013L;
	
	private JScrollPane scrollPane;

	public HeatMapContainer(Collection<Component> collection) {
		setTitle("Scrolling Pane Application");
		setSize(400, 600);
		setBackground(Color.gray);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		
		getContentPane().add(topPanel);
		
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(collection.size(),1));
		
		for (Component cmp : collection) {
			pan.add(cmp);
		}
		
		scrollPane = new JScrollPane();
		scrollPane.getViewport().add(pan);
		topPanel.add(scrollPane, BorderLayout.CENTER);
	}

}
