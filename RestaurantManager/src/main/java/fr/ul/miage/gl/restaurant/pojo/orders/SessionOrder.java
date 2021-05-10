package fr.ul.miage.gl.restaurant.pojo.orders;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;

@Entity
@Table(name = "\"SESSION_ORDER\"")
public class SessionOrder {

	@ManyToOne @JoinColumn(name = "dish_id")
	private Dish dish;
	
	@ManyToOne @JoinColumn(name = "order_id")
	private Order order;
	
	
	
	public SessionOrder(Dish dish, Order order, boolean child) {
		super();
		this.dish = dish;
		this.order = order;
		this.child = child;
		this.statut = EnumSessionOrderStat.PENDING;
	}

	private boolean child;	

	@Enumerated(EnumType.STRING)
	private EnumSessionOrderStat statut;

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
	
	
	
	
	

}