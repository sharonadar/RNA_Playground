package Games.sirnaHeatmap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import reader.ReadDetails;

public class HeatMapContainer extends JFrame {

	private static final long serialVersionUID = -4266517992127846013L;
	
	private JScrollPane scrollPane;

	public HeatMapContainer(List<ReadDetails> inputs,
			Map<String, List<Component>> graphs) {
		setTitle("Scrolling Pane Application");
		setSize(800, 500);
		setBackground(Color.gray);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		
		getContentPane().add(topPanel);
		
		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		
		int i = 1;
		for (ReadDetails rd : inputs) {
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridx = i++;
			constraints.gridy = 0;
			pan.add(new Label(rd.getName()),constraints);
		}
		
		int j = 0;
		for (Entry<String, List<Component>> cmps : graphs.entrySet()) {
			j++;
			i = 0;
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridx = i++;
			constraints.gridy = j;
			pan.add(new Label(cmps.getKey()), constraints);
			
			for (Component cmp : cmps.getValue()) {
				constraints = new GridBagConstraints();
				constraints.gridx = i++;
				constraints.gridy = j;
				pan.add(cmp, constraints);
			}
		}
		
		scrollPane = new JScrollPane();
		scrollPane.getViewport().add(pan);
		topPanel.add(scrollPane, BorderLayout.CENTER);
	}

}
