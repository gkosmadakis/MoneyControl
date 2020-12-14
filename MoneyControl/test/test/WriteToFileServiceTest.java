/**
 * MoneyControlGUITest.java
 * Created: 19 Nov 2020
 * Author: cousm
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import loaddata.LoadExpenses;
import service.WriteToFileService;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class WriteToFileServiceTest {
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test
	public void testWriteToFile () throws ParseException {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		loadExpenses.readTheFile();
		WriteToFileService writeToFileService = new WriteToFileService();
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse("19/11/2020");  
		writeToFileService.writeToFile("15", "Shopping", date,"expensesTest.txt",loadExpenses);
		
		String line = null;
		try {
			FileInputStream fstream = new FileInputStream("expensesTest.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fstream));
			String nextLine;
			while ((nextLine = reader.readLine()) != null) {
				line = nextLine;		
			}		
			reader.close();
			fstream.close();
		}
		catch (IOException e) {
			System.out.println("File not found");
		}
	
		assertEquals(true, line.equals("15     Shopping                                19/11/2020"));
		
		/*Delete the line added to keep the file in its original form for the rest of the tests */
		deleteLineFromFile("15 Shopping 19/11/2020", "expensesTest.txt");
	}
	
	private void deleteLineFromFile (String lineToDelete, String fileName) {
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			StringBuilder fileContent = new StringBuilder();
			while ((strLine = br.readLine()) != null) {
				if (!strLine.replace(" ", "").equalsIgnoreCase(lineToDelete.replaceAll("\\[", "").replaceAll("\\]", "").replace(" ", ""))) {
					fileContent.append(strLine);
					fileContent.append(System.getProperty("line.separator"));
				}
			}
			FileWriter fstreamWrite = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstreamWrite);
			out.write(fileContent.toString().trim());
			out.close();
			br.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
