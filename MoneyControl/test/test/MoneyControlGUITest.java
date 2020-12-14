/**
 * MoneyControlGUITest.java
 * Created: 25 Nov 2020
 * Author: cousm
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.junit.jupiter.api.Test;

import loaddata.LoadExpenses;
import view.maingui.MoneyControlGUI;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class MoneyControlGUITest {
	/**
	 * 
	 */
	@Test
	public void testbuildMenuBar () {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		MoneyControlGUI moneyControlGUI = new MoneyControlGUI(loadExpenses, yearToMonthToDescriptionWithAmounts);
		
		JMenuBar jMenubar = moneyControlGUI.buildMenuBar(loadExpenses);
		assertEquals(3, jMenubar.getMenuCount());
		assertEquals("File", jMenubar.getMenu(0).getText());
		assertEquals("Expenses", jMenubar.getMenu(1).getText());
		assertEquals("Help", jMenubar.getMenu(2).getText());
	}
	
	/**
	 * 
	 */
	@Test
	public void testLayoutTop () {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		MoneyControlGUI moneyControlGUI = new MoneyControlGUI(loadExpenses, yearToMonthToDescriptionWithAmounts);
		
		JPanel testPanel = moneyControlGUI.layoutTop();
		
		assertEquals(19, testPanel.getComponentCount());
	}
	
	/**
	 * 
	 */
	@Test
	public void testCreateOrUpdateBalanceChart() {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		MoneyControlGUI moneyControlGUI = new MoneyControlGUI(loadExpenses, yearToMonthToDescriptionWithAmounts);
		
		ChartPanel testChartPanel = moneyControlGUI.createOrUpdateBalanceChart(2000.0);
		assertNotNull(testChartPanel.getChart());
	}
	
	
}
