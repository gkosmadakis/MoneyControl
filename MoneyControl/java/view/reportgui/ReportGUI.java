/**
 * ReportGUI.java
 * Created: 11 Nov 2020
 * Author: cousm
 */
package view.reportgui;

import static utils.Constants.EXPENSES_REPORT;
import static utils.Constants.MONTH_DATA;
import static utils.Constants.TOTAL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import service.ReportService;
import utils.DataUtilities;
import utils.LoadProperties;
import view.maingui.AlertDialog;

/**
 * @author cousm Class that builds the Report GUI and  listens to changes of month or year
 *  
 */
public class ReportGUI {
	
	private JComboBox<String> monthSelection, yearSelection;
	private JTextPane reportArea;
	private StringBuilder allTheLines;
	private TreeSet<String> allTheYears;
	private DataUtilities dataUtilities;
	private ReportService reportService;
	
	/**
	 * @param allTheLines
	 * @param allTheYears
	 */
	public ReportGUI (StringBuilder allTheLines, TreeSet<String> allTheYears) {
		super();
		this.allTheLines = allTheLines;
		this.allTheYears = allTheYears;
		dataUtilities = new DataUtilities();
		reportService = new ReportService();
		setupDataForGUI(allTheLines, allTheYears);
	}

	/**
	 * @param allTheLines
	 * @param allTheYears
	 */
	private void setupDataForGUI (StringBuilder allTheLines, TreeSet<String> allTheYears) {
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		String monthData [] = propertiesMap.get(MONTH_DATA).split(",");
		monthSelection = new JComboBox<String>(monthData);
		
		String[] yearDataArray = dataUtilities.convertTreeSetToArray(allTheYears);
	    yearSelection = new JComboBox<String>(yearDataArray);
	    
	    changeReportArea(allTheLines);
	}

	/**
	 * Build Report GUI window
	 */
	public void buildReportArea () {
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();
		JFrame reportFrame = new JFrame();
		StringBuilder allTheLines = this.getAllTheLines();
		if (allTheLines.length() == 0) {
			AlertDialog alertDialog = new AlertDialog();
			alertDialog.informUserError("You have not saved any expenses, the file is empty. Save an expense first and try again");
		}
		else {
			JScrollPane scrollPane = createReportArea();
			
			createMonthYearPanels(northPanel);
			southPanel.add(scrollPane);
			
			addPanelsToReportFrame(northPanel, southPanel, reportFrame);	
		}
	}

	private JScrollPane createReportArea () {
		reportArea = new JTextPane();
		reportArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
		JScrollPane scrollPane = new JScrollPane(reportArea);
		scrollPane.setPreferredSize(new Dimension(480, 280));
		scrollPane.setViewportView(reportArea);
		return scrollPane;
	}

	private void createMonthYearPanels (JPanel northPanel) {
		JPanel yearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel monthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		monthPanel.setBackground(Color.WHITE);
		yearPanel.setBackground(Color.WHITE);
		
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		monthSelection.setSelectedIndex(month);
		
		monthPanel.add(monthSelection);
		yearPanel.add(yearSelection);
		
		northPanel.add(yearPanel);
		northPanel.add(monthPanel);
	}

	private void addPanelsToReportFrame (JPanel northPanel, JPanel southPanel, JFrame reportFrame) {
		reportFrame.add(northPanel, BorderLayout.NORTH);
		reportFrame.add(southPanel, BorderLayout.CENTER);
		reportFrame.pack();
		reportFrame.setSize(550, 400);
		reportFrame.setTitle(EXPENSES_REPORT);
		reportFrame.setVisible(true);
	}

	/**
	 * @param allTheLines
	 * Listen to changes of month or year and filter the expenses shown in the area accordingly
	 */
	public void changeReportArea (final StringBuilder allTheLines) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		yearSelection.setSelectedIndex(dataUtilities.findCurrentYearIndexInYearsArray(year, this.getAllTheYears()));
		monthSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				String monthSelected = (String) monthSelection.getSelectedItem();
				String yearSelected = (String) yearSelection.getSelectedItem();
				if (monthSelected.equals(TOTAL)) {
					reportArea.setText(dataUtilities.extractThreeColumnsFromAllTheLines(allTheLines.toString()));
				}
				else {
					try {
						cal.setTime(new SimpleDateFormat("MMM").parse(monthSelected));
					}
					catch (ParseException exception) {
						exception.printStackTrace();
					}
					int monthInt = cal.get(Calendar.MONTH) + 1;
					reportArea.setText(dataUtilities.extractThreeColumnsFromAllTheLines(reportService.filterFileForThisMonthAndYear(monthInt, allTheLines, yearSelected).toString()));
				}
			}
		});
	}
	
	/**
	 * @return allTheLines
	 */
	public StringBuilder getAllTheLines () {
		return allTheLines;
	}

	/**
	 * @return allTheYears
	 */
	public TreeSet<String> getAllTheYears () {
		return allTheYears;
	}
}
