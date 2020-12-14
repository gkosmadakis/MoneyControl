/**
 * Created: 26 Nov 2020
 * Author: cousm
 */
package view.maingui.action;

import static utils.Constants.EDIT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import view.editgui.MoneyControlEdit;

/**
 * @author cousm Class that handles the click on the Edit button on the GUI
 *
 */
public class EditAction extends AbstractAction implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 */
	public EditAction () {
		super(EDIT);
	}

	public void actionPerformed (ActionEvent arg0) {
		editAction();
	}
	
	private void editAction () {
		MoneyControlEdit editFrame = new MoneyControlEdit();
		editFrame.setSize(750, 400);
		editFrame.setTitle(EDIT);
		editFrame.setVisible(true);
	}
	
	
}
