package fr.ul.miage.gl.restaurant.cooker;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestCreateCategoryAndDish {

	@Test
	@DisplayName("Ensure category is created")
	public void testCreateCategory() {

		final Category cat = new Category("TestCreateCat");

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {
			
			assertNull(Category.find.getByName(cat.getName()));
			cat.save();
			assertEquals(Category.find.getByName(cat.getName()), cat);

		});
	}
	
	@Test
	@DisplayName("Ensure dish is created")
	public void testCreateDish() {

		final Category cat = new Category("TestCreate");
		final Dish dish = new Dish(cat, "Yo", 10);
		
		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {
			
			cat.save();
			assertNull(Dish.find.byId(dish.getDish_id()));
			dish.save();
			assertEquals(Dish.find.byId(dish.getDish_id()), dish);

		});
	}
}
