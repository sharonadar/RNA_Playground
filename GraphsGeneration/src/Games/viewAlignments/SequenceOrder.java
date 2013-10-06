package Games.viewAlignments;

public enum SequenceOrder {
	
	MATCH_CHAR('M'),
	REPLACE_CHAR('E'),
	DELETE_CHAR('D'),
	SOFT_CLIPPING('S'),
	INSERT_CHAR('I');
	
	final char representor;
	
	SequenceOrder(char representor) {
		this.representor = representor;
	}
	
	public static SequenceOrder getSequenceOrder(char str) {
		for (SequenceOrder seq : SequenceOrder.values()) {
			if(seq.representor == str)
				return seq;
		}
		return REPLACE_CHAR;
	}
}