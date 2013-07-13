package Games.whatsIntheBin;

import java.awt.Component;
import java.util.List;
import java.util.Map;

import reader.AlignmentHandlersIfc;
import alignment.Alignment;

public class WhatsInTheBin implements AlignmentHandlersIfc{
	
	private List<InterestingBin> bins;
	
	public WhatsInTheBin(List<InterestingBin> bins) {
		this.bins = bins;
	}
	
	@Override
	public void handleAlignment(Object ref, String name, Alignment align,
			double count) {

		for (InterestingBin bin : bins) {
			if(bin.isInteresting(align)) {
				System.out.println(align.getSequence() + "\t" + count + "\t" + align.getPosition());
			}
		}
	}

	@Override
	public Map<String, Component> collectResults() {
		return null;
	}

	@Override
	public void done() throws Exception {
	}

	@Override
	public void startFile(String name) {
	}

	@Override
	public void doneFile(String name) throws Exception {
	}
}
