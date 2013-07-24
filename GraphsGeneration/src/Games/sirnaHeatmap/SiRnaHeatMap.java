package Games.sirnaHeatmap;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import reader.AlignmentHandlersIfc;
import reader.AlignmentReader;
import reader.ReadDetails;
import alignment.Alignment;
import app.handlers.SingleColoredGeneAlignmentStatistics;
import app.specificReads.SpecificReadsInput;

import common.filters.PrimaryAlignmentFilter;

import ensembl.EnsambleGenes;
import ensembl.EnsambleParser;
import ensembl.goCategory.GoCategory;
import filters.EnsambleGeneNameFilter;
import general.range.Range;

public class SiRnaHeatMap {
	
	private SpecificReadsInput params;
	private static final int MERGIN = 1000;
	private static final int N_BINS = 100;
	
	public SiRnaHeatMap(SpecificReadsInput params) {
		this.params = params;
	}
	
	public Map<String, List<Component>> generateGraph() throws Exception {
		EnsambleParser<GoCategory> parser = new EnsambleParser<GoCategory>(GoCategory.class);
		EnsambleGenes<GoCategory> categories = 
				parser.parseFile(params.getReferenceFileName(), 
						new EnsambleGeneNameFilter<GoCategory>(params.getGeneNames()), MERGIN);
		
		TestSpecificReadsReader generator = new TestSpecificReadsReader(categories);
		generator.handleReads(params.getInputs(), new PrimaryAlignmentFilter());
		return generator.getResults(params.getInputs());
	}

	private static class TestSpecificReadsReader extends AlignmentReader {

		public TestSpecificReadsReader(EnsambleGenes<GoCategory> context) {
			super(context);
		}
		
		public Map<String, List<Component>> getResults(List<ReadDetails> resOrder) {
			Map<String, List<Component>> res = new HashMap<String, List<Component>>();
			for (Entry<String, List<AlignmentHandlersIfc>> itr : containers.entrySet()) {
				List<Component> comps = new ArrayList<Component>();
				for (AlignmentHandlersIfc handler : itr.getValue()) {
					Map<String, Component> handlerRes = handler.collectResults(); 
					for (ReadDetails rd : resOrder) {
						comps.add(handlerRes.get(rd.getName()));
					}
				}
				res.put(itr.getKey(), comps);
			}
			return res;
		}
		
		@SuppressWarnings("unchecked")
		protected Map<String, List<AlignmentHandlersIfc>> createContainers() {
			EnsambleGenes<GoCategory> categoriesHandler = (EnsambleGenes<GoCategory>) context;
			List<GoCategory> categories = categoriesHandler.getAll();
			
			Map<String, List<AlignmentHandlersIfc>> containers = new HashMap<String, List<AlignmentHandlersIfc>>();
			
			for (GoCategory category : categories) {
				List<AlignmentHandlersIfc> handlers = new ArrayList<AlignmentHandlersIfc>();
				String geneName = category.getEnsemblGeneID();
				handlers.add(new SingleColoredGeneAlignmentStatistics(N_BINS, MERGIN, geneName + "_align", category.getRange()));
				containers.put(geneName, handlers);
			}
			return containers;
		}
	
		@SuppressWarnings("unchecked")
		protected void handleSingleAlignment(ReadDetails read, Alignment align, double count) {
			EnsambleGenes<GoCategory> categories = (EnsambleGenes<GoCategory>) context;
			
			Range range = align.getRange();
			
			for (GoCategory category : categories.get(align.getChromosome(), align.isPlusStrand(), range, MERGIN)) {
				for (AlignmentHandlersIfc handler : containers.get(category.getEnsemblGeneID())) {
					handler.handleAlignment(Boolean.TRUE, read.getName(), align, count);
				}
			}
			
			for (GoCategory category : categories.get(align.getChromosome(), !align.isPlusStrand(), range, MERGIN)) {
				for (AlignmentHandlersIfc handler : containers.get(category.getEnsemblGeneID())) {
					handler.handleAlignment(Boolean.FALSE, read.getName(), align, count);
				}
			}
		}
	}	
}
