/**
 * FileImportExportTest.java
 * Created: 20 Nov 2020
 * Author: cousm
 */
package test;

import static org.junit.Assert.assertTrue;
import static utils.Constants.DATE_FORMAT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import service.FileImportExportService;

/**
 * @author cousm
 *
 */
public class FileImportExportTest {
	/**
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testProcessExport() throws IOException {
		
		StringBuilder allTheLinesSource = readTheTestFile("expensesTest.txt");
		FileImportExportService fileImportExportService = new FileImportExportService();
		
		File sourcePath = new File("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\expensesTest.txt");
		File destPath = new File ("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\Expenses Exported\\expensesTest.txt");
		fileImportExportService.processExport(sourcePath, destPath);
		
		StringBuilder allTheLinesExported = readTheTestFile("Expenses Exported\\expensesTest.txt");
		assertTrue(allTheLinesSource.toString().equals(allTheLinesExported.toString()));	
	}
	
	/**
	 * 
	 */
	@Test
	public void testProcessImport () {
		
		addNewLineToExportExpenses();
		StringBuilder allTheLinesSource = readTheTestFile("Expenses Exported\\expensesTest.txt");
		FileImportExportService fileImportExportService = new FileImportExportService();
		
		String sourcePath = "C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\Expenses Exported\\expensesTest.txt";
		String destPath = "C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\expensesTest.txt";
		fileImportExportService.processImport(sourcePath, destPath);
		
		StringBuilder allTheLinesImported = readTheTestFile("expensesTest.txt");
		assertTrue(allTheLinesSource.toString().equals(allTheLinesImported.toString()));
		
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = formatter.format(new Date());
		deleteLineFromFile("50 Test "+dateString, "Expenses Exported\\expensesTest.txt");
		deleteLineFromFile("50 Test "+dateString, "expensesTest.txt");
	}
	
	/**
	 * 
	 */
	private StringBuilder readTheTestFile (String fileName) {
		StringBuilder allTheLines = new StringBuilder();
		try {
			FileInputStream fstream = new FileInputStream(fileName);
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
	
	/**
	 * 
	 */
	private void addNewLineToExportExpenses () {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Expenses Exported\\expensesTest.txt", true)));
			Format formatter = new SimpleDateFormat(DATE_FORMAT);
			String dateFormatted = formatter.format(new Date());
			
			out.write(System.lineSeparator());// prints a new line
			String formatStr = "%-7s%-40s%-10s";// formats the columns
			out.append(String.format(formatStr, "50", "Test", dateFormatted));
			out.close();
		}
		catch (IOException e) {
			System.out.println("Error processing file:" + e);
		}
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
