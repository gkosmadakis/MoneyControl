/**
 * SaveAction.java
 * Created: 26 Nov 2020
 * Author: cousm
 */
package view.maingui.action;

import static utils.Constants.NOT_SELECTABLE_OPTION;
import static utils.Constants.SAVE_TO_FILE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import loaddata.LoadExpenses;
import service.WriteToFileService;
import view.maingui.AlertDialog;
import view.maingui.UpdateBalance;

/**
 * @author cousm Class that handles the click on the save to file button on the GUI
 *
 */
public class SaveAction extends AbstractAction implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField expense;
	private JComboBox<String> description;
	private JDatePickerImpl datePicker;
	private String expensesFile;
	private LoadExpenses loadExpenses;
	private WriteToFileService writeToFileService;
	private UpdateBalance updateBalance;
	
	/**
	 * @param expense 
	 * @param description 
	 * @param datePicker 
	 * @param expensesFile 
	 * @param loadExpenses 
	 * @param updateBalance 
	 * 
	 */
	public SaveAction (JTextField expense, JComboBox<String> description, JDatePickerImpl datePicker, String expensesFile, LoadExpenses loadExpenses, UpdateBalance updateBalance) {
		super(SAVE_TO_FILE);
		this.expense = expense;
		this.description = description;
		this.datePicker = datePicker;
		this.expensesFile = expensesFile;
		this.loadExpenses = loadExpenses;
		this.updateBalance = updateBalance;
		writeToFileService = new WriteToFileService();
	}
	
	public void actionPerformed (ActionEvent arg0) {
		 writeToFileAction(expense, description,datePicker, expensesFile,loadExpenses);
	}
	
	private void writeToFileAction (JTextField expense, JComboBox<String> description, JDatePickerImpl datePicker, String expensesFile,LoadExpenses loadExpenses) {
		AlertDialog alertDialog = new AlertDialog();
		String descriptionCombo = (String) description.getSelectedItem();
		if (expense.getText().equals("") || descriptionCombo.equals(NOT_SELECTABLE_OPTION) || datePicker.getModel().getValue() == null) {
			alertDialog.informUserError("Some of the fields are empty. Please fill them all and try again");
		}
		else {
			writeToFileService.writeToFile(expense.getText(),descriptionCombo, (Date) datePicker.getModel().getValue(),expensesFile,loadExpenses);
			expense.setText("");
			description.setSelectedItem(NOT_SELECTABLE_OPTION);
			datePicker.getModel().setValue(null);
			alertDialog.informUser("Your expenses are saved to the file");
			updateBalance.updateIncomeChart(loadExpenses.readTheFile());
		}
	}
	
	
}
