/**
 * Validations.java
 * Created: 4 Dec 2020
 * Author: cousm
 */
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * @author cousm Utility Class that has a method to validate fields
 *
 */
public class Validations {

	/**
	 * @param datesFromTo
	 * @throws Exception
	 */
	public void validateFields (String datesFromTo) throws Exception {
		String[] token = datesFromTo.split("-");
		if (datesFromTo.equals("") || datesFromTo.equals(null)) {
			throw new Exception("Add expenses field is empty. Please supply a date range.");
		}
		else if (token.length < 2) {
			throw new Exception("You supplied only one date. Please supply a date range.");
		}
		else if (!datesFromTo.matches("^[0-9].*")) {
			throw new Exception("You entered words for a date. Please supply a correct date range in the format dd/MM/yyyy.");
		}
		String firstDate = token[0];
		String lastDate = token[1];
		if (!firstDate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$") || !lastDate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$")) {
			throw new Exception("Dates should be entered in the dd/MM/yyyy format. Please supply a correct date range.");
		}
	}
	
	/**
	 * @param expense
	 * @param regex 
	 */
	public void fieldValidateInput (JTextField expense, String regex) {
		((AbstractDocument)expense.getDocument()).setDocumentFilter(new DocumentFilter(){
	        Pattern regEx = Pattern.compile(regex);
	        @Override
	        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {          
	            Matcher matcher = regEx.matcher(text);
	            if(!matcher.matches()){
	                return;
	            }
	            super.replace(fb, offset, length, text, attrs);
	        }
	    });
	}
	
	
}
