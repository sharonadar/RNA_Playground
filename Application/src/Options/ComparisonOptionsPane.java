package Options;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
import app.comparison.ComparisonGraphGenerationParameters;
import app.comparison.ComparisonGraphGenerator;
import app.comparison.GraphGroupingOptions;
import app.comparison.GroupNameOptions;
import ensembl.general.ReferenceType;


public class ComparisonOptionsPane extends GraphOptionsPane{
	
	private static final long serialVersionUID = -1617801619822990769L;

	private JTabbedPane tabbedPane;
	private JTextField genomeRefText;
	private Checkbox complementaryStrand;
	private Label referenceType;
	private Choice groupNameChoice;
	private Choice graphGroupingChoice;
	private List referenceTypeList;
	
	public ComparisonOptionsPane(ApplicationMain parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		
		setLayout(new MigLayout("", "[][97.00,grow]", "[39.00][24.00][10.00][][][][][29.00][][20.00][][][][][61.00][grow]"));
		
		Label Title = new Label("Comparison graph");
		Title.setFont(new Font("Dialog", Font.BOLD, 16));
		add(Title, "cell 0 0 2 1,alignx center");
		
		Label genomeRefLabel = new Label("Reference");
		genomeRefLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		add(genomeRefLabel, "cell 0 1");
		
		genomeRefText = new JTextField("C:/cygwin/home/sharon.adar/Data/GO/ce_aligns.txt");
		genomeRefText.setColumns(10);
		add(genomeRefText, "flowx,cell 1 1,growx");
		
		JSeparator separator_1 = new JSeparator();
		add(separator_1, "cell 0 2 2 1,growx");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 3 3 1, growx");
		tabbedPane.addTab("Input 1", null, new ReadInput(), null);
		tabbedPane.addTab("Input 2", null, new ReadInput(), null);
		
		JSeparator separator = new JSeparator();
		add(separator, "cell 0 4 2 1,growx");
		
		referenceType = new Label("Reference type");
		add(referenceType, "cell 0 5");
		
		referenceTypeList = new List(4, true);
		for (ReferenceType option : ReferenceType.values()) {
			referenceTypeList.add(option.toString());
		}
		add(referenceTypeList, "cell 1 5");
		
		Label groupNameLabel = new Label("Group name");
		add(groupNameLabel, "cell 0 7");
		
		groupNameChoice = new Choice();
		for (GroupNameOptions option : GroupNameOptions.values()) {
			groupNameChoice.addItem(option.toString());
		}
		add(groupNameChoice, "cell 1 7,grow");
		
		Label graphGroupingLabel = new Label("Graph grouping");
		add(graphGroupingLabel, "cell 0 8");
		
		graphGroupingChoice = new Choice();
		for (GraphGroupingOptions option : GraphGroupingOptions.values()) {
			graphGroupingChoice.addItem(option.toString());
		}
		add(graphGroupingChoice, "flowy,cell 1 8,growx");
		
		complementaryStrand = new Checkbox("Complementary strand");
		add(complementaryStrand, "cell 0 9 2 1");
		
		Button chooseFileButtonGenomeReference = new Button("...");
		chooseFileButtonGenomeReference.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				genomeRefText.setText(FileChooserWindow.chooseFile());
			}
		});
		add(chooseFileButtonGenomeReference, "cell 1 1");
		
		generateButton = new Button("Generate");
		generateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				generateRequest();
			}
		});
		
		progressBar = new JProgressBar();
		add(progressBar, "cell 0 15,growx,aligny bottom");
		add(generateButton, "cell 1 15,growx,aligny bottom");
	}

	@Override
	protected void generateGraphs() throws Exception {
		java.util.List<ReadDetails> inputs = new ArrayList<ReadDetails>();
		for (int i = 0 ; i < tabbedPane.getComponentCount(); ++i) {
			ReadInput input = (ReadInput)tabbedPane.getComponentAt(i);
			inputs.add(input.getReadDetails());
		}
		ComparisonGraphGenerationParameters input = 
				new ComparisonGraphGenerationParameters(genomeRefText.getText(),inputs);
		input.setComplementaryStrand(complementaryStrand.getState());
		input.setGraphGroup(graphGroupingChoice.getSelectedItem());
		input.setGroupName(groupNameChoice.getSelectedItem());
		input.setRefTypes(referenceTypeList.getSelectedItems());

		ComparisonGraphGenerator asd = new ComparisonGraphGenerator(input);
		Map<String, Component> graphs = asd.generateGraph();
		for (Entry<String, Component> frame : graphs.entrySet()) {
			parent.getTabsPane().addTab(frame.getKey(), null, frame.getValue(),	null);
		}
	}
	
}
