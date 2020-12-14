/**
 * ChartDataUtilitiesTest.java
 * Created: 23 Nov 2020
 * Author: cousm
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import loaddata.LoadExpenses;
import utils.DataUtilities;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class DataUtilitiesTest {
	/**
	 * 
	 */
	@Test
	public void testRetrieveAmountsForSelectedMonthYearFromMap () {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		
		DataUtilities chartsDataUtilities = new DataUtilities();
		
		ArrayList<Double> amountsFound = chartsDataUtilities.retrieveAmountsForSelectedMonthYearFromMap("11", "2020", yearToMonthToDescriptionWithAmounts);
		assertTrue(amountsFound.contains(450.0) && amountsFound.contains(150.0));
	}
	
	/**
	 * 
	 */
	@Test
	public void testRetrieveDescriptionsForSelectedMonthYearFromMap() {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();
		
		DataUtilities chartsDataUtilities = new DataUtilities();
		LinkedHashSet<String> descriptionsFound = chartsDataUtilities.retrieveDescriptionsForSelectedMonthYearFromMap("01", "2016", yearToMonthToDescriptionWithAmounts);
		assertTrue(descriptionsFound.contains("Travel") && descriptionsFound.contains("Shopping"));
	}
	
	/**
	 * 
	 */
	@Test
	public void testConvertTreeSetToArray() {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		loadExpenses.readTheFile();
		TreeSet<String> allTheYears = loadExpenses.getAllTheYears();
		
		DataUtilities chartsDataUtilities = new DataUtilities();
		String[] yearsDataArray = chartsDataUtilities.convertTreeSetToArray(allTheYears);
		assertTrue(Arrays.asList(yearsDataArray).contains("2015") && Arrays.asList(yearsDataArray).contains("2016")
			&& Arrays.asList(yearsDataArray).contains("2017") && Arrays.asList(yearsDataArray).contains("2020"));
	}
	
	/**
	 * 
	 */
	@Test
	public void testFindCurrentYearIndexInYearsArray() {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		loadExpenses.readTheFile();
		TreeSet<String> allTheYears = loadExpenses.getAllTheYears();
		
		DataUtilities chartsDataUtilities = new DataUtilities();
		int indexFound = chartsDataUtilities.findCurrentYearIndexInYearsArray(2020, allTheYears);
		assertEquals(3, indexFound);
	}
	
	/**
	 * 
	 */
	@Test
	public void testSumAmountsOfMonths() {
		ArrayList<Double> arrayOfamount = new ArrayList<Double>();
		arrayOfamount.add(50.0);
		arrayOfamount.add(55.25);
		arrayOfamount.add(45.50);
		arrayOfamount.add(155.95);
		arrayOfamount.add(450.15);
		NumberFormat formatter = new DecimalFormat("#.##");
		
		DataUtilities chartsDataUtilities = new DataUtilities();
		double sum = chartsDataUtilities.sumAmountsOfMonths(arrayOfamount);
		
		assertTrue(formatter.format(sum).equals("756.85"));
	}
	
	/**
	 * 
	 */
	@Test
	public void testConvertMonthIntToMonthString() {
		/* First scenario */
		int monthInt = 1;
		DataUtilities dataUtilities = new DataUtilities();
		String monthString = dataUtilities.convertMonthIntToMonthString(monthInt);
		assertEquals("02", monthString);
		/* Second scenario */
		monthInt = 10;
		monthString = dataUtilities.convertMonthIntToMonthString(monthInt);
		assertEquals("11", monthString);
	}
	
	/**
	 * 
	 */
	@Test
	public void testExtractThreeColumnsFromAllTheLines() {
		
		DataUtilities dataUtilities = new DataUtilities();
		String allTheLines = "150  Shopping  09/11/2020 \n450  House Rent  10/11/2020";
		String outputString = dataUtilities.extractThreeColumnsFromAllTheLines(allTheLines);
		assertEquals(outputString, "150   Shopping        09/11/2020                         "+"\n"+"450   House Rent      10/11/2020                         "+"\n");
	}
}
