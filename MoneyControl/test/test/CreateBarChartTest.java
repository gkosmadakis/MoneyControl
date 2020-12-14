/**
 * ProcessBarChartTest.java
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
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.jupiter.api.Test;

import loaddata.LoadExpenses;
import view.chartsgui.CreateBarChart;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class CreateBarChartTest {
	/**
	 * 
	 */
	@Test
	public void testBuildBarChart () {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		JFreeChart chart = null;
		DefaultCategoryDataset result = null;
		ChartPanel chartPanel = new ChartPanel(chart);
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		CreateBarChart processBarChart = new CreateBarChart(chart, result, chartPanel, yearToMonthToDescriptionWithAmounts, loadExpenses, "2000");
		
		processBarChart.buildBarChart();
		
		assertNotNull(chartPanel.getChart());
	}
	
	/**
	 * 
	 */
	@Test
	public void testShowBarChart() {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		JFreeChart chart = null;
		DefaultCategoryDataset result = new DefaultCategoryDataset();;
		ChartPanel chartPanel = new ChartPanel(chart);
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		CreateBarChart processBarChart = new CreateBarChart(chart, result, chartPanel, yearToMonthToDescriptionWithAmounts, loadExpenses, "2000");
		processBarChart.buildBarChart();
		
		DefaultCategoryDataset output = processBarChart.showBarChart("April", "04", "2016");
		assertTrue(output.getRowKeys().contains("House Rent") && output.getRowKeys().contains("Travel"));
		
	}
}
