package fr.ul.miage.gl.restaurant.pojo.orders;

import javax.persistence.Entity;
import javax.persistence.Id;
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
	
	private boolean child;	

	private String statut;	

	
	
	

}