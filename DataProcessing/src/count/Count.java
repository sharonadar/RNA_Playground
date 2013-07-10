package count;

public class Count {

	private String sequence;
	private Double count;
	
	public Count(String line) {
		String[] res = line.split("\t");
		this.sequence = res[1];
		this.count = Double.parseDouble(res[0]);
	}

	public String getSequence() {
		return sequence;
	}

	public Double getCount() {
		return count;
	}
	
}
