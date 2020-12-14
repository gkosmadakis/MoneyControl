/**
 * Created: 26 Nov 2020
 * Author: cousm
 */
package view.maingui.action;

import static utils.Constants.IMPORT;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import service.FileImportExportService;

/**
 * @author cousm Class that handles the click to the Import button on the GUI
 *
 */
public class ImportAction extends AbstractAction implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String importSourcePath, importDestinationPath;
	private FileImportExportService fileImportExportService;
	
	
	/**
	 * @param importSourcePath 
	 * @param importDestinationPath 
	 */
	public ImportAction (String importSourcePath, String importDestinationPath) {
		super(IMPORT);
		this.importSourcePath = importSourcePath;
		this.importDestinationPath = importDestinationPath;
		fileImportExportService = new FileImportExportService();
	}

	public void actionPerformed (ActionEvent arg0) {
		 importAction(importSourcePath, importDestinationPath);
	}
	
	private void importAction (String importSourcePath, String importDestinationPath) {
		
		fileImportExportService.processImport(importSourcePath, importDestinationPath);
	}
	
	
}
