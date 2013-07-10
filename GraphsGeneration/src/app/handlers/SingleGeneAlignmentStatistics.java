package app.handlers;

import general.range.Range;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import reader.AlignmentHandlersIfc;
import alignment.Alignment;

public class SingleGeneAlignmentStatistics extends SingleGeneStatistics implements AlignmentHandlersIfc {
	
	private Map<String, Component> graphs = new HashMap<String, Component>();
	
	public SingleGeneAlignmentStatistics(final int n_bins, final int mergin, final String title, final Range gene) {
		super(title, n_bins, mergin, gene);
	}
	
	@Override
	public void handleAlignment(Object context, String read, Alignment align, double count) {
		Boolean sense = (Boolean)context;
		if(sense)
			fillData(senseP, align.getRange(), count);
		else 
			fillData(antisenseP, align.getRange(), -1 * count);
	}
	
	@Override
	public void startFile(String name) {
		cleanSample();
	}

	@Override
	public void doneFile(String name) {
		saveSample(name);
	}
	
	@Override
	public void done() throws Exception {
		graphs.put(this.geneName, generateGraphsFromSamples(this.geneName));
	}

	@Override
	public Map<String, Component> collectResults() {
		return graphs;
	}

}
