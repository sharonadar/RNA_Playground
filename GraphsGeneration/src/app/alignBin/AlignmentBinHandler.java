package app.alignBin;

import formats.BarGraph;
import general.ContainerParams;
import general.GraphParams;

import java.awt.Component;
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
	private String chrName;
	private int length;
	private AlignmentFunctionOptions function;
	private boolean separateGraph;
	private String[] names;
	
	int idx = -1;
	
	Map<String, Component> graphs = new HashMap<String, Component>();
	double[][][] mapping;
	
	public AlignmentBinHandler(String name, int length, AlignmentFunctionOptions function, int nSamples, boolean separateGraph) {
		
		this.chrName = name;
		this.length = length;
		this.function = function;
		this.mapping = new double[2][N_BINS][nSamples];
		this.names = new String[nSamples];
		this.separateGraph = separateGraph;
	}
	
	@Override
	public void handleAlignment(Object ref, String name, Alignment align,
			double count) {
		int binSize = ((length + N_BINS - 1)/ N_BINS);
		int pos = align.getPosition() / binSize;
		int strand = align.isPlusStrand() ? 0 : 1 ;
		
		mapping[strand][pos][idx] += count;
			
	}

	@Override
	public void done() throws Exception {
		if(logResults)
			logResults();
		
		DefaultCategoryDataset datasetP = new DefaultCategoryDataset();
		DefaultCategoryDataset datasetM = new DefaultCategoryDataset();

		for (int j = 0 ; j < names.length ; ++j) {
			int i = 0;
			for (double[] dsP : mapping[0]) {
				datasetP.addValue(getValue(function, dsP,j), names[j], String.valueOf(i++));
			}
	
			i = 0;
			for (double[] dsM : mapping[1]) {
				datasetM.addValue(getValue(function, dsM,j), names[j], String.valueOf(i++));
			}
			
			if(separateGraph) {
				graphs.put(names[j], generateSingleGraph(names[j], datasetP, datasetM));
				datasetP = new DefaultCategoryDataset();
				datasetM = new DefaultCategoryDataset();

			}
		}

		if(!separateGraph)
			graphs.put(chrName,	generateSingleGraph(chrName, datasetP, datasetM));
	}
	
	protected void logResults() {

		for(int strand = 0 ; strand < 2 ; ++strand) {
			for(int sample = 0 ; sample < names.length ; ++sample) {
				System.out.print(names[sample] +"\t" + chrName + "\t" + (strand == 0 ? "+" : "-") + "\t");
				for (double[] ds : mapping[strand]) {
					System.out.print(ds[sample] + "\t");
				}
				System.out.println();
			}
		}
	}

	private double getValue(AlignmentFunctionOptions function, double[] ds, int j) {
		if(function == null)
			return ds[j];
		
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
		names[idx] = name;
	}

	@Override
	public void doneFile(String name) throws Exception {
		return;
	}

	@Override
	public Map<String, Component> collectResults() {
		return graphs;
	}
}
