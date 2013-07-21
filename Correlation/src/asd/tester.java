package asd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class tester {
	
	public static final int K = 10;

	public static void main(String[] args) throws IOException {
		
		List<Couple> couples = generateCouples(1,3);
		NE.calc(couples);
//		generateCluster(couples);
	}

	protected static List<Couple> generateCouples(int i , int j)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("C:\\temp\\Sharon.txt")));
		String line;
		List<Couple> couples = new ArrayList<Couple>();
		while ((line = br.readLine()) != null) {
			String[] res = line.split("\t");
			Double x = Double.valueOf(res[i]);
			Double y = Double.valueOf(res[j]);
			if(x > 3 && y > 3)
				couples.add(new Couple(res[0], x , y));
		}
		br.close();
		return couples;
	}

	protected static void generateCluster(List<Couple> couples) {
		Random rand = new Random();
		List<Cluster> clusters = new ArrayList<Cluster>();
		for(int i = 0 ; i < K ; ++i) {
			clusters.add(new Cluster(couples.remove(rand.nextInt(couples.size()))));
		}
		
		for (Couple cpl : couples) {
			Cluster c = null;
			double maxDiff = -10;
			
			for (Cluster cls : clusters) {
				double oldCorrelation = Caluclator.getCorrelation(cls);
				cls.addValue(cpl);
				double newCorrelation = Caluclator.getCorrelation(cls);
				cls.removeValue(cpl);
				
				if(maxDiff < Math.max(maxDiff, newCorrelation - oldCorrelation)) {
					c = cls;
					maxDiff = newCorrelation - oldCorrelation;
				}
			}
			
			c.addValue(cpl);
		}
		
		for (Cluster cluster : clusters) {
			System.out.println(cluster.getSize() + "\t" + Caluclator.getCorrelation(cluster));
		}
	}
	
	/**
	 * Reduce randomly
	 * 
	 * @param couples
	 */
//	protected static void generateCluster2(List<Couple> couples) {
//		Cluster cls = new Cluster(couples);
//		Random rand = new Random();
//		
//		while(cls.getSize() > K) {
//			System.out.println(cls.getSize());
//			
//			while(true) {
//				int a = rand.nextInt(couples.size());
//				Couple c = cls.getValues().get(a);
//				double oldCorrelation = Caluclator.getCorrelation(cls);
//				cls.removeValue(c);
//				double newCorrelation = Caluclator.getCorrelation(cls);
//				if(oldCorrelation > newCorrelation) {
//					cls.addValue(c);
//				} else {
//					break;
//				}
//			}
//		}
//
//		System.out.println(Caluclator.getCorrelation(cls));
//		for (Couple couple : cls.getValues()) {
//			System.out.println(couple);
//		}
//		
//	}
}
