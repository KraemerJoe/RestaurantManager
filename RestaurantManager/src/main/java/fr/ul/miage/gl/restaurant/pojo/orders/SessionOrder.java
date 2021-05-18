package fr.ul.miage.gl.restaurant.pojo.orders;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import io.ebean.Model;

@Entity
@Table(name = "\"SESSION_ORDER\"")
public class SessionOrder extends Model{

	@Id
	protected long session_order_id;

	@ManyToOne
	@JoinColumn(name = "dish_id")
	private Dish dish;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	private Date date_creation;

	private Date date_completion;

	public SessionOrder(Dish dish, Order order, boolean child) {
		super();
		this.dish = dish;
		this.order = order;
		this.child = child;
		this.date_creation = new Date();
		this.statut = EnumSessionOrderStat.PENDING;
	}

	private boolean child;

	@Enumerated(EnumType.STRING)
	private EnumSessionOrderStat statut;

	public void setReadyToServe() {
		statut = EnumSessionOrderStat.READY_TO_SERVE;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public boolean isChild() {
		return child;
	}

	public void setChild(boolean child) {
		this.child = child;
	}

	public EnumSessionOrderStat getStatut() {
		return statut;
	}

	public void setStatut(EnumSessionOrderStat statut) {
		this.statut = statut;
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

	public long getSession_order_id() {
		return session_order_id;
	}

	public void setSession_order_id(long session_order_id) {
		this.session_order_id = session_order_id;
	}

}