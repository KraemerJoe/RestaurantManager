package fr.ul.miage.gl.restaurant.waiter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;

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

public class TestDishIsServed {

	@Test
	@DisplayName("Ensure a dish is set as served")
	public void testDishIsSetAsServed() {

		final Category cat = new Category("Test");
		final Dish dish = new Dish(cat, "Test", 10.0);
		final RawMaterial raw = new RawMaterial("Test", 100);
		final CompositionDish compo = new CompositionDish(dish, raw, 10000);
		final TableRestaurant table = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		final SessionClient session = new SessionClient(table, new Date());
		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			cat.save();
			dish.save();
			raw.save();
			compo.save();
			table.save();
			session.save();
			Order order = session.createOrder();
			ArrayList<Dish> what = new ArrayList<Dish>();
			what.add(dish);
			
			order.populateWithDish(what);
			
			ArrayList<SessionOrder> ordersSession = new ArrayList<SessionOrder>();
			ordersSession.addAll(SessionOrder.find.getSessionOrdersOfOrder(order.getOrder_id()));
			
			for (SessionOrder sessionOrder : ordersSession) {
				assertEquals(sessionOrder.getStatut(), EnumSessionOrderStat.PENDING);
				sessionOrder.setReadyToServe();
			}
			
			for (SessionOrder sessionOrder : ordersSession) {
				sessionOrder.served();
				assertEquals(sessionOrder.getStatut(), EnumSessionOrderStat.SERVED);
			}
		});
	}
}
