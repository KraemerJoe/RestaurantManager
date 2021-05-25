package fr.ul.miage.gl.restaurant.pojo.orders.finders;

import java.util.List;

import fr.ul.miage.gl.restaurant.pojo.orders.Order;
import io.ebean.Finder;

public class OrderFinder extends Finder<Long, Order> {

	public OrderFinder() {
		super(Order.class);
	}

	public List<Order> getOrdersOfSession(long id) {
		return query().where().eq("session_client_id", id).findList();
	}

}
