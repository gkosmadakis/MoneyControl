/**
 * Created: 26 Nov 2020
 * Author: cousm
 */
package view.maingui.action;

import static utils.Constants.EXPORT;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;

import service.FileImportExportService;

/**
 * @author cousm Class that handles the click on the export button in the GUI
 *
 */
public class ExportAction extends AbstractAction implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exportPath, importSourcePath;
	private FileImportExportService fileImportExportService;
	
	
	/**
	 * @param exportPath
	 * @param importSourcePath
	 */
	public ExportAction (String exportPath, String importSourcePath) {
		super(EXPORT);
		this.exportPath = exportPath;
		this.importSourcePath = importSourcePath;
		fileImportExportService = new FileImportExportService();
	}

	public void actionPerformed (ActionEvent arg0) {
		 exportAction(exportPath, importSourcePath);
	}
	
	private void exportAction (String exportPath, String importSourcePath) {
		try {
			fileImportExportService.processExport(new File(exportPath), new File(importSourcePath));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
}
