package Options;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
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
import utils.ReadInput;
import app.ApplicationMain;
import app.alignBin.AlignmentBinsComparison;
import app.alignBin.AlignmentBinsInput;
import app.alignBin.AlignmentFunctionOptions;

public class AlignmentOptionsPane extends GraphOptionsPane {
	
	private static final long serialVersionUID = 6095515619983723324L;
	
	private JTabbedPane tabbedPane;
	private Checkbox seperateGraphs;
	private Choice functionChoice;

	public AlignmentOptionsPane(ApplicationMain parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		setLayout(new MigLayout("", "[100px][][]", "[28px][28px][28px][28px][28px][28px][28px][22px,grow]"));
		
		Label Title = new Label("Alignment bin comparison");
		Title.setFont(new Font("Dialog", Font.BOLD, 16));
		add(Title, "cell 0 0 3 0,alignx center");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 1 3 1, growx");
		generateInputTab(tabbedPane);
		generateInputTab(tabbedPane);
		
		seperateGraphs = new Checkbox("Separate graphs");
				add(seperateGraphs, "cell 0 2 2 1");
		
		Label functionLabel = new Label("function");
		functionLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		add(functionLabel, "cell 0 3");
		
		functionChoice = new Choice();
		for (AlignmentFunctionOptions option : AlignmentFunctionOptions.values()) {
			functionChoice.addItem(option.toString());
		}
		add(functionChoice, "flowx,cell 1 3 1 1,growx");
		
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

		AlignmentBinsInput params = new AlignmentBinsInput(inputs, seperateGraphs.getState(), 
				AlignmentFunctionOptions.valueOf(functionChoice.getSelectedItem()));
		
		AlignmentBinsComparison abc = new AlignmentBinsComparison(params);
		
		 Map<String, Component> graphs = abc.generateGraph();
		 for (Entry<String, Component> frame : graphs.entrySet()) {
			 parent.getTabsPane().addTab(frame.getKey(), null, frame.getValue(), null);
		}
	}

}
