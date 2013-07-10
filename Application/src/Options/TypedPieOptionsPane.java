package Options;

import java.awt.Button;
import java.awt.Checkbox;
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
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import reader.ReadDetails;
import utils.FileChooserWindow;
import utils.ReadInput;
import app.ApplicationMain;
import app.typedPies.AlignmentTypesPies;
import app.typedPies.TypedPiesInput;

public class TypedPieOptionsPane extends GraphOptionsPane {
	
	private static final long serialVersionUID = 6095515619983723324L;
	
	private JTabbedPane tabbedPane;
	private JTextField genomeRefText;
	private Checkbox complementaryStrand;
	
	public TypedPieOptionsPane(ApplicationMain parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		setLayout(new MigLayout("", "[100px][][]", "[28px][23px][2px][57px][22px][2px][23px][22px,grow]"));
		
		Label Title = new Label("Pies");
		Title.setFont(new Font("Dialog", Font.BOLD, 16));
		add(Title, "cell 0 0 3 0,alignx center");
		
		Label genomeRefLabel = new Label("Reference");
		genomeRefLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		add(genomeRefLabel, "cell 0 1");
		
		genomeRefText = new JTextField("C:/cygwin/home/sharon.adar/Data/GO/ce_aligns.txt");
		genomeRefText.setColumns(10);
		add(genomeRefText, "flowx,cell 1 1 2 1,growx");

		Button genomeRefChooser = new Button("...");
		genomeRefChooser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				genomeRefText.setText(FileChooserWindow.chooseFile());
			}
		});
		add(genomeRefChooser, "cell 3 1");
		
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
		
		complementaryStrand = new Checkbox("Complementary strand");
		add(complementaryStrand, "cell 0 6 2 1");
		
		generateButton = new Button("Generate");
		generateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				generateRequest();
			}
		});
		
		progressBar = new JProgressBar();
		add(progressBar, "cell 0 7,growx,aligny bottom");
		add(generateButton, "cell 2 7,growx,aligny bottom");

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

		TypedPiesInput params = new TypedPiesInput(genomeRefText.getText(), inputs, complementaryStrand.getState());
		AlignmentTypesPies tsr = new AlignmentTypesPies(params);
		
		 Map<String, Component> graphs = tsr.generateGraph();
		 for (Entry<String, Component> frame : graphs.entrySet()) {
			 parent.getTabsPane().addTab(frame.getKey(), null, frame.getValue(), null);
		}
	}

}
