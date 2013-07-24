package Games.sirnaHeatmap;

import java.awt.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import reader.ReadDetails;
import app.specificReads.SpecificReadsInput;

public class SiRnaHeatmapGenerator {
	public static void main(String[] args) throws Exception {
		String genePath = "C:\\cygwin\\home\\sharon.adar\\Data\\GO\\ce_aligns.txt";
		
		List<ReadDetails> inputs = Arrays.asList(new ReadDetails[]{new ReadDetails("sample_2", 
				"C:\\cygwin\\home\\sharon.adar\\Data\\Samples\\sample_2.22g.align", 
				"C:\\cygwin\\home\\sharon.adar\\Data\\Samples\\sample_2.22g.count"),
				new ReadDetails("sample_21", 
						"C:\\cygwin\\home\\sharon.adar\\Data\\Samples\\sample_21.22g.align", 
						"C:\\cygwin\\home\\sharon.adar\\Data\\Samples\\sample_21.22g.count")});
		
		List<String> genes = Arrays.asList(new String[]{"W04B5.2","Y17D7B.4","H09G03.1","K02E2.6",
				"K06B9.6","K08D12.4","T08B6.2","W04B5.1","F39E9.10","F59H5.5","Y17D7B.7","Y36E3A.2","Y46D2A.1"});
		
		SpecificReadsInput params = new SpecificReadsInput(genePath, inputs, genes);
		SiRnaHeatMap heat = new SiRnaHeatMap(params);
		Map<String, List<Component>> graphs = heat.generateGraph();

		JFrame frame = new HeatMapContainer(inputs, graphs);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
