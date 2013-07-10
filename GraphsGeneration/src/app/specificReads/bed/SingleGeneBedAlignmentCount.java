package app.specificReads.bed;

import java.awt.Component;
import java.util.Collection;

import app.handlers.SingleGeneCount;
import bed.BedLine;

public class SingleGeneBedAlignmentCount extends SingleGeneCount implements BedAlignmentHandler {
	
	public SingleGeneBedAlignmentCount(String title) {
		super(title);
	}
	
	/* (non-Javadoc)
	 * @see app.specificReads.bed.BedAlignmentHandler#handleBedAlignment(java.lang.Object, java.lang.String, bed.BedLine, double)
	 */
	@Override
	public void handleBedAlignment(Boolean sense, String read, BedLine align, double count) {
		if(align.getScore() != 0)
			return;
		
		int length = align.getName().indexOf("-");
		String sequence = align.getName().substring(length);
		handleAlignment(sense, read, sequence, length, count);
		
	}

	/* (non-Javadoc)
	 * @see app.specificReads.bed.BedAlignmentHandler#done()
	 */
	@Override
	public Collection<Component> done() throws Exception {
		generateGraph();
		return getGraphs().values();
	}

	/* (non-Javadoc)
	 * @see app.specificReads.bed.BedAlignmentHandler#doneFile(java.lang.String)
	 */
	@Override
	public void doneFile(String name) {
		return;
	}

	/* (non-Javadoc)
	 * @see app.specificReads.bed.BedAlignmentHandler#startFile(java.lang.String)
	 */
	@Override
	public void startFile(String name) {
		startFileHandling(name);		
	}

}
