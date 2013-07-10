package app.comparison;

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

import ensembl.EnsambleGenes;
import ensembl.EnsambleParser;
import ensembl.EnsambleRef;
import ensembl.general.EnsambleTypedRef;
import filters.EnsambleReferenceTypeFilter;
import general.Chromosome;
import general.range.Range;

public class ComparisonGraphGenerator {

	private final ComparisonGraphGenerationParameters params;
	
	public ComparisonGraphGenerator(ComparisonGraphGenerationParameters params) {
		this.params = params;
	}
	
	public Map<String, Component> generateGraph() throws Exception {
		EnsambleParser<EnsambleTypedRef> parser = new EnsambleParser<EnsambleTypedRef>(EnsambleTypedRef.class);
		EnsambleGenes<EnsambleTypedRef> references = 
				parser.parseFile(params.getReferenceFileName(), new EnsambleReferenceTypeFilter(params.getRefTypes()));
		
		ComparisonGraphGeneratorReader generator = 
				new ComparisonGraphGeneratorReader(new ComparisonGraphGeneratorContext(references, params));
		generator.handleReads(params.getInputs(), new PrimaryAlignmentFilter());
		return generator.getResults();
	}
	
	public static class ComparisonGraphGeneratorContext {
		
		EnsambleGenes<EnsambleTypedRef> references;
		ComparisonGraphGenerationParameters params;
		
		public ComparisonGraphGeneratorContext(EnsambleGenes<EnsambleTypedRef> references,
				ComparisonGraphGenerationParameters params) {
			this.references = references;
			this.params = params;
		}
	}
	
	public static class ComparisonGraphGeneratorReader extends AlignmentReader {

		private ComparisonGraphGenerationParameters inputParams;

		public ComparisonGraphGeneratorReader(Object context) {
			super(context);
			inputParams = ((ComparisonGraphGeneratorContext)context).params;
		}

		@Override
		protected Map<String, List<AlignmentHandlersIfc>> createContainers() {
			ComparisonGraphGenerationParameters params = ((ComparisonGraphGeneratorContext)context).params;
			Map<String, List<AlignmentHandlersIfc>> containers = new HashMap<String, List<AlignmentHandlersIfc>>();
			GraphGroupingOptions options = GraphGroupingOptions.valueOf(params.getGraphGroup());
			GroupNameOptions groupName = GroupNameOptions.valueOf(params.getGroupName());
			switch (options) {
			case CHROMOSOME:
				for (Chromosome chr : Chromosome.values()) {
					containers.put(chr.name(),
								Arrays.asList(new AlignmentHandlersIfc[] { new ComparisonGraphHandler(chr.name(), groupName) }));
				}
				break;
			case TYPE:
				for (String refType : params.getRefTypes()) {
					containers.put(refType,
								Arrays.asList(new AlignmentHandlersIfc[] { new ComparisonGraphHandler(refType, groupName) }));
				}
				break;
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
			EnsambleGenes<EnsambleTypedRef> references = ((ComparisonGraphGeneratorContext)context).references;

			if (align.isSecondary())
				return;

			Range range = align.getRange();

			for (EnsambleRef ref : references.get(align.getChromosome(), getRelevantStrand(align), range)) {
				String graph = getGraphName(ref);

				for (AlignmentHandlersIfc handler : containers.get(graph)) {
					handler.handleAlignment(ref, read.getName(), align,	count);
				}
			}
		}

		private boolean getRelevantStrand(Alignment align) {
			if (inputParams.isComplementaryStrand())
				return !align.isPlusStrand();
			else
				return align.isPlusStrand();
		}

		protected String getGraphName(EnsambleRef ref) {
			GraphGroupingOptions options = GraphGroupingOptions.valueOf(inputParams.getGraphGroup());
			switch (options) {
			case CHROMOSOME:
				return ref.getChromosomeName().toString();
			case TYPE:
				return ((EnsambleTypedRef) ref).getType().toString();
			}
			return null;
		}
	}
}
