package app.handlers;

import formats.ScatterGraph;
import general.ContainerParams;
import general.GraphParams;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public abstract class SingleGeneCount {
	
	private static final boolean SEPERATE = true;
	
	public static enum NEC {
		A, C, T, G;
	}
	
	private final String title;
	
	private List<Map<String, Map<Integer, Double>>> senseP_A = new ArrayList<Map<String, Map<Integer, Double>>>(); 
	private List<Map<String, Map<Integer, Double>>> antisenseP_A = new ArrayList<Map<String, Map<Integer, Double>>>();
	
	private Map<String, Component> graphs = new HashMap<String, Component>();
	
	public SingleGeneCount(String title) {
		this.title = title;
		for(int i = 0 ; i < 4; ++i) {
			senseP_A.add(new HashMap<String, Map<Integer, Double>>());
			antisenseP_A.add(new HashMap<String, Map<Integer, Double>>());
		}
	}
	
	public void handleAlignment(boolean sense, String read, String sequence, int length, double count) {
		int ord = 0;
		if(SEPERATE)
			ord = NEC.valueOf(sequence.substring(0, 1)).ordinal();
		
		if(sense) 
			updateCount(senseP_A.get(ord).get(read), count, length);
		else 
			updateCount(antisenseP_A.get(ord).get(read), count, length);
	}
	
	protected void updateCount(Map<Integer, Double> sense, double count, int length) {
		Double current = sense.get(length);
		if(current == null)
			current = 0.0;
		sense.put(length, current + count);
	}
	
	protected void startFileHandling(String name) {
		
		for(int i = 0 ; i < 4 ; ++i) {
			senseP_A.get(i).put(name, new HashMap<Integer, Double>());
			antisenseP_A.get(i).put(name, new HashMap<Integer, Double>());
		}
	}
	
	protected void generateGraph() throws Exception {
		
		List<GraphParams> params = new ArrayList<GraphParams>();
		
		for(int i = 0 ; i < (SEPERATE ? 4 : 1); ++i) {
			XYSeriesCollection resultS = new XYSeriesCollection();
			for (Entry<String, Map<Integer, Double>> cur : senseP_A.get(i).entrySet()) {
				 XYSeries seriesS = new XYSeries(cur.getKey());
				    for (Entry<Integer, Double> cnt: cur.getValue().entrySet()) {
				    	seriesS.add(cnt.getKey(), cnt.getValue());
				    }
				    resultS.addSeries(seriesS);
			}
			params.add(new GraphParams("sense " + NEC.values()[i], "length", "count", resultS));
		    
		    XYSeriesCollection resultA = new XYSeriesCollection();
		    for (Entry<String, Map<Integer, Double>> cur : antisenseP_A.get(i).entrySet()) {
		    	XYSeries seriesA = new XYSeries(cur.getKey());
		 	    for (Entry<Integer, Double> cnt: cur.getValue().entrySet()) {
		 	    	seriesA.add(cnt.getKey(), cnt.getValue());
		 	    }
		 	    resultA.addSeries(seriesA);
		    }
		   
		    params.add(new GraphParams("anti-sense " + NEC.values()[i], "length", "count", resultA));
		}
		
		ContainerParams container = new ContainerParams(title);
		container.setHeight(600);
		container.setWeight(800);
		container.setnGraphColumns(SEPERATE ? 4 : 2);
		container.setnGraphRows(SEPERATE ? 2 : 1);
		graphs.put(title, ScatterGraph.getPainting(container, params));
	}


	public Map<String, Component> getGraphs() {
		return graphs;
	}
	
}
