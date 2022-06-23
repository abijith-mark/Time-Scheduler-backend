package com.mark.waif.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

@Service
public class DataService {
	
	public Date stringToDate(String str) {
    	Date date = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try{
            date = df.parse(str);
        }
        catch ( Exception ex ){
            System.out.println(ex);
        }
        return date;
    }
	
    public Date stringToDatePar(String str) {
    	Date date = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try{
            date = df.parse(str);
        }
        catch ( Exception ex ){
            System.out.println(ex);
        }
        return date;
    }
	
    public String dateToString(Date d) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    	return formatter.format(d);
    }
    
}
