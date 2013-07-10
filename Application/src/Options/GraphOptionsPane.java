package Options;

import java.awt.Button;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import app.ApplicationMain;

public abstract class GraphOptionsPane extends JPanel {
	
	private static final long serialVersionUID = -2781641388957659030L;
	
	protected ApplicationMain parent;
	
	protected Button generateButton;
	protected JProgressBar progressBar;
	
	public GraphOptionsPane(ApplicationMain parent) {
		this.parent = parent;
	}
	
	public void generateRequest() {
		generateButton.setEnabled(false);
		progressBar.setIndeterminate(true);
		
		try {
			generateGraphs();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			JOptionPane.showMessageDialog(null, exceptionAsString, 
					"Error occured <" + e.getMessage() +">", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.out.println("Failed while generating graphs");
		}
		
		progressBar.setIndeterminate(false);	
		generateButton.setEnabled(true);
	}

	protected abstract void generateGraphs() throws Exception;

	public abstract void init();

}