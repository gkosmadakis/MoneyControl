package appinit;

import static utils.Constants.MAIN_GUI_TITLE;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import loaddata.LoadExpenses;
import view.maingui.MoneyControlGUI;
import utils.LoadProperties;

/**
 * Main class that starts the app, loads the expenses file and initialises the main GUI
 *
 */
public class MainMoneyControl {
	
	/**
	 * @param args Main method to start the app. Read the expenses.txt file to load the data
	 */
	public static void main (String args[]) {
		LoadProperties.setPropertiesFromPropertiesFile(System.getProperty("user.dir")+"\\config.properties");
		LoadExpenses loadExpenses = new LoadExpenses();
		TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts = loadExpenses.readTheFile();

		MoneyControlGUI moneyControlGUI = new MoneyControlGUI(loadExpenses,yearToMonthToDescriptionWithAmounts);
		moneyControlGUI.setSize(350, 500);
		moneyControlGUI.setTitle(MAIN_GUI_TITLE);
		moneyControlGUI.setVisible(true);
	}
	
}
