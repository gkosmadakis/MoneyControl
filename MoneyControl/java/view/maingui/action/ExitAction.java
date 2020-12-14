/**
 * Created: 26 Nov 2020
 * Author: cousm
 */
package view.maingui.action;

import static utils.Constants.EXIT;
import static utils.Constants.INCOME_FILE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import service.IncomeFileService;
import utils.LoadProperties;

/**
 * @author cousm Class that handles the click on the X button to close the GUI
 *
 */
public class ExitAction extends AbstractAction implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField incomeField;
	private IncomeFileService incomeFileService;
	

	/**
	 * @param incomeField
	 */
	public ExitAction (JTextField incomeField) {
		super(EXIT);
		this.incomeField = incomeField;
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		String incomeFile = propertiesMap.get(INCOME_FILE);
		incomeFileService = new IncomeFileService(incomeFile);
	}

	public void actionPerformed (ActionEvent arg0) {
		 exitAction(incomeFileService);
	}
	
	private void exitAction (IncomeFileService incomeFileService) {
		if (incomeFileService.getIncomeEntered() != "") {
			incomeFileService.writeIncomeToFile(incomeField.getText());
		}
		System.exit(0);
	}
	
	
}
