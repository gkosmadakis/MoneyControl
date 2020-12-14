/**
 * CreateIncomeChart.java Created: 12 Nov 2020 Author: cousm
 */
package view.maingui;

import static utils.Constants.INCOME_FILE;

import java.awt.Color;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleInsets;

import service.IncomeFileService;
import utils.LoadProperties;

/**
 * @author cousm Class that creates the dataSet and the chart for the income chart
 */
public class CreateIncomeChart extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField incomeField;
	private String incomeFile;
	/**
	 * @param incomeField
	 */
	public CreateIncomeChart (final JTextField incomeField) {
		super();
		this.incomeField = incomeField;
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		incomeFile = propertiesMap.get(INCOME_FILE);
	}
	
	/**
	 * @return CategoryDataset
	 */
	protected CategoryDataset createCategoryDataset (double balance) {
		String incomeEntered = incomeField.getText();
		Double incomeEnteredAsDouble = 0.0;
		IncomeFileService incomeFileService = new IncomeFileService(incomeFile);
		if (incomeEntered.equals("")) {
			incomeEntered = incomeFileService.getIncomeEntered();
		}
		
		incomeEnteredAsDouble = Double.parseDouble(incomeEntered);
		double[][] data = new double[][] { { balance },
			{ (incomeEnteredAsDouble - balance) } };
		
		return DatasetUtilities.createCategoryDataset("", "", data);
	}
	
	protected JFreeChart createChart (final CategoryDataset dataset, double balance) {
		final JFreeChart chart = ChartFactory.createStackedBarChart("", // chart title
			"", // domain axis label
			"", // range axis label
			dataset, // data
			PlotOrientation.HORIZONTAL, // orientation
			false, // include legend
			false, // tooltips
			false // urls
		);
		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.getDomainAxis().setVisible(false);
		final StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
		plot.setInsets(new RectangleInsets(0, -4, 0, -4));// removes the white spaces right and left
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();// removes the X axis values
		rangeAxis.setVisible(false);
		renderer.setSeriesPaint(0, Color.green);// set the color of the bars
		renderer.setSeriesPaint(1, Color.red);
		if (balance < 0) {
			renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(1, Color.red);
		}
		return chart;
	}
	
	
}
