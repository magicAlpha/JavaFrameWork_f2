package com.magic.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


public class GenericUtil {

	public static void createFileUsingFileOutputStreamClass(String filenameWithPath, String data) throws IOException {
		FileOutputStream out = new FileOutputStream(filenameWithPath);
		out.write(data.getBytes());
		out.close();
	}
	
	public static String getCurrentDateTimeMS(){
        Date now = new Date();
        SimpleDateFormat formatedNow = new SimpleDateFormat("yyMMddhhmmssMs");
        String uniqueValue = formatedNow.format(now);
        return uniqueValue;
    }
	
	public static String getCurrentDate(){
        Date now = new Date();
        SimpleDateFormat formatedNow = new SimpleDateFormat("MM-dd-YYYY");
        String uniqueValue = formatedNow.format(now);
        return uniqueValue;
    }
	
	public static String getCurrentTime(){
        Date now = new Date();
        SimpleDateFormat formatedNow = new SimpleDateFormat("hh:mma");
        String uniqueValue = formatedNow.format(now);
        uniqueValue = StringUtils.stripStart(uniqueValue,"0");
        return uniqueValue.toLowerCase();
    }
	
	public static String getCurrentTimeMinusSecond(){
		Timestamp timestamp = new Timestamp(new Date().getTime());
	    SimpleDateFormat formatedNow = new SimpleDateFormat("hh:mma");
	    
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(timestamp.getTime());
	 
	    cal.add(Calendar.SECOND, -60);
	    timestamp = new Timestamp(cal.getTime().getTime());
	    
	    String uniqueValue = formatedNow.format(timestamp);
	    uniqueValue = StringUtils.stripStart(uniqueValue,"0");
        return uniqueValue.toLowerCase();
    }
	
	public void cleanUpFolder(String directoryPath) throws IOException {
		
		File file = new File(directoryPath);
		FileUtils.cleanDirectory(file); 
	}
}
