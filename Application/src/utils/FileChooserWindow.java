package utils;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;



public class FileChooserWindow {

	public static String chooseFile() {
		JFileChooser fileChooser = new JFileChooser();
		try {
		        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		        SwingUtilities.updateComponentTreeUI(fileChooser);
		} catch (Exception e) {
			System.out.println("Can't load file");
			throw new RuntimeException("Can't open file browser");
		}
		 
		int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	return (fileChooser.getSelectedFile().getAbsolutePath());
        }
        
        return null;
	}

}
