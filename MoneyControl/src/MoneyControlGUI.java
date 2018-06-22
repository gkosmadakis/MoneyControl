import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleInsets;

import java.util.Date;


public class MoneyControlGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField expense, datesFrom,incomeField;
	private JLabel balanceLabel;
	private double balance;
	private JComboBox<String> description,monthSelection;
	DefaultComboBoxModel<String> model;
	private static final String NOT_SELECTABLE_OPTION = " - Select an Option - ";
	private JButton saveButton, addButton,emptyButton, reportButton, editButton, chartButton, exportButton, importButton;
	private JTextPane reportArea;
	private JDatePickerImpl datePicker;
	ArrayList<Double> expenses = new ArrayList<Double>();
	static ArrayList<Date> dates = new ArrayList<Date>();
	public ArrayList<String> itemsAddedByUser;
	private String descriptionCombo,input, fileLine,fileLineJan,fileLineFeb,fileLineMar,fileLineApr,
	fileLineMay,fileLineJun,fileLineJul,fileLineAug,fileLineSep,fileLineOct,fileLineNov,fileLineDec, fileLineJan16;
	Vector<String> comboBoxItems;
	ChartPanel chartPanel;
	private static String incomeEntered;
	public static Double incomeEnteredAsDoubleMar, incomeEnteredAsDoubleApr, incomeEnteredAsDoubleMay,incomeEnteredAsDoubleJun;

	public MoneyControlGUI(){

		buildMenuBar();
		layoutTop();
		readDescriptionsFile();
		getTheInputFromComboBox();
		// read the file with the income
		readIncomeFile();
		
		changeReportArea();
		// the listener to show the balance when user adds the income
		incomeField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				
			}
			public void removeUpdate(DocumentEvent e) {
				
			}
			public void insertUpdate(DocumentEvent e) {
				processBalance();//the method that returns a double, the balance
				final CategoryDataset dataset = createDataset();
				final JFreeChart chart = createChart(dataset);
				chart.getPlot().setBackgroundPaint(getBackground()); 
				chart.getPlot().setOutlineVisible(false);
				// add the chart to a panel...
				chartPanel = new ChartPanel(chart);

				chartPanel.setPreferredSize(new java.awt.Dimension(320, 30));
				layOutBottom ();
			}
		});
		
		//a listener to add a delay of 3000 ms after the user has finished typing the income
		ActionListener listener = new ActionListener(){
			public void actionPerformed(ActionEvent event){
				// if the income have been added then set the income Field. 
				// This will trigger the insert Update method of the listener above
				if (incomeEntered != null) {
					incomeField.setText(incomeEntered);
					
				}
				balanceLabel.setText("Balance: "+ balance);
				
			}
			
		};
		
		Timer timer = new Timer(3000, listener);
		timer.start();
		
	}
	
	public void buildMenuBar() {

		// Creates a menubar for a JFrame
		JMenuBar menuBar = new JMenuBar();

		// Add the menubar to the frame
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		JMenu expensesMenu = new JMenu("Expenses");
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(fileMenu);
		menuBar.add(expensesMenu);
		menuBar.add(helpMenu);

		JMenuItem saveAction = new JMenuItem("Save To File");
		JMenuItem exportAction = new JMenuItem("Export");
		JMenuItem importAction = new JMenuItem("Import");
		JMenuItem exitAction = new JMenuItem("Exit");
		JMenuItem addExpensesAction = new JMenuItem("Add Expenses From");
		JMenuItem reportAction = new JMenuItem("Report");
		JMenuItem editAction = new JMenuItem("Edit");
		JMenuItem chartsAction = new JMenuItem("Charts");
		JMenuItem helpAction = new JMenuItem ("Guide");


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

		// Add a listener to the New menu item. actionPerformed() method will
		// invoked, if user triggred this menu item
		exportAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					processExport(new File ("C:\\eclipse-Indigo\\MyWorkspace\\MoneyControl\\expenses.txt")
					,new File( "C:\\eclipse-Indigo\\MyWorkspace\\MoneyControl\\Expenses Exported\\expenses.txt"));
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});

		importAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				processImport();
			}
		});

		addExpensesAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				addTheExpenses();
				ClearFieldsWhenButtonIsPressed();
			}
		});

		reportAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				buildReportArea();
			}
		});

		editAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				MoneyControlEdit editFrame = new MoneyControlEdit();
				editFrame.setSize(750, 400);
				editFrame.setTitle("Edit");
				editFrame.setVisible(true);
			}
		});

		chartsAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				MoneyControlChart CC = new MoneyControlChart();
				CC.pack();
				CC.setTitle("Charts");
				CC.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				CC.setVisible(true);
			}
		});

		saveAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (expense.getText().equals("")||descriptionCombo.equals(NOT_SELECTABLE_OPTION)||datePicker.getModel().getValue()==null){
					JOptionPane.showMessageDialog(null, "Some of the fields are empty. " +
							"Please fill them all and try again.","Result Summary", JOptionPane.ERROR_MESSAGE);
				}
				else {
					WriteToFile();
					ClearFieldsWhenButtonIsPressed();
					JOptionPane.showMessageDialog(null, "Your expenses are saved to a file","Result Summary", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		helpAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				showHelp();
			}
		});

		exitAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (incomeEntered != null) {
					WriteIncomeToFile();
				}
				System.exit(0);
			}
		});
	}

	public void layoutTop(){

		JPanel top = new JPanel(new GridLayout(10,2));
		JLabel expensesLabel = new JLabel("Expense Amount:");
		expense = new JTextField();
		JLabel expensesDescriptionLabel = new JLabel("Expense Description:");

		comboBoxItems = new Vector<String>();
		comboBoxItems.add(NOT_SELECTABLE_OPTION);
		comboBoxItems.add("House Rent");
		comboBoxItems.add("Shopping");
		comboBoxItems.add("SuperMarket");
		comboBoxItems.add("Travel");
		comboBoxItems.add("Mortgage");
		comboBoxItems.add("Council Tax");
		comboBoxItems.add("House Bills");
		comboBoxItems.add("Entertainment");
		model = new DefaultComboBoxModel<String>(comboBoxItems);
		description = new JComboBox<String>(model);
		description.setEditable(true);
		description.setToolTipText("To add a new item just type on the field and press Enter. Remove it by selecting it and pressing delete.");
		input = (String) description.getEditor().getItem();
		JLabel dateLabel= new JLabel("Date:");
		UtilDateModel model2 = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model2, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		saveButton = new JButton("Save to file");
		saveButton.addActionListener(this);
		emptyButton = new JButton();
		emptyButton.setVisible(false);
		addButton = new JButton("Add expenses from:");
		addButton.addActionListener(this);
		datesFrom = new JTextField();
		datesFrom.setToolTipText("Enter the dates From-To splitting by - to add all the expenses");
		reportButton = new JButton("Report");
		reportButton.addActionListener(this);
		editButton = new JButton("Edit");
		editButton.addActionListener(this);
		chartButton = new JButton("Charts");
		chartButton.addActionListener(this);
		exportButton = new JButton("Export");
		exportButton.addActionListener(this);
		importButton = new JButton ("Import");
		importButton.addActionListener(this);
		JButton emptyButton2 = new JButton("Test");
		emptyButton2.setVisible(false);
		JLabel incomeLabel = new JLabel("Enter your income:");
		incomeField = new JTextField();
		balanceLabel = new JLabel("Balance:");
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

	public void layOutBottom () {
		JPanel barPanel = new JPanel();
		barPanel.add(chartPanel);
		add(barPanel, BorderLayout.LINE_END);
	}

	private CategoryDataset createDataset() { 

		return createCategoryDataset();
	}
	
	public static String getTheIncome () {
		return incomeEntered;
	}

	public static Double getIncomeMar () {
		return incomeEnteredAsDoubleMar;
	}
	
	public static Double getIncomeApr () {
		return incomeEnteredAsDoubleApr;
	}
	
	public static Double getIncomeMay () {
		return incomeEnteredAsDoubleMay;
	}
	
	public  CategoryDataset createCategoryDataset() {

		String incomeEntered = incomeField.getText();
		Double incomeEnteredAsDouble = Double.parseDouble(incomeEntered);
		double[][] data = new double[][]

				{{balance } ,
				{(incomeEnteredAsDouble-balance) } };

		return DatasetUtilities.createCategoryDataset("", "", data);
	}

	private JFreeChart createChart(final CategoryDataset dataset) {

		final JFreeChart chart = ChartFactory.createStackedBarChart(
				"",                         // chart title
				"",                         // domain axis label
				"",                         // range axis label
				dataset,                    // data
				PlotOrientation.HORIZONTAL, // orientation
				false,                      // include legend
				false,                      // tooltips
				false                       // urls
				);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.getDomainAxis().setVisible(false);
		final StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();

		//removes the white spaces right and left
		plot.setInsets(new RectangleInsets(0,-4,0,-4));

		//removes the X axis values
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setVisible(false);

		//set the color of the bars
		renderer.setSeriesPaint(0, Color.green);
		renderer.setSeriesPaint(1, Color.red);

		if (balance<0) {
			renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(1, Color.red);
		}

		return chart;

	}

	public void getTheInputFromComboBox(){
		description.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent evt){
				if (evt.getKeyCode()== KeyEvent.VK_ENTER){

					input = (String) description.getEditor().getItem();
					if (itemsAddedByUser.contains(input)|| comboBoxItems.contains(input)){
						JOptionPane.showMessageDialog(null, "The description exists already in the list",
								"Result Summary", JOptionPane.ERROR_MESSAGE);
					}
					else if (!input.equals("")){
						model.addElement(input);	
						WriteDescriptionsToFile();
					}
				}
			}
		});

		description.getEditor().getEditorComponent().addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent evt){
				String fixedDescriptions[] = {"House Rent","Shopping","SuperMarket","Travel","Mortgage","Council Tax","House Bills","Entertainment"};
				if (evt.getKeyCode()== KeyEvent.VK_DELETE){

					input = (String) description.getEditor().getItem();
					if (Arrays.asList(fixedDescriptions).contains(input)) {
						JOptionPane.showMessageDialog(null, "This description is preinstalled in the " +
								"application and for operating reasons is not recommended to delete it",
								"Result Summary", JOptionPane.ERROR_MESSAGE);
					}
					else if (itemsAddedByUser.contains(input)){
						int index = description.getSelectedIndex();	
						deleteDescriptionFromFile ();
						model.removeElementAt(index);
					}
					else {
						JOptionPane.showMessageDialog(null, "The description does not exist in the list",
								"Result Summary", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

	public void WriteDescriptionsToFile(){

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("descriptions.txt", true)));
			String descriptionItem = input;

			out.append(descriptionItem);
			out.write(System.lineSeparator());//prints a new line

			out.close();
			itemsAddedByUser.add(descriptionItem);//inform the list that a new description is added
			}	catch (IOException e){
			
				System.out.println("Error processing file:" + e);
		}
	}

	public void readDescriptionsFile () {
		itemsAddedByUser = new ArrayList<String>();

		try
		{
			FileReader reader = new FileReader("descriptions.txt");
			Scanner in = new Scanner(reader);

			while (in.hasNextLine()) { 
				String descriptionItem = in.nextLine();
				model.addElement(descriptionItem);
				itemsAddedByUser.add(descriptionItem);
			}
			reader.close();
		}   catch (IOException e) {
			
			System.out.println("File not found");
		}
	}

	public void deleteDescriptionFromFile () {

		try {

			FileInputStream fstream = new FileInputStream("descriptions.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			StringBuilder fileContent = new StringBuilder();
			while ((strLine = br.readLine()) != null) {
				if (!strLine.equals(model.getSelectedItem())){
					fileContent.append(strLine);
					fileContent.append(System.getProperty("line.separator"));
				}
			}
			FileWriter fstreamWrite = new FileWriter("descriptions.txt");
			BufferedWriter out = new BufferedWriter(fstreamWrite);
			out.write(fileContent.toString());
			out.close();
			JOptionPane.showMessageDialog(null, "You deleted successfuly the selected item", "Result Summary", JOptionPane.INFORMATION_MESSAGE);

		} 	catch (Exception e) 
		{						//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public void WriteIncomeToFile(){

		ArrayList<String> storedIncomeInFile = new ArrayList<String>();
		try {
			FileReader reader = new FileReader("income.txt");
			Scanner in = new Scanner(reader);
			
			while (in.hasNextLine()) { 
					storedIncomeInFile.add(in.nextLine());
				}
				reader.close();
			String incomeItem = incomeEntered;
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("income.txt", true)));
				if (incomeItem != null && !storedIncomeInFile.contains(incomeItem)) {
					out.append(incomeItem);
					out.write(System.lineSeparator());//prints a new line
					
					}
						
					out.close();
			}	catch (IOException e){
			
				System.out.println("Error processing file:" + e);
		}
	}

	public void readIncomeFile () {
		
		try
		{
			FileReader reader = new FileReader("income.txt");
			Scanner in = new Scanner(reader);

			while (in.hasNextLine()) { 
				String incomeItem = in.nextLine();
				
				incomeEntered = incomeItem;
			}
			reader.close();
		}   catch (IOException e) {
			
			System.out.println("File not found");
		}
	}

	public void buildReportArea (){

		JFrame reportFrame = new JFrame();
		MainMoneyControl report = new MainMoneyControl();
		MainMoneyControl.readTheFile();
		fileLine = report.getFileLine();
		fileLineJan= report.getFileLineJan();
		fileLineFeb= report.getFileLineFeb();
		fileLineMar = report.getFileLineMar();
		fileLineApr= report.getFileLineApr();
		fileLineMay= report.getFileLineMay();
		fileLineJun= report.getFileLineJun();
		fileLineJul = report.getFileLineJul();
		fileLineAug= report.getFileLineAug();
		fileLineSep= report.getFileLineSep();
		fileLineOct= report.getFileLineOct();
		fileLineNov = report.getFileLineNov();
		fileLineDec= report.getFileLineDec();
		fileLineJan16= report.getFileLineJan16();


		if (fileLine.equals("")|| fileLine.equals(" ")||fileLine.equals(null)){
			JOptionPane.showMessageDialog(null, "You have not saved any expenses, the file is empty. "+
					"Save an expense first and try again","Result Summary", JOptionPane.ERROR_MESSAGE);
		}	

		else {
			reportArea = new JTextPane();
			JScrollPane scrollPane = new JScrollPane(reportArea);

			reportArea.setFont(new Font("Courier", Font.PLAIN, 14));
			JPanel monthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			monthPanel.setBackground(Color.WHITE);
			monthPanel.add(monthSelection);
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			monthSelection.setSelectedIndex(month);
			reportFrame.add(monthPanel, BorderLayout.NORTH);
			reportFrame.add(scrollPane);
			reportFrame.setSize(550, 400);
			reportFrame.setTitle("Expenses Report");
			reportFrame.setVisible(true);

		}// end of else
	}

	public void changeReportArea(){

		String monthData [] = new String[] {"January","February","March","April",
				"May","June","July","August","September","October","November","December", 
				"January 2016","Total"};
		monthSelection = new JComboBox<String>(monthData);

		monthSelection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String getMonthSelection = (String) monthSelection.getSelectedItem();

				switch (getMonthSelection){ 
			
				case "January":
					reportArea.setText(fileLineJan);
					break;
				case "February":
					reportArea.setText(fileLineFeb);
					break;
				case "March":
					reportArea.setText(fileLineMar);
					break;
				case "April":
					reportArea.setText(fileLineApr);
					break;
				case "May":
					reportArea.setText(fileLineMay);
					break;
				case "June":
					reportArea.setText(fileLineJun);
					break;
				case "July":
					reportArea.setText(fileLineJul);
					break;
				case "August":
					reportArea.setText(fileLineAug);
					break;
				case "September":
					reportArea.setText(fileLineSep);
					break;
				case "October":
					reportArea.setText(fileLineOct);
					break;
				case "November":
					reportArea.setText(fileLineNov);
					break;
				case "December":
					reportArea.setText(fileLineDec);
					break;
				case "January 2016":
					reportArea.setText(fileLineJan16);
					break;
				default:
					reportArea.setText(fileLine);
					break;
				}//end of switch
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		descriptionCombo = (String) description.getSelectedItem();

		if (e.getSource()==saveButton){
			if (expense.getText().equals("")||descriptionCombo.equals(NOT_SELECTABLE_OPTION)||datePicker.getModel().getValue()==null){
				JOptionPane.showMessageDialog(null, "Some of the fields are empty. " +
						"Please fill them all and try again.","Result Summary", JOptionPane.ERROR_MESSAGE);
			}
			else {
				WriteToFile();
				ClearFieldsWhenButtonIsPressed();
				JOptionPane.showMessageDialog(null, "Your expenses are saved to a file","Result Summary", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (e.getSource()==addButton){

			addTheExpenses();
			ClearFieldsWhenButtonIsPressed();

		}

		if (e.getSource()==reportButton){

			buildReportArea();
		}

		if (e.getSource()==editButton){
			MoneyControlEdit editFrame = new MoneyControlEdit();
			editFrame.setSize(750, 400);
			editFrame.setTitle("Edit");
			editFrame.setVisible(true);
		}

		if (e.getSource()==chartButton){
			MoneyControlChart CC = new MoneyControlChart();
			CC.pack();
			CC.setTitle("Charts");
			CC.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			CC.setVisible(true);
		}

		if (e.getSource()==exportButton){
			try {
				processExport(new File ("C:\\eclipse_Indigo\\workspace\\MoneyControl\\expenses.txt")
				,new File( "C:\\eclipse_Indigo\\workspace\\MoneyControl\\Expenses Exported\\expenses.txt"));
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}

		if (e.getSource()==importButton) {

			processImport();

		}
		
	}

	public void WriteToFile(){

		MainMoneyControl takeTheFile = new MainMoneyControl();
		MainMoneyControl.readTheFile();
		String fileLine = takeTheFile.getFileLine();

		try {

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("expenses.txt", true)));
			String expenseAmount = expense.getText();
			String expenseDesc = descriptionCombo;	
			Date selectedDate = (Date) datePicker.getModel().getValue();
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			String date = formatter.format(selectedDate);
			int length = 46;
			if (!fileLine.contains("Amount")){
				out.printf("%-" + length + "s %s%n", "Amount Description", "Date");//print the header	
			}	

			out.write(System.lineSeparator());//prints a new line
			String formatStr = "%-7s%-40s%-10s";//formats the columns
			out.append(String.format(formatStr, expenseAmount, expenseDesc, date));

			out.close();
		}

		catch (IOException e)
		{
			System.out.println("Error processing file:" + e);
		}
	}

	public void ClearFieldsWhenButtonIsPressed(){
		expense.setText("");
		datesFrom.setText("");

	}

	public ArrayList<Date> getDaysBetweenDates (){

		String datesFromTo = datesFrom.getText();
		dates = new ArrayList<Date>();//the arraylist where i store the dates

		if (datesFromTo.equals("")||datesFromTo.equals(null)){
			JOptionPane.showMessageDialog(null, "Add expenses field is empty. " +
					"Please supply a date range.", "Result Summary", JOptionPane.ERROR_MESSAGE);
		}
		else if (!datesFromTo.matches("^[0-9].*")){
			JOptionPane.showMessageDialog(null, "You entered words for a date. " +
					"Please supply a correct date range in the format dd/MM/yyyy.", "Result Summary", JOptionPane.ERROR_MESSAGE);
		}
		else {
			String [] token = datesFromTo.split("-");//store dates splitted by -
			String firstdate = token[0];//take the first date entered by the user
			String lastdate = token[1];//take the second date entered by the user

			DateFormat format = new SimpleDateFormat("dd/MM/yy");
			Date startdate = null;
			if (!firstdate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$")|| !lastdate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$")){
				JOptionPane.showMessageDialog(null, "Dates should be entered in the dd/MM/yyyy format. " +
						"Please supply a correct date range.", "Result Summary", JOptionPane.ERROR_MESSAGE);
			}
			else {
				try {
					startdate = format.parse(firstdate);//convert the first date into Date

				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "Wrong format of the date." +
							"Please supply a correct date range.", "Result Summary", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

				Date enddate=null;
				try {
					enddate = format.parse(lastdate);//convert the second date into Date

				} catch (ParseException e) {

					e.printStackTrace();
				}

				Calendar calendar = new GregorianCalendar();
				calendar.setTime(startdate);

				while (calendar.getTime().getTime() <= enddate.getTime())
				{
					Date result = calendar.getTime();//take the date
					dates.add(result);//add it to dates arraylist
					calendar.add(Calendar.DATE, 1);
				}
			}
		}
		return dates;//so dates has a range of dates. for instance if the user enters
		// 15/09/2015-17/09/2015 dates will store 15/09/2015 16/09/2015 17/09/2015
	}

	public void addTheExpenses(){

		getDaysBetweenDates();
		if (dates.isEmpty()){
			return;
		}

		else {
			DateFormat format = new SimpleDateFormat("dd/MM/yy");
			Date dateIntheFile = null;
			String expenseAmount = null;
			Set <Date> datesMatchedUserInput = new TreeSet<Date>();
			expenses.clear();
			try {

				FileReader reader = new FileReader("expenses.txt");
				Scanner in = new Scanner(reader);//read the file
				int lineIndex = 0;//this is to count the lines
				while (in.hasNextLine()) { 
					String line = in.nextLine();

					if (++lineIndex>2)//i need to read after the second line
					{	
						StringTokenizer st = new StringTokenizer(line);
						expenseAmount = st.nextToken();//take the amount from the file
						int index = line.lastIndexOf(" ");
						String date = line.substring(index, line.length());//st.nextToken();//take the date

						try {
							dateIntheFile = format.parse(date);//convert the date into Date

						} catch (ParseException e) {

							e.printStackTrace();
						}

						double firstDateAmountNumber=0.0;
						if (dates.contains(dateIntheFile)){
							datesMatchedUserInput.add(dateIntheFile);//add the Date in a list that will maintain them as the while loop checks all the dates.
							String firstDateAmount=expenseAmount;//if the dates taken from the input are the same
							firstDateAmountNumber = Double.parseDouble(firstDateAmount);//with those in the file
							expenses.add(firstDateAmountNumber);//then look each line and find the amount given.		
						}// add the amount in the list of expenses.	
					}
				}// end of while
				Collections.sort(dates);
				if (datesMatchedUserInput.isEmpty()){
					JOptionPane.showMessageDialog(null, "The are no expenses in the dates you entered! " +
							"Please try wiht different dates." ,"Result Summary", JOptionPane.INFORMATION_MESSAGE);
				}
				Date nextValue=null;
				for (Iterator<Date> it = datesMatchedUserInput.iterator(); it.hasNext();) {
					nextValue =  it.next();
				}
				if (dates.contains(nextValue)) {
					sumTheExpenses();//this sums up all the expenses for the date range. it is not inside the other if
					//if (dates.contains(datesMatchedUserInput)) because it would pop up all the times the while loop is counting	
				}
				reader.close();
			}
			catch (IOException e) {
				System.out.println("File not found");
			}	
		}
	}

	public double sumTheExpenses(){
		String datesFromTo = datesFrom.getText();
		double sum = 0;
		for(int i = 0; i < expenses.size(); i++){
			sum += expenses.get(i);
		}
		System.out.println(sum);
		JOptionPane.showMessageDialog(null, "Expenses for: "+datesFromTo+"."+" You have spent: " +sum,"Total Expenses", JOptionPane.INFORMATION_MESSAGE);
		return sum;
	}

	public ArrayList<String> getTheItemsByUser(){
		return itemsAddedByUser;
	}
	//method that takes an arrayofamount and adds all of its elements
	public double addAllTheMonthsExpenses (ArrayList<Double> arrayOfamount) {
		double sum =0.0;
		for (int i=0; i<arrayOfamount.size(); i++) {
			sum += arrayOfamount.get(i);
		}
		return sum;
	}

	// calculates the balance income- expenses for the current month
	public double  processBalance () {
		balance=0.0;
		Calendar cal = Calendar.getInstance();
		String currentMonth = new SimpleDateFormat("MMM").format(cal.getTime());
		incomeEntered = incomeField.getText();
		Double incomeEnteredAsDouble = Double.parseDouble(incomeEntered);
		if (currentMonth.equals("Mar")) {
			MainMoneyControl retrieveAmount = new MainMoneyControl();
			ArrayList<Double> arrayOfamountMar16 = retrieveAmount.getTheAmountMar16();
			double amountSpentForCurrentMonth = addAllTheMonthsExpenses(arrayOfamountMar16);
			balance = incomeEnteredAsDouble- amountSpentForCurrentMonth;
			incomeEnteredAsDoubleMar = Double.parseDouble(incomeEntered);
		}
		if (currentMonth.equals("Apr")) {
			MainMoneyControl retrieveAmount = new MainMoneyControl();
			ArrayList<Double> arrayOfamountApr16 = retrieveAmount.getTheAmountApr16();
			double amountSpentForCurrentMonth = addAllTheMonthsExpenses(arrayOfamountApr16);
			balance = incomeEnteredAsDouble- amountSpentForCurrentMonth;
			incomeEnteredAsDoubleApr = Double.parseDouble(incomeEntered);
		}
		if (currentMonth.equals("May")) {
			MainMoneyControl retrieveAmount = new MainMoneyControl();
			ArrayList<Double> arrayOfamountMay16 = retrieveAmount.getTheAmountMay16();
			double amountSpentForCurrentMonth = addAllTheMonthsExpenses(arrayOfamountMay16);
			balance = incomeEnteredAsDouble- amountSpentForCurrentMonth;
			incomeEnteredAsDoubleMay = Double.parseDouble(incomeEntered);
		}
		if (currentMonth.equals("Jun")) {
			MainMoneyControl retrieveAmount = new MainMoneyControl();
			ArrayList<Double> arrayOfamountJun16 = retrieveAmount.getTheAmountJun16();
			double amountSpentForCurrentMonth = addAllTheMonthsExpenses(arrayOfamountJun16);
			balance = incomeEnteredAsDouble- amountSpentForCurrentMonth;
			incomeEnteredAsDoubleJun = Double.parseDouble(incomeEntered);
		}
		return balance;
	}
	

	//process the export function
	private static void processExport (File source, File dest) throws IOException {

		InputStream input = null;
		OutputStream output = null;

		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;

			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
			System.out.println("Expenses file is exported in location "+dest);
		}
	}

	//process the import function
	private void processImport () {

		Path source = Paths.get("C:\\eclipse-Indigo\\MyWorkspace\\MoneyControl\\Expenses Exported\\expenses.txt");
		Path dest = Paths.get("C:\\eclipse-Indigo\\MyWorkspace\\MoneyControl\\expenses.txt");
		try {
			Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("The expenses file is imported from "+source);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showHelp () {
		JFrame helpFrame = new JFrame();

		final String helpContent = "<html>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Welcome to Money Control" + "<br>" + "<br>" +"Here you can " +
				"add your expenses as they occur by entering the amount," +"<br>" +
				"the description and the date made. These three elements are saved in "+"<br>" +
				"a file of the application. To view this file press on Report. " +
				"They are also classified" +"<br>"+"by month. "+"You can add expenses occured between "+
				"two dates for example" +"<br>"+"01/05/2016-15/05/2016, just remember to add the hyphen - "+
				"symbol between" +"<br>"+"the two dates. You can Edit an expense or delete one you do not "+
				"need. "+"<br>"+"To edit an expense search first for it by amount or by description."+"<br>"+
				"Then select it, press on the Edit an Expense button, make any changes needed"+"<br>"+
				"and press Save. To delete it, select it and then press on the Delete an "+"<br>"+
				"expense button. The charts button shows you three types of charts, a pie chart, "+"<br>"+
				"a bar chart and a line chart. The first two charts are showing every month's " +
				"<br>"+"expenses, "+"the line chart shows all expenses by month so you can " +
				"see which" +"<br>"+" month you " +"have spent more. You can export your expenses " +
				"on a location of " +"<br>"+"your " +"computer and you can import them " +
				"back. So you can make any changes" +"<br>"+"on the file you have exported and " +
				"then by pressing import you can pass" +"<br>"+"these changes by importing " +
				"the expenses file. Finally, you can enter your income"+"<br>"+ "and one graph " +
				"will be displayed showing the balance (income-expenses made" +"<br>"+"for the "+
				"current month) " +
				"</html>";

		JLabel textLabel = new JLabel(helpContent);


		helpFrame.add(textLabel, BorderLayout.NORTH);

		helpFrame.setSize(550, 400);
		helpFrame.setVisible(true);
	}
	
		
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			if (incomeEntered != null) {
				WriteIncomeToFile();
			}
			System.exit(0);
		}
	}


}
