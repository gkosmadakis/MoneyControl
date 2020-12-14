/**
 * ProcessPieChartTest.java
 * Created: 23 Nov 2020
 * Author: cousm
 */
package test;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.jupiter.api.Test;

import loaddata.LoadExpenses;
import view.chartsgui.CreatePieChart;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class CreatePieChartTest {
	/**
	 * 
	 */
	@Test
	public void testBuildPieChart () {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		JFreeChart chart = null;
		DefaultPieDataset result = null;
		ChartPanel chartPanel = new ChartPanel(chart);
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		CreatePieChart processPieChart = new CreatePieChart(chart, result, chartPanel, yearToMonthToDescriptionWithAmounts, loadExpenses, "2000");
		
		processPieChart.buildPieChart("Pie Chart Test", "Total Expenses Pie");
		
		assertNotNull(chartPanel.getChart());
	}
	
	/**
	 * 
	 */
	@Test
	public void testShowPieChart() {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		JFreeChart chart = null;
		DefaultPieDataset result = new DefaultPieDataset();;
		ChartPanel chartPanel = new ChartPanel(chart);
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		CreatePieChart processPieChart = new CreatePieChart(chart, result, chartPanel, yearToMonthToDescriptionWithAmounts, loadExpenses, "2000");
		processPieChart.buildPieChart("Pie Chart Test", "Total Expenses Pie");
		
		DefaultPieDataset output = processPieChart.showPieChart("November", "11", "2020");
		assertTrue(output.getKeys().contains("House Rent") && output.getKeys().contains("Shopping"));
	}
}
