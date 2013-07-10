package general;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class GraphUtilities {

	public static void saveToFile(String title, JFreeChart chart) {
		try {
			ChartUtilities.saveChartAsJPEG(
					new File("C:/cygwin/home/sharon.adar/tmp/" + title + ".jpg"), 
					chart, 800, 500);
		} catch (IOException e) {
			System.err.println("Failed to save file");
		}
	}
}
