/**
 * UpdateBalanceTest.java
 * Created: 16 Dec 2020
 * Author: cousm
 */
package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;
import org.junit.jupiter.api.Test;

import loaddata.LoadExpenses;
import service.IncomeFileService;
import service.WriteToFileService;
import utils.LoadProperties;
import view.maingui.MoneyControlGUI;
import view.maingui.UpdateBalance;

/**
 * @author cousm
 *
 */
public class UpdateBalanceTest {
	/**
	 * 
	 */
	@Test
	public void testCreateOrUpdateBalanceChart() {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		UpdateBalance updateBalance = setUpTest(loadExpenses);
		
		ChartPanel testChartPanel = updateBalance.createOrUpdateBalanceChart(2000.0);
		assertNotNull(testChartPanel.getChart());
	}

	/**
	 * 
	 */
	@Test
	public void testSetupBalance() {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		Date date = addAnExpenseToFile(loadExpenses);
		
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		IncomeFileService incomeFileService = new IncomeFileService("incomeTest.txt");
		JLabel balanceLabel = new JLabel();
		JTextField incomeField = new JTextField();
		MoneyControlGUI moneyControlGUI = new MoneyControlGUI(loadExpenses, yearToMonthToDescriptionWithAmounts);		
		UpdateBalance updateBalance = new UpdateBalance(balanceLabel, incomeFileService, incomeField, moneyControlGUI);
		
		double testBalance = updateBalance.setUpBalance(yearToMonthToDescriptionWithAmounts, balanceLabel);
		
		assertTrue(testBalance == 1400.0);
		deleteExpenseFromFile(date);
	}
	
	/**
	 * 
	 */
	@Test
	public void testUpdateIncomeChart() {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		Date date = addAnExpenseToFile(loadExpenses);
		
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		JLabel balanceLabel = new JLabel();
		IncomeFileService incomeFileService = new IncomeFileService("incomeTest.txt");
		JTextField incomeField = new JTextField();
		
		incomeField.setText("2000");
		
		MoneyControlGUI moneyControlGUI = new MoneyControlGUI(loadExpenses, yearToMonthToDescriptionWithAmounts);		
		UpdateBalance updateBalance = new UpdateBalance(balanceLabel, incomeFileService, incomeField, moneyControlGUI);
		
		updateBalance.updateIncomeChart(yearToMonthToDescriptionWithAmounts);
		
		assertTrue(balanceLabel.getText().equals("Balance: 1400.0"));
		
		deleteExpenseFromFile(date);
	}
	
	private UpdateBalance setUpTest (LoadExpenses loadExpenses) {
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		JLabel balanceLabel = new JLabel();
		IncomeFileService incomeFileService = new IncomeFileService("incomeTest.txt");
		JTextField incomeField = new JTextField();
		MoneyControlGUI moneyControlGUI = new MoneyControlGUI(loadExpenses, yearToMonthToDescriptionWithAmounts);		
		UpdateBalance updateBalance = new UpdateBalance(balanceLabel, incomeFileService, incomeField, moneyControlGUI);
		return updateBalance;
	}
	
	private void deleteExpenseFromFile (Date date) {
		/* Delete the expense to keep the expensesTest file as it was */
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = formatter.format(date);
		deleteLineFromFile("600 Shopping "+dateString, "expensesTest.txt");
	}

	private Date addAnExpenseToFile (LoadExpenses loadExpenses) {
		loadExpenses.setExpensesFileIsNotEmpty(true);
		WriteToFileService writeToFileService = new WriteToFileService();
		Date date = new Date();  
		writeToFileService.writeToFile("600", "Shopping", date,"expensesTest.txt",loadExpenses);
		return date;
	}
	
	private void deleteLineFromFile (String lineToDelete, String fileName) {
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			StringBuilder fileContent = new StringBuilder();
			while ((strLine = br.readLine()) != null) {
				if (!strLine.replace(" ", "").equalsIgnoreCase(lineToDelete.replaceAll("\\[", "").replaceAll("\\]", "").replace(" ", ""))) {
					fileContent.append(strLine);
					fileContent.append(System.getProperty("line.separator"));
				}
			}
			FileWriter fstreamWrite = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstreamWrite);
			out.write(fileContent.toString().trim());
			out.close();
			br.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
