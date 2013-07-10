package app.handlers;

import java.awt.Component;

import org.jfree.data.category.DefaultCategoryDataset;

import formats.GeneAlignmentGraph;
import general.ContainerParams;
import general.range.Range;

public abstract class SingleGeneStatistics {
	
	private static final boolean log = true;

	protected final String geneName;
	protected final int MERGIN;
	protected final int N_BINS;
	protected final double BIN_SIZE;
	protected final int OFFSET;
	
	DefaultCategoryDataset sense = new DefaultCategoryDataset();
	DefaultCategoryDataset antisense = new DefaultCategoryDataset();
	
	protected double[] senseP; 
	protected double[] antisenseP;
	protected double[] dataG;
	
	
	public SingleGeneStatistics(String geneName, final int n_bins, final int mergin, final Range gene) {
		this.N_BINS = n_bins;
		this.MERGIN = mergin;
		this.geneName = geneName;
		
		double length = (gene.getEnd() - gene.getStart()) + 2 * MERGIN;   
		this.BIN_SIZE = length / N_BINS;
		this.OFFSET = gene.getStart() - MERGIN;
		
		dataG = new double[N_BINS];
		
		fillData(dataG, gene, -1);
	}
	
	protected void fillData(double[] bins, Range range, double count) {
		int s_pos = (int) Math.max(((range.getStart() - OFFSET)/ BIN_SIZE) , 0);
		int e_pos = (int) Math.min(Math.floor((range.getEnd() - OFFSET) / BIN_SIZE) , N_BINS - 1);
		
		for(int i = s_pos; i <= e_pos ; ++i) {
			bins[i] += count; 
		}
	}
	
	public void cleanSample() {
		senseP = new double[N_BINS];
		antisenseP = new double[N_BINS];		
	}
	
	public void saveSample(String name) {
		if(log)
			logResults(name);
		
		for(int i = 0 ; i < N_BINS ; ++i) {
			String binName =  Double.toString((i + 0.5) * BIN_SIZE + OFFSET);
			sense.addValue(senseP[i], name, binName);
			antisense.addValue(antisenseP[i], name, binName);
		}
	}
	
	private void logResults(String name) {
		System.out.print(name + "\t" + geneName + "\tsense\t");
		for(int i = 0 ; i < N_BINS ; ++i) 
			System.out.print(senseP[i] + "\t");
		System.out.println();
		System.out.print(name + "\t" + geneName + "\tanti-sense\t");
		for(int i = 0 ; i < N_BINS ; ++i) 
			System.out.print(antisenseP[i] + "\t");
		System.out.println();
	}

	public Component generateGraphsFromSamples(String title) throws Exception {
		DefaultCategoryDataset gene = new DefaultCategoryDataset();
		
		for(int i = 0 ; i < N_BINS ; ++i) {
			String binName =  Double.toString((i + 0.5) * BIN_SIZE + OFFSET);
			gene.addValue(dataG[i], "genome", binName);
		}
		
		ContainerParams container = new ContainerParams(title);
		container.setHeight(600);
		container.setWeight(800);
		return GeneAlignmentGraph.getPainting(container, sense, antisense, gene);
	}
}
