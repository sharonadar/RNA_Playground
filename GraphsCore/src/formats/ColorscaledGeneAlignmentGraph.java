package formats;

import general.ContainerParams;
import general.GraphUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Paint;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.GrayPaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

public class ColorscaledGeneAlignmentGraph {
	
	private static class ColorfullPaintScale extends GrayPaintScale {

		private static final long serialVersionUID = -6412437775488897526L;
		
		public ColorfullPaintScale(int i, int j) {
			super(i, j);
		}

		@Override
		public Paint getPaint(double value) {
			if(value == -1)
				return new Color(0,0,255);
			
			 double v = Math.max(value, getLowerBound());
			 v = Math.min(v, getUpperBound());
			 double g = ((v - getLowerBound()) / (getUpperBound() - getLowerBound())) * 255.0;
			 if(g == 0)
				 return Color.BLACK;
			 int val = (int)(Math.log(g+1) / Math.log(1.022));
			 return new Color(val, 255 - val, 0);
		}
	}
	
	public static Component getPainting(ContainerParams container, DefaultXYZDataset dataset) {
		
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
        xAxis.setVisible(false);
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLowerMargin(0.0);
        yAxis.setUpperMargin(0.0);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setVisible(false);
        
        XYBlockRenderer renderer = new XYBlockRenderer();
        ColorfullPaintScale paintScale = new ColorfullPaintScale(0, 100);
        
        renderer.setPaintScale(paintScale);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setBackgroundPaint(Color.lightGray);
        JFreeChart chart = new JFreeChart(null, plot);
        
//        TextTitle title = new TextTitle(container.getTitle());
//        title.setPosition(RectangleEdge.LEFT);
//        chart.setTitle(title);
        chart.removeLegend();
        
        PaintScaleLegend psl = new PaintScaleLegend(paintScale, xAxis);
        psl.setAxisOffset(5.0);
        psl.setPosition(RectangleEdge.RIGHT);
        psl.setBackgroundPaint(Color.lightGray);
        psl.setMargin(new RectangleInsets(5, 5, 5, 5));
        chart.addSubtitle(psl);
        
		GraphUtilities.saveToFile(container.getTitle(), chart);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(container.getWeight(), container.getHeight()));
		return chartPanel;
	}

}
