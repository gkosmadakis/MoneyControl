/**
 * FileUtilities.java
 * Created: 26 Nov 2020
 * Author: cousm
 */
package utils;

import java.io.PrintWriter;

import loaddata.LoadExpenses;

/**
 * @author cousm Utility Class that has a method related with File handling
 *
 */
public class FileUtilities {
	/**
	 * @param out
	 * @param length
	 * @param loadExpenses 
	 */
	public void writeFileHeader (PrintWriter out, int length, LoadExpenses loadExpenses) {
		if (!loadExpenses.getExpensesFileIsNotEmpty()) {
			out.printf("%-" + length + "s %s%n", "Amount Description", "Date");// print the header
			loadExpenses.setExpensesFileIsNotEmpty(true);
		}
	}
}
