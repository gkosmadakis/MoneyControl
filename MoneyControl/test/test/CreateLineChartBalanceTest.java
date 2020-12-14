/**
 * ProcessLineChartBalanceTest.java
 * Created: 23 Nov 2020
 * Author: cousm
 */
package test;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JComboBox;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.junit.jupiter.api.Test;

import loaddata.LoadExpenses;
import view.chartsgui.CreateLineChartBalance;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class CreateLineChartBalanceTest {
	/**
	 * 
	 */
	@Test
	public void testBuildLineChartBalance () {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		JFreeChart chart = null;
		ChartPanel chartPanel = new ChartPanel(chart);
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		String [] yearDataArray = new String[]{"2016","2017","2018","2019","2020"};
		JComboBox<String> yearSelection = new JComboBox<String>(yearDataArray);
		CreateLineChartBalance processLineChartBalance = new CreateLineChartBalance(chart, chartPanel, "2000", yearToMonthToDescriptionWithAmounts, yearSelection, loadExpenses );
		
		processLineChartBalance.buildLineBalanceChart();
		
		assertNotNull(chartPanel.getChart());
		
	}
}
