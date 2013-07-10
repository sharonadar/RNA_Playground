package ensembl;

import general.Chromosome;
import general.range.Range;

public class EnsambleRef {
	private String ensemblGeneID;
	private String ensemblTranscriptID;
	private Chromosome chromosome;
	private int geneStart;
	private int geneEnd;
	private int strand;
	
	public EnsambleRef(String inputStr) {
		String[] res = inputStr.split("\t");
		this.ensemblGeneID = res[0];
		this.ensemblTranscriptID = res[1];
		this.chromosome = Chromosome.fromString(res[2]);
		this.geneStart = Integer.parseInt(res[3]);
		this.geneEnd = Integer.parseInt(res[4]);
		this.strand = Integer.parseInt(res[5]);
	}
	
	public String getEnsemblGeneID() {
		return ensemblGeneID;
	}
	public void setEnsemblGeneID(String ensemblGeneID) {
		this.ensemblGeneID = ensemblGeneID;
	}
	public String getEnsemblTranscriptID() {
		return ensemblTranscriptID;
	}
	public void setEnsemblTranscriptID(String ensemblTranscriptID) {
		this.ensemblTranscriptID = ensemblTranscriptID;
	}
	public Chromosome getChromosomeName() {
		return chromosome;
	}
	public void setChromosomeName(Chromosome chromosomeName) {
		this.chromosome = chromosomeName;
	}
	public int getGeneStart() {
		return geneStart;
	}
	public void setGeneStart(int geneStart) {
		this.geneStart = geneStart;
	}
	public int getGeneEnd() {
		return geneEnd;
	}
	public void setGeneEnd(int geneEnd) {
		this.geneEnd = geneEnd;
	}
	
	public Range getRange() {
		return new Range(getGeneStart(), getGeneEnd());
	}
	
	public int getStrand() {
		return strand;
	}
	public void setStrand(int strand) {
		this.strand = strand;
	}
	
	public boolean isPlusStrand() {
		return (this.strand == 1);
	}

}
