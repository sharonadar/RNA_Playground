package general;


public class GraphParams {

	private String title;
	private String xLabel;
	private String yLabel;
	private Object dataset;
	
	public GraphParams(String title, String xLabel, String yLabel, Object dataset) {
		super();
		this.title = title;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.dataset = dataset;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getxLabel() {
		return xLabel;
	}
	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}
	public String getyLabel() {
		return yLabel;
	}
	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}
	public Object getDataset() {
		return dataset;
	}
	public void setDataset(Object dataset) {
		this.dataset = dataset;
	}
}
