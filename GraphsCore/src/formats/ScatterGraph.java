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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYDataset;


public class ScatterGraph {

	public static Component getPainting(final ContainerParams container,
			List<GraphParams> params) throws Exception {
		JPanel chartPanel = new JPanel(new GridLayout(container.getnGraphRows() , 
        		container.getnGraphColumns()));
        for (GraphParams param : params) {
        	final JFreeChart chart = createChart(param.getTitle(),
    				param.getxLabel(), param.getyLabel(),
    				(XYDataset) param.getDataset());
        	chartPanel.add(new ChartPanel(chart));
        	GraphUtilities.saveToFile(container.getTitle() + "_" + param.getTitle(), chart);
		}
		return chartPanel;
	}

	private static JFreeChart createChart(final String title, final String xLabel,
			final String yLabel, final XYDataset dataset) {

		JFreeChart chart = ChartFactory.createScatterPlot(title, 
				xLabel, // x axis label
				yLabel, // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);

		XYPlot plot = (XYPlot) chart.getPlot();
		StandardXYItemRenderer renderer2 = new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES);
	    plot.setRenderer(renderer2);
	    
		return chart;

	}

}
