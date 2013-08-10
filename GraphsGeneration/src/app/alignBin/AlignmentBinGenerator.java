package app.alignBin;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import reader.AlignmentHandlersIfc;
import reader.AlignmentReader;
import reader.ReadDetails;
import alignment.Alignment;

import common.filters.PrimaryAlignmentFilter;

public class AlignmentBinGenerator {
	
	public static void main(String[] args) throws Exception {
		AlignmentBinGenerator generator = new AlignmentBinGenerator();
		Map<String, Component> res = generator.generateGraph();
		
		JFrame frame = new JFrame();
		JTabbedPane panel = new JTabbedPane();
		for (Entry<String, Component> cmp : res.entrySet()) {
			panel.add(cmp.getKey(), cmp.getValue());
		}
		
		frame.setContentPane(panel);
		frame.setTitle("Genes and transportable areas");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private class AlignmentBinGeneratorParams {
		
		public AlignmentBinGeneratorParams(int nSamples, int length) {
			this.nSamples = nSamples;
			this.length = length;
		}
		int length;
		int nSamples;
	}
	
	private class MyFilter extends PrimaryAlignmentFilter {
		
		@Override
		public boolean shouldKeep(Alignment align) {
			if(!super.shouldKeep(align))
				return false;
			if(align.getSequence().length() != 21)
				return false;
			if(align.getSequence().charAt(0) != 'T')
				return false;
			return true;
		}
	}

	public Map<String, Component> generateGraph() throws Exception {
		
		int geneSize = (int) (new File("C:/cygwin/home/sharon.adar/Data/Genome/Extra/p2NY_Prab-3_rde-4_SL2.txt")).length();
		List<ReadDetails> inputs = new ArrayList<ReadDetails>();
		inputs.add(new ReadDetails("sample_2", "C:/cygwin/home/sharon.adar/Data/Samples/florcuent/sample_2.15.rab.align", 
				 "C:/cygwin/home/sharon.adar/Data/Samples/sample_2.15.count"));
		inputs.add(new ReadDetails("sample_21", "C:/cygwin/home/sharon.adar/Data/Samples/florcuent/sample_21.15.rab.align", 
				 "C:/cygwin/home/sharon.adar/Data/Samples/sample_21.15.count"));
		inputs.add(new ReadDetails("sample_22", "C:/cygwin/home/sharon.adar/Data/Samples/florcuent/sample_22.15.rab.align", 
				 "C:/cygwin/home/sharon.adar/Data/Samples/sample_22.15.count"));
		
		AlignmentBinReader generator = new AlignmentBinReader(new AlignmentBinGeneratorParams(inputs.size(), geneSize));
		generator.handleReads(inputs, new MyFilter());
		return generator.getResults();
	}
	
	public static class AlignmentBinReader extends AlignmentReader {

		public AlignmentBinReader(Object length) {
			super(length);
		}

		@Override
		protected Map<String, List<AlignmentHandlersIfc>> createContainers() {
			AlignmentBinGeneratorParams params = (AlignmentBinGeneratorParams)this.context;
			containers = new HashMap<String, List<AlignmentHandlersIfc>>();
			containers.put(null, Arrays.asList(
					new AlignmentHandlersIfc[] {new AlignmentBinHandler("gene", params.length, null, params.nSamples, false)}));
			return containers;
		}

		public Map<String, Component> getResults() {
			Map<String, Component> res = new HashMap<String, Component>();
			for (Entry<String, List<AlignmentHandlersIfc>> itr : containers.entrySet()) {
				for (AlignmentHandlersIfc handler : itr.getValue()) {
					Map<String, Component> handlerRes = handler.collectResults();
					for (Entry<String, Component> frames : handlerRes.entrySet()) {
						res.put(frames.getKey(), frames.getValue());
					}
				}
			}
			return res;
		}

		@Override
		protected void handleSingleAlignment(ReadDetails read, Alignment align,
				double count) {
			
			List<AlignmentHandlersIfc> handlers = containers.get(null);
			for (AlignmentHandlersIfc handler : handlers) {
				handler.handleAlignment(null, read.getName(), align, count);
			}
		}
	}
}
