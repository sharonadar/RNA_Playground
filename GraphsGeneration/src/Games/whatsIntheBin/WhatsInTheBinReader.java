package Games.whatsIntheBin;

import general.Chromosome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import reader.AlignmentHandlersIfc;
import reader.AlignmentReader;
import reader.ReadDetails;
import alignment.Alignment;

import common.filters.PrimaryAlignmentFilter;

public class WhatsInTheBinReader extends AlignmentReader {

	private WhatsInTheBin tester;

	public WhatsInTheBinReader(List<InterestingBin> bins) {
		super(null);
		this.tester = new WhatsInTheBin(bins);
	}
	
	@Override
	protected Map<String, List<AlignmentHandlersIfc>> createContainers() {
		containers = new HashMap<String, List<AlignmentHandlersIfc>>();
		return containers;
	}

	@Override
	protected void handleSingleAlignment(ReadDetails read, Alignment align,
			double count) {

		if (!align.isMapped() || align.isSecondary())
			return;

		tester.handleAlignment(null, null, align, count);
	}
	
	public static void main(String[] args) throws Exception {
		List<ReadDetails> inputs = new ArrayList<ReadDetails>();
		inputs.add(new ReadDetails("sample_22", "C:/cygwin/home/sharon.adar/Data/Samples/sample_22.21U.align", 
				 "C:/cygwin/home/sharon.adar/Data/Samples/sample_22.21U.count"));
		inputs.add(new ReadDetails("sample_2", "C:/cygwin/home/sharon.adar/Data/Samples/sample_2.21U.align", 
				 "C:/cygwin/home/sharon.adar/Data/Samples/sample_2.21U.count"));
		List<InterestingBin> bins = new ArrayList<InterestingBin>();
		bins.add(new InterestingBin(Chromosome.I, 62, false));
		
		WhatsInTheBinReader generator = new WhatsInTheBinReader(bins);
		generator.handleReads(inputs, new PrimaryAlignmentFilter());
	}
}
