package asd;

public class Couple {
	
	private String name;
	private Double valueX;
	private Double valueY;
	
	public Couple(String name, Double valueA, Double valueB) {
		this.name = name;
		this.valueX = valueA;
		this.valueY = valueB;
	}
	public Double getValueX() {
		return valueX;
	}
	public Double getValueY() {
		return valueY;
	}
	
	@Override
	public String toString() {
		return name + "\t" + valueX + "\t" + valueY;
	}
	
}
