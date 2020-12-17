/**
 * FileImportExport.java Created: 12 Nov 2020 Author: cousm
 */
package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author cousm Service Class to perform import and export of the expenses file
 */
public class FileImportExportService {
	/**
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public void processExport (File source, File dest) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		}
		finally {
			input.close();
			output.close();
			System.out.println("Expenses file is exported in location " + dest);
		}
	}
	
	/**
	 * @param sourcePath
	 * @param destPath
	 */
	public void processImport (String sourcePath, String destPath) {
		Path source = Paths.get(sourcePath);
		Path dest = Paths.get(destPath);
		try {
			Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("The expenses file is imported from " + source);
		}
		catch (IOException e) {
			System.err.println("Error importing expenses file: "+e.getMessage());
		}
	}
}
