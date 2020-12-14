/**
 * loadExpenses.java
 * Created: 10 Nov 2020
 * Author: cousm
 */
package loaddata;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import utils.LoadProperties;
import static utils.Constants.*;

/**
 * @author cousm Class that loads the data from the file and populates the TreeMap yearToMonthToDescriptionWithAmounts
 * with these data.
 */
public class LoadExpenses {
	
	private StringBuilder allTheLines;
	private TreeSet<String> allTheYears;
	private boolean expensesFileIsNotEmpty;

	
	/**
	 * @return Map<String, TreeMap<String, Map<String, List<Float>>>>
	 */
	/*2020-> 01-> Description-> List of amounts
	 *				  YEAR             MONTH      DESCRIPTION AMOUNTS
	 * private HashMap<String, TreeMap<String, Map<String, List<Float>>>>*/
	public TreeMap<String, TreeMap<String, Map<String, List<Float>>>> readTheFile() {
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		String expensesFile = propertiesMap.get(EXPENSES_FILE);
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = new TreeMap<String, TreeMap<String, Map<String, List<Float>>>>();
		allTheLines = new StringBuilder();
	    allTheYears = new TreeSet<String>();
		try {
			FileReader reader = new FileReader(expensesFile);
			Scanner in = new Scanner(reader);
			int lineIndex = 0;// this is to count the lines
			while (in.hasNextLine()) {
				String line = in.nextLine();
				if (line.startsWith(AMOUNT)) {
					expensesFileIsNotEmpty = true;
				}
				lineIndex = readDataAndPopulateMap(yearToMonthToDescriptionWithAmounts, lineIndex, line);
			} // end of while
			reader.close();
			in.close();
		}
		catch (IOException e) {
			System.out.println("File not found");
		}
		return yearToMonthToDescriptionWithAmounts;
	}

	private int readDataAndPopulateMap (TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts, int lineIndex, String line) {
		if (++lineIndex > 2) {
			int index = line.lastIndexOf(" ");
			String date = line.substring(index, line.length());
			Float amount = Float.valueOf(line.substring(0, line.indexOf(" ")));
			String desc = line.substring(line.indexOf(" "), index).trim();
			String extractMonthFromDate = date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"));
			String extractYearFromDate = date.substring(date.lastIndexOf("/") + 1, date.length());
			allTheLines.append(line+"\n");
			allTheYears.add(extractYearFromDate);
			populateMainMap(amount, desc, yearToMonthToDescriptionWithAmounts, extractMonthFromDate, extractYearFromDate);
		}
		return lineIndex;
	}

	private void populateMainMap (Float amount, String desc, TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts, String extractMonthFromDate, String extractYearFromDate) {
		TreeMap<String, Map<String, List<Float>>> tempFirstMap;
		HashMap<String, List<Float>> tempSecondMap;
		List<Float> tempList;/*if the key = year is in the map then get the tempFirstMap*/
		if (yearToMonthToDescriptionWithAmounts.containsKey(extractYearFromDate)) {
		    tempFirstMap = yearToMonthToDescriptionWithAmounts.get(extractYearFromDate);
		    /*if the key = month and the tempSecondMap has the current description then get the tempList create a new set and call the
		     method to add amounts that are with the same description. if the tempSecondMap does not have the description then create a new tempList*/
		    if (tempFirstMap.containsKey(extractMonthFromDate)) {
		        tempSecondMap = (HashMap<String, List<Float>>) tempFirstMap.get(extractMonthFromDate);
		        tempList = addAmountToListForExistingMonthInMap(amount, desc, tempSecondMap);
		    }  /* if the key = month is not in the map at all then get the tempFirstMap, create a new tempSecondMap, a new set and a new list.
		     * Add the amount to the list, the desc in the set */
		    else {
		        tempFirstMap = yearToMonthToDescriptionWithAmounts.get(extractYearFromDate);
		        tempSecondMap = new HashMap<String, List<Float>>();
		        tempList = addAmountToListForNonExistingMonthInMap(amount, desc);
		    }/*if the key = year is not in the map then create new tempFirstMap, a new tempSecondMap, a new set and a new tempList*/
		}/*add the amount in the tempList the description in the set */
		else {
		    tempFirstMap = new TreeMap<String, Map<String, List<Float>>>();
		    tempSecondMap = new HashMap<String, List<Float>>();
		    tempList = addAmountToListForNonExistingMonthInMap(amount, desc);
		}
		tempSecondMap.put(desc, tempList);/* Store fields in the maps and then in the main map */
		tempFirstMap.put(extractMonthFromDate, tempSecondMap);
		yearToMonthToDescriptionWithAmounts.put(extractYearFromDate, tempFirstMap);
	}

	private List<Float> addAmountToListForExistingMonthInMap (Float amount, String desc, HashMap<String, List<Float>> tempSecondMap) {
		List<Float> tempList;
		if(tempSecondMap.get(desc) != null) {
		    tempList = (List<Float>) tempSecondMap.get(desc);//tempList size is always 1
		    Set<String> descriptionsSet = new LinkedHashSet<>();
		    descriptionsSet.add(desc);//descriptionsSet size is always 1
		    addAmountsWithDuplicates(descriptionsSet,desc, String.valueOf(amount), tempList);
		}
		else {
		    tempList = new ArrayList<Float>();
		    tempList.add(amount);
		}
		return tempList;
	}

	private List<Float> addAmountToListForNonExistingMonthInMap (Float amount, String desc) {
		Set<String> descriptionsSet = new LinkedHashSet<>();
		List<Float> tempList = new ArrayList<Float>();
		tempList.add(amount);
		descriptionsSet.add(desc);
		return tempList;
	}
	
	/**
	 * @param descriptions
	 * @param desc
	 * @param amount
	 * @param arrayAmount
	 */
	public static void addAmountsWithDuplicates (Set<String> descriptions, String desc, String amount, List<Float> arrayAmount) {
		if (descriptions.contains(desc)) {
			int i = 0;
			for (Iterator<String> s = descriptions.iterator(); s.hasNext(); i++) {
				String descFound = (String) s.next();
				if (desc.equals(descFound)) {
					Float amountWithDuplicate = arrayAmount.get(i) + Float.valueOf(amount);// add up the amounts if there are duplicates
					arrayAmount.set(i, amountWithDuplicate);
					break;
				}
			}
		}
		else {
			descriptions.add(desc);
			arrayAmount.add(Float.valueOf(amount));
		}
	}

	/**
	 * @return allTheLines
	 */
	public StringBuilder getAllTheLines () {
		return allTheLines;
	}

	/**
	 * @return allTheYears
	 */
	public TreeSet<String> getAllTheYears () {
		return allTheYears;
	}

	/**
	 * @return expensesFileIsEmpty
	 */
	public boolean getExpensesFileIsNotEmpty () {
		return expensesFileIsNotEmpty;
	}

	/**
	 * @param expensesFileIsNotEmpty
	 */
	public void setExpensesFileIsNotEmpty (boolean expensesFileIsNotEmpty) {
		this.expensesFileIsNotEmpty = expensesFileIsNotEmpty;
	}
	
	
	
	
}
