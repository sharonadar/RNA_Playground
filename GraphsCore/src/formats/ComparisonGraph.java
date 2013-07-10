package formats;

import general.ContainerParams;
import general.GraphParams;

import java.awt.Component;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;


public class ComparisonGraph {

	public static Component getPainting(final ContainerParams container,
			GraphParams params) throws Exception {

		JFreeChart chart = createChart(params.getTitle(),
				params.getxLabel(), params.getyLabel(),
				(XYDataset) params.getDataset());
		final ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;

	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return The chart.
	 */
	private static JFreeChart createChart(final String title, final String xLabel,
			final String yLabel, final XYDataset dataset) {

		// create the chart...
		JFreeChart chart = ChartFactory.createScatterPlot(title, 
				xLabel, // x axis label
				yLabel, // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);

		XYPlot plot = (XYPlot) chart.getPlot(); 
		
		// label the points
		StandardXYItemRenderer renderer = new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES);
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        XYItemLabelGenerator generator = new XYItemLabelGenerator() {
			@Override
			public String generateLabel(XYDataset dataset, int series, int id) {
				XYLabelSeries xySeries = (XYLabelSeries) ((XYSeriesCollection)dataset).getSeries().get(series);
				return xySeries.getLabel(id);
			}
		};
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);
        plot.setRenderer(renderer);
        
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        double max = Math.max(domain.getRange().getUpperBound(), range.getRange().getUpperBound());
        domain.setRange(0.00, max);
        range.setRange(0.00, max);
        
        addGuideline(dataset, plot);
        
		return chart;

	}

	private static void addGuideline(final XYDataset dataset, XYPlot plot) {
		int datasetId = plot.getDatasetCount();
        double maxVal = Math.max(((XYSeriesCollection)dataset).getRangeUpperBound(true), 
        		((XYSeriesCollection)dataset).getDomainUpperBound(true)); 
        XYSeriesCollection seriesCollection = new XYSeriesCollection();
        XYLabelSeries guideLine = new XYLabelSeries("line");
        guideLine.add(0.0, 0.0);
        guideLine.add(maxVal, maxVal);
        seriesCollection.addSeries(guideLine);
        StandardXYItemRenderer renderer2 = new StandardXYItemRenderer(StandardXYItemRenderer.LINES);
        plot.setDataset(datasetId, seriesCollection);
        plot.setRenderer(datasetId, renderer2);
	}

}
