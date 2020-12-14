/**
 * SearchService.java
 * Created: 7 Dec 2020
 * Author: cousm
 */
package service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JTextField;

import utils.LoadProperties;
import utils.MultiMap;
import static utils.Constants.*;

/**
 * @author cousm Service class to process the searches (Amount, Description) and read all the lines to support
 * the search
 *
 */
public class SearchService {
	
	/**
	 * @param amountOrDescField 
	 * @param buttonPressed 
	 * @return lineToEditMultiple
	 * @throws Exception 
	 */
	public String processSearch (JTextField amountOrDescField, String buttonPressed) throws Exception {
		ArrayList<String> allTheLines = readTheLines();
		String searchResults = "";
		String amountOrDesc = amountOrDescField.getText();
		String amountOrDescPart = null;
		String linefound = "";
		String lineToEditMultiple = "";
		MultiMap amountOrDescFoundMap = new MultiMap();
		validate(amountOrDescField, buttonPressed);
		for (int i = 0; i < allTheLines.size(); i++) {
			linefound = allTheLines.get(i);
			if (amountOrDesc.matches(".*\\d.*")) {
				amountOrDescPart = linefound.substring(0, linefound.indexOf(" "));
			}
			else {
				int index = linefound.lastIndexOf(" ");
				amountOrDescPart = linefound.substring(linefound.indexOf(" "), index);
			}
			amountOrDescFoundMap.put(amountOrDescPart.trim(), linefound);
		}
		searchResults = findResults(searchResults, amountOrDesc, amountOrDescFoundMap, lineToEditMultiple);
		return searchResults;
	}

	/**
	 * @param amountOrDescField
	 * @throws Exception 
	 */
	private void validate (JTextField amountOrDescField, String buttonPressed) throws Exception {
		if (amountOrDescField.getText().equals("")) {
			if (buttonPressed.equals(AMOUNT)) {
				throw new Exception("Amount field is empty!");
			}
			else {
				throw new Exception("Description field is empty!");
			}
		}
	}

	/**
	 * @param searchResults
	 * @param amountOrDesc
	 * @param amountOrDescFoundMap
	 * @return
	 */
	private String findResults (String searchResults, String amountOrDesc, MultiMap amountOrDescFoundMap, String lineToEditMultiple) throws Exception {
		if (amountOrDescFoundMap.containsKey(amountOrDesc)) {
			List<String> lineToEdit = amountOrDescFoundMap.get(amountOrDesc);
			if (lineToEdit.size() > 1) {
				for (int i = 0; i < lineToEdit.size(); i++) {
					lineToEditMultiple += lineToEdit.get(i) + "\n";
				}
				searchResults = lineToEditMultiple;
			}
			else {
				searchResults = lineToEdit.toString().replaceAll("\\[", "").replaceAll("\\]", "");
			}	
		}
		else if (!amountOrDescFoundMap.containsKey(amountOrDesc)) {
			throw new Exception("No expense found! Try again with different amount");
		}
		return searchResults;
	}
	
	/**
	 * Read the file with expenses and add them to an arrayList allTheLines
	 * @return allTheLines
	 */
	public ArrayList<String> readTheLines () {
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		String expensesFile = propertiesMap.get(EXPENSES_FILE);
		ArrayList<String> allTheLines = new ArrayList<String>();
		try {
			FileReader reader = new FileReader(expensesFile);
			Scanner in = new Scanner(reader);
			int lineIndex = 0;
			while (in.hasNextLine()) {
				String line = in.nextLine();
				if (++lineIndex > 2) {
					allTheLines.add(line.trim().replaceAll("\\s+", " "));
				}
			}
			reader.close();
			in.close();
		}
		catch (IOException e) {
			System.out.println("File not found");
		}
		return allTheLines;
	}

}
