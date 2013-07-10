package app.alignBin;

import formats.BarGraph;
import general.Chromosome;
import general.ContainerParams;
import general.GraphParams;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import reader.AlignmentHandlersIfc;
import alignment.Alignment;

public class AlignmentBinHandler implements AlignmentHandlersIfc{

	private final boolean logResults = true;
	private final int N_BINS = 100;
	private Chromosome chr;
	private AlignmentFunctionOptions function;
	private ArrayList<String> names = new ArrayList<String>();
	
	int idx = -1;
	
	Map<String, Component> graphs = new HashMap<String, Component>();
	double[][][] mapping = new double[2][N_BINS][2];
	
	public AlignmentBinHandler(Chromosome chr, AlignmentFunctionOptions function) {
		this.chr = chr;
		this.function = function;
	}
	
	@Override
	public void handleAlignment(Object ref, String name, Alignment align,
			double count) {
		Chromosome chr = align.getChromosome();
		int binSize = ((chr.getLength() + N_BINS - 1)/ N_BINS);
		int pos = align.getPosition() / binSize;
		int strand = align.isPlusStrand() ? 0 : 1 ;
		
		mapping[strand][pos][idx] += count;
	}

	@Override
	public void done() throws Exception {
		
		if(function != null) {
			
			if(logResults)
				logResults();
			
			DefaultCategoryDataset datasetP = new DefaultCategoryDataset();
			DefaultCategoryDataset datasetM = new DefaultCategoryDataset();
	
			int i = 0;
			for (double[] dsP : mapping[0]) {
				datasetP.addValue(getValue(function, dsP), "", String.valueOf(i++));
			}
	
			i = 0;
			for (double[] dsM : mapping[1]) {
				datasetM.addValue(getValue(function, dsM), "", String.valueOf(i++));
			}
	
			graphs.put(chr.name(),
					generateSingleGraph(chr.name(), datasetP, datasetM));
		}
	}
	
	protected void logResults() {

		for(int j = 0 ; j < 2 ; ++j) {
			for(int i = 0 ; i < 2 ; ++i) {
				System.out.print(names.get(i) +"\t" + chr.name() + "\t" + (j == 0 ? "+" : "-") + "\t");
				for (double[] ds : mapping[j]) {
					System.out.print(ds[i] + "\t");
				}
				System.out.println();
			}
		}
	}

	private double getValue(AlignmentFunctionOptions function, double[] ds) {
		switch (function) {
		case DIFFERENCE:
			return ds[0] - ds[1];
		case SUM:
			return ds[0] + ds[1];
		}
		return 0;
	}

	private Component generateSingleGraph(String chrName, CategoryDataset datasetP, CategoryDataset datasetM) throws Exception {
		GraphParams paramsP = new GraphParams(chrName + "_plus_strand", 
				"offset", "distribution", datasetP);
		
		GraphParams paramsM = new GraphParams(chrName + "_minus_strand", 
				"offset", "distribution", datasetM);
	
		ContainerParams container = new ContainerParams(chrName);
		container.setHeight(600);
		container.setWeight(500);
		container.setnGraphColumns(1);
		container.setnGraphRows(2);
		
		
	 	return BarGraph.getPainting(container,Arrays.asList(new GraphParams[]{paramsP, paramsM}));
	}

	@Override
	public void startFile(String name) {
		idx++;
		names.add(name);
	}

	@Override
	public void doneFile(String name) throws Exception {
		if(function == null) {
			
			DefaultCategoryDataset datasetP = new DefaultCategoryDataset();
			DefaultCategoryDataset datasetM = new DefaultCategoryDataset();
			
			int j = 0;
			for (double[] dsP : mapping[0]) {
				datasetP.addValue(dsP[idx], "", String.valueOf(j++));
			}
			
			j = 0;
			for (double[] dsM : mapping[1]) {
				datasetM.addValue(dsM[idx], "", String.valueOf(j++));
			}
			
			graphs.put(chr.name() + " " + name, 
					generateSingleGraph(chr.name(), datasetP, datasetM));
		}
	}

	@Override
	public Map<String, Component> collectResults() {
		return graphs;
	}
}
