/**
 * Created: 26 Nov 2020
 * Author: cousm
 */
package view.maingui.action;

import static utils.Constants.GUIDE;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * @author cousm Class that handles the click on the Help button on the GUI
 *
 */
public class HelpAction extends AbstractAction implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 */
	public HelpAction () {
		super(GUIDE);
	}

	public void actionPerformed (ActionEvent arg0) {
		helpAction();
	}
	
	private void helpAction () {
		JFrame helpFrame = new JFrame();
		final String helpContent = "<html>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Welcome to Money Control" + "<br>" + "<br>" + "Here you can " + "add your expenses as they occur by entering the amount," + "<br>" + "the description and the date made. These three elements are saved in " + "<br>" + "a file of the application. To view this file press on Report. " + "They are also classified" + "<br>" + "by month. " + "You can add expenses occured between " + "two dates for example" + "<br>" + "01/05/2016-15/05/2016, just remember to add the hyphen - " + "symbol between" + "<br>" + "the two dates. You can Edit an expense or delete one you do not " + "need. " + "<br>" + "To edit an expense search first for it by amount or by description." + "<br>" + "Then select it, press on the Edit an Expense button, make any changes needed" + "<br>" + "and press Save. To delete it, select it and then press on the Delete an " + "<br>" + "expense button. The charts button shows you three types of charts, a pie chart, " + "<br>" + "a bar chart and a line chart. The first two charts are showing every month's " + "<br>" + "expenses, " + "the line chart shows all expenses by month so you can " + "see which" + "<br>" + " month you " + "have spent more. You can export your expenses " + "on a location of " + "<br>" + "your " + "computer and you can import them " + "back. So you can make any changes" + "<br>" + "on the file you have exported and " + "then by pressing import you can pass" + "<br>" + "these changes by importing " + "the expenses file. Finally, you can enter your income" + "<br>" + "and one graph " + "will be displayed showing the balance (income-expenses made" + "<br>" + "for the " + "current month) " + "</html>";
		JLabel textLabel = new JLabel(helpContent);
		helpFrame.add(textLabel, BorderLayout.NORTH);
		helpFrame.setSize(550, 400);
		helpFrame.setVisible(true);
	}
	
	
}
