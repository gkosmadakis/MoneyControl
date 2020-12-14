/**
 * LoadExpensesTest.java
 * Created: 19 Nov 2020
 * Author: cousm
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import loaddata.LoadExpenses;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class LoadExpensesTest {
	
	
	/**
	 * 
	 */
	@Test
	public void testReadTheFile () {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		List<String> allYearsFound = new ArrayList<String>();
		List<String> allMonthsFound = new ArrayList<String>();
		Set<String> allDescriptionsFound = new HashSet<String>();
		List<Float> allAmountsFound = new ArrayList<Float>();
		
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> testMap = loadExpenses.readTheFile();
		
		for (Map.Entry<String, TreeMap<String, Map<String, List<Float>>>> yearEntry : testMap.entrySet()) {
			allYearsFound.add(yearEntry.getKey());
			
			for (Map.Entry<String, Map<String, List<Float>>> monthEntry : yearEntry.getValue().entrySet()) {
				allMonthsFound.add(String.valueOf(monthEntry.getKey()));
			
				for (Map.Entry<String, List<Float>> descriptionsEntry : monthEntry.getValue().entrySet()) {
				
					allDescriptionsFound.add(descriptionsEntry.getKey());
					
					for (Float amountFloat : descriptionsEntry.getValue()) {
						allAmountsFound.add(amountFloat);
					}
				}
			}
		}
		
		assertEquals(true, allYearsFound.contains("2015") && allYearsFound.contains("2016") && allYearsFound.contains("2017") &&allYearsFound.contains("2020"));
		
		assertEquals(true, allMonthsFound.contains("10") && allMonthsFound.contains("01") &&allMonthsFound.contains("03") &&allMonthsFound.contains("04")
			&&allMonthsFound.contains("05") && allMonthsFound.contains("06") && allMonthsFound.contains("07") && allMonthsFound.contains("08")
			&& allMonthsFound.contains("09") && allMonthsFound.contains("11"));
		
		assertEquals(true, allDescriptionsFound.contains("House Rent") && allDescriptionsFound.contains("Shopping") && allDescriptionsFound.contains("Travel"));
		
		assertEquals(true, allAmountsFound.contains(100f) && allAmountsFound.contains(50f) && allAmountsFound.contains(25f) && allAmountsFound.contains(275f)
			&& allAmountsFound.contains(200f) && allAmountsFound.contains(250f) && allAmountsFound.contains(26f) && allAmountsFound.contains(55f) 
			&& allAmountsFound.contains(35f));
	}
	
	/**
	 * 
	 */
	@Test
	public void testAddAMountsWithDuplicates() {	
		Set<String> descriptions = new HashSet<String>();
		descriptions.add("Travel");
		String desc = "Travel";
		String amount = "300";
		List<Float> arrayAmount = new ArrayList<Float>();
		arrayAmount.add(500f);
		LoadExpenses.addAmountsWithDuplicates(descriptions, desc, amount, arrayAmount);
		
		for(Float totalAmount : arrayAmount) {
			assertTrue(totalAmount == 800f);
		}
	}
	
	
	
}
