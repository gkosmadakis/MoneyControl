/**
 * DeleteExpense.java
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
 * @author cousm Service Class that deletes an expense from the file
 *
 */
public class DeleteExpenseService {
	
	private String editLine;
	
	/**
	 * @param editLine
	 */
	public DeleteExpenseService (String editLine) {
		super();
		this.editLine = editLine;
	}

	/**
	 * method that deletes an expense from the file
	 */
	public void deleteAnExpense () {
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		String expensesFile = propertiesMap.get(EXPENSES_FILE);
		try {
			FileInputStream fstream = new FileInputStream(expensesFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			StringBuilder fileContent = new StringBuilder();
			while ((strLine = br.readLine()) != null) {
				System.out.println(strLine);
				// if the line in the file is not the same with the one selected by the user
				if (!strLine.replace(" ", "").equalsIgnoreCase(editLine.replaceAll("\\[", "").replaceAll("\\]", "").replace(" ", ""))) {
					fileContent.append(strLine);// then write it to the file.
					fileContent.append(System.getProperty("line.separator"));																				
					/* So the line that will be the same as the editLine, the line selected to
					the JTextPane will not be written to the file, it will be deleted.*/
				}
			}
			writeUpdatedFile(expensesFile, br, fileContent);
		}
		catch (Exception e) {// Catch exception if any
			e.printStackTrace();
		}
	}

	/**
	 * @param expensesFile
	 * @param br
	 * @param fileContent
	 * @throws IOException
	 */
	private void writeUpdatedFile (String expensesFile, BufferedReader br, StringBuilder fileContent) throws IOException {
		FileWriter fstreamWrite = new FileWriter(expensesFile);
		BufferedWriter out = new BufferedWriter(fstreamWrite);
		out.write(fileContent.toString().trim());
		out.close();
		br.close();
	}
	
	
}
