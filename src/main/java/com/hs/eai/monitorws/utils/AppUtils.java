package com.hs.eai.monitorws.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AppUtils {

	private static AppUtils instance = null;

	private final static String BIRTHDATY_DATE_FORMAT = "dd-MM-yyyy";
	private final static String ACCOUNT_URI_TEMPLATE = "account.uri.temmplate";
    private final static long  ONE_DAY_IN_MILLIS = 86400000;//millisecs
    
	protected AppUtils() {
		// Exists only to defeat instantiation.
	}

	public static AppUtils getInstance() {
		if (instance == null) {
			instance = new AppUtils();
		}
		return instance;
	}

	/**
	 * 
	 * @return current date
	 */
	public java.sql.Date getDateFromString(String date) {

		java.util.Date javaDate = null;
		java.sql.Date sqlDate = null;
		try {

			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			javaDate = format.parse(date);
			sqlDate = new java.sql.Date(javaDate.getTime());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		
		return sqlDate;

	}
	public String ConvertDateToDateTime(String date){

		return ConvertDateToPreferedFormat(getDateFromString(date));
	}
	public String ConvertDateToPreferedFormat(java.sql.Date date) {

		    String dateTime = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			final GregorianCalendar gc = new GregorianCalendar();
			gc.setTime( date );
		    gc.set( Calendar.HOUR_OF_DAY, 0 );
		    gc.set( Calendar.MINUTE, 0 );
		    gc.set( Calendar.SECOND, 0 );
		    gc.set( Calendar.MILLISECOND, 0 );
		   
		    Date afterAddingTenMins = new Date(gc.getTimeInMillis());
			dateTime = format.format(afterAddingTenMins);
			
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		return dateTime;

	}
	public String ConstructDatePlusOneDayMinOneSecond(java.sql.Date date){
		
		String dateTime = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long t= date.getTime();
			Date afterAddingTenMins = new Date(t + ( ONE_DAY_IN_MILLIS - 1));
			dateTime = format.format(afterAddingTenMins);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return dateTime;
	}
	
	
	public Double StringToDoubleFormat(String number) {

		double hours;
		
		try{
			number = number.replace(",", ".");
			hours = Double.parseDouble(number);
		}catch(NumberFormatException ex){
			System.out.println("Number format exception: can not format "+number +"to double");
			return null;
		}

		return hours;
	}
	public Integer StringToInteger(String number){
		
		Integer numberId = null ;
		
		try{
			 numberId = Integer.parseInt(number);
		}catch(NumberFormatException ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return numberId;
	}
}
