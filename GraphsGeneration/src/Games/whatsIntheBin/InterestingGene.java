package Games.whatsIntheBin;

import general.Chromosome;
import general.range.Range;
import alignment.Alignment;

public class InterestingGene implements InterestingPart{
	
	private static final int MARGIN = 1000;
	private final Chromosome chr;
	private final boolean isPlusStrand;
	private final Range range;
	
	public InterestingGene(Chromosome chr, Range range, boolean isPlusStrand) {
		this.chr = chr;
		this.range = range;
		this.isPlusStrand = isPlusStrand;
	}
	
	public boolean isInteresting(Alignment align) {
		if(this.chr != align.getChromosome()) {
			return false;
		}
		
		// We want to have the reversed strand
		if(this.isPlusStrand == align.isPlusStrand())
			return false;
		
		return (this.range.compare(align.getRange(), MARGIN) == 0);
	}

}
