package fr.ul.miage.gl.restaurant.pojo.dishes.finders;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<Dish> menuOfTheDay() {
		return query().where().eq("menu", true).findList();
	}

	public List<Dish> byCategoryEnoughStock(Category cat) {
		return query().where().eq("category", cat).findList().stream().filter(d -> d.enoughRawMaterial())
				.collect(Collectors.toList());
	}

}
