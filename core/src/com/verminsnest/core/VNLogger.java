package com.verminsnest.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VNLogger {
	private static SimpleDateFormat formatter;
	private static VNLogger instance;
	private VNLogger(){
		formatter = new SimpleDateFormat("HH:mm:ss");
	}
	public static void log(String message, Class<?> clazz){
		if(instance == null) instance = new VNLogger();
		Date date = new Date();
		System.out.println(formatter.format(date) + " " +clazz.getSimpleName() + ": " + message);
	}
}
