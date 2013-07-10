package Options;

import java.awt.Button;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JProgressBar;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import utils.FileChooserWindow;
import app.ApplicationMain;
import app.pies.GeneratePieGraph;

public class PiesOptionsPane extends GraphOptionsPane {
	
	private static final long serialVersionUID = 6095515619983723324L;
	
	private JTextField align;
	
	public PiesOptionsPane(ApplicationMain parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		setLayout(new MigLayout("", "[100px][][]", "[28px][28px][28px][28px][28px][28px][28px][22px,grow]"));
		
		Label Title = new Label("Pies");
		Title.setFont(new Font("Dialog", Font.BOLD, 16));
		add(Title, "cell 0 0 3 0,alignx center");
		
		Label alignmentInput1Label = new Label("Input file");
		add(alignmentInput1Label, "cell 0 1");
		
		align = new JTextField("C:/cygwin/home/sharon.adar/Data/Samples/sample_21.21U.align");
		add(align, "flowx,cell 1 1,growx");
		
		Button chooseFileButtonAlignment = new Button("...");
		chooseFileButtonAlignment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				align.setText(FileChooserWindow.chooseFile());
			}
		});
		add(chooseFileButtonAlignment, "cell 2 1");
		
		generateButton = new Button("Generate");
		generateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				generateRequest();
			}
		});
		
		progressBar = new JProgressBar();
		add(progressBar, "cell 0 7,growx,aligny bottom");
		add(generateButton, "cell 1 7,growx,aligny bottom");

	}

	@Override
	protected void generateGraphs() throws Exception {
		GeneratePieGraph pie = new GeneratePieGraph(align.getText());
		Map<String, Component> graphs = pie.generateGraph();
		for (Entry<String, Component> frame : graphs.entrySet()) {
			parent.getTabsPane().addTab(frame.getKey(), null, frame.getValue(), null);
		}
	}

}
