package asd;


public class Caluclator {
	
	public static double getCorrelation(Cluster c) {
		if(c.getSize() == 1)
			return 1;
		
		Double avgX = c.getAverageX();
		Double avgY = c.getAverageY();
		
		Double sumUp = 0.0;
		Double sumDX = 0.0;
		Double sumDY = 0.0;
		
		for (Couple cpl : c.getValues()) {
			sumUp += ((cpl.getValueX() - avgX) * (cpl.getValueY() - avgY));
			sumDX += Math.pow(cpl.getValueX() - avgX, 2);
			sumDY += Math.pow(cpl.getValueY() - avgY, 2);
		}
		
		return (sumUp / Math.sqrt(sumDX * sumDY ));
	}

	public static void main(String[] args) {
		Cluster cls = new Cluster(new Couple("a", 1.0, 3.0));
		cls.addValue(new Couple("b", 2.0, 6.0));
		cls.addValue(new Couple("b", 3.0, 9.0));
		cls.addValue(new Couple("b", 4.0, 12.0));
		System.out.println(Caluclator.getCorrelation(cls));
	}
}
