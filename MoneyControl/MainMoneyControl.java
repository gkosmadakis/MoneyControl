import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class MainMoneyControl {
	private static String fileLine, fileLineJan,fileLineFeb,fileLineMar,fileLineApr,
	fileLineMay,fileLineJun,fileLineJul,fileLineAug,fileLineSep,fileLineOct,fileLineNov,fileLineDec, fileLineJan16, fileLineFeb16,fileLineMar16,fileLineApr16,fileLineMay16, fileLineJun16;
	static Map <String, Double> storeAmounts = new HashMap<String, Double>();
	static MultiMap amountAndDescriptionsNotDuplicate = new MultiMap();
	private static LinkedHashSet<String> descriptionsNotDuplicateJan = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateFeb = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateMar = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateApr = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateMay = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateJun = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateJul = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateAug = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateSep = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateOct = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateNov = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateDec = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateJan16 = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateFeb16 = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateMar16 = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateApr16 = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateMay16 = new LinkedHashSet<String>();
	private static LinkedHashSet<String> descriptionsNotDuplicateJun16 = new LinkedHashSet<String>();
	private static double houseRent, shopSuper, houseUtil, shopCloth, travel, mortgage, council, entertainment,itemAmount; 
	private static String [] descriptions;
	private static ArrayList<Double> arrayOfamountJan,arrayOfamountFeb,arrayOfamountMar,arrayOfamountApr,
	arrayOfamountMay,arrayOfamountJun,arrayOfamountJul,arrayOfamountAug,arrayOfamountSep,
	arrayOfamountOct,arrayOfamountNov,arrayOfamountDec,arrayOfamountJan16,arrayOfamountFeb16,arrayOfamountMar16,arrayOfamountApr16, arrayOfamountMay16, arrayOfamountJun16;
	final static MultiMap descriptionToindexMap = new MultiMap();
	
	public static void main (String args[]) {
			
			MoneyControlGUI mainGUI = new MoneyControlGUI();
			mainGUI.setSize(350, 500);
			mainGUI.setTitle("Money Control");
			mainGUI.setVisible(true);
			readTheFile();
	}
	
	public static void readTheFile() {
		
		fileLine="";
		fileLineJan="";
		fileLineFeb="";
		fileLineMar="";
		fileLineApr="";
		fileLineMay="";
		fileLineJun="";
		fileLineJul="";
		fileLineAug="";
		fileLineSep="";
		fileLineOct="";
		fileLineNov="";
		fileLineDec="";
		fileLineJan16="";
		fileLineFeb16="";
		fileLineMar16="";
		fileLineApr16="";
		fileLineMay16="";
		fileLineJun16="";
		descriptions =new String [] {"House Rent", "SuperMarket","House Bills",
				"Shopping" ,"Travel","Mortgage", "Council Tax", "Entertainment" };
		MoneyControlGUI items = new MoneyControlGUI();
		ArrayList <String> itemsAddedByUser= items.getTheItemsByUser();
		
		arrayOfamountJan =new ArrayList<Double>();
		arrayOfamountFeb =new ArrayList<Double>();
		arrayOfamountMar =new ArrayList<Double>();
		arrayOfamountApr =new ArrayList<Double>();
		arrayOfamountMay =new ArrayList<Double>();
		arrayOfamountJun =new ArrayList<Double>();
		arrayOfamountJul =new ArrayList<Double>();
		arrayOfamountAug =new ArrayList<Double>();
		arrayOfamountSep =new ArrayList<Double>();
		arrayOfamountOct =new ArrayList<Double>();
		arrayOfamountNov =new ArrayList<Double>();
		arrayOfamountDec =new ArrayList<Double>();
		arrayOfamountJan16 =new ArrayList<Double>();
		arrayOfamountFeb16 =new ArrayList<Double>();
		arrayOfamountMar16 =new ArrayList<Double>();
		arrayOfamountApr16= new ArrayList<Double>();
		arrayOfamountMay16= new ArrayList<Double>();
		arrayOfamountJun16= new ArrayList<Double>();
		String amount;
		String date;
		String desc="";
		double amountWithDuplicate=0.0;//this is to sum the amount when a duplicate desc found
		amountAndDescriptionsNotDuplicate = new MultiMap ();//not currently used
		descriptionsNotDuplicateJan = new LinkedHashSet<String>();//store unique descriptions
		descriptionsNotDuplicateFeb = new LinkedHashSet<String>();
		descriptionsNotDuplicateMar = new LinkedHashSet<String>();
		descriptionsNotDuplicateApr = new LinkedHashSet<String>();
		descriptionsNotDuplicateMay = new LinkedHashSet<String>();//store unique descriptions
		descriptionsNotDuplicateJun = new LinkedHashSet<String>();
		descriptionsNotDuplicateJul = new LinkedHashSet<String>();
		descriptionsNotDuplicateAug = new LinkedHashSet<String>();
		descriptionsNotDuplicateSep = new LinkedHashSet<String>();//store unique descriptions
		descriptionsNotDuplicateOct = new LinkedHashSet<String>();
		descriptionsNotDuplicateNov = new LinkedHashSet<String>();
		descriptionsNotDuplicateDec = new LinkedHashSet<String>();
		descriptionsNotDuplicateJan16 = new LinkedHashSet<String>();
		descriptionsNotDuplicateFeb16 = new LinkedHashSet<String>();
		descriptionsNotDuplicateMar16 = new LinkedHashSet<String>();
		descriptionsNotDuplicateApr16 = new LinkedHashSet<String>();
		descriptionsNotDuplicateMay16 = new LinkedHashSet<String>();
		descriptionsNotDuplicateJun16 = new LinkedHashSet<String>();
		try
		{
		FileReader reader = new FileReader("expenses.txt");
		Scanner in = new Scanner(reader);
		int lineIndex = 0;//this is to count the lines
		while (in.hasNextLine()) { 
			
			String line = in.nextLine();
			fileLine += line+ "\n";
			if (line.startsWith("Amount")){
				fileLineJan+= line+"\n"+"\n";
				fileLineFeb+= line+"\n"+"\n";
				fileLineMar+= line+"\n"+"\n";
				fileLineApr+= line+"\n"+"\n";
				fileLineMay+= line+"\n"+"\n";
				fileLineJun+= line+"\n"+"\n";
				fileLineJul+= line+"\n"+"\n";
				fileLineAug+= line+"\n"+"\n";
				fileLineSep+= line+"\n"+"\n";
				fileLineOct+= line+"\n"+"\n";
				fileLineNov+= line+"\n"+"\n";
				fileLineDec+= line+"\n"+"\n";
				fileLineJan16+= line+"\n"+"\n";
				fileLineFeb16+= line+"\n"+"\n";
				fileLineMar16+= line+"\n"+"\n";
				fileLineApr16+= line+"\n"+"\n";
				fileLineMay16+= line+"\n"+"\n";
				fileLineJun16+= line+"\n"+"\n";
			}
			if (++lineIndex>2){
			
			int index = line.lastIndexOf(" ");
			date = line.substring(index, line.length());
			amount = line.substring(0, line.indexOf(" "));
			desc = line.substring(line.indexOf(" "), index).trim();
			String extractMonthFromDate = date.substring(date.indexOf("/")+1, date.lastIndexOf("/"));
			String extractYearFromDate = date.substring( date.lastIndexOf("/")+1, date.length());
			
			if (extractMonthFromDate.equals("01")) {
				fileLineJan+= line+"\n";
				if (descriptionsNotDuplicateJan.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateJan.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountJan.get(i)+Double.parseDouble(amount);
								arrayOfamountJan.set(i, amountWithDuplicate);
						}
					}
				}
				else {
						descriptionsNotDuplicateJan.add(desc);
						arrayOfamountJan.add(Double.parseDouble(amount));
					 }
			}
			
			if (extractMonthFromDate.equals("02")) {
				fileLineFeb+= line+"\n";
				if (descriptionsNotDuplicateFeb.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateFeb.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountFeb.get(i)+Double.parseDouble(amount);
								arrayOfamountFeb.set(i, amountWithDuplicate);
						}
					}
				}
				else {
						descriptionsNotDuplicateFeb.add(desc);
						arrayOfamountFeb.add(Double.parseDouble(amount));
					 }
			}
			
			if (extractMonthFromDate.equals("03")) {
				fileLineMar+= line+"\n";
				if (descriptionsNotDuplicateMar.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateMar.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountMar.get(i)+Double.parseDouble(amount);
								arrayOfamountMar.set(i, amountWithDuplicate);
						}
					}
				}
				else {
						descriptionsNotDuplicateMar.add(desc);
						arrayOfamountMar.add(Double.parseDouble(amount));
					 }
			}
			
			if (extractMonthFromDate.equals("04")) {
				fileLineApr+= line+"\n";
				if (descriptionsNotDuplicateApr.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateApr.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountApr.get(i)+Double.parseDouble(amount);
								arrayOfamountApr.set(i, amountWithDuplicate);
						}
					}
				}
				else {
						descriptionsNotDuplicateApr.add(desc);
						arrayOfamountApr.add(Double.parseDouble(amount));
					 }
			}
			
			if (extractMonthFromDate.equals("05")) {
				fileLineMay+= line+"\n";
				if (descriptionsNotDuplicateMay.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateMay.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountMay.get(i)+Double.parseDouble(amount);
								arrayOfamountMay.set(i, amountWithDuplicate);
						}
					}
				}
				else {
						descriptionsNotDuplicateMay.add(desc);
						arrayOfamountMay.add(Double.parseDouble(amount));
					 }
			}
			
			if (extractMonthFromDate.equals("06")) {
				fileLineJun+= line+"\n";
				if (descriptionsNotDuplicateJun.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateJun.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountJun.get(i)+Double.parseDouble(amount);
								arrayOfamountJun.set(i, amountWithDuplicate);
						}
					}
				}
				else {
						descriptionsNotDuplicateJun.add(desc);
						arrayOfamountJun.add(Double.parseDouble(amount));
					 }
			}
			
			if (extractMonthFromDate.equals("07")) {
				fileLineJul+= line+"\n";
				if (descriptionsNotDuplicateJul.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateJul.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountJul.get(i)+Double.parseDouble(amount);
								arrayOfamountJul.set(i, amountWithDuplicate);
						}
					}
				}
				else {
				descriptionsNotDuplicateJul.add(desc);
				arrayOfamountJul.add(Double.parseDouble(amount));
				}
			}
			
			if (extractMonthFromDate.equals("08")) {
				fileLineAug+= line+"\n";
				if (descriptionsNotDuplicateAug.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateAug.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountAug.get(i)+Double.parseDouble(amount);
								arrayOfamountAug.set(i, amountWithDuplicate);	
						}
					}
				}
				else {
						descriptionsNotDuplicateAug.add(desc);
						arrayOfamountAug.add(Double.parseDouble(amount));
					 }
			}
			
			if (extractMonthFromDate.equals("09")) {
				fileLineSep+= line+"\n";
				if (descriptionsNotDuplicateSep.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateSep.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountSep.get(i)+Double.parseDouble(amount);
								arrayOfamountSep.set(i, amountWithDuplicate);
						}
					}
				}
				else {
				descriptionsNotDuplicateSep.add(desc);
				arrayOfamountSep.add(Double.parseDouble(amount));
				}
			}
			
			if (extractMonthFromDate.equals("10")) {
				fileLineOct+= line+"\n";
				if (descriptionsNotDuplicateOct.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateOct.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountOct.get(i)+Double.parseDouble(amount);
								arrayOfamountOct.set(i, amountWithDuplicate);
						}
					}
				}
				else {
						descriptionsNotDuplicateOct.add(desc);
						arrayOfamountOct.add(Double.parseDouble(amount));
					}
				}
			
			if (extractMonthFromDate.equals("11")) {
				fileLineNov += line+ "\n";
				if (descriptionsNotDuplicateNov.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateNov.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountOct.get(i)+Double.parseDouble(amount);
								arrayOfamountNov.set(i, amountWithDuplicate);
						}
					}
				}
				else {
						descriptionsNotDuplicateNov.add(desc);
						arrayOfamountNov.add(Double.parseDouble(amount));
					}
				}
			
			if (extractMonthFromDate.equals("12")) {
				fileLineDec+= line+"\n";
				if (descriptionsNotDuplicateDec.contains(desc)) {
					int i=0;
					for (Iterator<String> s = descriptionsNotDuplicateDec.iterator(); s.hasNext();i++){
						String descfound= s.next();
							if (desc.equals(descfound)) {
								amountWithDuplicate=arrayOfamountOct.get(i)+Double.parseDouble(amount);
								arrayOfamountDec.set(i, amountWithDuplicate);
						}
					}
				}
				else {
						descriptionsNotDuplicateDec.add(desc);
						arrayOfamountDec.add(Double.parseDouble(amount));
					}
				}
			if (extractMonthFromDate.equals("01")&& extractYearFromDate.equals("2016")) {
				fileLineJan16+= line+"\n";
			if (descriptionsNotDuplicateJan16.contains(desc)) {
				int i=0;
				for (Iterator<String> s = descriptionsNotDuplicateJan16.iterator(); s.hasNext();i++){
					String descfound= s.next();
						if (desc.equals(descfound)) {
							amountWithDuplicate=arrayOfamountJan16.get(i)+Double.parseDouble(amount);
							arrayOfamountJan16.set(i, amountWithDuplicate);
					}
				}
			}
			else {
					descriptionsNotDuplicateJan16.add(desc);
					arrayOfamountJan16.add(Double.parseDouble(amount));
				 }
		}
			if (extractMonthFromDate.equals("02")&& extractYearFromDate.equals("2016")) {
				fileLineFeb16+= line+"\n";
			if (descriptionsNotDuplicateFeb16.contains(desc)) {
				int i=0;
				for (Iterator<String> s = descriptionsNotDuplicateFeb16.iterator(); s.hasNext();i++){
					String descfound= s.next();
						if (desc.equals(descfound)) {
							amountWithDuplicate=arrayOfamountFeb16.get(i)+Double.parseDouble(amount);
							arrayOfamountFeb16.set(i, amountWithDuplicate);
					}
				}
			}
			else {
					descriptionsNotDuplicateFeb16.add(desc);
					arrayOfamountFeb16.add(Double.parseDouble(amount));
				 }
		}
			if (extractMonthFromDate.equals("03")&& extractYearFromDate.equals("2016")) {
				fileLineMar16+= line+"\n";
			if (descriptionsNotDuplicateMar16.contains(desc)) {
				int i=0;
				for (Iterator<String> s = descriptionsNotDuplicateMar16.iterator(); s.hasNext();i++){
					String descfound= s.next();
						if (desc.equals(descfound)) {
							amountWithDuplicate=arrayOfamountMar16.get(i)+Double.parseDouble(amount);
							arrayOfamountMar16.set(i, amountWithDuplicate);
					}
				}
			}
			else {
					descriptionsNotDuplicateMar16.add(desc);
					arrayOfamountMar16.add(Double.parseDouble(amount));
				 }
		}
			if (extractMonthFromDate.equals("04")&& extractYearFromDate.equals("2016")) {
				fileLineApr16+= line+"\n";
			if (descriptionsNotDuplicateApr16.contains(desc)) {
				int i=0;
				for (Iterator<String> s = descriptionsNotDuplicateApr16.iterator(); s.hasNext();i++){
					String descfound= s.next();
						if (desc.equals(descfound)) {
							amountWithDuplicate=arrayOfamountApr16.get(i)+Double.parseDouble(amount);
							arrayOfamountApr16.set(i, amountWithDuplicate);
					}
				}
			}
			else {
					descriptionsNotDuplicateApr16.add(desc);
					arrayOfamountApr16.add(Double.parseDouble(amount));
				 }
		}
			if (extractMonthFromDate.equals("05")&& extractYearFromDate.equals("2016")) {
				fileLineMay16+= line+"\n";
			if (descriptionsNotDuplicateMay16.contains(desc)) {
				int i=0;
				for (Iterator<String> s = descriptionsNotDuplicateMay16.iterator(); s.hasNext();i++){
					String descfound= s.next();
						if (desc.equals(descfound)) {
							amountWithDuplicate=arrayOfamountMay16.get(i)+Double.parseDouble(amount);
							arrayOfamountMay16.set(i, amountWithDuplicate);
					}
				}
			}
			else {
					descriptionsNotDuplicateMay16.add(desc);
					arrayOfamountMay16.add(Double.parseDouble(amount));
				 }
		}
			if (extractMonthFromDate.equals("06")&& extractYearFromDate.equals("2016")) {
				fileLineJun16+= line+"\n";
			if (descriptionsNotDuplicateJun16.contains(desc)) {
				int i=0;
				for (Iterator<String> s = descriptionsNotDuplicateJun16.iterator(); s.hasNext();i++){
					String descfound= s.next();
						if (desc.equals(descfound)) {
							amountWithDuplicate=arrayOfamountJun16.get(i)+Double.parseDouble(amount);
							arrayOfamountJun16.set(i, amountWithDuplicate);
					}
				}
			}
			else {
					descriptionsNotDuplicateJun16.add(desc);
					arrayOfamountJun16.add(Double.parseDouble(amount));
				 }
		}
			
			if (line.contains(descriptions[0])){
				amount = line.substring(0, line.indexOf(" "));//get the amount
				houseRent+= Double.parseDouble(amount);//store the amount to the House rent	
			}
			else if (line.contains(descriptions[1])){
				amount = line.substring(0, line.indexOf(" "));//get the amount
				shopSuper+= Double.parseDouble(amount);//store the amount to the shopping (supermarket)	
			}
			else if (line.contains(descriptions[2])){
				amount = line.substring(0, line.indexOf(" "));//get the amount
				houseUtil+= Double.parseDouble(amount);//store the amount to the House rent	house utilities
			}
			else if (line.contains(descriptions[3])){
				amount = line.substring(0, line.indexOf(" "));//get the amount
				shopCloth+= Double.parseDouble(amount);//store the amount to shopping (clothes)
			}
			else if (line.contains(descriptions[4])){
				amount = line.substring(0, line.indexOf(" "));//get the amount
				travel+= Double.parseDouble(amount);//store the amount to the travel
			}
			else if (line.contains(descriptions[5])){
				amount = line.substring(0, line.indexOf(" "));//get the amount
				mortgage+= Double.parseDouble(amount);//store the amount to the mortgage	
			}
			else if (line.contains(descriptions[6])){
				amount = line.substring(0, line.indexOf(" "));//get the amount
				council+= Double.parseDouble(amount);//store the amount to the council tax		
			}
			else if (line.contains(descriptions[7])){
				amount = line.substring(0, line.indexOf(" "));//get the amount
				entertainment+= Double.parseDouble(amount);//store the amount to the entertainment	
			}
			else if (itemsAddedByUser.contains(line.substring(line.indexOf(" "), index).trim())){
				for (int i=0; i< itemsAddedByUser.size(); i++){
					amount = line.substring(0, line.indexOf(" "));//get the amount
					String item = itemsAddedByUser.get(i);//get the item
					itemAmount += Double.parseDouble(amount);
					storeAmounts.put(item, itemAmount);//store the amount to the item
				}
			}
			}
			
			}//end of while
		System.out.println(descriptionsNotDuplicateSep);
		System.out.println(arrayOfamountSep);
		System.out.println(descriptionsNotDuplicateOct);
		System.out.println(arrayOfamountOct);
		System.out.println(descriptionsNotDuplicateJan);
		System.out.println(arrayOfamountJan);
		System.out.println(descriptionsNotDuplicateMay);
		System.out.println(arrayOfamountMay);
		storeAmounts.put("House Rent", houseRent);
		storeAmounts.put("SuperMarket",shopSuper);
		storeAmounts.put("House Bills", houseUtil);
		storeAmounts.put("Shopping", shopCloth);
		storeAmounts.put("Travel", travel);
		storeAmounts.put("Mortgage", mortgage);
		storeAmounts.put("Council Tax", council);
		storeAmounts.put("Entertainment", entertainment);
		
		reader.close();
		}
		catch (IOException e) {
			System.out.println("File not found");
		}
		
		
		List <String> allThedescriptions = new ArrayList<String>();
		for (int i=2; i<fileLine.split("\n").length; i++){
		String descLine= fileLine.split("\n")[i];//iterate through the fileline and get the 
		int index = descLine.lastIndexOf(" ");//descriptions. Add them to a list allThedescriptions
		String  description = descLine.substring(descLine.indexOf(" "),index).trim();
		allThedescriptions.add(description);
		}
		
		Set <String> set = new HashSet<String>(allThedescriptions);//Create a set with the descriptions
		if (set.size()< allThedescriptions.size())//Set receives does not receive duplicate elements
		{										// if the size of the set is smaller than the size of the list allThedescriptions
			//that means that the file contains duplicate descriptions (eg. House Rent is appeared twice
		
		}
		else if (set.size()== allThedescriptions.size()){
			System.out.println("The file does not have duplicates or is empty");
			//that means the file does not have duplicates or is blank.
		}
	}
	
	public String getFileLine(){
		return fileLine;
	}
	
	public String getFileLineJan(){
		return fileLineJan;
	}
	
	public String getFileLineFeb(){
		return fileLineFeb;
	}
	
	public String getFileLineMar(){
		return fileLineMar;
	}
	
	public String getFileLineApr(){
		return fileLineApr;
	}
	
	public String getFileLineMay(){
		return fileLineMay;
	}
	
	public String getFileLineJun(){
		return fileLineJun;
	}
	
	public String getFileLineJul(){
		return fileLineJul;
	}
	
	public String getFileLineAug(){
		return fileLineAug;
	}
	
	public String getFileLineSep(){
		return fileLineSep;
	}
	
	public String getFileLineOct(){
		return fileLineOct;
	}
	
	public String getFileLineNov(){
		return fileLineNov;
	}
	
	public String getFileLineDec(){
		return fileLineDec;
	}
	
	public String getFileLineJan16(){
		return fileLineJan16;
	}
	
	public String getFileLineFeb16(){
		return fileLineFeb16;
	}
	
	public String getFileLineMar16(){
		return fileLineMar16;
	}
	
	public String getFileLineApr16(){
		return fileLineApr16;
	}
	
	public String getFileLineMay16(){
		return fileLineMay16;
	}
	
	public String getFileLineJun16(){
		return fileLineJun16;
	}
	
	public Map<String, Double> getStoreAmounts(){
		
		return storeAmounts;
	}
	
	public LinkedHashSet<String> getThedescJan() {
		return descriptionsNotDuplicateJan;
	}
	
	public LinkedHashSet<String> getThedescFeb() {
		return descriptionsNotDuplicateFeb;
	}
	
	public LinkedHashSet<String> getThedescMar() {
		return descriptionsNotDuplicateMar;
	}
	
	public LinkedHashSet<String> getThedescApr() {
		return descriptionsNotDuplicateApr;
	}
	
	public LinkedHashSet<String> getThedescMay() {
		return descriptionsNotDuplicateMay;
	}
	
	public LinkedHashSet<String> getThedescJun() {
		return descriptionsNotDuplicateJun;
	}
	
	public LinkedHashSet<String> getThedescJul() {
		return descriptionsNotDuplicateJul;
	}
	
	public LinkedHashSet<String> getThedescAug() {
		return descriptionsNotDuplicateAug;
	}
	
	public LinkedHashSet<String> getThedescSep() {
		return descriptionsNotDuplicateSep;
	}
	
	public LinkedHashSet<String> getThedescOct() {
		return descriptionsNotDuplicateOct;
	}
	
	public LinkedHashSet<String> getThedescNov() {
		return descriptionsNotDuplicateNov;
	}
	
	public LinkedHashSet<String> getThedescDec() {
		return descriptionsNotDuplicateDec;
	}
	
	public LinkedHashSet<String> getThedescJan16() {
		return descriptionsNotDuplicateJan16;
	}
	
	public ArrayList<Double> getTheAmountJan(){
		return arrayOfamountJan;
	}
	
	public ArrayList<Double> getTheAmountFeb(){
		return arrayOfamountFeb;
	}
	
	public ArrayList<Double> getTheAmountMar(){
		return arrayOfamountMar;
	}
	
	public ArrayList<Double> getTheAmountApr(){
		return arrayOfamountApr;
	}
	
	public ArrayList<Double> getTheAmountMay(){
		return arrayOfamountMay;
	}
	
	public ArrayList<Double> getTheAmountJun(){
		return arrayOfamountJun;
	}
	
	public ArrayList<Double> getTheAmountJul(){
		return arrayOfamountJul;
	}
	
	public ArrayList<Double> getTheAmountAug(){
		return arrayOfamountAug;
	}
	
	public ArrayList<Double> getTheAmountSep(){
		return arrayOfamountSep;
	}
	
	public ArrayList<Double> getTheAmountOct(){
		return arrayOfamountOct;
	}
	
	public ArrayList<Double> getTheAmountNov(){
		return arrayOfamountNov;
	}
	
	public ArrayList<Double> getTheAmountDec(){
		return arrayOfamountDec;
	}
	
	public ArrayList<Double> getTheAmountJan16(){
		return arrayOfamountJan16;
	}
	
	public ArrayList<Double> getTheAmountFeb16(){
		return arrayOfamountFeb16;
	}
	
	public ArrayList<Double> getTheAmountMar16(){
		return arrayOfamountMar16;
	}
	
	public ArrayList<Double> getTheAmountApr16(){
		return arrayOfamountApr16;
	}
	
	public ArrayList<Double> getTheAmountMay16(){
		return arrayOfamountMay16;
	}
	
	public ArrayList<Double> getTheAmountJun16(){
		return arrayOfamountJun16;
	}
	
	
	
	
	
	
}
