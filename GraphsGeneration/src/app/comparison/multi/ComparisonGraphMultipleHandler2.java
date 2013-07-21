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

public class ComparisonGraphMultipleHandler2 implements AlignmentHandlersIfc{

	private final String name;
	private final String graphName;
	private final GroupNameOptions groupName;
	private Map<String, Component> graphs = new HashMap<String, Component>();
	private Map<String, Map<String, Double>> values = new HashMap<String, Map<String, Double>>();
	private Map<String, Map<String, Double>> valuesBefore = new HashMap<String, Map<String, Double>>(); 
	private Map<String, Map<String, Double>> valuesAfter = new HashMap<String, Map<String, Double>>(); 
	
	private List<String> names = new ArrayList<String>();
	Map<String, EnsambleRef> aligns = new HashMap<String, EnsambleRef>();
	
	public ComparisonGraphMultipleHandler2(String name, String graphName, GroupNameOptions groupName) {
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
			keys.addAll(valuesAfter.get(name).keySet());
			keys.addAll(valuesBefore.get(name).keySet());
		}
		
		printVals(keys);
	}

	private void printVals(Set<String> keys) {
		System.out.print("gene\tchr\tstrand\toffset\t");
		for (String name : names) {
			System.out.print(name + "\tb_" + name + "\ta_" + name + "\t");
		}
		System.out.println();
		
		for (String key : keys) {
			EnsambleRef ref = aligns.get(key);
			printStats(key, ref);
		}
	}

	private void printStats(String key, EnsambleRef ref) {
		
		System.out.print(key +"\t");
		System.out.print(ref.getChromosomeName() + "\t" + ref.getStrand() + "\t" + ref.getGeneStart() + "\t");
		
		for (String name : names) {
			Double val = values.get(name).containsKey(key) ? values.get(name).get(key) : 0.0;
			Double before = valuesBefore.get(name).containsKey(key) ? valuesBefore.get(name).get(key) : 0.0;
			Double after = valuesAfter.get(name).containsKey(key) ? valuesAfter.get(name).get(key) : 0.0;
			System.out.print(val + "\t" + before + "\t" + after + "\t");
		}
		
		System.out.println();
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
