package app.typedPies;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.general.DefaultPieDataset;

import reader.AlignmentHandlersIfc;
import alignment.Alignment;
import ensembl.general.ReferenceType;
import formats.PieChart;
import general.ContainerParams;
import general.GraphParams;

public class CountTypesHandler implements AlignmentHandlersIfc {
	
	boolean verbose = true;
	
	Map<String, Component> graphs = new HashMap<String, Component>();
	private Map<ReferenceType, Double> counts;
	
	@Override
	public void doneFile(String name) throws Exception {
		
		final DefaultPieDataset dataset = new DefaultPieDataset();
		ReferenceType[] values = ReferenceType.values();
		
		logResults(name, values);
		
		for(int i = 0 ; i < values.length ; ++i){
			dataset.setValue(values[i], counts.get(values[i]));
		}
        
        GraphParams gp = new GraphParams(name, "", "", dataset);
    
	    List<GraphParams> params = new ArrayList<GraphParams>();
	    params.add(gp);
		
	    ContainerParams container = new ContainerParams("Alignment type distribution");
	    container.setHeight(400);
	    container.setWeight(400);
	    
		graphs.put(name, PieChart.getPainting(container, params));
	}

	private void logResults(String name, ReferenceType[] values) {
		if(verbose) {
			System.out.println(name);
			for(int i = 0 ; i < values.length ; ++i){
				System.out.println(values[i] + "\t" + counts.get(values[i]));
			}
		}
	}

	@Override
	public void handleAlignment(Object context, String name, Alignment align, double count) {
		ReferenceType type = (ReferenceType)context;
		counts.put(type, counts.get(type) + count);
	}

	@Override
	public void done() throws Exception {
		return;
	}

	@Override
	public void startFile(String name) {
		counts = new HashMap<ReferenceType, Double>();
		ReferenceType[] values = ReferenceType.values();
		for(int i = 0 ; i < values.length ; ++i){
			counts.put(values[i], (double) 0);
		}
	}

	@Override
	public Map<String, Component> collectResults() {
		return graphs;
	}

}
