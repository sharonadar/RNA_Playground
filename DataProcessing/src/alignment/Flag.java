package alignment;

public class Flag {
	
	private String name;
	private String type;
	private String value;
	
	public Flag(String flagStr) {
		String res[] = flagStr.split(":");
		this.name = res[0];
		this.type = res[1];
		this.value = res[2];
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
