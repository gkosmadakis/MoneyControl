/**
 * DescriptionsFileService.java
 * Created: 4 Dec 2020
 * Author: cousm
 */
package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;


/**
 * @author cousm Service Class that reads/writes/deletes descriptions from/to the descriptions file
 *
 */
public class DescriptionsFileService {
	private String descriptionsFile;
	private ArrayList<String> itemsAddedByUser;
	private DefaultComboBoxModel<String> model;
	
	/**
	 * @param descriptionsFile
	 * @param input
	 * @param itemsAddedByUser
	 * @param model
	 */
	public DescriptionsFileService (String descriptionsFile, String input, ArrayList<String> itemsAddedByUser, DefaultComboBoxModel<String> model) {
		super();
		this.descriptionsFile = descriptionsFile;
		this.itemsAddedByUser = itemsAddedByUser;
		this.model = model;
	}

	/**
	 * Stores a user added description to a file descriptions.txt
	 * @param userInput 
	 */
	public void writeDescriptionsToFile (String userInput) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(descriptionsFile, true)));
			out.append(userInput);
			out.write(System.lineSeparator());// prints a new line
			out.close();
			// inform the list that a new description is added
		}
		catch (IOException e) {
			System.out.println("Error processing file:" + e);
		}
	}
	
	/**
	 * Reads the descriptions.txt file
	 * @return itemAddedByUser
	 */
	public ArrayList<String> readDescriptionsFile () {
		itemsAddedByUser = new ArrayList<String>();
		try {
			FileReader reader = new FileReader(descriptionsFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				String descriptionItem = in.nextLine();
				model.addElement(descriptionItem);
				itemsAddedByUser.add(descriptionItem);
			}
			reader.close();
			in.close();
		}
		catch (IOException e) {
			System.out.println("File not found");
		}
		return itemsAddedByUser;
	}
	
	/**
	 * Deletes a description from the descriptions.txt file
	 * @param descriptionToDelete 
	 */
	public void deleteDescriptionFromFile (String descriptionToDelete) {
		try {
			FileInputStream fstream = new FileInputStream(descriptionsFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			StringBuilder fileContent = new StringBuilder();
			while ((strLine = br.readLine()) != null) {
				if (!strLine.equals(descriptionToDelete)) {
					fileContent.append(strLine);
					fileContent.append(System.getProperty("line.separator"));
				}
			}
			FileWriter fstreamWrite = new FileWriter(descriptionsFile);
			BufferedWriter out = new BufferedWriter(fstreamWrite);
			out.write(fileContent.toString());
			out.close();
			br.close();
		}
		catch (Exception e) { // Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
