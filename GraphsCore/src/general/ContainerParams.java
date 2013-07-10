package general;

public class ContainerParams {

	
	// TODO get rid of useless params
	
	private String title;
	private int nGraphRows = 1;
	private int nGraphColumns = 1;
	private int height = 270;
	private int weight = 500;
	private String fileName = "";
	private String directory = "c:\\temp\\21U\\";
	
	public ContainerParams(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getnGraphRows() {
		return nGraphRows;
	}
	public void setnGraphRows(int nGraphRows) {
		this.nGraphRows = nGraphRows;
	}
	public int getnGraphColumns() {
		return nGraphColumns;
	}
	public void setnGraphColumns(int nGraphColumns) {
		this.nGraphColumns = nGraphColumns;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getDirectory() {
		return this.directory ;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

}
