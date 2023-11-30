package viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import mainDashboard.AnalysisReturn;

public class BarChart implements View {

	JPanel panel;
	Number[][] resultArray;
	String analysisName;
	String[] methodNames;

	public BarChart(AnalysisReturn analysisReturn, JPanel panel) {
		this.panel = panel;
		resultArray = analysisReturn.getAnalysisResult();
		analysisName = analysisReturn.getAnalysisName();
		methodNames = analysisReturn.getMethodNames();
	}

	public void createChart() {
		int numberOfSeries = resultArray.length - 1;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String[] legendNames = new String[resultArray.length];
		String abac = analysisName.substring( analysisName.indexOf("for ")+4).replace(", and ", ",");
		abac=abac.replace(" and ", ",");
		legendNames = abac.split(",");
		for (int i = 0; i < numberOfSeries; i++) {
			for (int j = 0; j < resultArray[0].length; j++) {
				System.out.print(resultArray[i][j]);
				dataset.addValue(resultArray[i + 1][j], legendNames[i], resultArray[0][j] + "");
			}
		}

		JFreeChart barChart = ChartFactory.createBarChart(analysisName, "Year", "", (CategoryDataset) dataset,
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		panel.add(chartPanel);

	}

}
