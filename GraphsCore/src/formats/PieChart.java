package formats;

import general.ContainerParams;
import general.GraphParams;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;


public class PieChart {

	public static Component getPainting(final ContainerParams container,
			List<GraphParams> params) throws Exception {
		JPanel chartPanel = new JPanel(new GridLayout(container.getnGraphRows() , 
        		container.getnGraphColumns()));
        for (GraphParams param : params) {
        	final JFreeChart chart = createChart(param.getTitle(), (PieDataset) param.getDataset());
        	chartPanel.add(new ChartPanel(chart));
		}
		return chartPanel;
	}

    private static JFreeChart createChart(final String title, final PieDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createPieChart(
            title,  			 // chart title
            dataset,             // data
            true,               // include legend
            false,
            false
        );

        PiePlot plot = (PiePlot)chart.getPlot();
        plot.setDirection(Rotation.ANTICLOCKWISE);
        plot.setLegendLabelGenerator(
        	    new StandardPieSectionLabelGenerator("{0} {1} {2}"));
        return chart;
    }
    
}
