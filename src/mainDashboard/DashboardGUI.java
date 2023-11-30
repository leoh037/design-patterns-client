package mainDashboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import login.MainWindow;
import viewer.ViewFactory;

public class DashboardGUI extends JFrame{
	
	public static void main(String[] args) {
		DashboardGUI.getInstance();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	private String selectedSource;
	private String selectedSourceCode;
	private String selectedCountry;
	private String selectedCountryCode;
	private String selectedYearFrom;
	private String selectedYearTo;
	private String selectedViewName;
	private String selectedView;
	private String selectedMethodName;
	private String selectedAnalysisMethod;
	private String httpRequestDomain;
	private AnalysisReturn analysisReturn;
	private ProcessServerResponse serverResult;
	
	private static DashboardGUI instance = null;
	
	private Vector<String> countryNames;
	private Vector<String> years;
	private Vector<String> viewNames;
	private Vector<String> methodNames;
	private Vector<String> dataSources;
	
	private JComboBox<String> countryList;
	private JComboBox<String> yearFromList;
	private JComboBox<String> yearToList;
	private JComboBox<String> viewsList;
	private JComboBox<String> methodList;
	private JComboBox<String> sourceList;
	
	private JLabel countryLabel;
	private JLabel fromLabel;
	private JLabel toLabel;
	private JLabel viewsLabel;
	private JLabel methodLabel;
	private JLabel sourceLabel;
	
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel resultPanel;
	
	private static HashMap<String, String> country = new HashMap<String, String>();
	private static HashMap<String, String> method = new HashMap<String, String>();
	private static HashMap<String, String> view = new HashMap<String, String>();
	private static HashMap<String, String> dataSource = new HashMap<String, String>();
	
	private JButton addView;
	private JButton removeView;
	private JButton recalculate;
	
	private ViewFactory viewFactory;
	private ArrayList<String> charts;
	
	private String inline;
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	public static DashboardGUI getInstance() {
		if(instance == null) {
			instance = new DashboardGUI();
		}
		return instance;
	}

	private DashboardGUI() { 
		
		this.setTitle("Main Dashboard");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		this.setSize(1600, 900);
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		countryLabel = new JLabel("Country");
		fromLabel = new JLabel("From");
		toLabel = new JLabel("To");
		viewsLabel = new JLabel("Available Views: ");
		methodLabel = new JLabel("Choose Analysis Method: ");
		sourceLabel = new JLabel("Chose Data Source");

		dataSources = new Vector<String>();
		addDataSource("World Bank", "WB");
		addDataSource("COVID-19 Open Data Working Group", "CD");
		sourceList = new JComboBox<String>(dataSources);
		
		countryNames = new Vector<String>();
		addCountry("USA", "USA");
		addCountry("Canada", "CAN");
		addCountry("France", "FRA");
		addCountry("China", "CHN");
		addCountry("Brazil", "BRA");
		countryNames.sort(null);
		countryList = new JComboBox<String>(countryNames);
		countryList.setToolTipText("Select a country from the dropdown list");
		
		///// note that year 2022 breaks some analyses
		years = new Vector<String>();
		for (int i = 2021; i >= 2000; i--) {
			years.add("" + i);
		}
		yearFromList = new JComboBox<String>(years);
		yearFromList.setToolTipText("Select a start year");
		yearToList = new JComboBox<String>(years);
		yearToList.setToolTipText("Select an end year");
		
		viewNames = new Vector<String>();
		addView("Pie Chart", "Pie");
		addView("Line Chart", "Line");
		addView("Bar Chart", "Bar");
		addView("Scatter Chart", "Scatter");
		
		viewsList = new JComboBox<String>(viewNames);
		viewsList.setToolTipText("Select a view from the dropdown list");
		
		methodNames = new Vector<String>();
		addAnalysisMethod("APC Hospital beds (per 1,000 people)", "APC.Hospital.Beds");
		addAnalysisMethod("APC for PM2.5 Air Pollution, CO2 Emissions, and Energy Use", "APC.Air.Pollution+CO2.Emissions+Energy.Use");
		addAnalysisMethod("APC Education & Health Expenditure", "APC.Education+Health.Expenditure");
		addAnalysisMethod("Forest Area & PM2.5 Air Pollution", "Forest.Area+Air.Pollution");
		addAnalysisMethod("Ratio of CO2 emissions per GDP (per capita)", "Ratio.CO2.emissions.Per.GDP");
		addAnalysisMethod("Ratio of current health expenditure per hospial beds", "Ratio.Current.Health.Expenditure.Per.Hospial.Beds");
		addAnalysisMethod("Average for Government Expenditure on Education", "Average.Government.Expenditure.Education");
		addAnalysisMethod("Average for Forest Area", "Average.Forest.Area");
		addAnalysisMethod("Display Covid Cases", "Covid.Cases");
		methodList = new JComboBox<String>(methodNames);
		methodList.setToolTipText("Select an analysis method from the dropdown list");
		
		addView = new JButton("+");
		removeView = new JButton("-");
		recalculate = new JButton("Recalculate");
		
		northPanel = new JPanel();
		northPanel.add(sourceLabel);
		northPanel.add(sourceList);
		northPanel.add(countryLabel);
		northPanel.add(countryList);
		northPanel.add(fromLabel);
		northPanel.add(yearFromList);
		northPanel.add(toLabel);
		northPanel.add(yearToList);
		
		southPanel = new JPanel();
		southPanel.add(viewsLabel);
		southPanel.add(viewsList);
		southPanel.add(addView);
		southPanel.add(removeView);
		southPanel.add(methodLabel);
		southPanel.add(methodList);
		southPanel.add(recalculate);
		
		
		charts = new ArrayList<String>();
		charts.add("Report");

		recalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == recalculate) {
					selectedSource = sourceList.getSelectedItem().toString();
					selectedSourceCode = dataSource.get(selectedSource);
					selectedCountry = countryList.getSelectedItem().toString();
					selectedCountryCode = country.get(selectedCountry);
					selectedYearFrom = yearFromList.getSelectedItem().toString();
					selectedYearTo = yearToList.getSelectedItem().toString();
					selectedMethodName = methodList.getSelectedItem().toString();
					selectedAnalysisMethod = method.get(selectedMethodName);
					String message = "";
					
					if(Integer.parseInt(selectedYearFrom) >= Integer.parseInt(selectedYearTo)) {
						message = "The start year must be smaller than the end year";
						JOptionPane.showMessageDialog(null, message);
					}
					else {
						
						//httpRequestDomain = "http://api.worldbank.org/v2";
						//httpRequestDomain = "https://api.opencovid.ca/summary?";
						
						if(selectedSourceCode.equals("CD")) httpRequestDomain = "https://api.opencovid.ca/summary?";
						else httpRequestDomain = "http://api.worldbank.org/v2";
						
						String urlString = String.format("http://localhost:8000/Analysis/?analysis_method=%s&country=%s&year_range=%s&request_domain=%s", selectedAnalysisMethod, selectedCountryCode,
								selectedYearFrom+":"+selectedYearTo, httpRequestDomain);
						connectToServer(urlString);
					
						///////////////////////////////////////////////////////////////
						resultPanel.removeAll();
							
						serverResult = new ProcessServerResponse(inline);
						analysisReturn = serverResult.getAnalysisReturn();
						
						viewFactory = ViewFactory.getFactory(analysisReturn, charts, resultPanel);
						viewFactory.createViews();
						resultPanel.revalidate();
						resultPanel.repaint();
					}
				}
			}
		});
		
		addView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultPanel.removeAll();
				selectedViewName = viewsList.getSelectedItem().toString();
				selectedView = view.get(selectedViewName);
				if(!charts.contains(selectedView)) {
					charts.add(selectedView);
					viewFactory.createViews();
					resultPanel.revalidate();
					resultPanel.repaint();
				}
			}
		});
	
		removeView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(charts.size() > 1) {
					selectedViewName = viewsList.getSelectedItem().toString();
					selectedView = view.get(selectedViewName);
					if(charts.remove(selectedView)){
						resultPanel.removeAll();
						viewFactory.createViews();
						resultPanel.revalidate();
						resultPanel.repaint();
					}
				}
			}
		});
		
		resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(2, 3));
		
		getContentPane().add(northPanel, BorderLayout.NORTH);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		getContentPane().add(resultPanel, BorderLayout.CENTER);
		
		this.setVisible(true);
		
	}
	
	public void addCountry(String countryName, String countryCode) {
		countryNames.add(countryName);
		country.put(countryName, countryCode);
	}
	
	public void addAnalysisMethod(String methodName, String methodCode) {
		methodNames.add(methodName);
		method.put(methodName, methodCode);
	}
	
	public void addView(String viewName, String viewCode) {
		viewNames.add(viewName);
		view.put(viewName, viewCode);
	}
	
	public void addDataSource(String sourceName, String sourceNameCode) {
		dataSources.add(sourceName);
		dataSource.put(sourceName, sourceNameCode);
	}
	
	public void connectToServer(String urlString) {
		System.out.println(urlString);
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.connect();
			
			int responsecode = conn.getResponseCode();
			System.out.println("Response Code " + responsecode);
			// IF THE RESPONSE IS 200 OK GET THE LINE WITH THE RESULTS
			if (responsecode == 200) {
				inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				//System.out.println("-------- Here is the response to the client: " + inline);
				
				sc.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
}
