package fr.ul.miage.gl.restaurant.cooker;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.CompositionDish;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import fr.ul.miage.gl.restaurant.pojo.dishes.exceptions.NegativeStockException;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestStockDecrement {

	@Test
	@DisplayName("Ensure stocks are always >= 0 and throw NegativeStockException")
	public void testNegativeStocks() {

		final Category cat = new Category("Test");
		final Dish dish = new Dish(cat, "Test", 10.0);
		final RawMaterial raw = new RawMaterial("Test", 100);
		final CompositionDish compo = new CompositionDish(dish, raw, 10000);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			cat.save();
			dish.save();
			raw.save();
			compo.save();

			ArrayList<Dish> dishs = new ArrayList<Dish>();
			dishs.add(dish);

			assertThrows(NegativeStockException.class, () -> dish.decrementStock());

		});
	}
}
