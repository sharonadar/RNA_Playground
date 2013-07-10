package app.pies;

  import formats.PieChart;
import general.ContainerParams;
import general.GraphParams;
import general.group.Group;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.general.DefaultPieDataset;

import alignment.Alignment;
import alignment.AlignmentParser;

import common.filters.EmptyFilter;
import common.groups.DefaultAlignmentGroupPrimary;
import common.groups.DefaultAlignmentKeyExtractor;

public class GeneratePieGraph {
	
	private String fileName;

	public GeneratePieGraph(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, Component> generateGraph() throws Exception {
		String fileName = (new File(this.fileName)).getName();
		
		AlignmentParser parser = new AlignmentParser();
		Collection<DefaultAlignmentGroupPrimary> res = parser.parseFile(
				this.fileName, new EmptyFilter<Alignment>(),
				new DefaultAlignmentKeyExtractor(),
				DefaultAlignmentGroupPrimary.class);
		
		double nonMapped 	= 0;
		double uniqPlus		= 0;
		double nonUniqPlus 	= 0;
		double uniqMinus 	= 0;
		double nonUniqMinus	= 0;
		double nonUniq		= 0;
		
		for (Group<Alignment> alignments : res) {
			
			boolean uniq = (alignments.size() == 1); 
			if(!uniq) 
				nonUniq += 1;
			
			Alignment align = alignments.getRepresentor();
			if (align != null) {
				if (align.isPlusStrand()) {
					if(uniq)
						uniqPlus += 1;
					nonUniqPlus += 1;
				} else {
					if(uniq)
						uniqMinus += 1;
					nonUniqMinus += 1;
				}
			} else {
				nonMapped += 1;
			}
		}
		
	    List<GraphParams> params = new ArrayList<GraphParams>();
	    params.add(isAlignedDistribution(nonMapped, uniqPlus, nonUniqPlus, uniqMinus, nonUniqMinus, nonUniq));
	    params.add(numberAlignedDistribution(nonMapped, uniqPlus, nonUniqPlus, uniqMinus, nonUniqMinus, nonUniq));
	    params.add(plusMinusDistribution(nonMapped, uniqPlus, nonUniqPlus, uniqMinus, nonUniqMinus, nonUniq));
	    params.add(plusMinusDistributionPrimary(nonMapped, uniqPlus, nonUniqPlus, uniqMinus, nonUniqMinus, nonUniq));
		
	    ContainerParams container = new ContainerParams("Alignment distribution");
	    container.setFileName(fileName);
	    container.setHeight(500);
	    container.setWeight(1200);
	    container.setnGraphColumns(2);
	    container.setnGraphRows(2);
	    
	    Map<String, Component> res1 = new HashMap<String, Component>();
		res1.put(fileName, PieChart.getPainting(container, params));
		return res1;
	}

	private GraphParams isAlignedDistribution(double nonMapped, double uniqPlus, double nonUniqPlus, 
			double uniqMinus, double nonUniqMinus, double nonUniq) {
		
		final DefaultPieDataset dataset = new DefaultPieDataset();
	        dataset.setValue("Not mapped", nonMapped);
	        dataset.setValue("Mapped", nonUniqMinus + nonUniqPlus);
	        
	    return new GraphParams("mapped", "", "", dataset);
	}
	
	private GraphParams numberAlignedDistribution(double nonMapped, double uniqPlus, double nonUniqPlus, 
			double uniqMinus, double nonUniqMinus, double nonUniq) {
		
		final DefaultPieDataset dataset = new DefaultPieDataset();
	        dataset.setValue("Not mapped", nonMapped);
	        dataset.setValue("Mapped once", uniqPlus + uniqMinus);
	        dataset.setValue("Mapped more than once", nonUniq);
	        
	    return new GraphParams("number_mapped", "", "", dataset);
	}
	
	private GraphParams plusMinusDistribution(double nonMapped, double uniqPlus, double nonUniqPlus, 
			double uniqMinus, double nonUniqMinus, double nonUniq) {
		
		final DefaultPieDataset dataset = new DefaultPieDataset();
	        dataset.setValue("Not mapped", nonMapped);
	        dataset.setValue("Unique minus", uniqMinus);
	        dataset.setValue("Unique plus", uniqPlus);
	        dataset.setValue("Mapped more than once", nonUniq);
	    return new GraphParams("uniq_map", "", "", dataset);
	}
	
	private GraphParams plusMinusDistributionPrimary(double nonMapped, double uniqPlus, double nonUniqPlus, 
			double uniqMinus, double nonUniqMinus, double nonUniq) {
		
		final DefaultPieDataset dataset = new DefaultPieDataset();
	        dataset.setValue("Not mapped", nonMapped);
	        dataset.setValue("Unique minus", nonUniqMinus);
	        dataset.setValue("Unique plus", nonUniqPlus);
	    return new GraphParams("not_uniq_map", "", "", dataset);
	}
}
