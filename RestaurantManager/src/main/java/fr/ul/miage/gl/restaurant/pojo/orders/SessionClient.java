package fr.ul.miage.gl.restaurant.pojo.orders;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ul.miage.gl.restaurant.pojo.orders.finders.SessionClientFinder;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import io.ebean.Model;

@Entity
@Table(name = "\"SESSION_CLIENT\"")
public class SessionClient extends Model{

	public static SessionClientFinder find = new SessionClientFinder();

	@Id
	protected long session_client_id;

	@ManyToOne
	@JoinColumn(name = "table_id")
	private TableRestaurant table_id;

	private Date date_arrival;

	private Date date_release;

	public Order createOrder() {
		Order order = new Order(this);
		order.save();
		return order;
	}
	
	public SessionClient(TableRestaurant table_id, Date date_arrival) {
		super();
		this.table_id = table_id;
		this.date_arrival = date_arrival;
	}
	
	public SessionClient(long session_client_id, TableRestaurant table_id, Date date_arrival, Date date_release) {
		super();
		this.session_client_id = session_client_id;
		this.table_id = table_id;
		this.date_arrival = date_arrival;
		this.date_release = date_release;
	}
	
	public void terminate() {
		date_release = new Date();
		this.save();
		
		table_id.setToClean();
		table_id.save();
	}
	

	public SessionClient(long session_client_id) {
		super();
		this.session_client_id = session_client_id;
	}

	public long getSession_client_id() {
		return session_client_id;
	}

	public void setSession_client_id(long session_client_id) {
		this.session_client_id = session_client_id;
	}

	public TableRestaurant getTable_id() {
		return table_id;
	}

	public void setTable_id(TableRestaurant table_id) {
		this.table_id = table_id;
	}

	public Date getDate_arrival() {
		return date_arrival;
	}

	public void setDate_arrival(Date date_arrival) {
		this.date_arrival = date_arrival;
	}

	public Date getDate_release() {
		return date_release;
	}

	public void setDate_release(Date date_release) {
		this.date_release = date_release;
	}





}