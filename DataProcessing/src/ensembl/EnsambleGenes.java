package ensembl;

import ensembl.general.RangeGroup;
import general.Chromosome;
import general.range.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnsambleGenes<EReference extends EnsambleRef> {
	
	private List<Map<Chromosome, Map<Range, RangeGroup<EReference>>>> genes;
	
	public EnsambleGenes(
			List<Map<Chromosome, Map<Range, RangeGroup<EReference>>>> clusterRangeTree) {
		this.genes = clusterRangeTree;
	}
	
	public List<EReference> get(Chromosome chr, boolean plusStrand, Range range) {
		return get(chr, plusStrand, range, 0);
	}
	
	public List<EReference> get(Chromosome chr, boolean plusStrand, Range range, int mergins) {
		int sense = plusStrand ? 0 : 1;
		RangeGroup<EReference> relevantRefs = genes.get(sense).get(chr).get(range);
		if(relevantRefs != null)
			return relevantRefs.getByExactRange(range, mergins);
		return new ArrayList<EReference>();
	}
	
	public List<EReference> getAll() {
		List<EReference> results = new ArrayList<EReference>();
		for (Map<Chromosome, Map<Range, RangeGroup<EReference>>> strand : genes) {
			for (Map<Range, RangeGroup<EReference>> chr : strand.values()) {
				for (RangeGroup<EReference> cateories : chr.values()) {
					results.addAll(cateories);
				}
			}
		}
		return results;
	}

}
 