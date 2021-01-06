package com.audit.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public DateUtils() {}
	
	public static Date convertStringToDate(String format,String dateInString) throws ParseException{
	    return new SimpleDateFormat(format).parse(dateInString);
	}
	
	public static boolean isDateSmallerThanToday(Date endDate){
		if (new Date().compareTo(endDate) > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) throws ParseException {
		String endDate = "2019-01-15";
		Date e = convertStringToDate("yyyy-MM-dd",endDate);
		System.out.println(isDateSmallerThanToday(e));
	}
	
}