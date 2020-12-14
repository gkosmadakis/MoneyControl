/**
 * ProcessLineChart.java
 * Created: 13 Nov 2020
 * Author: cousm
 */
package view.chartsgui;

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
import static utils.Constants.*;

/**
 * @author cousm Class that creates the line chart
 * 
 */
public class CreateLineChart extends MoneyControlChart {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts;
	private DataUtilities dataUtilities;
	
	/**
	 * @param chart
	 * @param chartPanel
	 * @param yearToMonthToDescriptionWithAmounts 
	 * @param loadExpenses 
	 * @param incomeEntered 
	 */
	public CreateLineChart (JFreeChart chart, ChartPanel chartPanel, TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts, LoadExpenses loadExpenses, String incomeEntered) {
		super(loadExpenses, incomeEntered);
		this.chart = chart;
		this.chartPanel = chartPanel;
		this.yearToMonthToDescriptionWithAmounts = yearToMonthToDescriptionWithAmounts;
		dataUtilities = new DataUtilities();
	}

	/**
	 * @param yearSelection
	 */
	public void buildLineChart (JComboBox<String> yearSelection) {
		final CategoryDataset datasetLine = createDatasetLine(yearSelection);
		chart = createChartLine(datasetLine);
		chartPanel.setChart(chart);
		LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, false);// show the labels
																				// with the value on
																				// each bar
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(" " + "{1}, {2}", NumberFormat.getInstance()));
		chart.getCategoryPlot().setRenderer(renderer);
	}
	
	private CategoryDataset createDatasetLine (JComboBox<String> yearSelection) {
		final DefaultCategoryDataset datasetLine = new DefaultCategoryDataset();
		final String series1 = "Months";
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		String types [] = propertiesMap.get(MONTHS_LIST).split(",");
		for (int i = 0; i < types.length; i++) {
			String monthString = dataUtilities.convertMonthIntToMonthString(i);
			datasetLine.addValue(dataUtilities.sumAmountsOfMonths(dataUtilities.retrieveAmountsForSelectedMonthYearFromMap(monthString, yearSelection.getSelectedItem().toString(), yearToMonthToDescriptionWithAmounts)), series1, types[i]);
		}
		return datasetLine;
	}
	
	private JFreeChart createChartLine (final CategoryDataset dataset) {
		// create the chart...
		final JFreeChart chart = ChartFactory.createLineChart("Month Expenses", // chart title
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
