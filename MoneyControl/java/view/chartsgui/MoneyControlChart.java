package view.chartsgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import loaddata.LoadExpenses;
import utils.DataUtilities;
import utils.LoadProperties;
import static utils.Constants.*;

/**
 * @author cousm Class to build the GUI window that shows the charts
 */
public class MoneyControlChart extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JComboBox<String> yearSelection;
	private TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts;
	private CreatePieChart processPieChart;
	private CreateBarChart processBarChart;
	private CreateLineChart processLineChart;
	private CreateLineChartBalance processLineChartBalance;
	private JFreeChart chart;
	private JRadioButton pieChart, barChart, lineChart, lineChartBalance;
	private ChartPanel chartPanel;
	private LoadExpenses loadExpenses;
	private String incomeEntered;
	private String monthsListFromProperties;
	private JList<String> listOfMonths;
	private DataUtilities dataUtilities;
	
	/**
	 * @param loadExpenses
	 * @param incomeEntered
	 */
	public MoneyControlChart (LoadExpenses loadExpenses, String incomeEntered) {
		this.loadExpenses = loadExpenses;
		this.incomeEntered = incomeEntered;
		yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		loadProperties();
		dataUtilities = new DataUtilities();
		layoutGUI();
		
	}
	
	/**
	 * Builds the GUI to show the charts
	 */
	public void layoutGUI () {
		JPanel mainPanel = new JPanel();// the main window
		JPanel topPanel = new JPanel();// the top panel that has the JList, the empty panel and the radiobuttons
								
		JPanel listMonthsPanel = createListMonthsPanel();// the panel with the months list
		JPanel listYearsPanel = createListYearsPanel();// the panel with the years list
		
		JPanel bottomPanel = new JPanel();// the bottom panel that has the chartPanel
		
		chartPanel = new ChartPanel(chart);
		chartPanel.setPopupMenu(null);
		chartPanel.setPreferredSize(new java.awt.Dimension(680, 330));
		mainPanel.setPreferredSize(new java.awt.Dimension(830, 530));
		JPanel emptyPanel = new JPanel();
		emptyPanel.setPreferredSize(new Dimension(330, 20));
		emptyPanel.setBackground(new Color(0, 0, 0, 0));
		
		JPanel radioPanel = createRadioChartButtons();
		
		addComponentsToMainPanel(mainPanel, topPanel, listMonthsPanel, listYearsPanel, bottomPanel, emptyPanel, radioPanel);
		
		monthListSelectionListener(listOfMonths);
	}

	private JPanel createListMonthsPanel() {
		JPanel listMonthsPanel = new JPanel();// the panel with the JList
		listMonthsPanel.setLayout(new BoxLayout(listMonthsPanel, BoxLayout.PAGE_AXIS));
		listMonthsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		String [] listData = monthsListFromProperties.split(",");// contents of the JList
		listOfMonths = new JList<String>(listData);
		JScrollPane listScroller = new JScrollPane(listOfMonths);// add the list to the scrollPane
		listOfMonths.setLayoutOrientation(JList.VERTICAL_WRAP);
		listOfMonths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMonthsPanel.add(listScroller);
		
		return listMonthsPanel;
	}
	
	private JPanel createListYearsPanel() {
		JPanel listYearsPanel = new JPanel();
		TreeSet<String> allTheYearsInFile = loadExpenses.getAllTheYears();
		DataUtilities chartsDataUtilities = new DataUtilities();
		String[] yearDataArray = chartsDataUtilities.convertTreeSetToArray(allTheYearsInFile);
		yearSelection = new JComboBox<String>(yearDataArray);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		yearSelection.setSelectedIndex(chartsDataUtilities.findCurrentYearIndexInYearsArray(year, loadExpenses.getAllTheYears()));
		listYearsPanel.add(yearSelection);
		
		return listYearsPanel;
	}
	
	private JPanel createRadioChartButtons () {
		JPanel radioPanel = new JPanel();
		pieChart = new JRadioButton(PIE, false);
		pieChart.setActionCommand(PIE_COMMAND);
		barChart = new JRadioButton(BAR, false);
		barChart.setActionCommand(BAR_COMMAND);
		lineChart = new JRadioButton(LINE, false);
		lineChart.setActionCommand(LINE_COMMAND);
		lineChartBalance = new JRadioButton(BALANCE, false);
		lineChartBalance.setActionCommand(LINE_COMMAND_BALANCE);
		addActionListenerToRadioButtons();
		ButtonGroup group = new ButtonGroup();
		group.add(pieChart);
		group.add(barChart);
		group.add(lineChart);
		group.add(lineChartBalance);
		pieChart.setPreferredSize(new Dimension(45, 10));
		barChart.setPreferredSize(new Dimension(45, 10));
		radioPanel.add(pieChart);
		radioPanel.add(barChart);
		radioPanel.add(lineChart);
		radioPanel.add(lineChartBalance);
		return radioPanel;
	}

	private void addActionListenerToRadioButtons () {
		pieChart.addActionListener(this);
		barChart.addActionListener(this);
		lineChart.addActionListener(this);
		lineChartBalance.addActionListener(this);
	}

	private void addComponentsToMainPanel (JPanel mainPanel, JPanel topPanel, JPanel listMonthsPanel, JPanel listYearsPanel, JPanel bottomPanel, JPanel emptyPanel, JPanel radioPanel) {
		mainPanel.setLayout(new FlowLayout());
		topPanel.add(listMonthsPanel);
		topPanel.add(listYearsPanel);
		topPanel.add(emptyPanel);
		topPanel.add(radioPanel);
		topPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2, true));
		bottomPanel.add(chartPanel);
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);
		add(mainPanel);
	}
	
	private void loadProperties () {
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		monthsListFromProperties = propertiesMap.get(MONTHS_LIST);
	}

	private void monthListSelectionListener (final JList<String> list) {
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged (ListSelectionEvent e) {
				String selectedMonthString = dataUtilities.convertMonthIntToMonthString(list.getSelectedIndex());
				String selectedMonthName = list.getSelectedValue();
				if (pieChart.isSelected()) {
					processPieChart.showPieChart(selectedMonthName, selectedMonthString, yearSelection.getSelectedItem().toString());
				}
				else if (barChart.isSelected()) {
					processBarChart.showBarChart(selectedMonthName, selectedMonthString, yearSelection.getSelectedItem().toString());
				}
			}
		});
	}
	
	@Override
	public void actionPerformed (ActionEvent e) {
		DefaultPieDataset result = null;
		DefaultCategoryDataset dataset = null;
		if (e.getActionCommand().equals(PIE_COMMAND)) {
			resetChartPanel(chartPanel, pieChart);
			processPieChart = new CreatePieChart(chart, result, chartPanel, yearToMonthToDescriptionWithAmounts, loadExpenses, incomeEntered);
			processPieChart.buildPieChart("Pie Chart Test", "Total Expenses Pie");
		}
		else if (e.getActionCommand().equals(BAR_COMMAND)) {
			resetChartPanel(chartPanel, barChart);
			processBarChart = new CreateBarChart(chart, dataset, chartPanel, yearToMonthToDescriptionWithAmounts, loadExpenses, incomeEntered);
			processBarChart.buildBarChart();
		}
		else if (e.getActionCommand().equals(LINE_COMMAND)) {
			resetChartPanel(chartPanel, lineChart);
			processLineChart = new CreateLineChart(chart, chartPanel, yearToMonthToDescriptionWithAmounts, loadExpenses, incomeEntered);
			processLineChart.buildLineChart(yearSelection);
		}
		else if (e.getActionCommand().equals(LINE_COMMAND_BALANCE)) {
			resetChartPanel(chartPanel, lineChartBalance);
			processLineChartBalance = new CreateLineChartBalance(chart, chartPanel, incomeEntered, yearToMonthToDescriptionWithAmounts, yearSelection, loadExpenses);
			processLineChartBalance.buildLineBalanceChart();
		}
	}
	
	private void resetChartPanel(ChartPanel chartPanel, JRadioButton chartSelected){
		chartPanel.revalidate();
		chartSelected.setSelected(true);
	}
	
}
