/**
 * ProcessBarChart.java
 * Created: 13 Nov 2020
 * Author: cousm
 */
package view.chartsgui;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import loaddata.LoadExpenses;
import service.PieBarChartService;
import utils.DataUtilities;

/**
 * @author cousm Class that creates the Bar Chart
 *
 */
public class CreateBarChart extends MoneyControlChart {
	private static final long serialVersionUID = 1L;
	private JFreeChart chart;
	private DefaultCategoryDataset dataset = null;
	private ChartPanel chartPanel;
	private TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts;
	private DataUtilities dataUtilities;
	private PieBarChartService pieBarChartService;
	

	/**
	 * @param loadExpenses
	 * @param incomeEntered
	 * @param chart
	 * @param dataset
	 * @param chartPanel
	 * @param yearToMonthToDescriptionWithAmounts
	 */
	public CreateBarChart (JFreeChart chart, DefaultCategoryDataset dataset, ChartPanel chartPanel, TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts,LoadExpenses loadExpenses, String incomeEntered) {
		super(loadExpenses, incomeEntered);
		this.chart = chart;
		this.dataset = dataset;
		this.chartPanel = chartPanel;
		this.yearToMonthToDescriptionWithAmounts = yearToMonthToDescriptionWithAmounts;
		dataUtilities = new DataUtilities();
		pieBarChartService = new PieBarChartService();
	}

	/**
	 * Builds and shows the Bar Chart
	 */
	public void buildBarChart () {
		dataset = new DefaultCategoryDataset();
		chart = createChartBar(dataset);
		
		chartPanel.setChart(chart);
		BarRenderer renderer = new BarRenderer();// show the labels with the value on each bar
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		chart.getCategoryPlot().setRenderer(renderer);
	}
	
	private JFreeChart createChartBar (final CategoryDataset dataset) {
		// create the chart...
		chart = ChartFactory.createBarChart("Total Expenses Bar", // chart title
			"Category", // domain axis label
			"Amount", // range axis label
			dataset, // data
			PlotOrientation.VERTICAL, // orientation
			true, // include legend
			true, // tooltips?
			false // URLs?
		);
		return chart;
	}
	
	/**
	 * @param monthTitle
	 * @param month
	 * @param yearSelection
	 * @return dataSet
	 */
	public DefaultCategoryDataset showBarChart (String monthTitle, String month, String yearSelection) {
		dataset.clear();
		chart.getTitle().setText("Expenses for " + monthTitle + " " + yearSelection);
		pieBarChartService.populateDataset(month, yearSelection, dataset, yearToMonthToDescriptionWithAmounts, dataUtilities);
		
		return dataset;
	}

	
}
