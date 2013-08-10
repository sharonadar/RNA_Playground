package app.alignBin;

import general.Chromosome;

import java.awt.Component;
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

public class AlignmentBinsComparison {

	private final AlignmentBinsInput params;
	
	public AlignmentBinsComparison(AlignmentBinsInput params) {
		this.params = params;
	}
	
	public Map<String, Component> generateGraph() throws Exception {
		
		AlignmentBinReader generator = new AlignmentBinReader(params);
		generator.handleReads(params.getInputs(), new PrimaryAlignmentFilter());
		return generator.getResults();
	}
	
	public static class AlignmentBinReader extends AlignmentReader {

		public AlignmentBinReader(Object context) {
			super(context);
		}

		@Override
		protected Map<String, List<AlignmentHandlersIfc>> createContainers() {
			AlignmentBinsInput input = (AlignmentBinsInput)context;
			containers = new HashMap<String, List<AlignmentHandlersIfc>>();
			Chromosome[] chr = Chromosome.values();
			for (Chromosome chromosome : chr) {
				AlignmentFunctionOptions function = input.isSeparateGraphs() ? null : input.getFunction();
				containers.put(chromosome.name(), Arrays.asList(new AlignmentHandlersIfc[] {
						new AlignmentBinHandler(chromosome.name(), chromosome.getLength(), function, 
								input.getInputs().size(), input.isSeparateGraphs())}));
			}
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
			
			Chromosome chr = align.getChromosome();
			List<AlignmentHandlersIfc> handlers = containers.get(chr.name());
			for (AlignmentHandlersIfc handler : handlers) {
				handler.handleAlignment(null, read.getName(), align, count);
			}
		}
	}
}
