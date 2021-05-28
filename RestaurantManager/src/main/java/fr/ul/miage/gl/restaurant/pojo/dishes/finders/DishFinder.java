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

	// retrouve tous les plats d'une catégorie
	public List<Dish> byCategory(Category cat) {
		return query().where().eq("category", cat).findList();
	}
	
	// retrouves tous les plats du menu du jour
	public List<Dish> menuOfTheDay() {
		return query().where().eq("menu", true).findList();
	}

	// retrouve toutes les catégories ou il y a assez de stock pour faire un plat minimum
	public List<Dish> byCategoryEnoughStock(Category cat) {
		return query().where().eq("category", cat).findList().stream().filter(d -> d.enoughRawMaterial())
				.collect(Collectors.toList());
	}

}
