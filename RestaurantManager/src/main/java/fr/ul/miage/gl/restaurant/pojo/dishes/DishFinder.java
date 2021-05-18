package fr.ul.miage.gl.restaurant.pojo.dishes;

import java.util.List;

import io.ebean.Finder;

public class DishFinder extends Finder<Long, Dish> {

	public DishFinder() {
		super(Dish.class);
	}

	public Dish byUniqueName(String name) {

		return query().where().eq("name", name).findOne();
	}
	
	public List<Dish> byCategory(Category cat){
		return query().where().eq("category", cat).findList();
	}

}
