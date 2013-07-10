package Options;

import java.awt.Button;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import reader.ReadDetails;
import utils.FileChooserWindow;
import utils.ReadInput;
import app.ApplicationMain;
import app.specificReads.SpecificReadsInput;
import app.specificReads.TestSpecificReads;

public class GeneAlignmentOptionsPane extends GraphOptionsPane {
	
	private static final long serialVersionUID = 6095515619983723324L;
	
	private JTextField genomeRefText;
	private JTabbedPane tabbedPane;
	private TextArea geneNamesText;
	
	public GeneAlignmentOptionsPane(ApplicationMain parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		setLayout(new MigLayout("", "[100px][8px][330px]", "[28px][23px][2px][57px][22px][2px][23px][22px,grow]"));
		
		Label Title = new Label("Gene alignment");
		Title.setFont(new Font("Dialog", Font.BOLD, 16));
		add(Title, "cell 0 0 2 1,alignx center");
		
		Label genomeRefLabel = new Label("Reference");
		genomeRefLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		add(genomeRefLabel, "cell 0 1");
		
		genomeRefText = new JTextField("C:/cygwin/home/sharon.adar/Data/GO/ce_aligns.txt");
		genomeRefText.setColumns(10);
		add(genomeRefText, "flowx,cell 2 1,growx");
		
		Button genomeRefChooser = new Button("...");
		genomeRefChooser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				genomeRefText.setText(FileChooserWindow.chooseFile());
			}
		});
		add(genomeRefChooser, "cell 2 1");
		
		JSeparator separator_1 = new JSeparator();
		add(separator_1, "cell 0 2 3 1, growx");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 3 3 1, growx");
		
		generateInputTab(tabbedPane);
		
		Button addNewInput = new Button("add Input");
		addNewInput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				generateInputTab(tabbedPane);
			}
		});
		add(addNewInput, "cell 0 4, growx");
		
		JSeparator separator_2 = new JSeparator();
		add(separator_2, "cell 0 5 3 1,growx");
		
		generateButton = new Button("Generate");
		generateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				generateRequest();
			}
		});
		
		Label geneNamesLabel = new Label("Gene names");
		geneNamesLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		add(geneNamesLabel, "flowx,cell 0 6");
		
		geneNamesText = new TextArea("F13B12.1,F40F8.1,W08F4.3,B0336.3,F26A3.5,B0336.7,"
				+ "C04F12.5,F25B3.6,C36C9.1,F53C11.5,F33H2.3,T28D9.1");
		add(geneNamesText, "flowx,cell 2 6");
		
		progressBar = new JProgressBar();
		add(progressBar, "cell 0 7,growx,aligny bottom");
		add(generateButton, "cell 2 7,growx,aligny bottom");
	}

	private void generateInputTab(JTabbedPane tabbedPane) {
		for(int i = 0 ; i < 6 ; ++i) {
			ReadInput panel = new ReadInput();
			tabbedPane.addTab("Input " + tabbedPane.getTabCount(), null, panel, null);
		}
	}

	@Override
	protected void generateGraphs() throws Exception {
		List<String> genes = Arrays.asList(geneNamesText.getText().split(","));
		List<ReadDetails> inputs = new ArrayList<ReadDetails>();
		for (int i = 0 ; i < tabbedPane.getComponentCount(); ++i) {
			ReadInput input = (ReadInput)tabbedPane.getComponentAt(i);
			inputs.add(input.getReadDetails());
		}

		SpecificReadsInput params = new SpecificReadsInput(genomeRefText.getText(), 
				inputs, genes);
		
		TestSpecificReads tsr = new TestSpecificReads(params);
		
		 Map<String, Component> graphs = tsr.generateGraph();
		 for (Entry<String, Component> frame : graphs.entrySet()) {
			 parent.getTabsPane().addTab(frame.getKey(), null, frame.getValue(), null);
		}
	}

}
