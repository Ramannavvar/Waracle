package com.waracle.cakemgr.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ReadProperties {
	private static final Logger logger = Logger.getLogger(ReadProperties.class);
	
	public static String readProperty(String propertyName) {
		InputStream inputStream = ReadProperties.class.getClassLoader().getResourceAsStream("resources.properties");
		Properties properties = new Properties();
		
		try {
			properties.load(inputStream);
			inputStream.close();
			return properties.getProperty(propertyName);
		} catch (IOException e) {
			logger.fatal("resources.properties file error " + e.getMessage());			
		}
		return "unknown";
	}
		
}
