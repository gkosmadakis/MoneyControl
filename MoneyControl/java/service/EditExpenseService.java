/**
 * ProcessEditExpense.java
 * Created: 13 Nov 2020
 * Author: cousm
 */
package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import utils.LoadProperties;

import static utils.Constants.*;

/**
 * @author cousm Service class that executes the edit of an expense
 *
 */
public class EditExpenseService {
	private String lineToEdit, editedLine;
	
	/**
	 * @param lineToEdit
	 * @param editedLine
	 */
	public EditExpenseService (String lineToEdit, String editedLine) {
		super();
		this.lineToEdit = lineToEdit;
		this.editedLine = editedLine;
	}


	/**
	 * The method that replaces in the file the edited expense line
	 */
	public void editAnExpense () {
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		String expensesFile = propertiesMap.get(EXPENSES_FILE);
		try {
			FileInputStream fstream = new FileInputStream(expensesFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			StringBuilder fileContent = new StringBuilder();
			while ((strLine = br.readLine()) != null) {
				// get the line as edited by the user and remove initial [ and last ]
				String lineEdited = editedLine.replaceAll("\\[", "").replaceAll("\\]", "");
				/* As we read the file we want to edit the line that is sent to the selectLinePane and is equal 
				   to the one that is read from the file*/
				if (lineToEdit.replaceAll("\\[", "").replaceAll("\\]", "").equals(strLine.replaceAll("\\s+", " ")) && !strLine.equals("")) {
					performEditOfExpense(fileContent, lineEdited);
				}
				else {
					fileContent.append(strLine);// update content as it is
					fileContent.append(System.getProperty("line.separator"));
				}
			}
			writeTheEditedFile(expensesFile, br, fileContent);
		}
		catch (IOException e) {// Catch exception if any
			System.err.println("Error in editing expense: " + e.getMessage());
		}
	}


	/**
	 * @param fileContent
	 * @param lineEdited
	 */
	private void performEditOfExpense (StringBuilder fileContent, String lineEdited) {
		String formatStr = "%-6s%-40s%-10s";// formats the columns
		String amount = lineEdited.substring(0, lineEdited.indexOf(" "));// prints the amount
		int index = lineEdited.lastIndexOf(" ");
		String desc = lineEdited.substring(lineEdited.indexOf(" "), index);// prints the description
		String date = lineEdited.substring(index, lineEdited.length());// prints the date
		fileContent.append(String.format(formatStr, amount, desc, date));// write edited line in the file
		fileContent.append(System.getProperty("line.separator"));// write a line
	}


	/**
	 * @param expensesFile
	 * @param br
	 * @param fileContent
	 * @throws IOException
	 */
	private void writeTheEditedFile (String expensesFile, BufferedReader br, StringBuilder fileContent) throws IOException {
		FileWriter fstreamWrite = new FileWriter(expensesFile);
		BufferedWriter out = new BufferedWriter(fstreamWrite);
		out.write(fileContent.toString().trim());
		out.close();
		br.close();
	}
}
