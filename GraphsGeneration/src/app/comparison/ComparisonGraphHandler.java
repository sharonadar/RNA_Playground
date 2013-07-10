package app.comparison;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeriesCollection;

import reader.AlignmentHandlersIfc;
import alignment.Alignment;
import ensembl.EnsambleRef;
import ensembl.goCategory.GoCategory;
import formats.ComparisonGraph;
import formats.XYLabelSeries;
import general.ContainerParams;
import general.GraphParams;

public class ComparisonGraphHandler implements AlignmentHandlersIfc{

	private final String graphName;
	private final GroupNameOptions groupName;
	private Map<String, Component> graphs = new HashMap<String, Component>();
	private Map<String, Map<String, Double>> values = new HashMap<String, Map<String, Double>>(); 
	private List<String> names = new ArrayList<>();
	
	public ComparisonGraphHandler(String graphName, GroupNameOptions groupName) {
		this.graphName = graphName;
		this.groupName = groupName;
	}
	
	@Override
	public void handleAlignment(Object ref, String name, Alignment align,
			double count) {
		String instance = getReferenceName((EnsambleRef) ref);
		Double value = 0.0;
			
		if(values.get(name).containsKey(instance))
			value += values.get(name).get(instance);
		
		values.get(name).put(instance, value + count);
	}

	@Override
	public void done() throws Exception {
		Map<String, Double> dataset1 = values.get(names.get(0));
		Map<String, Double> dataset2 = values.get(names.get(1));
		
		System.out.println(graphName + "\t" + names.get(0) + "\t" + names.get(1));
		XYSeriesCollection result = new XYSeriesCollection();
		XYLabelSeries series = new XYLabelSeries(graphName);

		Set<String> keys = new HashSet<String>();
		keys.addAll(dataset1.keySet());
		keys.addAll(dataset2.keySet());
		
		for (String key : keys) {
			Double count1 = dataset1.containsKey(key) ? dataset1.get(key) : 0.0;
			Double count2 = dataset2.containsKey(key) ? dataset2.get(key) : 0.0;
			
			System.out.println(key + "\t" + count1 + "\t" + count2);
			series.add(new XYDataItem(count1, count2), key);
		}
		
		result.addSeries(series);

		GraphParams gParams = new GraphParams(graphName, names.get(0), names.get(1), result);
		ContainerParams container = new ContainerParams(graphName);
		container.setHeight(2000);
		container.setWeight(2000);
		graphs.put(graphName, ComparisonGraph.getPainting(container, gParams));
	}

	@Override
	public void startFile(String name) {
		values.put(name, new HashMap<String, Double>());
		names.add(name);
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
