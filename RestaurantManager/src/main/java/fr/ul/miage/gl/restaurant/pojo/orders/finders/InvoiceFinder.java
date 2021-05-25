package fr.ul.miage.gl.restaurant.pojo.orders.finders;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import fr.ul.miage.gl.restaurant.pojo.orders.Invoice;
import io.ebean.Finder;

public class InvoiceFinder extends Finder<Long, Invoice> {

	private LocalDate today = LocalDate.now();
	public InvoiceFinder() {
		super(Invoice.class);
	}

	public List<Invoice> ofTheDay(){
		// today    
		Calendar date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);

		Calendar tmrw = new GregorianCalendar();
		tmrw.add(Calendar.DAY_OF_MONTH, 1);
		
        return query().where().lt("date", tmrw.getTime()).ge("date", date.getTime()).findList();
	}

	public List<Invoice> ofTheWeek(){
	    LocalDate monday = today;
	    while (monday.getDayOfWeek() != DayOfWeek.MONDAY)
	    {
	      monday = monday.minusDays(1);
	    }


	    LocalDate sunday = today;
	    while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY)
	    {
	      sunday = sunday.plusDays(1);
	    }
	    
	    return query().where().le("date", sunday).ge("date", monday).findList();
	}

	public List<Invoice> ofTheMonth(){
		 LocalDate firstDay = today.with(TemporalAdjusters.firstDayOfMonth());
		    LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());
		    
		    return query().where().le("date", lastDay).ge("date", firstDay).findList();
	}

}
