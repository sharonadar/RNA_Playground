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

import net.miginfocom.swing.MigLayout;
import reader.ReadDetails;
import utils.RangeInput;
import utils.ReadInput;
import app.ApplicationMain;
import app.specificReads.SpecificRangeInput;
import app.specificReads.TestSpecificRange;

public class RangeAlignmentOptionsPane extends GraphOptionsPane {
	
	private static final long serialVersionUID = 6095515619983723324L;
	
	private JTabbedPane tabbedPane;
	private RangeInput inputRanges;
	
	public RangeAlignmentOptionsPane(ApplicationMain parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		setLayout(new MigLayout("", "", "[][][][][grow]"));
		
		Label Title = new Label("Range alignment");
		Title.setFont(new Font("Dialog", Font.BOLD, 16));
		add(Title, "cell 0 0 2 1,alignx center");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 1 3 1, growx");
		
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
		
		add(addNewInput, "cell 0 2, growx");
		
		inputRanges = new RangeInput();
		add(inputRanges, "cell 0 3 3 1, growx");
		
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

		SpecificRangeInput params = new SpecificRangeInput(
				inputs, inputRanges.getRange());
		
		TestSpecificRange tsr = new TestSpecificRange(params);
		
		 Map<String, Component> graphs = tsr.generateGraph();
		 for (Entry<String, Component> frame : graphs.entrySet()) {
			 parent.getTabsPane().addTab(frame.getKey(), null, frame.getValue(), null);
		}
	}

}
