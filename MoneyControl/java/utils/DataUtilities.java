/**
 * ChartsDataUtilities.java
 * Created: 13 Nov 2020
 * Author: cousm
 */
package utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author cousm Utility Class that has several methods related with data
 *
 */
public class DataUtilities {
	
	/**
	 * @param month
	 * @param yearSelected
	 * @param yearToMonthToDescriptionWithAmounts
	 * @return amountsFound
	 */
	public ArrayList<Double> retrieveAmountsForSelectedMonthYearFromMap (String month, String yearSelected, TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts) {
		ArrayList<Double> amountsFound = new ArrayList<Double>();
		TreeMap<String, Map<String, List<Float>>> currentYearMap = yearToMonthToDescriptionWithAmounts.get(String.valueOf(yearSelected));
		Map<String, List<Float>> currentMonthMap = currentYearMap.get(month);
		if (currentMonthMap != null) {
			for (List<Float> listOfExpenses : currentMonthMap.values()) {
				for (Float amountFloat : listOfExpenses) {
					amountsFound.add((double) amountFloat);
				}
			}
		}
		return amountsFound;
	}
	
	/**
	 * @param month
	 * @param yearSelected
	 * @param yearToMonthToDescriptionWithAmounts
	 * @return descriptionsFound
	 */
	public LinkedHashSet<String> retrieveDescriptionsForSelectedMonthYearFromMap (String month, String yearSelected, TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts) {
		LinkedHashSet<String> descriptionsFound = new LinkedHashSet<String>();
		TreeMap<String, Map<String, List<Float>>> currentYearMap = yearToMonthToDescriptionWithAmounts.get(String.valueOf(yearSelected));
		Map<String, List<Float>> currentMonthMap = currentYearMap.get(month);
		if (currentMonthMap != null) {
			for (String currentMonthDescriptionEntry : currentMonthMap.keySet()) {
				descriptionsFound.add(currentMonthDescriptionEntry);
			}
		}
		return descriptionsFound;
	}
	
	/**
	 * @param allTheYears
	 * @return yearDataArray
	 */
	public String[] convertTreeSetToArray (TreeSet<String> allTheYears) {
		String[] yearDataArray = new String[allTheYears.size()];
		int k = 0;
		for (String i : allTheYears) {
			yearDataArray[k++] = i;
		}
		return yearDataArray;
	}
	
	/**
	 * @param year 
	 * @param allTheYears
	 * @return index of Year Drop-down
	 */
	public int findCurrentYearIndexInYearsArray (int year, TreeSet<String> allTheYears) {
		String[] yearDataArray = convertTreeSetToArray(allTheYears);
		int len = yearDataArray.length;
		int index = 0;
		while (index < len) {
			// if the i-th element is year then return the index
			if (yearDataArray[index].equals(String.valueOf(year))) {
				return index;
			}
			else {
				index = index + 1;
			}
		}
		return index;
	}
	
	/**
	 * @param arrayOfamount
	 * @return sum of amounts for a month
	 */
	public double sumAmountsOfMonths (ArrayList<Double> arrayOfamount) {
		int i;
		double sum = 0;
		for (i = 0; i < arrayOfamount.size(); i++) {
			sum += arrayOfamount.get(i);
		}
		return sum;
	}
	
	/**
	 * @param selectedIndex
	 * @return monthString
	 */
	public String convertMonthIntToMonthString (int selectedIndex) {
		String monthString = "";
		int selectedMonth = selectedIndex +1;
		if(selectedMonth < 10) {
			monthString = "0"+selectedMonth;
		}
		else {
			monthString = String.valueOf(selectedMonth);
		}
		return monthString;
	}
	
	/**
	 * @param allTheLines
	 * @return formattedString
	 */
	public String extractThreeColumnsFromAllTheLines (String allTheLines) {
		String formattedString = "";
		if (allTheLines.length() > 0) {
			String[] tokens = allTheLines.split("\n");
			for (String element : tokens) {
				String[] lineToken = element.split("  +");
				formattedString += String.format("%-5s %-15s %-35s", lineToken[0], lineToken[1], lineToken[2]) + "\n";
			}
		}
		return formattedString;
	}
	
	
}
