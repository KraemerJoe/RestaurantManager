package fr.ul.miage.gl.restaurant.pojo.dishes.finders;

import java.util.List;

import fr.ul.miage.gl.restaurant.pojo.dishes.CompositionDish;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import io.ebean.Finder;

public class CompositionDishFinder extends Finder<Long, CompositionDish> {

	public CompositionDishFinder() {
		super(CompositionDish.class);
	}

	//permet de retourner la liste de composition d'un plat
	public List<CompositionDish> compositionOfDish(Dish dish) {
		return query().where().eq("dish", dish).findList();
	}

}
