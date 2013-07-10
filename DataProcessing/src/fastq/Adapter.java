package fastq;


/**
 * This class represents the sequence adapter 
 */
public class Adapter {
	private final String adapter;
	
	public Adapter(final String adapter) {
		this.adapter = adapter;
	}
	
	public boolean isLegalSequence(final Fastq fastq) {
		return (fastq.getSequence().indexOf(this.adapter) > 0);
	}
			
	public void clipSequence(Fastq fastq) {
		int idx = fastq.getSequence().indexOf(this.adapter);
		fastq.setSequence(fastq.getSequence().substring(0, idx));
		fastq.setQualityScore(fastq.getQualityScore().substring(0, idx));
	}
}
