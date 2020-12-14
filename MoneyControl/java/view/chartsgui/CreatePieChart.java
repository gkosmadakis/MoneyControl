/**
 * ProcessPieChart.java
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
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import loaddata.LoadExpenses;
import service.PieBarChartService;
import utils.DataUtilities;

/**
 * @author cousm Class that creates the Pie chart 
 *
 */
public class CreatePieChart extends MoneyControlChart {
	private static final long serialVersionUID = 1L;
	private JFreeChart chart;
	private DefaultPieDataset dataset = null;
	private ChartPanel chartPanel;
	private TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts;
	private DataUtilities dataUtilities;
	private PieBarChartService pieBarChartService;
	

	/**
	 * @param loadExpenses
	 * @param incomeEntered
	 * @param chart
	 * @param result
	 * @param chartPanel
	 * @param yearToMonthToDescriptionWithAmounts
	 */
	public CreatePieChart (JFreeChart chart, DefaultPieDataset result, ChartPanel chartPanel, TreeMap<String, TreeMap<String, Map<String, List<Float>>>> yearToMonthToDescriptionWithAmounts,LoadExpenses loadExpenses, String incomeEntered) {
		super(loadExpenses, incomeEntered);
		this.chart = chart;
		this.dataset = result;
		this.chartPanel = chartPanel;
		this.yearToMonthToDescriptionWithAmounts = yearToMonthToDescriptionWithAmounts;
		dataUtilities = new DataUtilities();
		pieBarChartService = new PieBarChartService();
	}

	/**
	 * @param appTitle
	 * @param chartTitle
	 */
	public void buildPieChart (String appTitle, String chartTitle) {
		dataset = new DefaultPieDataset();
		chart = createChartPie(dataset, chartTitle);
		chartPanel.setChart(chart);
		Plot plot = chart.getPlot();// show the percentages as labels on every pie
		((PiePlot) plot).setLabelGenerator(new StandardPieSectionLabelGenerator("{2}"));
	}
	
	private JFreeChart createChartPie (PieDataset dataset, String title) {
		chart = ChartFactory.createPieChart3D(title, dataset, true, false, true);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(0);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
	
	/**
	 * @param monthForTitle
	 * @param month
	 * @param yearSelection
	 * @return result
	 */
	public DefaultPieDataset showPieChart (String monthForTitle, String month, String yearSelection) {
		dataset.clear();
		chart.getTitle().setText("Expenses for " + monthForTitle + " " + yearSelection);
		
		pieBarChartService.populateDataset(month, yearSelection, dataset, yearToMonthToDescriptionWithAmounts, dataUtilities);
		
		return dataset;
	}
	
	
}
