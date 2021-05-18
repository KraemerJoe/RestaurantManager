package fr.ul.miage.gl.restaurant.pojo.dishes;

import io.ebean.Finder;

public class CategoryFinder extends Finder<Long, Category> {

	public CategoryFinder() {
		super(Category.class);
	}

	public Category byUniqueName(String name) {

		return query().where().eq("name", name).findOne();
	}

}
