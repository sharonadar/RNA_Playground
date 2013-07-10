package ensembl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ensembl.general.RangeGroup;
import general.Chromosome;
import general.ParserFilter;
import general.range.Range;
import general.range.RangeComparator;

public class EnsambleParser<EReference extends EnsambleRef>{
	
	public Class<EReference> refType;
	public Class<RangeGroup<EReference>> groupType;
	
	@SuppressWarnings("unchecked")
	public EnsambleParser(Class<EReference> refType) {
		this.refType = refType;
		this.groupType = (Class<RangeGroup<EReference>>) (new RangeGroup<EReference>(null)).getClass();
	}
	
	public EnsambleGenes<EReference> parseFile(
			String fileName, ParserFilter<EReference> filter) throws Exception {
		return parseFile(fileName, filter, 0);
	}
	
	public EnsambleGenes<EReference> parseFile(
			String fileName, ParserFilter<EReference> filter, int mergin) throws Exception {
		
		List<Map<Chromosome, Map<Range, RangeGroup<EReference>>>> clusterRangeTree = 
				new ArrayList<Map<Chromosome, Map<Range, RangeGroup<EReference>>>>();
		clusterRangeTree.add(new HashMap<Chromosome, Map<Range,RangeGroup<EReference>>>());
		clusterRangeTree.add(new HashMap<Chromosome, Map<Range,RangeGroup<EReference>>>());
		
		for (Chromosome chr : Chromosome.values()) {
			clusterRangeTree.get(0).put(chr, new TreeMap<Range, RangeGroup<EReference>>(new RangeComparator(mergin)));
			clusterRangeTree.get(1).put(chr, new TreeMap<Range, RangeGroup<EReference>>(new RangeComparator(mergin)));
		}
		
		Constructor<EReference> refCtor = refType.getConstructor(String.class);
		Constructor<RangeGroup<EReference>> groupCtor = groupType.getConstructor(Range.class);
		
		BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		String line;
		boolean first = true;
		while ((line = br.readLine()) != null) {
			if(first) {
				first = false;
				continue;
			}
			
			EReference gc = refCtor.newInstance(line);
			if(!filter.shouldKeep(gc))
				continue;
		   
		   Range range = gc.getRange();
		   int strand = (gc.getStrand() == 1) ? 0 : 1;
		   Chromosome chr = gc.getChromosomeName();
		   RangeGroup<EReference> group = clusterRangeTree.get(strand).get(chr).get(range);
		   if(group == null) {
			   group = groupCtor.newInstance(range);
			   group.add(gc);
			   clusterRangeTree.get(strand).get(chr).put(range, group);
		   } else {
			   // We don't want to count once for each gene
			   boolean shouldAdd = true;
				for (EnsambleRef ensambleRef : group) {
					if (ensambleRef.getEnsemblGeneID().equals(gc.getEnsemblGeneID())) {
						shouldAdd = false;
						break;
					}
				}
				if(shouldAdd) {
					// Add and unite with other groups
					RangeGroup<EReference> newGroup = groupCtor.newInstance(range);
					newGroup.add(gc);
					while(true) {
						group = clusterRangeTree.get(strand).get(chr).remove(range);
						if(group == null)
							break;
						newGroup.unite(group);
					}
					clusterRangeTree.get(strand).get(chr).put(newGroup.getRange(), newGroup);
				}
		   }
		}
		br.close();
		return new EnsambleGenes<EReference>(clusterRangeTree);
	}
	
}
