/**
 * Created: 26 Nov 2020
 * Author: cousm
 */
package view.maingui.action;

import static utils.Constants.REPORT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import loaddata.LoadExpenses;
import view.reportgui.ReportGUI;

/**
 * @author cousm Class that handles the click on the Report button on the GUI
 *
 */
public class ReportAction extends AbstractAction implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoadExpenses loadExpenses;
	

	/**
	 * @param loadExpenses 
	 */
	public ReportAction (LoadExpenses loadExpenses) {
		super(REPORT);
		this.loadExpenses = loadExpenses;
	}

	public void actionPerformed (ActionEvent arg0) {
		reportAction(loadExpenses);
	}

	private void reportAction (LoadExpenses loadExpenses) {
		loadExpenses.readTheFile();
		ReportGUI reportGUI = new ReportGUI(loadExpenses.getAllTheLines(), loadExpenses.getAllTheYears());
		reportGUI.buildReportArea();
	}
	
	
}
