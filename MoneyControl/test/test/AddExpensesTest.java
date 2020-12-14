/**
 * AddExpensesTest.java
 * Created: 20 Nov 2020
 * Author: cousm
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

import service.SumExpensesForDatesService;
import utils.DateUtilities;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class AddExpensesTest {
	/**
	 * Tests the method getDaysBetweenDates
	 * @throws Exception 
	 */
	@Test
	public void testGetDaysBetweenDates () throws Exception {
		JTextField datesFrom = new JTextField();
		datesFrom.setText("15/11/2020-20/11/2020");
		DateUtilities dateUtilities = new DateUtilities();
		
		ArrayList<Date> testDates = dateUtilities.getDaysBetweenDates(datesFrom);
		
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<String> datesListString = new ArrayList<String>();
		for(Date dateFound : testDates) {
			String dateString = formatter.format(dateFound);
			datesListString.add(dateString);
		}
		
		assertEquals(6, testDates.size());
		assertTrue(datesListString.contains("15/11/2020") && datesListString.contains("16/11/2020") && datesListString.contains("17/11/2020")
			&& datesListString.contains("18/11/2020")&& datesListString.contains("19/11/2020") && datesListString.contains("20/11/2020"));
	}
	
	/**
	 * Tests the method addExpenses
	 * @throws Exception 
	 */
	@Test
	public void testAddExpenses() throws Exception {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		JTextField datesFrom = new JTextField();
		datesFrom.setText("09/11/2020-10/11/2020");
		SumExpensesForDatesService addExpenses = new SumExpensesForDatesService();
		
		double testSum = addExpenses.addTheExpenses(datesFrom);
		assertTrue(testSum == 600.0);
	}
	
	/**
	 * Test the method sumExpenses
	 */
	@Test
	public void testSumExpenses() {
		String datesFrom = "09/11/2020-10/11/2020";
		ArrayList<Double> expenses = new ArrayList<Double>();
		expenses.add(150.0);
		expenses.add(450.0);
		SumExpensesForDatesService addExpenses = new SumExpensesForDatesService();
		
		double testSum = addExpenses.sumTheExpenses(datesFrom, expenses);
		assertTrue(testSum == 600.0);
	}
	
	
}
