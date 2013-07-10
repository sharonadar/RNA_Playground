package reader;

public class ReadDetails {
	private String name;
	private String countFile;
	private String alignFile;
	
	public ReadDetails(String name, String alignFile, String countFile) {
		this.name = name;
		this.alignFile = alignFile;
		this.countFile = countFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountFile() {
		return countFile;
	}

	public void setCountFile(String countFile) {
		this.countFile = countFile;
	}

	public String getAlignFile() {
		return alignFile;
	}

	public void setAlignFile(String alignFile) {
		this.alignFile = alignFile;
	}
	
}
