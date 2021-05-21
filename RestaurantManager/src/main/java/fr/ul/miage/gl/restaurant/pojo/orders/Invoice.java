package fr.ul.miage.gl.restaurant.pojo.orders;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.ebean.Model;

@Entity
@Table(name = "\"INVOICE\"")
public class Invoice extends Model{

	@Id
	protected long invoice_id;

	@ManyToOne
	@JoinColumn(name = "session_client_id")
	private SessionClient sessionClient;

	private double total_price;

	private Date date;
	
	private boolean lunch;

	public Invoice(SessionClient sessionClient, double total_price, boolean lunch) {
		super();
		this.sessionClient = sessionClient;
		this.total_price = total_price;
		this.date = new Date();
		this.lunch = lunch;
	}
	
	public static void createInvoice(SessionClient sessionClient, double total_price, boolean lunch) {
		Invoice invoice = new Invoice(sessionClient, total_price, lunch);
		invoice.save();
	}

	public long getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}

	public SessionClient getSessionClient() {
		return sessionClient;
	}

	public void setSessionClient(SessionClient sessionClient) {
		this.sessionClient = sessionClient;
	}

	public double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isLunch() {
		return lunch;
	}

	public void setLunch(boolean lunch) {
		this.lunch = lunch;
	}
	
	

}