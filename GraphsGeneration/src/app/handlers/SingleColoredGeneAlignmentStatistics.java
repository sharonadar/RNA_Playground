package app.handlers;

import formats.ColorscaledGeneAlignmentGraph;
import general.ContainerParams;
import general.range.Range;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.xy.DefaultXYZDataset;

import reader.AlignmentHandlersIfc;
import alignment.Alignment;

public class SingleColoredGeneAlignmentStatistics extends SingleGeneStatistics implements AlignmentHandlersIfc {
	
	private Map<String, Component> graphs = new HashMap<String, Component>();
	private final Range gene;
	
	public SingleColoredGeneAlignmentStatistics(final int n_bins, final int mergin, final String title, final Range gene) {
		super(title, n_bins, mergin, gene);
		this.gene = gene;
	}
	
	@Override
	public void handleAlignment(Object context, String read, Alignment align, double count) {
		Boolean sense = (Boolean)context;
		if(sense)
			fillData(senseP, align.getRange(), count);
		else 
			fillData(antisenseP, align.getRange(), count);
	}
	
	@Override
	public void startFile(String name) {
		cleanSample();
	}

	@Override
	public void doneFile(String name) throws Exception {
		double[][] data = new double[3][N_BINS * 3];
		
		for(int i = 0 ; i < N_BINS ; ++i) {
			addValue(data, 1,i, senseP[i]);
			addValue(data, 0,i, antisenseP[i]);
		}
		
		int s_pos = (int) Math.max(((gene.getStart() - OFFSET)/ BIN_SIZE) , 0);
		int e_pos = (int) Math.min(Math.floor((gene.getEnd() - OFFSET) / BIN_SIZE) , N_BINS - 1);
		addValue(data, 0,s_pos, -1);
		addValue(data, 1,s_pos, -1);
		addValue(data, 0,e_pos, -1);
		addValue(data, 1,e_pos, -1);
		
		DefaultXYZDataset datasets = new DefaultXYZDataset();
		datasets.addSeries(name, data);
		graphs.put(name, generateGraphsFromSamples(this.geneName, datasets));
	}
	
	public void addValue(double[][] values, int x, int y, double z){
		values[0][x * N_BINS + y] = y;
		values[1][x * N_BINS + y] = x;
        values[2][x * N_BINS + y] = z;
	}
	
	@Override
	public void done() throws Exception {
		return;
	}
	
	public Component generateGraphsFromSamples(String title, DefaultXYZDataset datasets) throws Exception {
		ContainerParams container = new ContainerParams(title);
		container.setHeight(100);
		container.setWeight(300);
		return ColorscaledGeneAlignmentGraph.getPainting(container, datasets);
	}

	@Override
	public Map<String, Component> collectResults() {
		return graphs;
	}

}
