/**
 * ReportService.java
 * Created: 7 Dec 2020
 * Author: cousm
 */
package service;

/**
 * @author cousm Service that returns filtered expenses by month and year to be shown in the report area 
 *
 */
public class ReportService {
	
	/**
	 * @param month
	 * @param allTheLines
	 * @param yearSelected
	 * @return filteredLinesForMonth
	 */
	public StringBuilder filterFileForThisMonthAndYear (int month, StringBuilder allTheLines, String yearSelected) {
		StringBuilder filteredLinesForMonth = new StringBuilder();
		String[] lines = allTheLines.toString().split("\\n");
		for(String lineToCheckMonth: lines){
			int index = lineToCheckMonth.lastIndexOf(" ");
			String date = lineToCheckMonth.substring(index, lineToCheckMonth.length());
			String extractMonthFromDate = date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"));
			int monthFromDate = 0;
			if(extractMonthFromDate.startsWith("0")) {
				 monthFromDate = Integer.parseInt(extractMonthFromDate.substring(1, extractMonthFromDate.length()));
			}
			else {
				monthFromDate = Integer.parseInt(extractMonthFromDate);
			}
			String extractYearFromDate = date.substring(date.lastIndexOf("/") + 1, date.length());
			if(monthFromDate == month && extractYearFromDate.equals(yearSelected)) {
				filteredLinesForMonth.append(lineToCheckMonth+"\n");
			}
		}
		return filteredLinesForMonth;
	}

}
