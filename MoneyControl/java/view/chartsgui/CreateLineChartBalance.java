/**
 * ProcessLineChartBalance.java
 * Created: 13 Nov 2020
 * Author: cousm
 */
package view.chartsgui;

import static utils.Constants.MONTHS_LIST;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JComboBox;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import loaddata.LoadExpenses;
import utils.DataUtilities;
import utils.LoadProperties;
import view.maingui.AlertDialog;
/**
 * @author cousm Class that creates the Line Chart Balance
 *
 */
public class CreateLineChartBalance extends MoneyControlChart {
	private static final long serialVersionUID = 1L;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private String incomeEntered;
	private TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts;
	private JComboBox<String> yearSelection;
	private DataUtilities dataUtilities;

	/**
	 * @param chart
	 * @param chartPanel
	 * @param incomeEntered
	 * @param yearToMonthToDescriptionWithAmounts
	 * @param yearSelection
	 * @param loadExpenses 
	 */
	public CreateLineChartBalance (JFreeChart chart, ChartPanel chartPanel, String incomeEntered, TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts, JComboBox<String> yearSelection, LoadExpenses loadExpenses) {
		super(loadExpenses, incomeEntered);
		this.chart = chart;
		this.chartPanel = chartPanel;
		this.incomeEntered = incomeEntered;
		this.yearToMonthToDescriptionWithAmounts = yearToMonthToDescriptionWithAmounts;
		this.yearSelection = yearSelection;
		dataUtilities = new DataUtilities();
	}

	/**
	 * Shows the balance line Chart
	 */
	public void buildLineBalanceChart () {
		final CategoryDataset datasetLineBalance = createDatasetLineBalance();
		chart = createChartLineBalance(datasetLineBalance);
		chartPanel.setChart(chart);
		LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, false);// show the labels
																				// with the value on
																				// each bar
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(" " + "{1}, {2}", NumberFormat.getInstance()));
		chart.getCategoryPlot().setRenderer(renderer);
	}
	
	
	
	private CategoryDataset createDatasetLineBalance () {
		final DefaultCategoryDataset datasetLineBalance = new DefaultCategoryDataset();
		if (!incomeEntered.equals("")) {
			Double incomeDouble = Double.parseDouble(incomeEntered);
			final String series1 = "Months";
			Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
			String types [] = propertiesMap.get(MONTHS_LIST).split(",");
			for (int i = 0; i < types.length; i++) {
				String monthString = dataUtilities.convertMonthIntToMonthString(i);
				datasetLineBalance.addValue(incomeDouble - dataUtilities.sumAmountsOfMonths(dataUtilities.retrieveAmountsForSelectedMonthYearFromMap(monthString, yearSelection.getSelectedItem().toString(), yearToMonthToDescriptionWithAmounts)), series1, types[i]);
			}
		}
		else {
			AlertDialog alertDialog = new AlertDialog();
			alertDialog.informUserError("There is no income saved for a savings chart to be displayed. Enter first your income.");
		}
		return datasetLineBalance;
	}
	
	private JFreeChart createChartLineBalance (final CategoryDataset dataset) {
		// create the chart...
		final JFreeChart chart = ChartFactory.createLineChart("Savings Chart", // chart title
			"Type", // domain axis label
			"Value", // range axis label
			dataset, // data
			PlotOrientation.VERTICAL, // orientation
			true, // include legend
			true, // tooltips
			false // urls
		);
		return chart;
	}
}
