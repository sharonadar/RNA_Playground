package general;

public enum Chromosome {

	I(15072428),
	II(15279351),
	III(13783707),
	IV(17493799),
	V(20924154),
	X(17718871),
	M(13799);
	
	private int length;
	
	private Chromosome(int length) {
		this.length = length;
	}
	
	public static Chromosome fromString(String val) {
		String newVal = val.replaceFirst("[c|C][h|H][r|R]", "");
		newVal = newVal.replace("MtDNA", "M");
		for (Chromosome chr : Chromosome.values()) {
			if(chr.name().equals(newVal))
				return chr;
		}
		return null;
	}
	
	public int getLength() {
		return length;
	}
}
