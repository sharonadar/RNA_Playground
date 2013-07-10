package Games;

import ensembl.EnsambleGenes;
import ensembl.EnsambleParser;
import ensembl.goCategory.GoCategory;
import filters.EnsambleGeneNameFilter;
import general.Chromosome;
import general.range.Range;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import app.handlers.SingleGeneStatistics;

public class findTransposableElements {
	
	private static final String GENOME_NAME = "C:/cygwin/home/sharon.adar/Data/GO/ce_aligns.txt";
	private static final List<String> GENES = Arrays.asList(new String[]{"Y17D7B.5"});
	private static final Integer MERGIN = 1000;
	private static final Integer N_BINS = 100;

	public static void main(String[] args) throws Exception {
		
		Map<Chromosome, List<Range>> ranges = getRanges();
		
		EnsambleParser<GoCategory> parser = new EnsambleParser<GoCategory>(GoCategory.class);
		EnsambleGenes<GoCategory> categories = parser.parseFile(GENOME_NAME, 
						new EnsambleGeneNameFilter<GoCategory>(GENES), MERGIN);
		
		// Generate handlers
		Map<String, GeneHandler> handlers = new HashMap<String, GeneHandler>();
		for (GoCategory goCategory : categories.getAll()) {
			handlers.put(goCategory.getEnsemblGeneID(), 
					new GeneHandler(goCategory.getEnsemblGeneID(), N_BINS, MERGIN, goCategory.getRange()));
		}
		
		for (Entry<Chromosome, List<Range>> rangeOnChr : ranges.entrySet()) {
			for (Range range : rangeOnChr.getValue()) {
				for (GoCategory relCat : categories.get(rangeOnChr.getKey(), true, range, MERGIN)) {
					handlers.get(relCat.getEnsemblGeneID()).handle(range);
				}
			}
		}
		
		JFrame mainComponent = new JFrame();
		mainComponent.setTitle("Genes and transportable areas");
		mainComponent.setBounds(100, 100, 800, 500);
		mainComponent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane();
		for (Entry<String, GeneHandler> handler : handlers.entrySet()) {
			tabbedPane.addTab(handler.getKey(), handler.getValue().generateGraphsFromSamples(handler.getKey())); 
		}
		mainComponent.setContentPane(tabbedPane);
		mainComponent.setVisible(true);
	}
	
	private static class GeneHandler extends SingleGeneStatistics {

		public GeneHandler(String geneName, int n_bins, int mergin, Range gene) {
			super(geneName, n_bins, mergin, gene);
			cleanSample();
		}
		
		public void handle(Range range) {
			if(this.geneName.equals("F26A3.5")) {
				int r = 3;
				r++;
			}
			fillData(senseP, range, 1);
		}
		
		@Override
		public Component generateGraphsFromSamples(String title)
				throws Exception {
			saveSample("transposables");
			return super.generateGraphsFromSamples(title);
		}
		
	}

	protected static Map<Chromosome, List<Range>> getRanges()
			throws IOException {
		Map<Chromosome, List<Range>> ranges = new HashMap<Chromosome, List<Range>>();
		ranges.put(Chromosome.I, getRangesForFile("C:/cygwin/home/sharon.adar/Data/Genome/CE/chrI.fa"));
		ranges.put(Chromosome.II, getRangesForFile("C:/cygwin/home/sharon.adar/Data/Genome/CE/chrII.fa"));
		ranges.put(Chromosome.III, getRangesForFile("C:/cygwin/home/sharon.adar/Data/Genome/CE/chrIII.fa"));
		ranges.put(Chromosome.IV, getRangesForFile("C:/cygwin/home/sharon.adar/Data/Genome/CE/chrIV.fa"));
		ranges.put(Chromosome.V, getRangesForFile("C:/cygwin/home/sharon.adar/Data/Genome/CE/chrV.fa"));
		ranges.put(Chromosome.X, getRangesForFile("C:/cygwin/home/sharon.adar/Data/Genome/CE/chrX.fa"));
		ranges.put(Chromosome.M, getRangesForFile("C:/cygwin/home/sharon.adar/Data/Genome/CE/chrM.fa"));
		return ranges;
	}

	public static List<Range> getRangesForFile(String chrFile) throws IOException {
		
		List<Range> rangs = new ArrayList<Range>();
		BufferedReader br = new BufferedReader(new FileReader(new File(chrFile)));
		String line;
		int i = 0;
		int start = -1;
		int last;
		
		while ((line = br.readLine()) != null) {
			 if(line.startsWith(">"))
				 continue;
			 
			 last = i;
			 for(; i < line.length() + last ; ++i) {
				 if(Character.isLowerCase(line.charAt(i - last))) {
					 if(start == -1)
						 start = i;
				 } else {
					 if(start != -1) {
						 rangs.add(new Range(start, i -1));
						 start = -1;
					 }
				 }
			 }
		}
		
		if(start != -1) {
			rangs.add(new Range(start, i -1));
		}
		br.close();
		return rangs;
	}
}
