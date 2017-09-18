package com.rhl.auxilium.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.sql.SQLException;

public class Log4jLogger {
	/* Get actual class name to be printed on */
	  static Logger log = Logger.getLogger(Log4jLogger.class.getName());

	  public static void main(String[] args)
	                throws IOException,SQLException{
	   
	     log.debug("Hello this is an debug message");
	     log.info("Hello this is an info message");
	  }

}
