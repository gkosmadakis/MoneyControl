import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

	public class MoneyControlChart extends JFrame implements ActionListener{
		Map <String, Double> storedAmounts = new HashMap<String, Double>();
		private static LinkedHashSet<String>  arrayOfdescJan,arrayOfdescFeb,arrayOfdescMar,arrayOfdescApr,
		arrayOfdescMay,arrayOfdescJun,arrayOfdescJul,arrayOfdescAug,
		arrayOfdescSep,arrayOfdescOct,arrayOfdescNov,arrayOfdescDec, arrayOfdescJan16;
		private static ArrayList<Double> arrayOfamountJan,arrayOfamountFeb,arrayOfamountMar,arrayOfamountApr,
		arrayOfamountMay,arrayOfamountJun,arrayOfamountJul,arrayOfamountAug,arrayOfamountSep,
		arrayOfamountOct,arrayOfamountNov,arrayOfamountDec, arrayOfamountJan16;
		
		private double sumOfExpenses;
		private static final long serialVersionUID = 1L;
		private JFreeChart chart;
		private JRadioButton pieChart, barChart, lineChart,lineChartBalance;
		ButtonGroup group;
		private JPanel emptyPanel,radioPanel,mainPanel,topPanel, listPanel, bottomPanel;;
		private ChartPanel chartPanel;
		DefaultPieDataset result=null;
		DefaultCategoryDataset dataset=null;
		private JScrollPane listScroller;
		
		
		public MoneyControlChart (){
				
			layoutTop();
			
		}
		
		public void layoutTop() {
			mainPanel = new JPanel();//the main window
			topPanel = new JPanel();//the top panel that has the JList, the empty panel and the radiobuttons
			listPanel = new JPanel();//the panel with the JList
			listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
			listPanel.add(Box.createRigidArea(new Dimension(0,5)));
			bottomPanel = new JPanel();//the bottom panel that has the chartPanel
			chartPanel = new ChartPanel(chart);
			chartPanel.setPopupMenu(null);
			
			chartPanel.setPreferredSize(new java.awt.Dimension(680, 330));
			mainPanel.setPreferredSize(new java.awt.Dimension(830, 530));
			emptyPanel = new JPanel();
			radioPanel = new JPanel();
		
			String [] listData ={"Total Expenses","January","February","March","April","May","June",
					"July","August", "September", "October", "November", "December", "January 2016"};//contents of the JList
			JList<String> jList = new JList<String>(listData);
			final JList<String> list = jList;
			listScroller  = new JScrollPane(list);//add the list to the scrollPane
			//listScroller.setPreferredSize(new Dimension(110,60));//the size of the scrollbar
			list.setLayoutOrientation(JList.VERTICAL_WRAP);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listPanel.add(listScroller);//add the scrollPane with the list in the listPanel
			
			pieChart = new JRadioButton("Pie",  false);
			pieChart.setActionCommand("pieCommand");
			barChart = new JRadioButton("Bar", false);
			barChart.setActionCommand("barCommand");
			lineChart = new JRadioButton("Line", false);
			lineChart.setActionCommand("lineCommand");
			lineChartBalance = new JRadioButton("Balance", false);
			lineChartBalance.setActionCommand("lineCommandBalance");
			pieChart.addActionListener(this);
			barChart.addActionListener(this);
			lineChart.addActionListener(this);
			lineChartBalance.addActionListener(this);
			group = new ButtonGroup();
	        group.add(pieChart);
	        group.add(barChart);
	        group.add(lineChart);
	        group.add(lineChartBalance);
			emptyPanel.setPreferredSize(new Dimension(380,20));
			emptyPanel.setBackground(new Color(0,0,0,0));	
			pieChart.setPreferredSize(new Dimension(45,10));
			barChart.setPreferredSize(new Dimension(45,10));
		
			radioPanel.add(pieChart);
			radioPanel.add(barChart);
			radioPanel.add(lineChart);
			radioPanel.add(lineChartBalance);
			
			mainPanel.setLayout(new FlowLayout());
			topPanel.add(listPanel);
			topPanel.add(emptyPanel);
			topPanel.add(radioPanel);
			topPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2, true));
			bottomPanel.add(chartPanel);
			
			mainPanel.add(topPanel);
			mainPanel.add(bottomPanel);
			
			add(mainPanel);
			
			
			list.addListSelectionListener (new ListSelectionListener () {
    		   public void valueChanged (ListSelectionEvent e) {
    			   int selectedVar;
    			   selectedVar = list.getSelectedIndex();	   
			if ( pieChart.isSelected() ) {
			      // Month selections apply to the pie chart view
			      switch ( selectedVar  ) {
			         case 1:
			        	 result.clear();
			        	 chart.getTitle().setText("Expenses for January");
 		    	    	getTheAmountsForJan();
 		    	    	getTheDescriptionsForJan();
 		    	    	int i=0;
 		    		  for (Iterator<String> s = arrayOfdescJan.iterator();s.hasNext(); i++){
 		    			  String desc = s.next();
 		    			  result.setValue(desc, arrayOfamountJan.get(i)); 
 		    		  }
			            break;
			         case 2:
			        	  result.clear(); 
    		    		  chart.getTitle().setText("Expenses for February");
    		    		  getTheAmountsForFeb();
    		        	  getTheDescriptionsForFeb();
    		        	  int j=0;
    		    		  for (Iterator<String> s = arrayOfdescFeb.iterator();s.hasNext();j++){
    		    			  String desc = s.next();
    		    			  result.setValue(desc, arrayOfamountFeb.get(j)); 
   		    		  		}
			             break;
			         case 3:
			        	 result.clear();
			        	 chart.getTitle().setText("Expenses for March");
 		    	    	getTheAmountsForMar();
 		    	    	getTheDescriptionsForMar();
 		    	    	int k=0;
 		    		  for (Iterator<String> s = arrayOfdescMar.iterator();s.hasNext(); k++){
 		    			  String desc = s.next();
 		    			  result.setValue(desc, arrayOfamountMar.get(k)); 
 		    		  }
			            break;
			         case 4:
			        	  result.clear(); 
    		    		  chart.getTitle().setText("Expenses for April");
    		    		  getTheAmountsForApr();
    		        	  getTheDescriptionsForApr();
    		        	  int l=0;
    		    		  for (Iterator<String> s = arrayOfdescApr.iterator();s.hasNext();l++){
    		    			  String desc = s.next();
    		    			  result.setValue(desc, arrayOfamountApr.get(l)); 
   		    		  		}
			             break;
			         case 5:
			        	 result.clear();
			        	 chart.getTitle().setText("Expenses for May");
 		    	    	getTheAmountsForMay();
 		    	    	getTheDescriptionsForMay();
 		    	    	int m=0;
 		    		  for (Iterator<String> s = arrayOfdescMay.iterator();s.hasNext(); m++){
 		    			  String desc = s.next();
 		    			  result.setValue(desc, arrayOfamountMay.get(m)); 
 		    		  }
			            break;
			         case 6:
			        	  result.clear(); 
    		    		  chart.getTitle().setText("Expenses for June");
    		    		  getTheAmountsForJun();
    		        	  getTheDescriptionsForJun();
    		        	  int n=0;
    		    		  for (Iterator<String> s = arrayOfdescJun.iterator();s.hasNext();n++){
    		    			  String desc = s.next();
    		    			  result.setValue(desc, arrayOfamountJun.get(n)); 
   		    		  		}
			             break;
			         case 7:
			        	 result.clear();
			        	 chart.getTitle().setText("Expenses for July");
 		    	    	getTheAmountsForJul();
 		    	    	getTheDescriptionsForJul();
 		    	    	int p=0;
 		    		  for (Iterator<String> s = arrayOfdescJul.iterator();s.hasNext(); p++){
 		    			  String desc = s.next();
 		    			  result.setValue(desc, arrayOfamountJul.get(p)); 
 		    		  }
			            break;
			         case 8:
			        	  result.clear(); 
    		    		  chart.getTitle().setText("Expenses for August");
    		    		  getTheAmountsForAug();
    		        	  getTheDescriptionsForAug();
    		        	  int q=0;
    		    		  for (Iterator<String> s = arrayOfdescAug.iterator();s.hasNext();q++){
    		    			  String desc = s.next();
    		    			  result.setValue(desc, arrayOfamountAug.get(q)); 
   		    		  		}
    		    		 break;
			         case 9:
			        	 result.clear();
			        	 chart.getTitle().setText("Expenses for September");
 		    	    	getTheAmountsForSep();
 		    	    	getTheDescriptionsForSep();
 		    	    	int r=0;
 		    		  for (Iterator<String> s = arrayOfdescSep.iterator();s.hasNext(); r++){
 		    			  String desc = s.next();
 		    			  result.setValue(desc, arrayOfamountSep.get(r)); 
 		    		  }
			            break;
			         case 10:
			        	  result.clear(); 
    		    		  chart.getTitle().setText("Expenses for October");
    		    		  getTheAmountsForOct();
    		        	  getTheDescriptionsForOct();
    		        	  int t=0;
    		    		  for (Iterator<String> s = arrayOfdescOct.iterator();s.hasNext(); t++){
    		    			  String desc = s.next();
    		    			  result.setValue(desc, arrayOfamountOct.get(t)); 
   		    		  		}
			             break;
			         case 11:
			        	 result.clear();
			        	 chart.getTitle().setText("Expenses for November");
 		    	    	getTheAmountsForNov();
 		    	    	getTheDescriptionsForNov();
 		    	    	int u=0;
 		    		  for (Iterator<String> s = arrayOfdescNov.iterator();s.hasNext(); u++){
 		    			  String desc = s.next();
 		    			  result.setValue(desc, arrayOfamountNov.get(u)); 
 		    		  }
			            break;
			         case 12:
			        	  result.clear(); 
    		    		  chart.getTitle().setText("Expenses for December");
    		    		  getTheAmountsForDec();
    		        	  getTheDescriptionsForDec();
    		        	  int x=0;
    		    		  for (Iterator<String> s = arrayOfdescDec.iterator();s.hasNext();x++){
    		    			  String desc = s.next();
    		    			  result.setValue(desc, arrayOfamountDec.get(x)); 
   		    		  		}
			             break;
			         case 13:
			        	 result.clear();
			        	 chart.getTitle().setText("Expenses for January 2016");
 		    	    	getTheAmountsForJan16();
 		    	    	getTheDescriptionsForJan16();
 		    	    	int h=0;
 		    		  for (Iterator<String> s = arrayOfdescJan16.iterator();s.hasNext(); h++){
 		    			  String desc = s.next();
 		    			  result.setValue(desc, arrayOfamountJan16.get(h)); 
 		    		  }
			            break;
			         default:
			        	 result.clear();
			        	
			        	 chart.getTitle().setText("Total Expenses Pie");
   		    		  for (Map.Entry<String, Double> entry : storedAmounts.entrySet()){
   		    			  //i want to pass only the values that are >0, otherwise the expenses 
   		    			  //	that the user has added
   		    		       	 if (entry.getValue()>0){ 
   		    		      result.setValue(entry.getKey(), entry.getValue()/sumOfExpenses);//add getkey=description-getvalue=amount in the result set
   		    		       		  }
   		    		       	  	}
			            break;	
			      		}
					}
			else if (barChart.isSelected()) {
				 
				final Comparable<String> category1 = "Expenses";
				 switch ( selectedVar ) {
				  case 1:
			        	 dataset.clear();
			        	 chart.getTitle().setText("Expenses for January");
		    	    	getTheAmountsForJan();
		    	    	getTheDescriptionsForJan();
		    	    	int i=0;
		    		  for (Iterator<String> s = arrayOfdescJan.iterator();s.hasNext(); i++){
		    			  String desc = s.next();
		    			  dataset.setValue( arrayOfamountJan.get(i),desc, category1); 
		    		  }
			            break;
			         case 2:
			        	 dataset.clear(); 
 		    		  chart.getTitle().setText("Expenses for February");
 		    		  getTheAmountsForFeb();
 		        	  getTheDescriptionsForFeb();
 		        	  int j=0;
 		    		  for (Iterator<String> s = arrayOfdescFeb.iterator();s.hasNext();j++){
 		    			  String desc = s.next();
 		    			  dataset.setValue( arrayOfamountFeb.get(j),desc,category1); 
		    		  		}
			             break;
			         case 3:
			        	 dataset.clear();
			        	 chart.getTitle().setText("Expenses for March");
		    	    	getTheAmountsForMar();
		    	    	getTheDescriptionsForMar();
		    	    	int k=0;
		    		  for (Iterator<String> s = arrayOfdescMar.iterator();s.hasNext(); k++){
		    			  String desc = s.next();
		    			  dataset.setValue( arrayOfamountMar.get(k),desc, category1); 
		    		  }
			            break;
			         case 4:
			        	 dataset.clear(); 
 		    		  chart.getTitle().setText("Expenses for April");
 		    		  getTheAmountsForApr();
 		        	  getTheDescriptionsForApr();
 		        	  int l=0;
 		    		  for (Iterator<String> s = arrayOfdescApr.iterator();s.hasNext();l++){
 		    			  String desc = s.next();
 		    			  dataset.setValue( arrayOfamountApr.get(l),desc,category1); 
		    		  		}
			             break;
			         case 5:
			        	 dataset.clear();
			        	 chart.getTitle().setText("Expenses for May");
		    	    	getTheAmountsForMay();
		    	    	getTheDescriptionsForMay();
		    	    	int m=0;
		    		  for (Iterator<String> s = arrayOfdescMay.iterator();s.hasNext(); m++){
		    			  String desc = s.next();
		    			  dataset.setValue( arrayOfamountMay.get(m),desc, category1); 
		    		  }
			            break;
			         case 6:
			        	 dataset.clear(); 
 		    		  chart.getTitle().setText("Expenses for June");
 		    		  getTheAmountsForJun();
 		        	  getTheDescriptionsForJun();
 		        	  int n=0;
 		    		  for (Iterator<String> s = arrayOfdescJun.iterator();s.hasNext();n++){
 		    			  String desc = s.next();
 		    			  dataset.setValue( arrayOfamountJun.get(n),desc, category1); 
		    		  		}
			             break;
			         case 7:
			        	 dataset.clear();
			        	 chart.getTitle().setText("Expenses for July");
		    	    	getTheAmountsForJul();
		    	    	getTheDescriptionsForJul();
		    	    	int p=0;
		    		  for (Iterator<String> s = arrayOfdescJul.iterator();s.hasNext(); p++){
		    			  String desc = s.next();
		    			  dataset.setValue( arrayOfamountJul.get(p), desc, category1); 
		    		  }
			            break;
			         case 8:
			        	 dataset.clear(); 
 		    		  chart.getTitle().setText("Expenses for August");
 		    		  getTheAmountsForAug();
 		        	  getTheDescriptionsForAug();
 		        	  int q=0;
 		    		  for (Iterator<String> s = arrayOfdescAug.iterator();s.hasNext();q++){
 		    			  String desc = s.next();
 		    			  dataset.setValue( arrayOfamountApr.get(q), desc, category1); 
		    		  		}
 		    		    break;
			         case 9:
			        	 dataset.clear();
			        	 chart.getTitle().setText("Expenses for September");
		    	    	getTheAmountsForSep();
		    	    	getTheDescriptionsForSep();
		    	    	int r=0;
		    		  for (Iterator<String> s = arrayOfdescSep.iterator();s.hasNext(); r++){
		    			  String desc = s.next();
		    			  dataset.setValue( arrayOfamountSep.get(r), desc, category1); 
		    		  }
			            break;
			         case 10:
			        	 dataset.clear(); 
 		    		  chart.getTitle().setText("Expenses for October");
 		    		  getTheAmountsForOct();
 		        	  getTheDescriptionsForOct();
 		        	  int t=0;
 		    		  for (Iterator<String> s = arrayOfdescOct.iterator();s.hasNext(); t++){
 		    			  String desc = s.next();
 		    			  dataset.setValue( arrayOfamountOct.get(t),desc, category1); 
		    		  		}
			             break;
			         case 11:
			        	 dataset.clear();
			        	 chart.getTitle().setText("Expenses for November");
		    	    	getTheAmountsForNov();
		    	    	getTheDescriptionsForNov();
		    	    	int u=0;
		    		  for (Iterator<String> s = arrayOfdescNov.iterator();s.hasNext(); u++){
		    			  String desc = s.next();
		    			  dataset.setValue( arrayOfamountNov.get(u), desc, category1); 
		    		  }
			            break;
			         case 12:
			        	 dataset.clear(); 
 		    		  chart.getTitle().setText("Expenses for December");
 		    		  getTheAmountsForDec();
 		        	  getTheDescriptionsForDec();
 		        	  int x=0;
 		    		  for (Iterator<String> s = arrayOfdescDec.iterator();s.hasNext();x++){
 		    			  String desc = s.next();
 		    			  dataset.setValue( arrayOfamountDec.get(x), desc, category1); 
		    		  		}
			             break;
			         case 13:
			        	 dataset.clear();
			        	 chart.getTitle().setText("Expenses for January 2016");
		    	    	getTheAmountsForJan16();
		    	    	getTheDescriptionsForJan16();
		    	    	int h=0;
		    		  for (Iterator<String> s = arrayOfdescJan16.iterator();s.hasNext(); h++){
		    			  String desc = s.next();
		    			  dataset.setValue( arrayOfamountJan16.get(h),desc, category1); 
		    		  }
			            break;
		         default:
		        
		        	 dataset.clear();
						chart.getTitle().setText("Total Expenses Bar");
						for (Map.Entry<String, Double> entry : storedAmounts.entrySet()){	
							if (entry.getValue()>0) {
								dataset.addValue(entry.getValue(), entry.getKey(), category1);
							}
						}	
		        	 break;
				 		}
					}
    		    } 
			});
		}// end of layoutTop
		
		  @Override
		    public void actionPerformed (ActionEvent e) {
			 
		    	 if (e.getActionCommand().equals("pieCommand")) {
		    		chartPanel.revalidate();
		    		buildPieChart("Pie Chart Test","Total Expenses Pie");
		    		pieChart.setSelected(true);
		    	}  
		    	 else if (e.getActionCommand().equals("barCommand")){
		    		chartPanel.revalidate();
		    		buildBarChart();
		    		barChart.setSelected(true);	
		    	}
		    	 else if (e.getActionCommand().equals("lineCommand")){
			    	chartPanel.revalidate();
			    	buildLineChart();
			    	lineChart.setSelected(true);	
			    	}
		    	 else if (e.getActionCommand().equals("lineCommandBalance")){
		    		 chartPanel.revalidate();
		    		 buildLineBalanceChart();
		    		 lineChartBalance.setSelected(true);
		    	 }
		    }
		  
		  public void buildPieChart(String appTitle, String chartTitle){
				
			  	PieDataset dataset = createDatasetPie();
				chart = createChartPie(dataset, chartTitle);
				//layoutTop();
				chartPanel.setChart(chart);
				Plot plot = chart.getPlot();//show the percentages as labels on every pie
				((PiePlot) plot).setLabelGenerator(new StandardPieSectionLabelGenerator("{2}"));
			}
		
		  
      private PieDataset createDatasetPie(){
    	  getTheStoredAmounts();//pass the stored Amounts from the main class
    	  sumTheExpenses();
    	  
    	  result = new DefaultPieDataset();
	       	 for (Map.Entry<String, Double> entry : storedAmounts.entrySet()){
	       		 //System.err.println(e.getKey() + ": " + e.getValue());
	       		 //i want to pass only the values that are >0, otherwise the expenses that the user has added
	       		  if (entry.getValue()>0){ 
	       	  result.setValue(entry.getKey(), entry.getValue()/sumOfExpenses);//add getkey=description-getvalue=amount in the result set
	       		  }
	       	  	}
    	  	return result;  
      	}
    	  
        private JFreeChart createChartPie(PieDataset dataset, String title){
        	
        	chart = ChartFactory.createPieChart3D(title, dataset, true, false, true);
        	PiePlot3D plot = (PiePlot3D) chart.getPlot();
        	plot.setStartAngle(0);
        	plot.setDirection(Rotation.CLOCKWISE);
        	plot.setForegroundAlpha(0.5f);
        	return chart;
        }
    
	
		public void buildBarChart() {

			final CategoryDataset dataset = createDatasetBar();
			chart = createChartBar(dataset);
			
			//layoutTop();
			chartPanel.setChart(chart);
			BarRenderer renderer = new BarRenderer();//show the labels with the value on each bar
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			chart.getCategoryPlot().setRenderer(renderer);
		}
		
		 private CategoryDataset createDatasetBar() {
				getTheStoredAmounts();
				dataset = new DefaultCategoryDataset();
				final Comparable<String> category1 = "Expenses";
				
				for (Map.Entry<String, Double> entry : storedAmounts.entrySet()){
					if (entry.getValue()>0) {
						dataset.addValue(entry.getValue(), entry.getKey(), category1);
					}
				}
			return dataset;
		}
		 
		 public void buildLineChart() {
			 final CategoryDataset datasetLine = createDatasetLine();
			 chart = createChartLine(datasetLine);
			 
			 chartPanel.setChart(chart);
			 
			 LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, false);//show the labels with the value on each bar
			 renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(" " +
			 		"{1}, {2}", NumberFormat.getInstance()));
			 chart.getCategoryPlot().setRenderer(renderer);
		 }
		 
		 public void buildLineBalanceChart () {
			
			 final CategoryDataset datasetLineBalance = createDatasetLineBalance();
			 chart = createChartLineBalance(datasetLineBalance);

			 chartPanel.setChart(chart);
			 
			 LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, false);//show the labels with the value on each bar
			 renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(" " +
			 		"{1}, {2}", NumberFormat.getInstance()));
			 chart.getCategoryPlot().setRenderer(renderer);
			
		 }

		private JFreeChart createChartBar(final CategoryDataset dataset) {

			// create the chart...
			chart = ChartFactory.createBarChart(
					"Total Expenses Bar",  	  // chart title
					"Category",               // domain axis label
					"Amount",                 // range axis label
					dataset,                  // data
					PlotOrientation.VERTICAL, // orientation
					true,                     // include legend
					true,                     // tooltips?
					false                     // URLs?
					);
			
			return chart;
		}
		
		private CategoryDataset createDatasetLine() {
			getTheAmountsForJan();
			getTheAmountsForFeb();
			getTheAmountsForMar();
			getTheAmountsForApr();
			getTheAmountsForMay();
			getTheAmountsForJun();
			getTheAmountsForJul();
			getTheAmountsForAug();
			getTheAmountsForSep();
			getTheAmountsForOct();
			getTheAmountsForNov();
			getTheAmountsForDec();
			getTheAmountsForJan16();
			// column keys...
			 final String series1 = "Months";
			 
		        final String type1 = "January";
		        final String type2 = "February";
		        final String type3 = "March";
		        final String type4 = "April";
		        final String type5 = "May";
		        final String type6 = "June";
		        final String type7 = "July";
		        final String type8 = "August";
		        final String type9 = "September";
		        final String type10 = "October";
		        final String type11 = "November";
		        final String type12 = "December";
		        final String type13 = "January 2016";
		        
		        final DefaultCategoryDataset datasetLine = new DefaultCategoryDataset();
		        
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountJan), series1, type1);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountFeb), series1, type2);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountMar), series1, type3);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountApr), series1, type4);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountMay), series1, type5);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountJun), series1, type6);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountJul), series1, type7);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountAug), series1, type8);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountSep), series1, type9);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountOct), series1, type10);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountNov), series1, type11);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountDec), series1, type12);
		        datasetLine.addValue(sumAmountsOfMonths(arrayOfamountJan16), series1, type13);
		        
		        return datasetLine;
		}
		
		private CategoryDataset createDatasetLineBalance () {
			getTheAmountsForJan();
			getTheAmountsForFeb();
			getTheAmountsForMar();
			getTheAmountsForApr();
			getTheAmountsForMay();
			getTheAmountsForJun();
			getTheAmountsForJul();
			getTheAmountsForAug();
			getTheAmountsForSep();
			getTheAmountsForOct();
			getTheAmountsForNov();
			getTheAmountsForDec();
			getTheAmountsForJan16();
			
			final String series1 = "Months";
		    final String type1 = "January";
	        final String type2 = "February";
	        final String type3 = "March";
	        final String type4 = "April";
	        final String type5 = "May";
	        final String type6 = "June";
	        final String type7 = "July";
	        final String type8 = "August";
	        final String type9 = "September";
	        final String type10 = "October";
	        final String type11 = "November";
	        final String type12 = "December";
	        final String type13 = "January 2016";
	        
			final DefaultCategoryDataset datasetLineBalance = new DefaultCategoryDataset();
			
			String income = MoneyControlGUI.getTheIncome();
			Double incomeOfMar = MoneyControlGUI.getIncomeMar();
			Double incomeOfApr = MoneyControlGUI.getIncomeApr();
			Double incomeOfMay = MoneyControlGUI.getIncomeMay();
			
			if (!income.isEmpty()) {
				Double incomeDouble = Double.parseDouble(income);
				
				datasetLineBalance.addValue(incomeDouble- sumAmountsOfMonths(arrayOfamountJan), series1, type1);
				datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountFeb), series1, type2);
				if (incomeOfMar != null){
					datasetLineBalance.addValue(incomeOfMar - sumAmountsOfMonths(arrayOfamountMar), series1, type3);
				}
				else {
					datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountMar), series1, type3);
				}
				if (incomeOfApr != null) {
					datasetLineBalance.addValue(incomeOfApr - sumAmountsOfMonths(arrayOfamountApr), series1, type4);
				}
				else {
					datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountApr), series1, type4);
				}
				if (incomeOfMay != null) {
					datasetLineBalance.addValue(incomeOfMay - sumAmountsOfMonths(arrayOfamountMay), series1, type5);
				}
				else {
					datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountMay), series1, type5);
				}
				datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountJun), series1, type6);
				datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountJul), series1, type7);
				datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountAug), series1, type8);
				datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountSep), series1, type9);
				datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountOct), series1, type10);
				datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountNov), series1, type11);
				datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountDec), series1, type12);
				datasetLineBalance.addValue(incomeDouble - sumAmountsOfMonths(arrayOfamountJan16), series1, type13);
			}
			else {
				JOptionPane.showMessageDialog(null, "There is no income saved for a savings chart to be displayed. Enter first your income.", 
						"Result Summary", JOptionPane.ERROR_MESSAGE);
			}
			return datasetLineBalance;
		}
		
		 private JFreeChart createChartLine(final CategoryDataset dataset) {
		        
		        // create the chart...
		        final JFreeChart chart = ChartFactory.createLineChart(
		            "Month Expenses",          // chart title
		            "Type",                    // domain axis label
		            "Value",                   // range axis label
		            dataset,                   // data
		            PlotOrientation.VERTICAL,  // orientation
		            true,                      // include legend
		            true,                      // tooltips
		            false                      // urls
		        );
		     
		        return chart;
		 }
		 
		 
		private JFreeChart createChartLineBalance (final CategoryDataset dataset) {
		        
		        // create the chart...
		        final JFreeChart chart = ChartFactory.createLineChart(
		            "Savings Chart",           // chart title
		            "Type",                    // domain axis label
		            "Value",                   // range axis label
		            dataset,                   // data
		            PlotOrientation.VERTICAL,  // orientation
		            true,                      // include legend
		            true,                      // tooltips
		            false                      // urls
		        );
		      
		
		        return chart;
		 }
		
		public double  sumAmountsOfMonths (ArrayList<Double> arrayOfamount) {
			
			int i;
			double sum = 0;
			for(i = 1; i < arrayOfamount.size(); i++){
			    sum += arrayOfamount.get(i);
			}
			
			return sum;
			
		}

		public Map <String, Double> getTheStoredAmounts (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			storedAmounts = amounts.getStoreAmounts();
			 
			 return (Map<String, Double>) storedAmounts;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForJan (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescJan = amounts.getThedescJan();
			 
			 return  arrayOfdescJan;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForFeb (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescFeb= amounts.getThedescFeb();
			 
			 return  arrayOfdescFeb;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForMar (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescMar= amounts.getThedescMar();
			 
			 return  arrayOfdescMar;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForApr (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescApr = amounts.getThedescApr();
			 
			 return  arrayOfdescApr;
		}
		
		
		public LinkedHashSet<String> getTheDescriptionsForMay (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescMay = amounts.getThedescMay();
			 
			 return  arrayOfdescMay;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForJun (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescJun = amounts.getThedescJun();
			 
			 return  arrayOfdescJun;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForJul (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescJul = amounts.getThedescJul();
			 
			 return  arrayOfdescJul;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForAug (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescAug = amounts.getThedescAug();
			 
			 return  arrayOfdescAug;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForSep (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescSep = amounts.getThedescSep();
			 
			 return  arrayOfdescSep;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForOct (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescOct = amounts.getThedescOct();
			 
			 return  arrayOfdescOct;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForNov (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescNov = amounts.getThedescNov();
			 
			 return  arrayOfdescNov;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForDec (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescDec = amounts.getThedescDec();
			 
			 return  arrayOfdescDec;
		}
		
		public LinkedHashSet<String> getTheDescriptionsForJan16 (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfdescJan16 = amounts.getThedescJan16();
			 
			 return  arrayOfdescJan16;
		}
		
		
		public ArrayList<Double> getTheAmountsForJan (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountJan = amounts.getTheAmountJan();
			 
			 return  arrayOfamountJan;
		}
		
		public ArrayList<Double> getTheAmountsForFeb (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountFeb = amounts.getTheAmountFeb();
			 
			 return  arrayOfamountFeb;
		}
		
		public ArrayList<Double> getTheAmountsForMar (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountMar = amounts.getTheAmountMar();
			 
			 return  arrayOfamountMar;
		}
		
		public ArrayList<Double> getTheAmountsForApr (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountApr = amounts.getTheAmountApr();
			 
			 return  arrayOfamountApr;
		}
		
		public ArrayList<Double> getTheAmountsForMay (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountMay = amounts.getTheAmountMay();
			 
			 return  arrayOfamountMay;
		}
		
		public ArrayList<Double> getTheAmountsForJun (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountJun = amounts.getTheAmountJun();
			 
			 return  arrayOfamountJun;
		}
		
		public ArrayList<Double> getTheAmountsForJul (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountJul = amounts.getTheAmountJul();
			 
			 return  arrayOfamountJul;
		}
		
		public ArrayList<Double> getTheAmountsForAug (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountAug = amounts.getTheAmountAug();
			 
			 return  arrayOfamountAug;
		}
		
		public ArrayList<Double> getTheAmountsForSep (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountSep = amounts.getTheAmountSep();
			 
			 return  arrayOfamountSep;
		}
		
		public ArrayList<Double> getTheAmountsForOct (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountOct = amounts.getTheAmountOct();
			 
			 return  arrayOfamountOct;
		}
		
		public ArrayList<Double> getTheAmountsForNov (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountNov = amounts.getTheAmountNov();
			 
			 return  arrayOfamountNov;
		}
		
		public ArrayList<Double> getTheAmountsForDec (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountDec = amounts.getTheAmountDec();
			 
			 return  arrayOfamountDec;
		}
		
		public ArrayList<Double> getTheAmountsForJan16 (){
			MainMoneyControl amounts = new MainMoneyControl();//get the store amounts from main class
			arrayOfamountJan16 = amounts.getTheAmountJan16();
			 
			 return  arrayOfamountJan16;
		}
			
		public double sumTheExpenses(){
				
			sumOfExpenses = 0;
			for (Map.Entry<String, Double> e : storedAmounts.entrySet()){
				sumOfExpenses += e.getValue();
			}
			return sumOfExpenses;
			}
		
		
		
		
		

}