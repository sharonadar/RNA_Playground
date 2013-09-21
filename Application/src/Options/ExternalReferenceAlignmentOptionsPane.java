package Options;

import java.awt.Button;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import reader.ReadDetails;
import utils.FileChooserWindow;
import utils.ReadInput;
import app.ApplicationMain;
import app.alignBin.AlignmentBinGenerator;
import app.alignBin.AlignmentBinsInput;

public class ExternalReferenceAlignmentOptionsPane extends GraphOptionsPane {
	
	private static final long serialVersionUID = 6095515619983723324L;
	
	private JTextField refText;
	private JTabbedPane tabbedPane;
	
	public ExternalReferenceAlignmentOptionsPane(ApplicationMain parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		setLayout(new MigLayout("", "", "[][][][][grow]"));
		
		Label Title = new Label("External gene alignment");
		Title.setFont(new Font("Dialog", Font.BOLD, 16));
		add(Title, "cell 0 0 2 1,alignx center");
		
		Label refLabel = new Label("Reference");
		refLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		add(refLabel, "cell 0 1");
		
		refText = new JTextField("C:/cygwin/home/sharon.adar/Data/Genome/Extra/elt-2_promoter.txt");
		refText.setColumns(50);
		add(refText, "flowx,cell 1 1,growx");
		
		Button refChooser = new Button("...");
		refChooser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refText.setText(FileChooserWindow.chooseFile());
			}
		});
		add(refChooser, "cell 2 1");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 2 3 1, growx");
		
		generateInputTab(tabbedPane);
		
		Button addNewInput = new Button("add Input");
		addNewInput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				generateInputTab(tabbedPane);
			}
		});
		
		generateButton = new Button("Generate");
		generateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				generateRequest();
			}
		});
		
		add(addNewInput, "cell 0 3, growx");
		
		progressBar = new JProgressBar();
		add(progressBar, "cell 0 4,growx,aligny bottom");
		add(generateButton, "cell 2 4,growx,aligny bottom");
	}

	private void generateInputTab(JTabbedPane tabbedPane) {
		ReadInput panel = new ReadInput();
		tabbedPane.addTab("Input " + tabbedPane.getTabCount(), null, panel, null);
	}

	@Override
	protected void generateGraphs() throws Exception {
		List<ReadDetails> inputs = new ArrayList<ReadDetails>();
		for (int i = 0 ; i < tabbedPane.getComponentCount(); ++i) {
			ReadInput input = (ReadInput)tabbedPane.getComponentAt(i);
			inputs.add(input.getReadDetails());
		}

		AlignmentBinsInput abi = new AlignmentBinsInput(inputs, refText.getText());
		AlignmentBinGenerator abg = new AlignmentBinGenerator(abi);
		
		 Map<String, Component> graphs = abg.generateGraph();
		 for (Entry<String, Component> frame : graphs.entrySet()) {
			 parent.getTabsPane().addTab(frame.getKey(), null, frame.getValue(), null);
		}
	}

}
