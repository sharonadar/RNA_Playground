package Games.whatsIntheBin;

import alignment.Alignment;
import general.Chromosome;

public class InterestingBin implements InterestingPart {
	
	private static final int N_BINS = 100;
	private final Chromosome chr;
	private final int bin;
	private final boolean isPlusStrand;
	
	public InterestingBin(Chromosome chr, int bin, boolean isPlusStrand) {
		this.chr = chr;
		this.bin = bin;
		this.isPlusStrand = isPlusStrand;
	}
	
	/* (non-Javadoc)
	 * @see Games.whatsIntheBin.InterestingPart#isInteresting(alignment.Alignment)
	 */
	@Override
	public boolean isInteresting(Alignment align) {
		if(this.chr != align.getChromosome()) {
			return false;
		}
		if(this.isPlusStrand != align.isPlusStrand())
			return false;
		
		int binSize = ((chr.getLength() + N_BINS - 1)/ N_BINS);
		int pos = align.getPosition() / binSize;
		if(pos != bin)
			return false;
		
		return true;
	}

}
