package fastq;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class represents a sinlge fastq line 
 */
public class Fastq {
	
	private String name;
	private String sequence;
	private String comment;
	private String qualityScore;
	
	public Fastq(String name, String sequence, String comment,
			String qualityScore) {
		super();
		this.name = name;
		this.sequence = sequence;
		this.comment = comment;
		this.qualityScore = qualityScore;
	}

	public static Fastq readFastqFromFile(BufferedReader br, boolean clip) throws IOException {
		String name = "";
		String line = br.readLine();
		if (line == null)
			return null;
		while(line.startsWith("@")) {
			name += line;
			line = br.readLine();
		}
		
		String sequence = line;
		String comment = br.readLine();
		String score = br.readLine();
		if(clip)
			return new Fastq(name, sequence.substring(1), comment, score.substring(1));
		else
			return new Fastq(name, sequence, comment, score);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getQualityScore() {
		return qualityScore;
	}
	public void setQualityScore(String qualityScore) {
		this.qualityScore = qualityScore;
	}

	@Override
	public String toString() {
		return name + "\n" + sequence + "\n" + comment + "\n" + qualityScore;
	}
}
