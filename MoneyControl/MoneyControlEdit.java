import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit;


public class MoneyControlEdit extends JFrame implements ActionListener, MouseListener  {


	private static final long serialVersionUID = 1L;
	private JButton searchDescButton, searchAmountButton, clearButton, saveButton,deleteButton,editButton;
	private JTextField descField, amountField;
	List<String> lineToEdit = new ArrayList<>();
	ArrayList <String> singleLine = new ArrayList<>();
	private String lineToEditMultiple,lineEdited;
	private JTextPane editLine,selectLinePane;
	private boolean expenseIsSelected;
	private JFrame editFrame;
	Action selectLine;

	public MoneyControlEdit(){

		layoutGui();
		editLine = new JTextPane();
		getContentPane().add( new JTextPane() );
		selectLine = getAction(DefaultEditorKit.selectLineAction);
	}

	private Action getAction(String name)
	{
		Action action = null;
		Action[] actions = editLine.getActions();

		for (int i = 0; i < actions.length; i++)
		{
			if (name.equals( actions[i].getValue(Action.NAME).toString() ) )
			{
				action = actions[i];
				break;
			}
		}
		return action;
	}

	public void layoutGui(){

		JPanel panel = new JPanel(new GridLayout(5,6));
		searchDescButton= new JButton("Search by description");
		searchDescButton.addActionListener(this);
		descField = new JTextField();
		amountField= new JTextField();
		searchAmountButton = new JButton("Search by Amount");
		searchAmountButton.addActionListener(this);
		clearButton = new JButton("Clear Results");
		clearButton.addActionListener(this);
		deleteButton= new JButton("Delete an expense");
		deleteButton.addActionListener(this);
		editButton= new JButton("Edit an expense");
		editButton.addActionListener(this);
		JButton emptyButton = new JButton();
		emptyButton.setVisible(false);
		String label="\t";
		String resultslabel = label.replaceAll("\t" , "                           ");
		JLabel resultsLabel = new JLabel( resultslabel+"--------------------RESULTS-------------------");
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
	public void actionPerformed(ActionEvent e) {

		readTheLines();

		if (e.getSource()==searchDescButton){

			String description = descField.getText();
			String linefound=null;
			String descFound=null;
			lineToEditMultiple= "";
			MultiMap  descriptionFoundMap = new MultiMap();

			if (description.equals("")) {
				JOptionPane.showMessageDialog(null, "Description field is empty!", 
						"Result Summary", JOptionPane.ERROR_MESSAGE);
			}
			else {

				for (int i=0; i<singleLine.size(); i++){
					linefound = singleLine.get(i);
					int index = linefound.lastIndexOf(" ");
					descFound = linefound.substring(linefound.indexOf(" "), index);
					descriptionFoundMap.put(descFound.trim(), linefound);
				}

				if (!linefound.equals("")){
					if (descriptionFoundMap.containsKey(description)){
						lineToEdit = descriptionFoundMap.get(description);

						if (lineToEdit.size()>1){
							for (int i=0; i<lineToEdit.size(); i++){
								lineToEditMultiple += lineToEdit.get(i)+ "\n";

							}
							editLine = new JTextPane();
							editLine.addMouseListener( this );
							editLine.setText(lineToEditMultiple);
							editLine.setFont(new Font("Courier", Font.PLAIN, 14));
							add( editLine, FlowLayout.CENTER);
						}
						else{
							editLine = new JTextPane();
							editLine.addMouseListener( this );
							editLine.setText(lineToEdit.toString().replaceAll("\\[", "").replaceAll("\\]",""));
							editLine.setFont(new Font("Courier", Font.PLAIN, 14));
							add( editLine, FlowLayout.CENTER);	
						}
					}
					else if (!descriptionFoundMap.containsKey(description)){
						JOptionPane.showMessageDialog(null, "No expense found! Try again with different description", 
								"Result Summary", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		if (e.getSource()==searchAmountButton){

			String amount = amountField.getText();
			String amountPart = null;
			String linefound= "";
			lineToEdit.clear();
			lineToEditMultiple= "";
			MultiMap amountFoundMap = new MultiMap();
			if (amount.equals("")) {
				JOptionPane.showMessageDialog(null, "Amount field is empty!", 
						"Result Summary", JOptionPane.ERROR_MESSAGE);
			}
			else {
				for (int i=0; i<singleLine.size(); i++){
					linefound = singleLine.get(i);
					amountPart = linefound.substring(0,linefound.indexOf(" "));
					amountFoundMap.put(amountPart, linefound);
				}
				if (amountFoundMap.containsKey(amount)){
					lineToEdit = amountFoundMap.get(amount);

					if (lineToEdit.size()>1){
						for (int i=0; i<lineToEdit.size(); i++){
							lineToEditMultiple += lineToEdit.get(i)+ "\n";

						}
						editLine = new JTextPane();
						editLine.addMouseListener( this );
						editLine.setText(lineToEditMultiple);
						editLine.setFont(new Font("Courier", Font.PLAIN, 14));
						add( editLine, FlowLayout.CENTER);
					}
					else {
						editLine = new JTextPane();
						editLine.addMouseListener( this );
						editLine.setText(lineToEdit.toString().replaceAll("\\[", "").replaceAll("\\]",""));
						editLine.setFont(new Font("Courier", Font.PLAIN, 14));
						add( editLine, FlowLayout.CENTER);
					}
				}
				else if (!amountFoundMap.containsKey(amount)){
					JOptionPane.showMessageDialog(null, "No expense found! Try again with different amount", 
							"Result Summary", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		if (e.getSource()==editButton){

			if (editLine.getText().equals("")){
				JOptionPane.showMessageDialog(null, "No expense is selected! Search first for an expense and try again to edit", 
						"Result Summary", JOptionPane.ERROR_MESSAGE);
			}
			else  if (editLine.getText()!=null && expenseIsSelected==false){
				JOptionPane.showMessageDialog(null, "Nothing is selected! Select first an expense to edit.", 
						"Result Summary", JOptionPane.ERROR_MESSAGE);
			}
			else {
				String selectedLine = editLine.getSelectedText();//this is the line that is sent to the selectLinePane
					if (selectedLine==null) {
						JOptionPane.showMessageDialog(null, "No expense is selected! Search first for an expense and try again to edit", 
								"Result Summary", JOptionPane.ERROR_MESSAGE);
					}
					else {
						selectLinePane = new JTextPane();
						selectLinePane.setText(selectedLine);//set this line to the pane
		
						saveButton = new JButton("save");
						saveButton.addActionListener(this);
		
						editFrame = new JFrame();
						editFrame.setSize(250,150);
						editFrame.setVisible(true);
						editFrame.add(selectLinePane, BorderLayout.NORTH);
						editFrame.add(saveButton, BorderLayout.SOUTH);
					}
			}
		}

		if (e.getSource()==saveButton){
			//probably i will have to delete this if because check is made before the user clicks on edit.
			if (editLine.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Nothing is saved! Search first for an expense to edit", 
						"Result Summary", JOptionPane.ERROR_MESSAGE);
			}

			else {
				EditAnExpense();
				editFrame.dispose();
			}
		}
		if (e.getSource()==deleteButton){
			//the user has not searched first for an expense
			if (editLine.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Nothing is deleted! Search first for an expense and try again", 
						"Result Summary", JOptionPane.ERROR_MESSAGE);
			}
			// the user has not selected an expense to delete
			else if (expenseIsSelected==false){
				JOptionPane.showMessageDialog(null, "Nothing is deleted! Select first an expense.", 
						"Result Summary", JOptionPane.ERROR_MESSAGE);
			}
			else {
				deleteAnExpense();
			}
		}

		if (e.getSource()==clearButton){
			if (editLine.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Search first for an expense and then you can clear it", 
						"Result Summary", JOptionPane.ERROR_MESSAGE);
			}
			else {
				ClearFieldsWhenButtonIsPressed();
			}
		}
	}

	public void readTheLines(){

		try
		{
			FileReader reader = new FileReader("expenses.txt");
			Scanner in = new Scanner(reader);
			int lineIndex = 0;
			singleLine = new ArrayList<String>();
			while (in.hasNextLine()) { 
				String line = in.nextLine();
				if (++lineIndex>2){

					//String result [] = line.trim().replaceAll("\\s+", " ").split(" "); 
					singleLine.add(line.trim().replaceAll("\\s+", " "));	  
				}
			}
			reader.close();
		}
		catch (IOException e) {
			System.out.println("File not found");
		}
	}

	public void ClearFieldsWhenButtonIsPressed(){
		if (editLine!= null){
			editLine.setText("");
		}
	}

	public void EditAnExpense(){

		try {
			FileInputStream fstream = new FileInputStream("expenses.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			StringBuilder fileContent = new StringBuilder();

			while ((strLine = br.readLine()) != null) {
				System.out.println(strLine); 
				//get the line as edited by the user and remove initial [ and last ]
				lineEdited= selectLinePane.getText().replaceAll("\\[", "").replaceAll("\\]","");
				//As we read the file we want to edit the line that is sent to the selectLinePane and is equal 
				//from the one that is read from the file
				if (editLine.getSelectedText().replaceAll("\\[", "").replaceAll("\\]","").
						equals(strLine.replaceAll("\\s+", " ")) && !strLine.equals("") ){

					String amount ="";
					String desc="";
					String date="";
					String formatStr =  "%-6s%-40s%-10s";//formats the columns

					String newLine = lineEdited;//Get the line as edited by the user
					amount = newLine.substring(0, newLine.indexOf(" "));//prints the amount
					int index = newLine.lastIndexOf(" ");
					desc = newLine.substring(newLine.indexOf(" "), index);//prints the description
					date = newLine.substring(index, newLine.length());//prints the date
					fileContent.append(String.format(formatStr,amount,desc,date));//write edited line in the file
					fileContent.append(System.getProperty("line.separator"));//write a line 			
				}
				else 
				{
					// update content as it is
					fileContent.append(strLine);
					fileContent.append(System.getProperty("line.separator"));
				}
			}
			FileWriter fstreamWrite = new FileWriter("expenses.txt");
			BufferedWriter out = new BufferedWriter(fstreamWrite);
			out.write(fileContent.toString());

			out.close();
			JOptionPane.showMessageDialog(null, "You edited successfuly your expenses", "Result Summary", JOptionPane.INFORMATION_MESSAGE);
		} 
		catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void deleteAnExpense(){
		int result=0;
		if (editLine.getSelectedText()!=null){
			result = JOptionPane.showConfirmDialog(null, "Do you really want to delete this expense?", 
					"Result Summary", JOptionPane.OK_CANCEL_OPTION);
		}//get the option from the OK cancel window, result is 0 if user presses OK and 2 if user presses cancel
		
		//the user has pressed after the last result or somewhere so no expense is selected
		else if (editLine.getSelectedText()== null) {
			JOptionPane.showMessageDialog(null, "You have not selected an expense. Click on an expense and try again.", "Result Summary", JOptionPane.INFORMATION_MESSAGE);
		}
		
		//the user has pressed OK
		if (result==0) {
			try {

				FileInputStream fstream = new FileInputStream("expenses.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				String strLine;
				StringBuilder fileContent = new StringBuilder();

				while ((strLine = br.readLine()) != null) {
					System.out.println(strLine);
					//if the line in the file is not the same with the one selected by the user
					if (!strLine.replace(" ", "").equalsIgnoreCase(editLine.getSelectedText().
							replaceAll("\\[", "").replaceAll("\\]","").replace(" ",""))){
						fileContent.append(strLine);
						fileContent.append(System.getProperty("line.separator"));//then write it to the file.
						//So the line that will be the same as the editLine, the line selected to the 
						//JTextPane will not be written to the file, it will be deleted.	 
					}
				}
				FileWriter fstreamWrite = new FileWriter("expenses.txt");
				BufferedWriter out = new BufferedWriter(fstreamWrite);
				out.write(fileContent.toString().trim());

				out.close();
				JOptionPane.showMessageDialog(null, "You deleted successfuly the selected expense", "Result Summary", JOptionPane.INFORMATION_MESSAGE);

			} 	catch (Exception e) {//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}
		//the user has pressed cancel
		else if (result==2){
			JOptionPane.showMessageDialog(null, "The expense is not deleted", "Result Summary", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if ( SwingUtilities.isLeftMouseButton(e)  && e.getClickCount() == 1)
		{
			selectLine.actionPerformed( null );
			expenseIsSelected= true;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}




}
