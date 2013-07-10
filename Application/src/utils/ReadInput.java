package utils;

import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import reader.ReadDetails;

public class ReadInput extends Panel{
	
	private static final long serialVersionUID = -4030829461321320648L;
	
	private JTextField align;
	private JTextField count;
	
	public ReadInput() {
		setLayout(new MigLayout("", "[][grow][]", ""));
		
		Label alignmentInput1Label = new Label("Alignment file");
		add(alignmentInput1Label, "cell 0 0");
		
		align = new JTextField("C:/cygwin/home/sharon.adar/Data/Samples/sample_21.15.align");
		add(align, "flowx,cell 1 0,growx");
		
		Button chooseFileButtonAlignment = new Button("...");
		chooseFileButtonAlignment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				align.setText(FileChooserWindow.chooseFile());
			}
		});
		add(chooseFileButtonAlignment, "cell 2 0");
		
		Label countFileInput1Label = new Label("Count File");
		add(countFileInput1Label, "cell 0 1");
		
		count = new JTextField("C:/cygwin/home/sharon.adar/Data/Samples/sample_21.15.count");
		add(count, "flowx,cell 1 1,growx");
		
		Button chooseFileButtonCount = new Button("...");
		chooseFileButtonCount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				count.setText(FileChooserWindow.chooseFile());
			}
		});
		add(chooseFileButtonCount, "cell 2 1");
	}
	
	public ReadDetails getReadDetails() {
		return new ReadDetails((new File(align.getText())).getName(), align.getText(), count.getText());
	}

}
