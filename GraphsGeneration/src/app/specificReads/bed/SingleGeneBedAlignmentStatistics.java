package app.specificReads.bed;

import general.range.Range;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import app.handlers.SingleGeneStatistics;
import bed.BedLine;

public class SingleGeneBedAlignmentStatistics extends SingleGeneStatistics implements BedAlignmentHandler {
	
	public SingleGeneBedAlignmentStatistics(final int n_bins, final int mergin, final String title, final Range gene) {
		super(title, n_bins, mergin, gene);
	}
	
	public void startFile(String name) {
		cleanSample();
	}

	public void doneFile(String name) {
		saveSample(name);
	}
	
	public Collection<Component> done() throws Exception {
		List<Component> res = new ArrayList<Component>();
		res.add(generateGraphsFromSamples(this.geneName));
		return res;
	}

	@Override
	public void handleBedAlignment(Boolean sense, String read, BedLine align,
			double count) {
		if(align.getScore() != 0)
			return;
		
		if(sense)
			fillData(senseP, align.getRange(), count);
		else 
			fillData(antisenseP, align.getRange(), -1 * count);
		
	}
	
}
