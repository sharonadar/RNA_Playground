package formats;

import general.ContainerParams;
import general.GraphParams;
import general.GraphUtilities;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

public class BarGraph {

	public static Component getPainting(final ContainerParams container,
			 final List<GraphParams> params) throws Exception {

		JPanel panel = new JPanel(new GridLayout(container.getnGraphRows(), 
        		container.getnGraphColumns()));
        
        for (GraphParams param : params) {
        	final JFreeChart chart = createChart(param);
        	GraphUtilities.saveToFile(param.getTitle(), chart);
        	panel.add(new ChartPanel(chart));
		}
        
		return panel;
	}

	private static JFreeChart createChart(GraphParams params) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart(params.getTitle(), // chart
																				// title
				params.getxLabel(), // domain axis label
				params.getyLabel(), // range axis label
				(CategoryDataset) params.getDataset(), // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				false, // tooltips?
				false // URLs?
				);

		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis axis = plot.getDomainAxis();
		axis.setCategoryMargin(0);
		return chart;

	}
}
