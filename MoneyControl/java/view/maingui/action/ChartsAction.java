/**
 * Created: 26 Nov 2020
 * Author: cousm
 */
package view.maingui.action;

import static utils.Constants.CHARTS;
import static utils.Constants.INCOME_FILE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import loaddata.LoadExpenses;
import service.IncomeFileService;
import utils.LoadProperties;
import view.chartsgui.MoneyControlChart;

/**
 * @author cousm Class that handles the click on Charts Button on the GUI
 *
 */
public class ChartsAction extends AbstractAction implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoadExpenses loadExpenses;
	private String incomeEntered;

	/**
	 * @param loadExpenses
	 */
	public ChartsAction (LoadExpenses loadExpenses) {
		super(CHARTS);
		this.loadExpenses = loadExpenses;
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		String incomeFile = propertiesMap.get(INCOME_FILE);
		IncomeFileService incomeFileService = new IncomeFileService(incomeFile);
		incomeEntered = incomeFileService.getIncomeEntered();
	}

	public void actionPerformed (ActionEvent arg0) {
		chartsAction();
		
	}
	
	private void chartsAction () {
		MoneyControlChart CC = new MoneyControlChart(loadExpenses, incomeEntered);
		CC.pack();
		CC.setTitle(CHARTS);
		CC.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		CC.setVisible(true);
	}
	
	
}
