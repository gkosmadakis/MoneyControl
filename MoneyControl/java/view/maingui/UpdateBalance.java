/**
 * UpdateBalance.java
 * Created: 15 Dec 2020
 * Author: cousm
 */
package view.maingui;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import interfaces.ISimpleDocumentListener;
import service.IncomeFileService;

/**
 * @author cousm Class that updates the balance and the income chart when the income or the expenses change
 *
 */
public class UpdateBalance extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JLabel balanceLabel;
	private IncomeFileService incomeFileService;
	private JTextField incomeField;
	private MoneyControlGUI moneyControlGUI;
	
	
	/**
	 * @param balanceLabel
	 * @param incomeFileService
	 * @param incomeField
	 * @param moneyControlGUI 
	 */
	public UpdateBalance (JLabel balanceLabel, IncomeFileService incomeFileService, JTextField incomeField, MoneyControlGUI moneyControlGUI)  {
		super();
		this.balanceLabel = balanceLabel;
		this.incomeFileService = incomeFileService;
		this.incomeField = incomeField;
		this.moneyControlGUI = moneyControlGUI;
	}
	
	/**
	 * @param yearToMonthToDescriptionWithAmounts
	 * @param balanceLabel
	 * @return balance
	 */
	public double setUpBalance (TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts,JLabel balanceLabel) {
		incomeFileService.readIncomeFile();
		double balance = incomeFileService.processBalance(incomeFileService.getIncomeEntered(), yearToMonthToDescriptionWithAmounts);
		createOrUpdateBalanceChart(balance);
		incomeField.setText(incomeFileService.getIncomeEntered());
		balanceLabel.setText("Balance: " + balance);
		
		return balance;
	}
	
	/**
	 * @param yearToMonthToDescriptionWithAmounts
	 * @param balanceLabel
	 */
	public void incomeFieldDocumentListener (TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts,JLabel balanceLabel) {
		incomeField.getDocument().addDocumentListener(new ISimpleDocumentListener() {
			@Override
			public void insertUpdate (DocumentEvent e) {
				updateIncomeChart(yearToMonthToDescriptionWithAmounts);
			}
			@Override
			public void removeUpdate (DocumentEvent e) {
				updateIncomeChart(yearToMonthToDescriptionWithAmounts);
			}
			@Override
			public void changedUpdate (DocumentEvent e) {
			}
			@Override
			public void update (DocumentEvent e) {
			}
		});
	}

	/**
	 * @param yearToMonthToDescriptionWithAmounts
	 */
	public void updateIncomeChart (TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts) {
		double balance = incomeFileService.processBalance(incomeField.getText(), yearToMonthToDescriptionWithAmounts);
		createOrUpdateBalanceChart(balance);
		balanceLabel.setText("Balance: " + balance);
	}
	
	/**
	 * @param balance
	 * @return chartPanel
	 */
	public ChartPanel createOrUpdateBalanceChart (double balance) {
		CreateIncomeChart processIncome = new CreateIncomeChart(incomeField);
		final CategoryDataset dataset = processIncome.createCategoryDataset(balance);
		final JFreeChart chart = processIncome.createChart(dataset, balance);
		chart.getPlot().setBackgroundPaint(getBackground());
		chart.getPlot().setOutlineVisible(false);
		// add the chart to a panel...
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(320, 30));
		// Adds the income chart to the main GUI
		JPanel barPanel = new JPanel();
		barPanel.add(chartPanel);
		moneyControlGUI.add(barPanel, BorderLayout.LINE_END);
		
		return chartPanel;
	}
}

