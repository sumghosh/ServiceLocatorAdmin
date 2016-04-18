package com.lexmark;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class SimpleDateFormatExample {
	public static void main(String[] args) {

		Date curDate = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		String DateToStr = format.format(curDate);
		System.out.println(DateToStr);

		format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		DateToStr = format.format(curDate);
		System.out.println(DateToStr);

		format = new SimpleDateFormat("dd MMMM yyyy zzzz", Locale.ENGLISH);
		DateToStr = format.format(curDate);
		System.out.println(DateToStr);

		format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		DateToStr = format.format(curDate);
		System.out.println(DateToStr);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		String text = format.format(timestamp);

		System.out.println(text);

		try {
			Date strToDate = format.parse(DateToStr);
			//System.out.println(strToDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
