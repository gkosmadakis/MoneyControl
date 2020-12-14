/**
 * DescriptionsTest.java
 * Created: 20 Nov 2020
 * Author: cousm
 */
package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Constants.COUNCIL_TAX;
import static utils.Constants.ENTERTAINMENT;
import static utils.Constants.HOUSE_BILLS;
import static utils.Constants.HOUSE_RENT;
import static utils.Constants.MORTGAGE;
import static utils.Constants.SHOPPING;
import static utils.Constants.SUPERMARKET;
import static utils.Constants.TRAVEL;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import service.DescriptionsFileService;
import utils.LoadProperties;

/**
 * @author cousm
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DescriptionsFileServiceTest {
	private Vector<String>comboBoxItems;
	private DefaultComboBoxModel<String> model;
	/**
	 * 
	 */
	@Before
	public void setUp() {	
		LoadProperties.setPropertiesFromPropertiesFile("C:\\Users\\cousm\\eclipse2018-workspace\\MoneyControl\\MoneyControl\\config.propertiesTest");
		comboBoxItems = new Vector<String>();
		comboBoxItems.add("NOT_SELECTABLE_OPTION");
		comboBoxItems.add(HOUSE_RENT);
		comboBoxItems.add(SHOPPING);
		comboBoxItems.add(SUPERMARKET);
		comboBoxItems.add(TRAVEL);
		comboBoxItems.add(MORTGAGE);
		comboBoxItems.add(COUNCIL_TAX);
		comboBoxItems.add(HOUSE_BILLS);
		comboBoxItems.add(ENTERTAINMENT);
		model = new DefaultComboBoxModel<String>(comboBoxItems);
		new JComboBox<String>(model);
	}
	/**
	 * Tests the method writeDescriptionToFile
	 */
	@Test
	public void testA() {
		setUp();
		ArrayList<String> itemsAddedByUser = new ArrayList<String>();
		DescriptionsFileService descriptions = new DescriptionsFileService("descriptionsTest.txt", "Test", itemsAddedByUser, model);
		
		descriptions.writeDescriptionsToFile("Test");
		ArrayList<String> testList = descriptions.readDescriptionsFile();
		assertTrue(testList.contains("Test"));
	}
	
	/**
	 * Tests the method readDescriptionFile
	 */
	@Test
	public void testB() {
		setUp();
		
		ArrayList<String> itemsAddedByUser = new ArrayList<String>();
		DescriptionsFileService descriptions = new DescriptionsFileService("descriptionsTest.txt", "Test", itemsAddedByUser, model);
		ArrayList<String> testList = descriptions.readDescriptionsFile();
		
		assertTrue(testList.contains("Test"));
	}
	
	/**
	 * Tests the method deleteDescriptionFromFile
	 */
	@Test
	public void testC() {
		setUp();
		
		ArrayList<String> itemsAddedByUser = new ArrayList<String>();
		DescriptionsFileService descriptions = new DescriptionsFileService("descriptionsTest.txt", "Test", itemsAddedByUser, model);
		descriptions.deleteDescriptionFromFile("Test");
		
		StringBuilder allTheLines = new StringBuilder();
		try {
			FileInputStream fstream = new FileInputStream("descriptionsTest.txt");
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
		assertTrue(!allTheLines.toString().contains("Test"));
	}
}
