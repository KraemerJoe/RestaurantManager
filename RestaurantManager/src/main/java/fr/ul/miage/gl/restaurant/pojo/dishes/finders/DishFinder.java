package fr.ul.miage.gl.restaurant.pojo.dishes.finders;

import java.util.List;

import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import io.ebean.Finder;

public class DishFinder extends Finder<Long, Dish> {

	public DishFinder() {
		super(Dish.class);
	}

	public List<Dish> byCategory(Category cat) {
		return query().where().eq("category", cat).findList();
	}

}
