package fr.ul.miage.gl.restaurant.waiter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.CompositionDish;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import fr.ul.miage.gl.restaurant.pojo.orders.Invoice;
import fr.ul.miage.gl.restaurant.pojo.orders.Order;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestWaiterCreateInvoice {

	@Test
	@DisplayName("Ensure table is set as TO_CLEAN if invoice created")
	public void testTableIsSetToToCleanIfInvoice() {

		final Category cat = new Category("Test");
		final Dish dish = new Dish(cat, "Test", 1.0);
		final RawMaterial raw = new RawMaterial("Test", 100);
		final CompositionDish compo = new CompositionDish(dish, raw, 1);
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
			table.createInvoice(true);
			session.terminate();
			assertEquals(session.getTable_id().getStatut(), EnumTableStat.TO_CLEAN);
			
		});
	}
	
	@Test
	@DisplayName("Ensure no invoice created if total is null")
	public void testNoInvoiceIfTotalNull() {

		final Category cat = new Category("Test");
		final Dish dish = new Dish(cat, "Test", 0.0);
		final RawMaterial raw = new RawMaterial("Test", 100);
		final CompositionDish compo = new CompositionDish(dish, raw, 1);
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
			
			assertNull(Invoice.find.getBySessionClient(session));
			
			order.populateWithDish(what);
			table.createInvoice(true);
			
			assertNull(Invoice.find.getBySessionClient(session));
			
			
		});
	}
	
	@Test
	@DisplayName("Ensure a an invoice is created")
	public void testCreateInvoice() {

		final Category cat = new Category("Test");
		final Dish dish = new Dish(cat, "Test", 10.0);
		final RawMaterial raw = new RawMaterial("Test", 100);
		final CompositionDish compo = new CompositionDish(dish, raw, 1);
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
			
			assertNull(Invoice.find.getBySessionClient(session));
			
			order.populateWithDish(what);
			table.createInvoice(true);
			
			assertNotNull(Invoice.find.getBySessionClient(session));
			
		});
	}
}
