package fr.ul.miage.gl.restaurant;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;

import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.CompositionDish;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import fr.ul.miage.gl.restaurant.pojo.dishes.exceptions.NegativeStockException;
import fr.ul.miage.gl.restaurant.pojo.orders.Order;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestStockRawMaterials {

	@Test
	@DisplayName("Ensure stocks decrement doesn't throw NegativeStockException if no required")
	public void testNoNegativeStocks() {

		final Category cat = new Category("Test");
		final Dish dish = new Dish(cat, "Test", 10.0);
		final RawMaterial raw = new RawMaterial("Test", 100);
		final CompositionDish compo = new CompositionDish(dish, raw, 1);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			cat.save();
			dish.save();
			raw.save();
			compo.save();
			
			ArrayList<Dish> dishs = new ArrayList<Dish>();
			dishs.add(dish);

			assertDoesNotThrow(() -> dish.decrementStock());
			
			

		});
	}
	
	@Test
	@DisplayName("Ensure stocks are always >= 0 and throw NegativeStockException")
	public void testNegativeStocks() {

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
			
			ArrayList<Dish> dishs = new ArrayList<Dish>();
			dishs.add(dish);

			assertThrows(NegativeStockException.class, () -> dish.decrementStock());
			
			

		});
	}
}
