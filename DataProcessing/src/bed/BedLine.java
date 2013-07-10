package bed;

import general.Chromosome;
import general.range.Range;

public class BedLine {
	
	private Chromosome chr;
	private int startPos;
	private int endPos;
	
	private String name;
	private int score;
	private boolean strand;
	private int thickStart;
	private int thickEnd;
	private String itemRGB;
	
	public BedLine(String line) {
		String[] str = line.split("\t");
		chr = Chromosome.valueOf(str[0].substring(3));
		startPos = Integer.valueOf(str[1]);
		endPos = Integer.valueOf(str[2]);
		name = str[3];
		score = Integer.valueOf(str[4]);
		strand = str[5].equals("+");
		thickStart = Integer.valueOf(str[6]);
		thickEnd = Integer.valueOf(str[7]);
		itemRGB = str[8];
	}
	
	public Chromosome getChr() {
		return chr;
	}
	public void setChr(Chromosome chr) {
		this.chr = chr;
	}
	public int getStartPos() {
		return startPos;
	}
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	public int getEndPos() {
		return endPos;
	}
	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean isPlusStrand() {
		return strand;
	}
	public void setStrand(boolean strand) {
		this.strand = strand;
	}
	public int getThickStart() {
		return thickStart;
	}
	public void setThickStart(int thickStart) {
		this.thickStart = thickStart;
	}
	public int getThickEnd() {
		return thickEnd;
	}
	public void setThickEnd(int thickEnd) {
		this.thickEnd = thickEnd;
	}
	public String getItemRGB() {
		return itemRGB;
	}
	public void setItemRGB(String itemRGB) {
		this.itemRGB = itemRGB;
	}

	public Range getRange() {
		return new Range(getStartPos(), getEndPos());
	}
}
