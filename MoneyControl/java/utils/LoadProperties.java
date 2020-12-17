/**
 * LoadProperties.java Created: 18 Nov 2020 Author: cousm
 */
package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author cousm Utility Class that loads properties from a property file
 */
public class LoadProperties {
	private static Map<String, String> propertiesMap;
	/**
	 * Load properties from file and add them to a map
	 * @param filePath
	 */
	public static void setPropertiesFromPropertiesFile (String filePath) {
		try (InputStream input = new FileInputStream(filePath)) {
			Properties prop = new Properties();
			// load a properties file
			prop.load(input);
			propertiesMap = new HashMap<String, String>();
			for (Object key : prop.keySet()) {
				propertiesMap.put((String) key, prop.getProperty((String) key));
			}
		}
		catch (IOException e) {
			System.err.println("Error reading properties file: " +e.getMessage());
		}
	}
	
	/**
	 * @return propertiesMap
	 */
	public static Map<String, String> getPropertiesMap () {
		return propertiesMap;
	}
}
