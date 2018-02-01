package com.http.util;

import java.util.Date;

import org.apache.log4j.Logger;
import org.testng.Reporter;

public class Log {

	private Logger logger;
	
	private DateFormat df;
	
	public Log(String s){
		logger = Logger.getLogger(s);
		df = new DateFormat();
	}
	
	public Log (Class<?> clazz) {
		logger = Logger.getLogger(clazz);
		df = new DateFormat();
	}
	
	public Log () {
		logger = Logger.getLogger("");
		df = new DateFormat();
	}
	
	public void info1(Object message){
		logger.info(message);
		Reporter.log(message.toString());
	}
	
	public void info(Object message){
		logger.info(message);	
		this.testngOutPut(message);
	}
	
	public void debug(Object message){
		logger.debug(message);	
		this.testngOutPut(message);
	}
	
	public void error(Object message){
		logger.error(message);	
		this.testngOutPut(message);
	}
	
	public void warn(Object message){
		logger.warn(message);	
		this.testngOutPut(message);
	}
	
	
	public void testngOutPut(Object message){
		Reporter.log(df.fomatDate(new Date())+message);
	}
	

}
