/**
 * PieBarChartService.java
 * Created: 8 Dec 2020
 * Author: cousm
 */
package service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DefaultPieDataset;

import utils.DataUtilities;

/**
 * @author cousm Service Class that populates the dataSet that is used by Pie and Bar Charts
 *
 */
public class PieBarChartService {
	
	/**
	 * @param month
	 * @param yearSelection
	 * @param dataset 
	 * @param yearToMonthToDescriptionWithAmounts 
	 * @param dataUtilities 
	 */
	public void populateDataset (String month, String yearSelection, AbstractDataset dataset, TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts, DataUtilities dataUtilities) {
		ArrayList<Double> arrayOfamount = dataUtilities.retrieveAmountsForSelectedMonthYearFromMap(month, yearSelection, yearToMonthToDescriptionWithAmounts);
		LinkedHashSet<String> arrayOfdesc = dataUtilities.retrieveDescriptionsForSelectedMonthYearFromMap(month, yearSelection, yearToMonthToDescriptionWithAmounts);
		int i = 0;
		final Comparable<String> category1 = "Expenses";
		for (Iterator<String> s = arrayOfdesc.iterator(); s.hasNext(); i++) {
			String desc = s.next();
			if(dataset instanceof DefaultCategoryDataset) {
				((DefaultCategoryDataset)dataset).setValue(arrayOfamount.get(i), desc, category1);
			}
			else {
				((DefaultPieDataset) dataset).setValue(desc, arrayOfamount.get(i));
			}
		}
	}
}
