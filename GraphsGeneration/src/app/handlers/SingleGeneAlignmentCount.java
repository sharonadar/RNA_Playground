package app.handlers;

import java.awt.Component;
import java.util.Map;

import reader.AlignmentHandlersIfc;
import alignment.Alignment;

public class SingleGeneAlignmentCount extends SingleGeneCount implements AlignmentHandlersIfc {
	
	public SingleGeneAlignmentCount(String title) {
		super(title);
	}
	
	/* (non-Javadoc)
	 * @see graphs.generators.geneAlignment.AlignmentHandlersIfc#handleAlignment(alignment.Alignment, double, boolean)
	 */
	@Override
	public void handleAlignment(Object context, String read, Alignment align, double count) {
		Boolean sense = (Boolean)context;
		if(align.isSecondary())
			return;
		
		int length = align.getSequence().length();
		handleAlignment(sense, read, align.getSequence(), length, count);
		
	}

	/* (non-Javadoc)
	 * @see graphs.generators.geneAlignment.AlignmentHandlersIfc#done()
	 */
	@Override
	public void done() throws Exception {
		generateGraph();
	}

	@Override
	public void doneFile(String name) {
		return;
	}

	@Override
	public Map<String, Component> collectResults() {
		return getGraphs();
	}

	@Override
	public void startFile(String name) {
		startFileHandling(name);		
	}

}
