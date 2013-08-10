package Games.whatsIntheBin;

import general.Chromosome;
import general.range.Range;

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

	public WhatsInTheBinReader(List<InterestingPart> bins) {
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

		tester.handleAlignment(null, read.getName(), align, count);
	}
	
	public static void main(String[] args) throws Exception {
		List<ReadDetails> inputs = new ArrayList<ReadDetails>();
		inputs.add(new ReadDetails("sample_22", "C:/cygwin/home/sharon.adar/Data/Samples/sample_22.22G.align", 
				 "C:/cygwin/home/sharon.adar/Data/Samples/sample_22.22G.count"));
		inputs.add(new ReadDetails("sample_21", "C:/cygwin/home/sharon.adar/Data/Samples/sample_21.22G.align", 
				 "C:/cygwin/home/sharon.adar/Data/Samples/sample_21.22G.count"));
		inputs.add(new ReadDetails("sample_2", "C:/cygwin/home/sharon.adar/Data/Samples/sample_2.22G.align", 
				 "C:/cygwin/home/sharon.adar/Data/Samples/sample_2.22G.count"));
		List<InterestingPart> bins = new ArrayList<InterestingPart>();
		bins.add(new InterestingGene(Chromosome.V, new Range(	18776199	,18780216),  false	));
		
		WhatsInTheBinReader generator = new WhatsInTheBinReader(bins);
		generator.handleReads(inputs, new PrimaryAlignmentFilter());
	}
}
