/**
 * EditExpenseWindow.java Created: 13 Nov 2020 Author: cousm
 */
package view.editgui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import service.EditExpenseService;
import view.maingui.AlertDialog;

/**
 * @author cousm Class that builds and shows the small edit expense window
 */
public class EditExpenseWindow implements ActionListener{
	private JTextPane editedLinePane;
	private JFrame editFrame;
	private JButton saveButton;
	private String selectedLine;
	private JTextPane lineSelectedToEdit;
	
	
	/**
	 * @param selectedLine
	 * @param lineSelectedToEdit 
	 */
	public EditExpenseWindow (String selectedLine, JTextPane lineSelectedToEdit) {
		super();
		this.selectedLine = selectedLine;
		this.lineSelectedToEdit = lineSelectedToEdit;
	}

	/**
	 * Start and show the small edit expense window
	 */
	protected void showEditExpenseWindow () {
		editedLinePane = new JTextPane();
		editedLinePane.setText(selectedLine);// set this line to the pane
		saveButton = new JButton("save");
		saveButton.addActionListener(this);
		editFrame = new JFrame();
		editFrame.setSize(250, 150);
		editFrame.setVisible(true);
		editFrame.add(editedLinePane, BorderLayout.NORTH);
		editFrame.add(saveButton, BorderLayout.SOUTH);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == saveButton) {
			EditExpenseService processEditExpense = new EditExpenseService(lineSelectedToEdit.getSelectedText(), editedLinePane.getText());
			processEditExpense.editAnExpense();
			AlertDialog alertDialog = new AlertDialog();
			alertDialog.informUser("You edited successfuly your expenses");
			editFrame.dispose();
		}
	}
}
