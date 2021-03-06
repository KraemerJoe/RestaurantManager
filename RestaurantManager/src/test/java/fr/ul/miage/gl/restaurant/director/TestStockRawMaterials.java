package fr.ul.miage.gl.restaurant.director;

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
	@DisplayName("Ensure stocks > Long.MAX_VALUE throw NegativeStockException")
	public void testStockLargerThanLongMaxValueIsNegative() {

		final RawMaterial raw = new RawMaterial("Test", 100);
		

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			raw.save();
			assertThrows(NegativeStockException.class, () -> raw.setNewStock(Long.MAX_VALUE+1));

		});
	}
	

}
