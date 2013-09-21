package app.alignBin;

import java.awt.Component;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import reader.AlignmentHandlersIfc;
import reader.AlignmentReader;
import reader.ReadDetails;
import alignment.Alignment;

import common.filters.PrimaryAlignmentFilter;

public class AlignmentBinGenerator {
	
	private AlignmentBinsInput params;
	
	public AlignmentBinGenerator(AlignmentBinsInput params) {
		this.params = params;
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
//			if(align.getSequence().length() != 21)
//				return false;
//			if(align.getSequence().charAt(0) != 'T')
//				return false;
			return true;
		}
	}

	public Map<String, Component> generateGraph() throws Exception {
		
		int geneSize = (int) (new File(params.getReferenceName())).length();
		
		AlignmentBinReader generator = new AlignmentBinReader(new AlignmentBinGeneratorParams(params.getInputs().size(), geneSize));
		generator.handleReads(params.getInputs(), new MyFilter());
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
