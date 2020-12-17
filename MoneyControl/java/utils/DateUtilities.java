/**
 * DateUtilities.java
 * Created: 27 Nov 2020
 * Author: cousm
 */
package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.swing.JTextField;

/**
 * @author cousm Utility Class that has methods processing dates
 *
 */
public class DateUtilities {
	
	/**
	 * @param datesFrom 
	 * @return ArrayList dates
	 * Returns the list of dates that the user provided to sum the expenses
	 * @throws Exception 
	 */
	public ArrayList<Date> getDaysBetweenDates (JTextField datesFrom) throws Exception {
		String datesFromTo = datesFrom.getText();
		ArrayList<Date> dates = new ArrayList<Date>();// the arrayList where i store the dates
		Validations validations = new Validations();
		validations.validateFields(datesFromTo);
		String[] token = datesFromTo.split("-");// store dates splitted by -
		String firstDate = token[0];// take the first date entered by the user
		String lastDate = token[1];// take the second date entered by the user
		DateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date startdate = null;
		startdate = parseStringDateToDate(format, firstDate);
		Date enddate = null;
		enddate = parseStringDateToDate(format, lastDate);
		countDatesFromStartToEndDate(dates, startdate, enddate);
		return dates;// so dates has a range of dates. for instance if the user enters 15/09/2015-17/09/2015 dates will store 15/09/2015 16/09/2015 17/09/2015
	}
	

	/**
	 * @param dates
	 * @param dateIntheFile
	 * @param datesMatchedUserInput 
	 * @param expenses
	 * @param expenseAmount
	 */
	public void listAmountsForMatchedDates (ArrayList<Date> dates, Date dateIntheFile, Set<Date> datesMatchedUserInput, ArrayList<Double> expenses, String expenseAmount) {
		double firstDateAmountNumber;
		if (dates.contains(dateIntheFile)) {
			/* add the Date in a list that will maintain them as the while loop checks all the dates */
			datesMatchedUserInput.add(dateIntheFile);
			String firstDateAmount = expenseAmount;
			firstDateAmountNumber = Double.parseDouble(firstDateAmount);						
			expenses.add(firstDateAmountNumber); // add the amount in the list of expenses.									
		}
	}
	
	/**
	 * @param dates
	 * @param startdate
	 * @param enddate
	 */
	public void countDatesFromStartToEndDate (ArrayList<Date> dates, Date startdate, Date enddate) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startdate);
		while (calendar.getTime().getTime() <= enddate.getTime()) {
			Date result = calendar.getTime();// take the date
			dates.add(result);// add it to dates arrayList
			calendar.add(Calendar.DATE, 1);
		}
	}
	
	/**
	 * @param format
	 * @param date
	 * @return dateIntheFile
	 */
	public Date parseStringDateToDate (DateFormat format, String date) {
		Date dateIntheFile = null;
		try {
			dateIntheFile = format.parse(date);// convert the date into Date
		}
		catch (ParseException e) {
			System.err.println("Error parsing string date to date: " +e.getMessage());
		}
		return dateIntheFile;
	}
	
	
}
