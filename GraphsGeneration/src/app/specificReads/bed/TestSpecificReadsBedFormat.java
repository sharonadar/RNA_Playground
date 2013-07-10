package app.specificReads.bed;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import bed.BedLine;
import ensembl.EnsambleGenes;
import ensembl.EnsambleParser;
import ensembl.EnsambleRef;
import ensembl.goCategory.GoCategory;
import filters.EnsambleGeneNameFilter;
import general.range.Range;


public class TestSpecificReadsBedFormat {
	
	public static final String INPUT_FILE  = 
			"C:/cygwin/home/sharon.adar/Data/Samples/tmp/prg-1.bed";
	public static final String GENOME = "C:/cygwin/home/sharon.adar/Data/GO/CE_GO.txt";
	public static final List<String> GENES = Arrays.asList(new String[]{"F13B12.1","F40F8.1","W08F4.3",
			"B0336.3","F26A3.5","B0336.7","C04F12.5","F25B3.6","C36C9.1","F53C11.5","F33H2.3","T28D9.1"});
	
	private static final int MERGIN = 1000;
	private static final int N_BINS = 100;
	
	private static Map<String, List<BedAlignmentHandler>> handlers = 
			new HashMap<String, List<BedAlignmentHandler>>();

	public static void main(String[] args) throws Exception {
		
		// INIT
		EnsambleParser<GoCategory> parser = new EnsambleParser<GoCategory>(GoCategory.class);
		EnsambleGenes<GoCategory> categories = 
				parser.parseFile(GENOME, new EnsambleGeneNameFilter<GoCategory>(GENES), MERGIN);
		
		initHandlers(categories);
		
		// Read
		for (List<BedAlignmentHandler> handlerList : handlers.values()) {
			for (BedAlignmentHandler handler : handlerList) {
				handler.startFile(INPUT_FILE);
			}
		}
		
		BufferedReader br = new BufferedReader(new FileReader(new File(INPUT_FILE)));
		String line;
		boolean firstline = true;
		while ((line = br.readLine()) != null) {
			if(firstline) {
				firstline = false;
				continue;
			}
			
		   	BedLine bl = new BedLine(line);
		   	handlerRead(categories, bl);
		}
		
		for (List<BedAlignmentHandler> handlerList : handlers.values()) {
			for (BedAlignmentHandler handler : handlerList) {
				handler.doneFile(INPUT_FILE);
			}
		}
		br.close();
		
		
		JFrame mainComponent = new JFrame();
		mainComponent.setTitle("RNA Playground");
		mainComponent.setBounds(100, 100, 800, 500);
		mainComponent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane();
		for (Entry<String, List<BedAlignmentHandler>> handlerList : handlers.entrySet()) {
			for (BedAlignmentHandler handler : handlerList.getValue()) {
				for (Component comp : handler.done()) {
					tabbedPane.addTab(handlerList.getKey(), comp);
				}
			}
		}
		mainComponent.setContentPane(tabbedPane);
		mainComponent.setVisible(true);
		
	}
	
	public static void handlerRead(EnsambleGenes<GoCategory> categories, BedLine bl){
		
		if(bl.getScore() != 0)
			return;
		
		Range range = new Range(bl.getStartPos(), bl.getEndPos());
		String name = bl.getName();
		String score = name.substring(name.indexOf("-") + 1, name.indexOf("/"));
		
		for (EnsambleRef category : categories.get(bl.getChr(), bl.isPlusStrand(), range, MERGIN)) {
			for (BedAlignmentHandler handler : handlers.get(category.getEnsemblGeneID())) {
				handler.handleBedAlignment(Boolean.TRUE, INPUT_FILE, bl, Double.valueOf(score));
			}
		}
		
		for (EnsambleRef category : categories.get(bl.getChr(), !bl.isPlusStrand(), range, MERGIN)) {
			for (BedAlignmentHandler handler : handlers.get(category.getEnsemblGeneID())) {
				handler.handleBedAlignment(Boolean.FALSE, INPUT_FILE, bl, Double.valueOf(score));
			}
		}
	}

	private static void initHandlers(EnsambleGenes<GoCategory> categories) {
		for (GoCategory category : categories.getAll()) {
			String geneName = category.getEnsemblGeneID();
			handlers.put(category.getEnsemblGeneID(), 
					Arrays.asList(new BedAlignmentHandler[]{
							new SingleGeneBedAlignmentCount(category.getEnsemblGeneID()),
							new SingleGeneBedAlignmentStatistics(N_BINS, MERGIN, geneName + "_align", category.getRange())
					}));
		}
	}
}
