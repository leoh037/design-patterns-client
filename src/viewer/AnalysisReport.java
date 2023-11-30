package viewer;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import org.jfree.chart.ChartPanel;

import mainDashboard.AnalysisReturn;


public class AnalysisReport implements View {
	
	JPanel panel;
	Number[][] resultArray;
	String analysisName;
	String[] methodNames;
	String reportMessage;
	
	public AnalysisReport(AnalysisReturn analysisReturn, JPanel panel) {
		this.panel = panel;
		this.resultArray = analysisReturn.getAnalysisResult();
		this.analysisName = analysisReturn.getAnalysisName();
		this.methodNames = analysisReturn.getMethodNames(); 
		this.reportMessage = analysisReturn.getAnalysisResultString();
	}

	public void createChart() {
		JTextPane report = new JTextPane();
		report.setEditable(false);
		report.setPreferredSize(new Dimension(400, 300));
		report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		report.setBackground(Color.white);
		
		report.setText(this.reportMessage);
		
		JScrollPane outputScrollPane = new JScrollPane(report);
		panel.add(outputScrollPane);
	}

}
