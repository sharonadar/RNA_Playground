package app.comparison.multi;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import reader.AlignmentHandlersIfc;
import alignment.Alignment;
import app.comparison.GroupNameOptions;
import ensembl.EnsambleRef;
import ensembl.goCategory.GoCategory;
import general.range.Range;

public class ComparisonGraphMultipleHandler implements AlignmentHandlersIfc{

	private final String name;
	private final String graphName;
	private final GroupNameOptions groupName;
	private Map<String, Component> graphs = new HashMap<String, Component>();
	private Map<String, Map<String, Double>> values = new HashMap<String, Map<String, Double>>();
	private Map<String, Map<String, Double>> valuesBefore = new HashMap<String, Map<String, Double>>(); 
	private Map<String, Map<String, Double>> valuesAfter = new HashMap<String, Map<String, Double>>(); 
	
	private List<String> names = new ArrayList<String>();
	Map<String, EnsambleRef> aligns = new HashMap<String, EnsambleRef>();
	
	public ComparisonGraphMultipleHandler(String name, String graphName, GroupNameOptions groupName) {
		this.name = name;
		this.graphName = graphName;
		this.groupName = groupName;
	}
	
	@Override
	public void handleAlignment(Object ref, String name, Alignment align,
			double count) {
		EnsambleRef gene = (EnsambleRef) ref;
		String instance = getReferenceName(gene);
		
		if(!aligns.containsKey(instance))
			aligns.put(instance, (EnsambleRef) ref);
		
		
		Range a = align.getRange();
		Range g = gene.getRange();
		
		int value = a.compareTo(g);
		
		if(value < 0) {
			addToCount(valuesBefore, name, instance, count);
		} else if (value > 0) {
			addToCount(valuesAfter, name, instance, count);
		} else {
			addToCount(values, name, instance, count);
		}
	}

	private void addToCount(Map<String, Map<String, Double>> map, String name, String instance, double count) {
		Double value = 0.0;
		if(map.get(name).containsKey(instance))
			value += map.get(name).get(instance);
		
		map.get(name).put(instance, value + count);
	}

	@Override
	public void done() throws Exception {
		Set<String> keys = new HashSet<String>();
		
		for (String name : names) {
			keys.addAll(values.get(name).keySet());
		}
		
		printVals(keys);
	}

	private void printVals(Set<String> keys) {
		for (String key : keys) {
			
			Double sample2 = values.get(names.get(0)).containsKey(key) ? values.get(names.get(0)).get(key) : 0.0;
			Double sample21 = values.get(names.get(1)).containsKey(key) ? values.get(names.get(1)).get(key) : 0.0;
			Double sample22 = values.get(names.get(2)).containsKey(key) ? values.get(names.get(2)).get(key) : 0.0;
			
			if(!shouldPrint(sample2, sample21, sample22))
				continue;
			
			EnsambleRef ref = aligns.get(key);
			printStats(key, ref, sample2, sample21, sample22);
		}
	}

	private void printStats(String key, EnsambleRef ref, Double sample2,
			Double sample21, Double sample22) {
		
		double diff = Math.min(Math.abs(sample21-sample22),Math.abs(sample21-sample2));
		Double value = diff / sample2;
		System.out.print(key +"\t");
		System.out.print(name + "\t");
		System.out.print(ref.getChromosomeName() + "\t" + ref.getStrand() + "\t" + ref.getGeneStart() + "\t");
		printValues(values, key);
		System.out.print(value + "\t");
		
		System.out.print(!(1.5 * Math.abs(sample2 - sample22) >= Math.abs(sample2 - sample21)));
		System.out.print("\t");
		System.out.print(!(sample21 >= sample22));
		System.out.print("\t");
		System.out.print(!(Math.max(Math.max(sample2, sample21), sample22) < 3));
		System.out.print("\t");
		printValues(valuesBefore, key);
		printValues(valuesAfter, key);
		System.out.println();
	}
	
	public boolean shouldPrint(double sample2, double sample21, double sample22) {
//		if(1.5 * Math.abs(sample2 - sample22) >= Math.abs(sample2 - sample21))
//			return false;
//		
//		if(1.5 * Math.abs(sample2 - sample22) >= Math.abs(sample22 - sample21))
//			return false;
//			
//		if(sample21 >= sample22)
//			return false;
//		
//		if(Math.min(Math.abs(sample21-sample22),Math.abs(sample21-sample2)) < 1)
//			return false;
//		
//		if(Math.max(Math.max(sample2, sample21), sample22) < 3)
//			return false;
		
		return true;
	}
	
	public void printStats() {
		
	}
	
	protected void printValues(Map<String, Map<String, Double>> map, String key) {
		Double sample2 = map.get(names.get(0)).containsKey(key) ? map.get(names.get(0)).get(key) : 0.0;
		Double sample21 = map.get(names.get(1)).containsKey(key) ? map.get(names.get(1)).get(key) : 0.0;
		Double sample22 = map.get(names.get(2)).containsKey(key) ? map.get(names.get(2)).get(key) : 0.0;
		
		System.out.print(sample2 + "\t");
		System.out.print(sample21 + "\t");
		System.out.print(sample22 + "\t");
	}

	@Override
	public void startFile(String name) {
		names.add(name);
		values.put(name, new HashMap<String, Double>());
		valuesBefore.put(name, new HashMap<String, Double>());
		valuesAfter.put(name, new HashMap<String, Double>());
	}

	@Override
	public void doneFile(String name) throws Exception {
		return;
	}

	@Override
	public Map<String, Component> collectResults() {
		return graphs;
	}
	
	protected String getReferenceName(EnsambleRef ref) {
		switch (groupName) {
		case GENE_NAME:
			return ref.getEnsemblGeneID();
		case GO_CATEGORY:
			return ((GoCategory)ref).getGoTermAccession();
		}
		throw new RuntimeException("Don't know what to group around");
	}

}
