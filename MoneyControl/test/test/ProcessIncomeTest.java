/**
 * ProcessIncomeTest.java
 * Created: 20 Nov 2020
 * Author: cousm
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

import loaddata.LoadExpenses;
import service.IncomeFileService;
import service.WriteToFileService;
import utils.LoadProperties;
import view.maingui.CreateIncomeChart;

/**
 * @author cousm
 *
 */
public class ProcessIncomeTest {
	private IncomeFileService incomeFileService;
	
	/**
	 * 
	 */
	private void setUp () {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		JTextField incomeField = new JTextField();
		new CreateIncomeChart(incomeField);
		incomeFileService = new IncomeFileService("incomeTest.txt");
	}
	
	/**
	 * 
	 */
	@Test
	public void testWriteIncomeToFile () {
		setUp();
		
		incomeFileService.writeIncomeToFile("2000");
		
		String incomeTestFound = readIncomeTestFile();
		assertEquals("2000", incomeTestFound);
	}
	
	/**
	 * 
	 */
	@Test
	public void testReadIncomeFile() {
		setUp();
		
		String incomeFound = incomeFileService.readIncomeFile();
		assertEquals("2000", incomeFound);
	}
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test
	public void testProcessBalance() throws ParseException {
		setUp();
		LoadExpenses loadExpenses = new LoadExpenses();
		loadExpenses.setExpensesFileIsNotEmpty(true);
		WriteToFileService writeToFileService = new WriteToFileService();
		Date date = new Date();  
		writeToFileService.writeToFile("600", "Shopping", date,"expensesTest.txt",loadExpenses);
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		double balanceFound = incomeFileService.processBalance("2000", yearToMonthToDescriptionWithAmounts);
		
		assertTrue(balanceFound == 1400.0);
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = formatter.format(date);
		deleteLineFromFile("600 Shopping "+dateString, "expensesTest.txt");
	}
	


	/**
	 * 
	 */
	private String readIncomeTestFile () {
		String incomeTestFound = "";
		try {
			FileReader reader = new FileReader("incomeTest.txt");
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				incomeTestFound = in.nextLine();
			}
			reader.close();
			in.close();
		}
		catch (IOException e) {
			System.out.println("File not found");
		}
		return incomeTestFound;
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
