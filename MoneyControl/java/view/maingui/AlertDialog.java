/**
 * AlertDialog.java
 * Created: 27 Nov 2020
 * Author: cousm
 */
package view.maingui;

import javax.swing.JOptionPane;

/**
 * @author cousm Class that extends JOptionPane to show error and information messages to the user
 *
 */
public class AlertDialog extends JOptionPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param errorMessage
	 */
	public void informUserError(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage, "Result Summary", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * @param header 
	 * @param message
	 * @param datesFromTo
	 * @param sum
	 */
	public void informUser(String header, String message, String datesFromTo, double sum) {
		JOptionPane.showMessageDialog(null, header + datesFromTo + "." + message + sum, "Total Expenses", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * @param message
	 */
	public void informUser(String message) {
		JOptionPane.showMessageDialog(null, message, "Result Summary", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * @param message
	 * @return result
	 */
	public int showConfirmDialog(String message) {
		return JOptionPane.showConfirmDialog(null, message, "Result Summary", JOptionPane.OK_CANCEL_OPTION);
	}
}
