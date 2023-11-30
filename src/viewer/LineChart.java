package viewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import mainDashboard.AnalysisReturn;


public class LineChart implements View {
	
	JPanel panel;
	Number[][] resultArray;
	String analysisName;
	String[] methodNames;

	public LineChart(AnalysisReturn analysisReturn, JPanel panel) {
		this.panel = panel;
		resultArray = analysisReturn.getAnalysisResult();
		analysisName = analysisReturn.getAnalysisName();
		methodNames = analysisReturn.getMethodNames();
	}

	public void createChart() {
		//System.out.print(resultArray.toString());
		int numberOfSeries = resultArray.length - 1;
		XYSeries[] series = new XYSeries[numberOfSeries];
		
		for(int i = 0; i < numberOfSeries; i++) {
			series[i] = new XYSeries(methodNames[i]);
			for (int j = 0; j < resultArray[0].length; j++) {
				series[i].add(resultArray[0][j], resultArray[i+1][j]);
			}
		}
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		for(int i = 0; i < numberOfSeries; i++) {
			dataset.addSeries(series[i]);
		}
		
		JFreeChart chart = ChartFactory.createXYLineChart(analysisName, "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(
		new TextTitle(analysisName, new Font("Serif", java.awt.Font.BOLD, 18)));

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		panel.add(chartPanel);
		
	}
}

