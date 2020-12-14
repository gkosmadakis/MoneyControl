/**
 * AddExpenses.java
 * Created: 12 Nov 2020
 * Author: cousm
 */
package service;

import static utils.Constants.DATE_FORMAT_SIMPLE;
import static utils.Constants.EXPENSES_FILE;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.swing.JTextField;

import utils.DateUtilities;
import utils.LoadProperties;

/**
 * @author cousm Service Class that reads the file and sums the expenses for the user entered dates
 *
 */
public class SumExpensesForDatesService {
	private DateUtilities dateUtilities;
	/**
	 * 
	 */
	public SumExpensesForDatesService () {
		super();
		dateUtilities = new DateUtilities();
	}
	
	/**
	 * Adds the expenses for the provided dates
	 * @param datesFrom 
	 * @return totalSum
	 * @throws Exception 
	 */
	public double addTheExpenses (JTextField datesFrom) throws Exception {
		double totalSum = 0.0;
		ArrayList<Date> dates = dateUtilities.getDaysBetweenDates(datesFrom);
		if (dates.isEmpty()) {
			return 0.0;
		}
		else {
			DateFormat format = new SimpleDateFormat(DATE_FORMAT_SIMPLE);
			ArrayList<Double> expenses = new ArrayList<Double>();
			Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
			String expensesFile = propertiesMap.get(EXPENSES_FILE);
			totalSum = readTheFileToFindMatchingDates(datesFrom, dates, format, expenses, expensesFile);
		}
		return totalSum;
	}

	/**
	 * @param datesFrom
	 * @param dates
	 * @param format
	 * @param expenses
	 * @param expensesFile
	 * @return
	 * @throws Exception 
	 */
	private double readTheFileToFindMatchingDates (JTextField datesFrom, ArrayList<Date> dates, DateFormat format, ArrayList<Double> expenses, String expensesFile) throws Exception {
		double totalSum = 0.0;
		Set<Date> datesMatchedUserInput = new TreeSet<Date>();
		try {
			FileReader reader = new FileReader(expensesFile);
			Scanner in = new Scanner(reader);
			int lineIndex = 0;// this is to count the lines
			while (in.hasNextLine()) {
				String line = in.nextLine();
				if (++lineIndex > 2){ // i need to read after the second line
					String expenseAmount = new StringTokenizer(line).nextToken();// take the amount from the file
					String date = line.substring(line.lastIndexOf(" "), line.length());//take// the date
					Date dateIntheFile = dateUtilities.parseStringDateToDate(format, date);
					/* if the dates taken from the input are contained in those in the file then look each line and find the amount given*/
					dateUtilities.listAmountsForMatchedDates(dates, dateIntheFile, datesMatchedUserInput, expenses, expenseAmount); 
				}
			} // end of while
			totalSum = sumExpensesForGivenDates(datesFrom, totalSum, dates, datesMatchedUserInput, expenses);
			reader.close();
			in.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return totalSum;
	}


	/**
	 * @param datesFrom
	 * @param totalSum
	 * @param dates
	 * @param datesMatchedUserInput
	 * @param expenses
	 * @return
	 * @throws Exception 
	 */
	private double sumExpensesForGivenDates (JTextField datesFrom, double totalSum, ArrayList<Date> dates, Set<Date> datesMatchedUserInput, ArrayList<Double> expenses) throws Exception {
		Collections.sort(dates);
		if (datesMatchedUserInput.isEmpty()) {
			throw new Exception("The are no expenses in the dates you entered! Please try with different dates.");
		}
		Date nextValue = null;
		for (Iterator<Date> it = datesMatchedUserInput.iterator(); it.hasNext();) {
			nextValue = it.next();
		}
		if (dates.contains(nextValue)) {
			totalSum = sumTheExpenses(datesFrom.getText(), expenses);
			datesFrom.setText("");
		}
		return totalSum;
	}
	
	/**
	 * @param datesRange 
	 * @param expenses 
	 * @return sum
	 * Returns the sum for the expenses
	 */
	public double sumTheExpenses (String datesRange, ArrayList<Double> expenses) {
		double sum = 0;
		for (int i = 0; i < expenses.size(); i++) {
			sum += expenses.get(i);
		}
		return sum;
	}
	
}
