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
		return isInteresting(align, false);
	}
	
	public boolean isInteresting(Alignment align, boolean reverse) {
		if(this.chr != align.getChromosome()) {
			return false;
		}
		
		if(getStrandSign(reverse) == align.isPlusStrand())
			return false;
		
		return (this.range.compare(align.getRange(), MARGIN) == 0);
	}
	
	private boolean getStrandSign(boolean reverse) {
		if(reverse)
			return !isPlusStrand;
		return isPlusStrand;
	}

	public Range getRange() {
		return range;
	}

	public Chromosome getChr() {
		return chr;
	}

}
