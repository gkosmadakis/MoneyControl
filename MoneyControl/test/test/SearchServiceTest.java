/**
 * MoneyControlEditTest.java
 * Created: 23 Nov 2020
 * Author: cousm
 */
package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

import service.SearchService;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
public class SearchServiceTest {
	/**
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testProcessSearchByAmountButton () throws Exception {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
				
		JTextField amountField = new JTextField();
		/*First scenario */
		amountField.setText("150");
		SearchService searchService = new SearchService();
		String searchResult = searchService.processSearch(amountField,"Amount");
		assertTrue(searchResult.equals("150 Shopping 09/11/2020"));
		
		/*Second scenario */
		amountField.setText("50");
		//allTheLines.add("50 Shopping 09/11/2020");
		searchResult = searchService.processSearch(amountField, "Amount");
		assertTrue(searchResult.equals("50 Shopping 06/01/2016"+"\n"+"50 Shopping 20/06/2017"+"\n"));
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testProcessSearchByDescriptionButton() throws Exception {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		
		JTextField descField = new JTextField();
		/*First scenario */
		descField.setText("Travel");
		SearchService searchService = new SearchService();
		String searchResult = searchService.processSearch(descField, "Desc");
		assertTrue(searchResult.equals("25 Travel 05/01/2016"+"\n"+"250 Travel 01/04/2016"+"\n"+"25 Travel 04/06/2016"+"\n"));
		
		/*Second scenario */
		descField.setText("Shopping");
		searchResult = searchService.processSearch(descField, "Desc");
		assertTrue(searchResult.equals("50 Shopping 06/01/2016"+"\n"+"26 Shopping 04/07/2016"+"\n"+
			"55 Shopping 02/08/2016"+"\n"+"35 Shopping 12/09/2016"+"\n"+"50 Shopping 20/06/2017"
			+"\n"+"150 Shopping 09/11/2020"+"\n"));
	}
	
	/**
	 * 
	 */
	@Test
	public void testReadAllTheLines() {
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		SearchService searchService = new SearchService();
		
		ArrayList<String> allTheLines = searchService.readTheLines();
		assertEquals(15, allTheLines.size());
		assertTrue(allTheLines.contains("100 House Rent 16/10/2015")&& allTheLines.contains("50 Shopping 06/01/2016")
			&& allTheLines.contains("25 Travel 05/01/2016")&& allTheLines.contains("275 House Rent 11/03/2016"));
	}
	
	
}
