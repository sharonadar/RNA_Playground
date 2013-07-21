package app.comparison.multi;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import reader.AlignmentHandlersIfc;
import reader.AlignmentReader;
import reader.ReadDetails;
import alignment.Alignment;
import app.comparison.ComparisonGraphGenerationParameters;
import app.comparison.GraphGroupingOptions;
import app.comparison.GroupNameOptions;

import common.filters.EmptyFilter;
import common.filters.PrimaryAlignmentFilter;

import ensembl.EnsambleGenes;
import ensembl.EnsambleParser;
import ensembl.EnsambleRef;
import ensembl.general.EnsambleTypedRef;
import ensembl.general.ReferenceType;
import general.Chromosome;
import general.range.Range;

public class ComparisonGraphMultiPrinter {
	
	private static final int MARGIN = 1000;
	
	public static void main(String[] args) throws Exception {
		List<ReadDetails> inputs = getInputFiles();
		ComparisonGraphGenerationParameters params = new ComparisonGraphGenerationParameters
				("C:/cygwin/home/sharon.adar/Data/GO/ce_aligns.txt", 
						inputs);
		
		params.setComplementaryStrand(true);
		params.setGraphGroup(GraphGroupingOptions.CHROMOSOME.name());
		params.setGroupName(GroupNameOptions.GENE_NAME.name());
		String[] values = new String[ReferenceType.values().length];
		int i = 0;
		for (ReferenceType itr : ReferenceType.values()) {
			values[i++] = itr.name();
		}
		params.setRefTypes(values);
		
		EnsambleGenes<EnsambleTypedRef> references = getGeneList(params);
		
		
		ComparisonGraphGeneratorReader generator = 
				new ComparisonGraphGeneratorReader(new ComparisonGraphGeneratorContext(references, params));
		generator.handleReads(params.getInputs(), new PrimaryAlignmentFilter());
	}

	protected static List<ReadDetails> getInputFiles() {
		List<ReadDetails> inputs = new ArrayList<ReadDetails>();
//		 String name = "15";
//		String name = "26G";
		String name = "22G";
		inputs.add(new ReadDetails("2." + name,
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_2." + name + ".align",
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_2." + name + ".count"));
		inputs.add(new ReadDetails("6." + name,
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_6." + name + ".align",
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_6." + name	+ ".count"));
		inputs.add(new ReadDetails("7." + name,
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_7." + name	+ ".align",
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_7." + name	+ ".count"));
		inputs.add(new ReadDetails("21." + name,
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_21." + name + ".align",
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_21." + name + ".count"));
		inputs.add(new ReadDetails("22." + name,
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_22." + name + ".align",
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_22." + name	+ ".count"));
		inputs.add(new ReadDetails("29." + name,
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_29." + name	+ ".align",
				"C:/cygwin/home/sharon.adar/Data/Samples/sample_29." + name	+ ".count"));
		return inputs;
	}

	protected static EnsambleGenes<EnsambleTypedRef> getGeneList(
			ComparisonGraphGenerationParameters params) throws Exception {
		
		EnsambleParser<EnsambleTypedRef> parser = new EnsambleParser<EnsambleTypedRef>(EnsambleTypedRef.class);
		EnsambleGenes<EnsambleTypedRef> references = 
				parser.parseFile(params.getReferenceFileName(), new EmptyFilter<EnsambleTypedRef>(), MARGIN);
		
		// Type
//		EnsambleGenes<EnsambleTypedRef> references = 
//		parser.parseFile(params.getReferenceFileName(), new EnsambleReferenceTypeFilter(types), MARGIN);
		
		// Name
//		List<String> geneNames = Arrays.asList(new String[]{"C01A2.1","C02B8.2","C11G6.2","C35D6.3","C36A4.11",
//				"C40A11.10","CD4.8","E01G4.5","E01G4.7","F31A9.2","F31A9.4","F39E9.7","F39F10.4","F52D2.6","F55A4.4",
//				"F55C9.3","F55C9.5","H09G03.1","H16D19.4","K02E2.6","K06B9.6","T05E12.8","T08B6.2","T25G12.11","W04B5.1",
//				"W04B5.2","W05H12.2","Y105C5A.14","Y116A8B.1","Y116F11A.1","Y17D7B.4","Y17D7C.1","Y37E11B.2","Y41C4A.17",
//				"Y43F8B.9","Y57G11C.51","Y71A12B.2","Y71A12B.3","Y82E9BR.20","Y82E9BR.6","ZC132.4","ZK380.5","ZK402.2",
//				"ZK402.3","ZK402.5"});
		
//		EnsambleGenes<EnsambleTypedRef> references = 
//				parser.parseFile(params.getReferenceFileName(), 
//						new EnsambleGeneNameFilter<EnsambleTypedRef>(geneNames), MARGIN);
		return references;
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
								Arrays.asList(new AlignmentHandlersIfc[] { new ComparisonGraphMultipleHandler2(chr.name(), chr.name(), groupName) }));
				}
				break;
			case TYPE:
				for (String refType : params.getRefTypes()) {
					containers.put(refType,
								Arrays.asList(new AlignmentHandlersIfc[] { new ComparisonGraphMultipleHandler2(refType, refType, groupName) }));
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
			for (EnsambleRef ref : references.get(align.getChromosome(), getRelevantStrand(align), range, MARGIN)) {
				
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
