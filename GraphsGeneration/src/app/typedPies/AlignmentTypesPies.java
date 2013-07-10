package app.typedPies;

import java.awt.Component;
import java.util.Map;

import common.filters.EmptyFilter;
import common.filters.PrimaryAlignmentFilter;

import ensembl.EnsambleGenes;
import ensembl.EnsambleParser;
import ensembl.general.EnsambleTypedRef;


public class AlignmentTypesPies {
	
	private TypedPiesInput params;
	
	public AlignmentTypesPies(TypedPiesInput params) {
		this.params = params;
	}
	
	public Map<String, Component> generateGraph() throws Exception {
		
		EnsambleParser<EnsambleTypedRef> parser = new EnsambleParser<EnsambleTypedRef>(EnsambleTypedRef.class);
		EnsambleGenes<EnsambleTypedRef> categories = 
				parser.parseFile(params.getReferenceFileName(),	new EmptyFilter<EnsambleTypedRef>());
		
		AlignmentTypesPiesReader generator = new AlignmentTypesPiesReader(categories, params.isComplementaryStrand());
		generator.handleReads(params.getInputs(), new PrimaryAlignmentFilter());
		return generator.getResults();
	}
	
}
