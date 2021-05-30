package fr.ul.miage.gl.restaurant.cooker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.CompositionDish;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import fr.ul.miage.gl.restaurant.pojo.orders.Order;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;
import fr.ul.miage.gl.restaurant.pojo.orders.enums.EnumSessionOrderStat;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestDishQueue {

	@Test
	@DisplayName("Ensure the queue is first for child")
	public void testDishQueueChildFirst() {

		final Category cat = new Category("Test");
		final Dish dish = new Dish(cat, "Test", 10.0);
		final Dish dish2 = new Dish(cat, "Test", 10.0);
		final RawMaterial raw = new RawMaterial("Test", 100);
		final CompositionDish compo = new CompositionDish(dish, raw, 1);
		final TableRestaurant table = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		final SessionClient session = new SessionClient(table, new Date());
		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			List<SessionOrder> orders = new ArrayList<SessionOrder>();
			orders = SessionOrder.find.pendingOrdersWithChildFirst();
			for (SessionOrder sessionOrder : orders) {
				sessionOrder.setReadyToServe();
				sessionOrder.save();
			}
			cat.save();
			dish.save();
			dish2.save();
			raw.save();
			compo.save();
			table.save();
			session.save();
			Order order = session.createOrder();
			ArrayList<Dish> what = new ArrayList<Dish>();
			what.add(dish);
			
			order.populateWithDish(what);
			

			orders = SessionOrder.find.pendingOrdersWithChildFirst();
			
			assertEquals(orders.size(), 1);
			
			ArrayList<Dish> what2 = new ArrayList<Dish>();
			dish2.setForChild(true);
			what2.add(dish2);
			order.populateWithDish(what2);
			orders = SessionOrder.find.pendingOrdersWithChildFirst();
			assertEquals(orders.size(), 2);
			assertEquals(orders.get(0).isChild(), true); // premier de la file est pour les enfants
			
			order.populateWithDish(what);
			orders = SessionOrder.find.pendingOrdersWithChildFirst();
			assertEquals(orders.size(), 3); // taille 3 mais..
			assertEquals(orders.get(0).isChild(), true); // .. toujours premier de la file est pour les enfants
			
		});
	}
}
