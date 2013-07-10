package fastq;

/**
 * This class is responsible for scoring the sequence
 */
public class Scorer {
	
	public enum ScorerType {
		SANGER,
		ILLUMINA
	}
	
	private final ScorerType type;
	
	public Scorer(final ScorerType type) {
		this.type = type;
	}
	
	public double getAverageScore(final Fastq fastq) {
		int sum = 0;
		char[] asd = fastq.getQualityScore().toCharArray();
		for (char c : asd) {
			sum += getSingleCharScore(c);
		}
		return sum / asd.length;
	}
	
	public double getMinimalScore(final Fastq fastq) {
		int min = Integer.MAX_VALUE;
		char[] asd = fastq.getQualityScore().toCharArray();
		for (char c : asd) {
			min = Math.min(min, getSingleCharScore(c));
		}
		return min;
	}
	
	private int getSingleCharScore(final char chr) {
		if (this.type == ScorerType.ILLUMINA)
			return chr - 64;
		else if (this.type == ScorerType.SANGER)
			return chr - 33;
		else
			throw new RuntimeException("Illegal input for scorer");
	}

}
