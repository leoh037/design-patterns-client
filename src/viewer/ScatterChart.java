package viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;

import mainDashboard.AnalysisReturn;

public class ScatterChart implements View {
	
	JPanel panel;
	Number[][] resultArray;
	String analysisName;
	String[] methodNames;

	public ScatterChart(AnalysisReturn analysisReturn, JPanel panel) {
		this.panel = panel;
		resultArray = analysisReturn.getAnalysisResult();
		analysisName = analysisReturn.getAnalysisName();
		methodNames = analysisReturn.getMethodNames();
	}

	public void createChart() {
		int numberOfSeries = resultArray.length - 1;
		TimeSeries[] series = new TimeSeries[numberOfSeries];
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		String[] legendNames = new String[resultArray.length];
		String abac = analysisName.substring( analysisName.indexOf("for ")+4).replace(", and ", ",");
		abac=abac.replace(" and ", ",");
		legendNames = abac.split(",");
		for(int i = 0; i < numberOfSeries; i++) {
			series[i] = new TimeSeries(legendNames[i]);
			for (int j = 0; j < resultArray[0].length; j++) {
				series[i].add(new Year(resultArray[0][j].intValue()), resultArray[i+1][j]);
			}
			dataset.addSeries(series[i]);
		}
		
//		TimeSeries series1 = new TimeSeries(methodNames[0]);
//		series1.add(new Year(2018), 5.6);
//		series1.add(new Year(2017), 5.7);
//		series1.add(new Year(2016), 5.8);
//		series1.add(new Year(2015), 5.8);
//		series1.add(new Year(2014), 5.9);
//		series1.add(new Year(2013), 6.0);
//		series1.add(new Year(2012), 6.1);
//		series1.add(new Year(2011), 6.2);
//		series1.add(new Year(2010), 6.4);
//
//		TimeSeries series2 = new TimeSeries(methodNames[0]);
//		series2.add(new Year(2018), 10624);
//		series2.add(new Year(2017), 10209);
//		series2.add(new Year(2016), 9877);
//		series2.add(new Year(2015), 9491);
//		series2.add(new Year(2014), 9023);
//		series2.add(new Year(2013), 8599);
//		series2.add(new Year(2012), 8399);
//		series2.add(new Year(2011), 8130);
//		series2.add(new Year(2010), 7930);
//
//		TimeSeries series3 = new TimeSeries(methodNames[0]);
//		series3.add(new Year(2018), 2.92);
//		series3.add(new Year(2017), 2.87);
//		series3.add(new Year(2016), 2.77);
//		series3.add(new Year(2015), 2.8);
//		series3.add(new Year(2014), 2.83);
//		series3.add(new Year(2013), 2.89);
//		series3.add(new Year(2012), 2.93);
//		series3.add(new Year(2011), 2.97);
//		series3.add(new Year(2010), 3.05);

		
	

		XYPlot plot = new XYPlot();
		XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
	

		plot.setDataset(0, dataset);
		plot.setRenderer(0, itemrenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));



		plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		//plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

		JFreeChart scatterChart = new JFreeChart(analysisName,
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(scatterChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		panel.add(chartPanel);
	}
	
}
