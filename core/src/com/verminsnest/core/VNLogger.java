package com.verminsnest.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VNLogger {
	private static SimpleDateFormat formatter;
	private static VNLogger instance;
	private VNLogger(){
		formatter = new SimpleDateFormat("HH:mm:ss");
	}
	/**
	 * Logs info messages from a source class
	 * @param message
	 * @param clazz
	 */
	public static void log(String message, Class<?> clazz){
		if(instance == null) instance = new VNLogger();
		Date date = new Date();
		System.out.println(formatter.format(date) + " INFO  " +clazz.getSimpleName() + ": " + message);
	}
	
	/**
	 * Logs error messages from a source class
	 * @param message
	 * @param clazz
	 */
	public static void logErr(String message, Class<?> clazz){
		if(instance == null) instance = new VNLogger();
		Date date = new Date();
		System.out.println(formatter.format(date) + " ERROR " +clazz.getSimpleName() + ": " + message);
	}
}
