package viewer;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mainDashboard.AnalysisReturn;



public class ViewFactory {

	private AnalysisReturn analysisReturn;
	private ArrayList<String> array;
	private JPanel panel;
	private static ViewFactory factoryInstance;
	private static HashMap<String, View> view = new HashMap<String, View>();
	
	public static ViewFactory getFactory(AnalysisReturn analysisReturn, ArrayList<String> array, JPanel panel) {
		if(factoryInstance == null) {
			factoryInstance = new ViewFactory(array, panel);
		}
		factoryInstance.analysisReturn = analysisReturn;
		return factoryInstance;
	}
	
	private ViewFactory(ArrayList<String> array, JPanel panel) {
		this.array = array;
		this.panel = panel;
	}
	
	public void createViews() {
		int index = 0;
		int size = array.size();
		while((index<size) && (array.get(index) != null)) {
			if(array.get(index).equals("Report")) {
				View report = new AnalysisReport(analysisReturn, panel);
				report.createChart();
			}
			if(array.get(index).equals("Bar")) {
				View bar = new BarChart(analysisReturn, panel);
				bar.createChart();
			}
			if(array.get(index).equals("Line")) {
				View line = new LineChart(analysisReturn, panel);
				line.createChart();
			}
			if(array.get(index).equals("Scatter")) {
				View scatter = new ScatterChart(analysisReturn, panel);
				scatter.createChart();
			}
			if(array.get(index).equals("Pie")) {
				View pie = new PieChart(analysisReturn, panel);
				pie.createChart();
			}
			index++;
		}
	}
	
}
