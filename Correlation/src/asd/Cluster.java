package asd;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	
	private List<Couple> values;
	private Double averageX = 0.0;
	private Double averageY = 0.0;
	
	public Cluster(List<Couple> couples) {
		Double sumX = 0.0;
		Double sumY = 0.0;
		
		for (Couple couple : couples) {
			sumX += couple.getValueX();
			sumY += couple.getValueY();
		}
		
		values = couples;
		averageX = sumX / couples.size();
		averageY = sumY / couples.size();
	}
	
	public Cluster(Couple couple) {
		values = new ArrayList<Couple>();
		values.add(couple);
		averageX = couple.getValueX();
		averageY = couple.getValueY();
	}

	public int getSize() {
		return values.size();
	}
	
	public Double getAverageX() {
		return averageX;
	}
	
	public Double getAverageY() {
		return averageY;
	}
	
	public List<Couple> getValues() {
		return values;
	}
	
	public void addValue(Couple c) {
		averageX = (averageX * values.size() + c.getValueX()) / (values.size() + 1);
		averageX = (averageY * values.size() + c.getValueY()) / (values.size() + 1);
		values.add(c);
	}
	
	public void removeValue(Couple c) {
		averageX = (averageX * values.size() - c.getValueX()) / (values.size() - 1);
		averageX = (averageY * values.size() - c.getValueY()) / (values.size() - 1);
		values.remove(c);
	}
}
