/**
 * Descriptions.java Created: 11 Nov 2020 Author: cousm
 */
package view.maingui;

import static utils.Constants.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import service.DescriptionsFileService;
import utils.LoadProperties;

/**
 * @author cousm Class to handle key Events, ENTER and DEL to add or delete a user added description
 */
public class Descriptions {
	private ArrayList<String> itemsAddedByUser;
	private String input;
	private JComboBox<String> description;
	private Vector<String> comboBoxItems;
	private DefaultComboBoxModel<String> model;
	private String fixedDescriptions;
	private String descriptionsFile;
	private AlertDialog alertDialog;
	private DescriptionsFileService descriptionsFileService;
	
	/**
	 * @param input
	 * @param description
	 * @param comboBoxItems
	 * @param model
	 */
	public Descriptions (String input, JComboBox<String> description, Vector<String> comboBoxItems, DefaultComboBoxModel<String> model) {
		super();
		this.input = input;
		this.description = description;
		this.comboBoxItems = comboBoxItems;
		this.model = model;
		loadProperties();
		alertDialog = new AlertDialog();
		descriptionsFileService = new DescriptionsFileService(descriptionsFile, input, itemsAddedByUser, model);
		itemsAddedByUser = descriptionsFileService.readDescriptionsFile();
	}
	
	/**
	 * Listens to key Events, ENTER and DEL to add or delete a user added description
	 */
	public void getTheInputFromDescriptionComboBox () {
		
		enterKeyListener();
		deleteKeyListener();
	}
	
	private void enterKeyListener () {
		description.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			public void keyPressed (KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					input = (String) description.getEditor().getItem();
					if (comboBoxItems.contains(input)) {
						alertDialog.informUserError("The description exists already in the list");
					}
					else if (!input.equals("")) {
						model.addElement(input);
						itemsAddedByUser.add(input);
						descriptionsFileService.writeDescriptionsToFile(input);
						alertDialog.informUser("Your description is added successfully");
					}
				}
			}
		});
	}
	
	private void deleteKeyListener () {
		description.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			public void keyPressed (KeyEvent evt) {
				String fixedDescriptionsArray[] = fixedDescriptions.split(",");
				if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
					input = (String) description.getEditor().getItem();
					if (Arrays.asList(fixedDescriptionsArray).contains(input)) {
						alertDialog.informUserError("This description is preinstalled in the application and for operating reasons is not allowed to delete it");
					}
					else if (itemsAddedByUser.contains(input)) {
						int index = description.getSelectedIndex();
						descriptionsFileService.deleteDescriptionFromFile((String) model.getSelectedItem());
						model.removeElementAt(index);
						itemsAddedByUser.remove(input);
						alertDialog.informUser("You deleted successfuly the selected item");
					}
					else {
						alertDialog.informUserError("The description does not exist in the list");
					}
				}
			}
		});
	}

	/**
	 * Loads the properties
	 */
	private void loadProperties () {
		Map<String, String> propertiesMap = LoadProperties.getPropertiesMap();
		fixedDescriptions = propertiesMap.get(FIXED_DESCRIPTIONS);
		descriptionsFile = propertiesMap.get(DESCRIPTIONS_FILE);
	}
}
