/**
 * Created: 26 Nov 2020
 * Author: cousm
 */
package view.maingui.action;

import static utils.Constants.EDIT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import loaddata.LoadExpenses;
import view.editgui.MoneyControlEdit;
import view.maingui.UpdateBalance;

/**
 * @author cousm Class that handles the click on the Edit button on the GUI
 *
 */
public class EditAction extends AbstractAction implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UpdateBalance updateBalance;
	private LoadExpenses loadExpenses;

	/**
	 * @param updateBalance 
	 * @param loadExpenses 
	 */
	public EditAction (UpdateBalance updateBalance, LoadExpenses loadExpenses) {
		super(EDIT);
		this.updateBalance = updateBalance;
		this.loadExpenses = loadExpenses;
	}

	public void actionPerformed (ActionEvent arg0) {
		editAction();
	}
	
	private void editAction () {
		MoneyControlEdit editFrame = new MoneyControlEdit(updateBalance, loadExpenses);
		editFrame.setSize(750, 400);
		editFrame.setTitle(EDIT);
		editFrame.setVisible(true);
	}
	
	
}
