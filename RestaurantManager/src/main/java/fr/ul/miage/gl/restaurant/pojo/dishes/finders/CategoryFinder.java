package fr.ul.miage.gl.restaurant.pojo.dishes.finders;

import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import io.ebean.Finder;

public class CategoryFinder extends Finder<Long, Category> {

	public CategoryFinder() {
		super(Category.class);
	}

}
