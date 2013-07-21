package asd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.distribution.CauchyDistribution;

public class NE {

	public static final int populationSize = 100;
	public static final int K = 100;
	
	public static void calc(List<Couple> inputCouples) {
		List<ClusterWrapper> clusters = initializePopulation(inputCouples);
		
		CauchyDistribution dist = new CauchyDistribution(0, populationSize);
		for (int gen = 0 ; gen < 10000 ; ++gen) {
			Collections.sort(clusters);
			Double bestCorrel = clusters.iterator().next().getCor(); 
			if(bestCorrel > 0.9999) 
				break;
			
			List<ClusterWrapper> newClusters = new ArrayList<ClusterWrapper>();
			for(int i = 0 ; i < populationSize * 0.2 ; ++i) {
				newClusters.add(clusters.get(i));
			}

			for(int i = 0 ; i < populationSize * 0.7 ; ++i) {
				int mIdx = (int) (Math.abs(dist.sample())%populationSize);
				int fIdx = (int) (Math.abs(dist.sample())%populationSize);
				
				newClusters.add(mixClusters(clusters.get(mIdx), clusters.get(fIdx), inputCouples));
			}
			
			for(int i = 0 ; i < populationSize * 0.1 ; ++i) {
				newClusters.add(createCluster(inputCouples));
			}
			// TODO add mutation
			clusters = newClusters;
		}
		
		Collections.sort(clusters);
		ClusterWrapper bestCls = clusters.iterator().next();
		System.out.println(bestCls.getCor());
		for (Couple cpl : bestCls.getValues()) {
			System.out.println(cpl);
		}
	}

	private static ClusterWrapper mixClusters(ClusterWrapper mother,
			ClusterWrapper father, List<Couple> inputCouples) {
		
		Random rand = new Random();
		Set<Couple> nc = new HashSet<Couple>();
		nc.addAll(mother.getValues());
		nc.addAll(father.getValues());
		
		while(nc.size() < K) {
			nc.add(inputCouples.get(rand.nextInt(inputCouples.size())));
		}
		
		List<Couple> result = new ArrayList<Couple>(nc);
		while(result.size() > K) {
			result.remove(rand.nextInt(result.size()));
		}
		
		return new ClusterWrapper(result);
	}

	protected static List<ClusterWrapper> initializePopulation(
			List<Couple> inputCouples) {
		List<ClusterWrapper> clusters = new ArrayList<ClusterWrapper>();
		for(int i = 0 ; i < populationSize ; ++i) {
			ClusterWrapper cw = createCluster(inputCouples);
			clusters.add(cw);
		}
		return clusters;
	}

	protected static ClusterWrapper createCluster(List<Couple> inputCouples) {
		Random rand = new Random();
		Set<Couple> couples = new HashSet<Couple>();
		while(couples.size() != K) {
			int idx = rand.nextInt(inputCouples.size());
			couples.add(inputCouples.get(idx));
		}
		ClusterWrapper cw = new ClusterWrapper(new ArrayList<Couple>(couples));
		return cw;
	}
}
