/**
 * Created: 26 Nov 2020
 * Author: cousm
 */
package view.maingui.action;

import static utils.Constants.ADD_EXPENSES_FROM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import service.SumExpensesForDatesService;
import view.maingui.AlertDialog;

/**
 * @author cousm Class that handles the Add Expenses click on the GUI
 *
 */
public class AddExpensesAction extends AbstractAction implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField datesFrom;
	private AlertDialog alertDialog;
	private SumExpensesForDatesService addExpenses;
	

	/**
	 * @param datesFrom 
	 */
	public AddExpensesAction (JTextField datesFrom) {
		super(ADD_EXPENSES_FROM);
		this.datesFrom = datesFrom;
		alertDialog = new AlertDialog();
		addExpenses  = new SumExpensesForDatesService();
	}

	public void actionPerformed (ActionEvent arg0) {
		addExpenseAction(datesFrom);
	}
	
	/**
	 * 
	 */
	private void addExpenseAction (JTextField datesFrom) {
		String datesFromString = datesFrom.getText();
		try {
			double sum = addExpenses.addTheExpenses(datesFrom);
			alertDialog.informUser("Expenses for: ", " You have spent: ", datesFromString, sum);
		}
		catch (Exception exception) {
			alertDialog.informUserError(exception.getMessage());
		}
	}
	
	
}
