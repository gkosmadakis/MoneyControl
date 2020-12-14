/**
 * ProcessEditExpenseTest.java
 * Created: 20 Nov 2020
 * Author: cousm
 */
package test;


import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

import service.EditExpenseService;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class ProcessEditExpenseServiceTest {
	
	/**
	 * 
	 */
	@Test
	public void testEditAnExpense () {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		String lineToEdit = "450 House Rent 10/11/2020"; 
		String editedLine = "250 House Rent 10/11/2020";
		
		EditExpenseService processEditAnExpense = new EditExpenseService(lineToEdit, editedLine);
		processEditAnExpense.editAnExpense();
		
		StringBuilder allTheLines = readTestFile();
		assertTrue(allTheLines.toString().contains("250    House Rent                              10/11/2020"));
		
		/*Edit back to the original values for next round of the test */
		lineToEdit ="250 House Rent 10/11/2020";
		editedLine = "450 House Rent 10/11/2020";
		processEditAnExpense = new EditExpenseService(lineToEdit, editedLine);
		processEditAnExpense.editAnExpense();
		
	}

	/**
	 * 
	 */
	private StringBuilder readTestFile () {
		StringBuilder allTheLines = new StringBuilder();
		try {
			FileInputStream fstream = new FileInputStream("expensesTest.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fstream));
			String nextLine;
			while ((nextLine = reader.readLine()) != null) {
				allTheLines.append(nextLine);		
			}		
			reader.close();
			fstream.close();
		}
		catch (IOException e) {
			System.out.println("File not found");
		}
		return allTheLines;
	}
}
