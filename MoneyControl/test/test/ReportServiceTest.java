/**
 * ReportGUITest.java
 * Created: 23 Nov 2020
 * Author: cousm
 */
package test;


import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import loaddata.LoadExpenses;
import service.ReportService;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class ReportServiceTest {
	/**
	 * 
	 */
	@Test
	public void testFilterFileForThisMonthAndYear () {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		LoadExpenses loadExpenses = new LoadExpenses();
		loadExpenses.readTheFile();
		
		StringBuilder allTheLines = loadExpenses.getAllTheLines(); 
		ReportService reportService = new ReportService();
		StringBuilder filteredLines = reportService.filterFileForThisMonthAndYear(11, allTheLines, "2020");

		assertTrue(filteredLines.toString().replaceAll("\\s+", " ").contains("150 Shopping 09/11/2020") 
			&& filteredLines.toString().replaceAll("\\s+", " ").contains("450 House Rent 10/11/2020"));
		
	}
}
