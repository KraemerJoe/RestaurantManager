package fr.ul.miage.gl.restaurant.waiter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestMenuOfTheDay {

	@Test
	@DisplayName("Ensure menu of the day return exact number of dishs")
	public void testCanAddMenuOfTheDay() {

		final Category cat = new Category("test");
		final Dish dish = new Dish(cat, "test", 2.0);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			cat.save();
			dish.save();
			
			ArrayList<Dish> dishs = new ArrayList<Dish>();
			dishs.addAll(Dish.find.menuOfTheDay());
			assertEquals(dishs.size(), 0);

			assertEquals(dish.isMenuOfTheDay(), false);
			dish.setMenuOfTheDay(true);
			assertEquals(dish.isMenuOfTheDay(), true);
			dishs.addAll(Dish.find.menuOfTheDay());
			assertEquals(dishs.size(), 1);

		});
	}
}
