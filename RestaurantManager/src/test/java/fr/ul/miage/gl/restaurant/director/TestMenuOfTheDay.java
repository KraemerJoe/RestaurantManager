package fr.ul.miage.gl.restaurant.director;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestMenuOfTheDay {

	@Test
	@DisplayName("Ensure director can add to the menu of the day")
	public void testCanAddMenuOfTheDay() {

		final Category cat = new Category("test");
		final Dish dish = new Dish(cat, "test", 2.0);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			cat.save();
			dish.save();

			assertEquals(dish.isMenuOfTheDay(), false);
			dish.setMenuOfTheDay(true);
			assertEquals(dish.isMenuOfTheDay(), true);
		});
	}

	@Test
	@DisplayName("Ensure director can remove to the menu of the day")
	public void testCanRemoveMenuOfTheDay() {

		final Category cat = new Category("test");
		final Dish dish = new Dish(cat, "test", 2.0);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			cat.save();
			dish.save();

			assertEquals(dish.isMenuOfTheDay(), false);
			dish.setMenuOfTheDay(true);
			assertEquals(dish.isMenuOfTheDay(), true);
			dish.setMenuOfTheDay(false);
			assertEquals(dish.isMenuOfTheDay(), false);
		});
	}

}
