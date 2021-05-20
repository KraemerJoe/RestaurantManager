package fr.ul.miage.gl.restaurant.pojo.orders;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.dishes.exceptions.NegativeStockException;
import fr.ul.miage.gl.restaurant.pojo.orders.enums.EnumOrderStat;
import fr.ul.miage.gl.restaurant.pojo.orders.finders.OrderFinder;
import fr.ul.miage.gl.restaurant.pojo.orders.finders.SessionClientFinder;
import io.ebean.Model;

@Entity
@Table(name = "\"ORDER\"")
public class Order extends Model{

	public static OrderFinder find = new OrderFinder();
	
	@Id
	protected long order_id;

	@ManyToOne
	@JoinColumn(name = "session_client_id")
	private SessionClient sessionClient;

	private Date date_creation;

	private Date date_completion;

	@Enumerated(EnumType.STRING)
	private EnumOrderStat statut;



	public Order(SessionClient sessionClient) {
		super();
		this.sessionClient = sessionClient;
		this.date_creation = new Date();
		this.statut = EnumOrderStat.PENDING;
	}

	public void populateWithDish(ArrayList<Dish> what) {
		for (Dish dish : what) {
			SessionOrder sessionOrder = new SessionOrder(dish, this, dish.isForChild());
			try {
				dish.decrementStock();
				sessionOrder.save();
			} catch (NegativeStockException e) {
				e.printStackTrace();
			}
		}
	}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public SessionClient getSessionClient() {
		return sessionClient;
	}

	public void setSessionClient(SessionClient sessionClient) {
		this.sessionClient = sessionClient;
	}

	public Date getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}

	public Date getDate_completion() {
		return date_completion;
	}

	public void setDate_completion(Date date_completion) {
		this.date_completion = date_completion;
	}

	public EnumOrderStat getStatut() {
		return statut;
	}

	public void setStatut(EnumOrderStat statut) {
		this.statut = statut;
	}

	
	

}