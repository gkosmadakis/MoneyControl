package view.editgui;
import static utils.Constants.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit;

import loaddata.LoadExpenses;
import service.DeleteExpenseService;
import service.SearchService;
import view.maingui.AlertDialog;
import view.maingui.UpdateBalance;

/**
 * Class to show the Edit an Expense GUI window and perform actions on its buttons
 *
 */
public class MoneyControlEdit extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JButton searchDescButton, searchAmountButton, clearButton, deleteButton, editButton;
	private JTextField descField, amountField;
	private JTextPane showResultsPane;
	private boolean expenseIsSelected;
	private Action selectLine;
	private AlertDialog alertDialog;
	private SearchService searchService;
	private UpdateBalance updateBalance;
	private LoadExpenses loadExpenses;
	
	/**
	 * Constructor
	 * @param updateBalance
	 * @param loadExpenses 
	 */
	public MoneyControlEdit (UpdateBalance updateBalance, LoadExpenses loadExpenses) {
		this.updateBalance = updateBalance;
		this.loadExpenses = loadExpenses;
		layoutGUI();
		showResultsPane = new JTextPane();
		getContentPane().add(new JTextPane());
		selectLine = getAction(DefaultEditorKit.selectLineAction);
		alertDialog = new AlertDialog();
		searchService = new SearchService();
	}
	
	private Action getAction (String name) {
		Action action = null;
		Action[] actions = showResultsPane.getActions();
		for (int i = 0; i < actions.length; i++) {
			if (name.equals(actions[i].getValue(Action.NAME).toString())) {
				action = actions[i];
				break;
			}
		}
		return action;
	}
	
	/**
	 * Show the Edit Expense GUI window
	 */
	public void layoutGUI () {
		JPanel panel = new JPanel(new GridLayout(5, 6));
		searchDescButton = new JButton(SEARCH_BY_DESCRIPTION);
		searchDescButton.addActionListener(this);
		descField = new JTextField();
		amountField = new JTextField();
		searchAmountButton = new JButton(SEARCH_BY_AMOUNT);
		searchAmountButton.addActionListener(this);
		clearButton = new JButton(CLEAR_RESULTS);
		clearButton.addActionListener(this);
		clearButton.setVisible(false);
		deleteButton = new JButton(DELETE_AN_EXPENSE);
		deleteButton.addActionListener(this);
		deleteButton.setVisible(false);
		editButton = new JButton(EDIT_AN_EXPENSE);
		editButton.addActionListener(this);
		editButton.setVisible(false);
		JButton emptyButton = new JButton();
		emptyButton.setVisible(false);
		String label = "\t";
		String resultslabel = label.replaceAll("\t", "                           ");
		JLabel resultsLabel = new JLabel(resultslabel + RESULTS);
		addButtonsToPanel(panel, emptyButton, resultsLabel);
	}

	/**
	 * @param panel
	 * @param emptyButton
	 * @param resultsLabel
	 */
	private void addButtonsToPanel (JPanel panel, JButton emptyButton, JLabel resultsLabel) {
		panel.add(searchDescButton);
		panel.add(descField);
		panel.add(searchAmountButton);
		panel.add(amountField);
		panel.add(clearButton);
		panel.add(deleteButton);
		panel.add(editButton);
		panel.add(emptyButton);
		panel.add(resultsLabel);
		add(panel, BorderLayout.NORTH);
	}
	
	@Override
	public void actionPerformed (ActionEvent e) {
		if(e.getSource() == searchDescButton) {
			initiateSearch(descField, DESCRIPTION);
		}
		if(e.getSource() == searchAmountButton) {
			initiateSearch(amountField, AMOUNT);
		}
		if(e.getSource() == editButton) {
			processEditButton();
		}
		if(e.getSource() == deleteButton) {
			processDeleteButton();
		}
		if(e.getSource() == clearButton) {
			processClearButton();
		}
	}
	
	private void initiateSearch (JTextField field, String buttonPressed) {
		try {
			showSearchResults(searchService.processSearch(field, buttonPressed));
			showHideButtons(true);
		}
		catch (Exception exception) {
			alertDialog.informUserError(exception.getMessage());
		}
	}
	
	private void processEditButton() {
		String selectedLine = showResultsPane.getSelectedText();// this is the line that is sent to the selectLinePane
		if (selectedLine == null) {
			alertDialog.informUserError("No expense is selected! Select first for an expense and try again to edit");
		}
		else {
			EditExpenseWindow editExpenseWindow = new EditExpenseWindow(selectedLine, showResultsPane);
			editExpenseWindow.showEditExpenseWindow();
		}
	}
	
	private void processDeleteButton () {
		// the user has not selected an expense to delete
		if (!expenseIsSelected) {
			alertDialog.informUserError("Nothing is deleted! Select first an expense.");
		}
		else {
			executeDeletion();
		}
	}
	
	private void executeDeletion () {
		 /* get the option from the OK cancel window, result is 0 if user presses OK and 2 if user
		presses cancel*/
		int result = alertDialog.showConfirmDialog("Do you really want to delete this expense?");
		// the user has pressed cancel
		if (result == 2) {
			alertDialog.informUser("The expense is not deleted");
		}
		else if(result == 0) {
			DeleteExpenseService deleteExpenseService = new DeleteExpenseService(showResultsPane.getSelectedText());
			deleteExpenseService.deleteAnExpense();
			alertDialog.informUser("You deleted successfuly the selected expense");
		}
	}
	
	private void processClearButton () {
		if (showResultsPane != null) {
			showResultsPane.setText("");
		}
		showHideButtons(false);
	}

	private void showHideButtons (boolean flag) {
		editButton.setVisible(flag);
		deleteButton.setVisible(flag);
		clearButton.setVisible(flag);
	}

	private void showSearchResults (String results) {
		showResultsPane = new JTextPane();
		showResultsPane.addMouseListener(this);
		showResultsPane.setText(results);
		showResultsPane.setFont(new Font(COURIER_FONT, Font.PLAIN, 14));
		add(showResultsPane, FlowLayout.CENTER);
	}
	
	@Override
	public void mouseClicked (MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
			selectLine.actionPerformed(null);
			expenseIsSelected = true;
		}
	}
	
	protected void processWindowEvent (WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			updateBalance.updateIncomeChart(loadExpenses.readTheFile());
			this.dispose();
		}
	}
	
	@Override
	public void mousePressed (MouseEvent e) {
	}
	
	@Override
	public void mouseReleased (MouseEvent e) {
	}
	
	@Override
	public void mouseEntered (MouseEvent e) {
	}
	
	@Override
	public void mouseExited (MouseEvent e) {
	}
}
