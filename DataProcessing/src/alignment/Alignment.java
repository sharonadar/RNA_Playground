package alignment;

import general.Chromosome;
import general.Utils;
import general.range.Range;

import java.util.HashMap;
import java.util.Map;


public class Alignment {

	// Query template NAME
	private int id;
	// bitwise FLAG
	private int flags;
	// Reference sequence NAME
	private Chromosome chromosome;
	// 1-based leftmost mapping POSition
	private int position;
	// MAPping Quality
	private int mappingQuality;
	// CIGAR string
	private String cigar;
	// Ref. name of the mate/next segment
	private String rnext;
	// Position of the mate/next segment
	private int pnext;
	// observed Template LENgth
	private int length;
	// segment SEQuence
	private String sequence;
	// ASCII of Phred-scaled base QUALity+33
	private String qual;
	
	// extra fields
	private Map<String, Flag> extraFields = new HashMap<String, Flag>();
	
	public Alignment(int pos, int length, boolean plus) {
		this.position = pos;
		this.length = length;
		if(!plus)
			this.flags = 16;
	}
	
	public Alignment(String inputStr) {
		String[] res 	= inputStr.split("\t");
		this.id 		= Integer.parseInt(res[0]);
		this.flags 		= Integer.parseInt(res[1]);
		this.chromosome = Chromosome.fromString(res[2]);
		this.position 	= Integer.parseInt(res[3]);
		this.mappingQuality = Integer.parseInt(res[4]);
		this.cigar 		= res[5];
		this.rnext 		= res[6];
		this.pnext 		= Integer.parseInt(res[7]);
		this.length 	= Integer.parseInt(res[8]);
		this.sequence 	= res[9];
		this.qual 		= res[10];
		
		for(int i = 11 ; i < res.length ; ++i) {
			Flag flag = new Flag(res[i]);
			this.extraFields.put(flag.getName(), flag);
		}
	}
		
	public boolean isMapped() {
		return (this.flags & 4) != 4;
	}
		
	public boolean isPlusStrand() {
		return ((this.flags == 0) || (this.flags == 256));
	}

	public boolean isMinusStrand() {
		return ((this.flags & 16) == 16);
	}
		
	public String getSequence() {
		if(this.isMinusStrand())
			return Utils.complement(this.sequence);
		return this.sequence;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public Chromosome getChromosome() {
		return chromosome;
	}

	public void setChromosome(Chromosome chromosome) {
		this.chromosome = chromosome;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Range getRange() {
		return new Range(this.getPosition(), this.getSequence().length() + this.getPosition());
	}
	
	public int getMappingQuality() {
		return mappingQuality;
	}

	public void setMappingQuality(int mappingQuality) {
		this.mappingQuality = mappingQuality;
	}

	public String getCigar() {
		return cigar;
	}

	public void setCigar(String cigar) {
		this.cigar = cigar;
	}

	public String getRnext() {
		return rnext;
	}

	public void setRnext(String rnext) {
		this.rnext = rnext;
	}

	public int getPnext() {
		return pnext;
	}

	public void setPnext(int pnext) {
		this.pnext = pnext;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getQual() {
		return qual;
	}

	public void setQual(String qual) {
		this.qual = qual;
	}

	public Map<String, Flag> getExtraFields() {
		return extraFields;
	}

	public void setExtraFields(Map<String, Flag> extraFields) {
		this.extraFields = extraFields;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public boolean isSecondary() {
		return ((this.flags & 0x100) == 0x100);
	}
}
		