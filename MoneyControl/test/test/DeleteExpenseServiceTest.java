/**
 * DeleteExpenseTest.java
 * Created: 20 Nov 2020
 * Author: cousm
 */
package test;


import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;


import org.junit.jupiter.api.Test;

import service.DeleteExpenseService;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class DeleteExpenseServiceTest {
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test
	public void testDeleteAnExpense () throws ParseException {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		String editLine = "[450 House Rent 10/11/2020]";
		
		DeleteExpenseService deleteExpenseService = new DeleteExpenseService(editLine);
		deleteExpenseService.deleteAnExpense();
		
		StringBuilder allTheLines = readTestFile();
		assertTrue(!allTheLines.toString().contains("450    House Rent                              10/11/2020"));
		
		/*Add back the expense for next round of the test */
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("expensesTest.txt", true)));
			
			out.write(System.lineSeparator());// prints a new line
			String formatStr = "%-7s%-40s%-10s";// formats the columns
			out.append(String.format(formatStr, "450", "House Rent", "10/11/2020"));
			out.close();
		}
		catch (IOException e) {
			System.out.println("Error processing file:" + e);
		}	
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
