package formats;

import java.awt.Component;

import general.ContainerParams;
import general.GraphUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GeneAlignmentGraph {
	
	public static Component getPainting(ContainerParams container, DefaultCategoryDataset plus,
			 DefaultCategoryDataset minus, DefaultCategoryDataset geneS) throws Exception {
		
		
		JFreeChart geneAlignsP = ChartFactory.createBarChart("Aignment +", "Offset", "Counts",  plus, PlotOrientation.VERTICAL, true, false, false);
		JFreeChart geneAlignsM = ChartFactory.createBarChart("Aignment -", "Offset", "Counts",  minus, PlotOrientation.VERTICAL, true, false, false);
		JFreeChart gene = ChartFactory.createBarChart("Gene", "", "",  geneS, PlotOrientation.VERTICAL, true, false, false);
	
		CombinedDomainCategoryPlot combinedPlot = new CombinedDomainCategoryPlot();
		CategoryPlot topPlot = gene.getCategoryPlot();
		NumberAxis topAxis = (NumberAxis) topPlot.getRangeAxis();
		topAxis.setLowerBound(0);
		topAxis.setUpperBound(1);
	
		CategoryPlot plusPlot = geneAlignsP.getCategoryPlot();
		CategoryPlot minusPlot = geneAlignsM.getCategoryPlot();
		combinedPlot.add(plusPlot, 10);
		combinedPlot.add(topPlot, 1);
		combinedPlot.add(minusPlot, 10);
	   
		JFreeChart combinedChart = new JFreeChart(container.getTitle(), combinedPlot);
		CategoryPlot plot = combinedChart.getCategoryPlot();
		CategoryAxis axis = plot.getDomainAxis();
		axis.setCategoryMargin(0);
		
		GraphUtilities.saveToFile(container.getTitle(), combinedChart);
		
		final ChartPanel chartPanel = new ChartPanel(combinedChart);
		return chartPanel;
	}	

}
