package view.maingui;

import static utils.Constants.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import interfaces.ISimpleDocumentListener;
import loaddata.LoadExpenses;
import service.IncomeFileService;
import utils.DateLabelFormatter;
import utils.LoadProperties;
import utils.Validations;
import view.maingui.action.AddExpensesAction;
import view.maingui.action.ChartsAction;
import view.maingui.action.EditAction;
import view.maingui.action.ExitAction;
import view.maingui.action.ExportAction;
import view.maingui.action.HelpAction;
import view.maingui.action.ImportAction;
import view.maingui.action.ReportAction;
import view.maingui.action.SaveAction;

/**
 * @author cousm Creates the main GUI of the application
 */
public class MoneyControlGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private String input;
	private JComboBox<String> description;
	private Vector<String> comboBoxItems;
	private DefaultComboBoxModel<String> model;
	private JTextField expense, datesFrom, incomeField;
	private JLabel balanceLabel;
	private static final String NOT_SELECTABLE_OPTION = " - Select an Option - ";
	private JButton saveButton, addButton, emptyButton, reportButton,
		editButton, chartButton, exportButton, importButton,emptyButton2;
	private JDatePickerImpl datePicker;
	private String importSourcePath;
	private String importDestinationPath;
	private String exportPath;
	private String expensesFile;
	private String incomeFile;
	private double balance;
	private IncomeFileService incomeFileService;
	private LoadExpenses loadExpenses;
	private Validations validations;
	
	
	/**
	 * @param loadExpenses
	 * @param yearToMonthToDescriptionWithAmounts 
	 */
	public MoneyControlGUI (LoadExpenses loadExpenses,TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts) {
		this.loadExpenses = loadExpenses;
		validations = new Validations();
		layoutTop();
		loadProperties();
		buildMenuBar(loadExpenses);
		loadDescriptions();
		
		incomeFileService = new IncomeFileService(incomeFile);
		setUpBalance(yearToMonthToDescriptionWithAmounts, balanceLabel);
		
		incomeFieldDocumentListener(yearToMonthToDescriptionWithAmounts, balanceLabel);
		
	}
	
	private void setUpBalance (TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts,JLabel balanceLabel) {
		incomeFileService.readIncomeFile();
		balance = incomeFileService.processBalance(incomeFileService.getIncomeEntered(), yearToMonthToDescriptionWithAmounts);
		createOrUpdateBalanceChart(balance);
		incomeField.setText(incomeFileService.getIncomeEntered());
		balanceLabel.setText("Balance: " + balance);
	}
	
	private void incomeFieldDocumentListener (TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts,JLabel balanceLabel) {
		incomeField.getDocument().addDocumentListener(new ISimpleDocumentListener() {
			@Override
			public void insertUpdate (DocumentEvent e) {
				updateIncomeChart(yearToMonthToDescriptionWithAmounts, balanceLabel);
			}
			@Override
			public void removeUpdate (DocumentEvent e) {
				updateIncomeChart(yearToMonthToDescriptionWithAmounts, balanceLabel);
			}
			@Override
			public void changedUpdate (DocumentEvent e) {
			}
			@Override
			public void update (DocumentEvent e) {
			}
		});
	}
	
	private void updateIncomeChart (TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts, JLabel balanceLabel) {
		balance = incomeFileService.processBalance(incomeField.getText(), yearToMonthToDescriptionWithAmounts);
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
		add(barPanel, BorderLayout.LINE_END);
		
		return chartPanel;
	}
	
	private void loadDescriptions () {
		Descriptions descriptions = new Descriptions(input, description, comboBoxItems, model);
		descriptions.getTheInputFromDescriptionComboBox();
	}

	private void loadProperties () {
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		importSourcePath = propertiesMap.get(IMPORT_SOURCE_PATH);
		importDestinationPath = propertiesMap.get(IMPORT_DESTINATION_PATH);
		exportPath = propertiesMap.get(EXPORT_PATH);
		expensesFile = propertiesMap.get(EXPENSES_FILE);
		incomeFile = propertiesMap.get(INCOME_FILE);
	}

	/**
	 * Builds the Menu of the main GUI window
	 * @param loadExpenses 
	 * @return menuBar
	 */
	public JMenuBar buildMenuBar (LoadExpenses loadExpenses) {
		// Creates a menubar for a JFrame
		JMenuBar menuBar = new JMenuBar();
		// Add the menubar to the frame
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu(FILE);
		JMenu expensesMenu = new JMenu(EXPENSES);
		JMenu helpMenu = new JMenu(HELP);
		menuBar.add(fileMenu);
		menuBar.add(expensesMenu);
		menuBar.add(helpMenu);
		JMenuItem saveAction = new JMenuItem(new SaveAction(expense, description,datePicker, expensesFile,loadExpenses));
		JMenuItem exportAction = new JMenuItem(new ExportAction(exportPath, importSourcePath));
		JMenuItem importAction = new JMenuItem(new ImportAction(importSourcePath, importDestinationPath));
		JMenuItem exitAction = new JMenuItem(new ExitAction(incomeField));
		JMenuItem addExpensesAction = new JMenuItem(new AddExpensesAction(datesFrom));
		JMenuItem reportAction = new JMenuItem(new ReportAction(loadExpenses));
		JMenuItem editAction = new JMenuItem(new EditAction());
		JMenuItem chartsAction = new JMenuItem(new ChartsAction(loadExpenses));
		JMenuItem helpAction = new JMenuItem(new HelpAction());
		addActionButtonsToMenuCategories(fileMenu, expensesMenu, helpMenu, saveAction, exportAction, importAction, exitAction, addExpensesAction, reportAction, editAction, chartsAction, helpAction);
		
		return menuBar;
	}

	private void addActionButtonsToMenuCategories (JMenu fileMenu, JMenu expensesMenu, JMenu helpMenu, JMenuItem saveAction, JMenuItem exportAction, JMenuItem importAction, JMenuItem exitAction, JMenuItem addExpensesAction, JMenuItem reportAction, JMenuItem editAction, JMenuItem chartsAction, JMenuItem helpAction) {
		fileMenu.add(saveAction);
		fileMenu.add(exportAction);
		fileMenu.add(importAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);
		expensesMenu.add(addExpensesAction);
		expensesMenu.add(reportAction);
		expensesMenu.add(editAction);
		expensesMenu.addSeparator();
		expensesMenu.add(chartsAction);
		helpMenu.add(helpAction);
	}
	
	/**
	 * Builds the body of the GUI window
	 * @return top
	 */
	public JPanel layoutTop () {
		JPanel top = new JPanel(new GridLayout(10, 2));
		JLabel expensesLabel = new JLabel(EXPENSE_AMOUNT);
		expense = new JTextField();
		validations.fieldValidateInput(expense, "\\d*");
		JLabel expensesDescriptionLabel = setUpDescriptionsComponent();
		JLabel dateLabel = setUpDateComponent();
		setUpButtons();
		JLabel incomeLabel = new JLabel(ENTER_YOUR_INCOME);
		incomeField = new JTextField();
		balanceLabel = new JLabel(BALANCE_COLON);
		addComponentsToTopPanel(top, expensesLabel, expensesDescriptionLabel, dateLabel, incomeLabel);
		
		return top;
	}
	
	private JLabel setUpDescriptionsComponent () {
		JLabel expensesDescriptionLabel = new JLabel(EXPENSE_DESCRIPTION);
		comboBoxItems = new Vector<String>();
		comboBoxItems.add(NOT_SELECTABLE_OPTION);
		comboBoxItems.add(HOUSE_RENT);
		comboBoxItems.add(SHOPPING);
		comboBoxItems.add(SUPERMARKET);
		comboBoxItems.add(TRAVEL);
		comboBoxItems.add(MORTGAGE);
		comboBoxItems.add(COUNCIL_TAX);
		comboBoxItems.add(HOUSE_BILLS);
		comboBoxItems.add(ENTERTAINMENT);
		model = new DefaultComboBoxModel<String>(comboBoxItems);
		description = new JComboBox<String>(model);
		description.setEditable(true);
		description.setToolTipText("To add a new item just type on the field and press Enter. Remove it by selecting it and pressing delete.");
		input = (String) description.getEditor().getItem();
		ComboBoxEditor editor = description.getEditor();
		JTextField descriptionTextField = (JTextField)editor.getEditorComponent();
		validations.fieldValidateInput(descriptionTextField, "^[a-zA-Z -]+$");
		return expensesDescriptionLabel;
	}
	
	private JLabel setUpDateComponent () {
		JLabel dateLabel = new JLabel(DATE);
		UtilDateModel model2 = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", TODAY);
		p.put("text.month", MONTH);
		p.put("text.year", YEAR);
		JDatePanelImpl datePanel = new JDatePanelImpl(model2, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		return dateLabel;
	}
	
	private void setUpButtons () {
		saveButton = new JButton(SAVE_TO_FILE);
		saveButton.addActionListener(this);
		emptyButton = new JButton();
		emptyButton.setVisible(false);
		addButton = new JButton(ADD_EXPENSES_FROM);
		addButton.addActionListener(this);
		datesFrom = new JTextField();
		datesFrom.setToolTipText("Enter the dates From-To splitting by - to add all the expenses");
		reportButton = new JButton(REPORT);
		reportButton.addActionListener(this);
		editButton = new JButton(EDIT);
		editButton.addActionListener(this);
		chartButton = new JButton(CHARTS);
		chartButton.addActionListener(this);
		exportButton = new JButton(EXPORT);
		exportButton.addActionListener(this);
		importButton = new JButton(IMPORT);
		importButton.addActionListener(this);
		emptyButton2 = new JButton("Test");
		emptyButton2.setVisible(false);
	}

	private void addComponentsToTopPanel (JPanel top, JLabel expensesLabel, JLabel expensesDescriptionLabel, JLabel dateLabel, JLabel incomeLabel) {
		top.add(expensesLabel);
		top.add(expense);
		top.add(expensesDescriptionLabel);
		top.add(description);
		top.add(dateLabel);
		top.add(datePicker);
		top.add(saveButton);
		top.add(emptyButton);
		top.add(addButton);
		top.add(datesFrom);
		top.add(reportButton);
		top.add(editButton);
		top.add(chartButton);
		top.add(exportButton);
		top.add(importButton);
		top.add(emptyButton2);
		top.add(incomeLabel);
		top.add(incomeField);
		top.add(balanceLabel);
		add(top, BorderLayout.NORTH);
	}
	
	@Override
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == saveButton) {
			new SaveAction(expense, description,datePicker, expensesFile,loadExpenses).actionPerformed(e);
		}
		if (e.getSource() == addButton) {
			new AddExpensesAction(datesFrom).actionPerformed(e);
		}
		if (e.getSource() == reportButton) {
			new ReportAction(loadExpenses).actionPerformed(e);
		}
		if (e.getSource() == editButton) {
			new EditAction().actionPerformed(e);
		}
		if (e.getSource() == chartButton) {
			new ChartsAction(loadExpenses).actionPerformed(e);
		}
		if (e.getSource() == exportButton) {
			new ExportAction(exportPath, importSourcePath).actionPerformed(e);
		}
		if (e.getSource() == importButton) {
			new ImportAction(importSourcePath, importDestinationPath).actionPerformed(e);
		}
	}
	
	protected void processWindowEvent (WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			ActionEvent actionEvent = new ActionEvent(e.getWindow(), 1, "Exit");
			new ExitAction(incomeField).actionPerformed(actionEvent);
		}
	}
	
	
	
}
