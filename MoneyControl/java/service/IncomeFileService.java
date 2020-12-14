/**
 * IncomeFileService.java
 * Created: 4 Dec 2020
 * Author: cousm
 */
package service;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author cousm Service Class that reads/writes from/to the income file and process the balance
 *
 */
public class IncomeFileService {
	
	private String incomeFile;
	private String incomeEntered;
	
	
	/**
	 * @param incomeFile
	 */
	public IncomeFileService (String incomeFile) {
		super();
		this.incomeFile = incomeFile;
		readIncomeFile();
	}

	/**
	 * @param incomeEntered adds the income to the file by appending it to a new line
	 */
	public void writeIncomeToFile (String incomeEntered) {
		ArrayList<String> storedIncomeInFile = new ArrayList<String>();
		try {
			FileReader reader = new FileReader(incomeFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				storedIncomeInFile.add(in.nextLine());
			}
			reader.close();
			in.close();
			String incomeItem = incomeEntered;
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(incomeFile, true)));
			if (incomeItem != null && !storedIncomeInFile.contains(incomeItem)) {
				out.append(incomeItem);
				out.write(System.lineSeparator());// prints a new line
			}
			out.close();
		}
		catch (IOException e) {
			System.out.println("Error processing file:" + e);
		}
	}
	
	/**
	 * reads the file with the income
	 * @return incomeEntered
	 */
	public String readIncomeFile () {
		try {
			FileReader reader = new FileReader(incomeFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				String incomeItem = in.nextLine();
				incomeEntered = incomeItem;
			}
			reader.close();
			in.close();
		}
		catch (IOException e) {
			System.out.println("File not found");
		}
		return incomeEntered;
	}
	
	/**
	 * @param currentIncome
	 * @param yearToMonthToDescriptionWithAmounts 
	 * @return calculates the balance income- expenses for the current month
	 */
	public double processBalance (String currentIncome, TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts) {
		double balance = 0.0;
		Float totalExpenses = 0f;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int currentMonth = month + 1;
		int year = cal.get(Calendar.YEAR);
		if (yearToMonthToDescriptionWithAmounts != null) {
			TreeMap<String, Map<String, List<Float>>> currentYearMap = yearToMonthToDescriptionWithAmounts.get(String.valueOf(year));
			if (currentYearMap != null) {
				Map<String, List<Float>> currentMonthMap = currentYearMap.get(String.valueOf(currentMonth));
				if (currentMonthMap != null) {
					for (List<Float> listOfExpenses : currentMonthMap.values()) {
						for (Float amountFloat : listOfExpenses) {
							totalExpenses += amountFloat;
						}
					}
				}
			}
			if (!currentIncome.equals("")) {
				balance = Double.parseDouble(currentIncome) - totalExpenses;
			}
		}
		return balance;
	}

	/**
	 * @return incomeEntered
	 */
	public String getIncomeEntered () {
		return incomeEntered;
	}
	
	
	
}
