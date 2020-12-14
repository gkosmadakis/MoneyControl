/**
 * WriteToFileService.java
 * Created: 26 Nov 2020
 * Author: cousm
 */
package service;

import static utils.Constants.DATE_FORMAT;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import loaddata.LoadExpenses;
import utils.FileUtilities;

/**
 * @author cousm Service class that writes a new expense added by the user to the expenses file
 *
 */
public class WriteToFileService {
	
	/**
	 * @param amount 
	 * @param description 
	 * @param date 
	 * @param expensesFile 
	 * @param loadExpenses 
	 */
	public void writeToFile (String amount, String description, Date date,String expensesFile, LoadExpenses loadExpenses) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(expensesFile, true)));
			Format formatter = new SimpleDateFormat(DATE_FORMAT);
			String dateFormatted = formatter.format(date);
			int length = 46;
			FileUtilities fileUtilities = new FileUtilities();
			fileUtilities.writeFileHeader(out, length,loadExpenses);
			out.write(System.lineSeparator());// prints a new line
			String formatStr = "%-7s%-40s%-10s";// formats the columns
			out.append(String.format(formatStr, amount, description, dateFormatted));
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	
}
