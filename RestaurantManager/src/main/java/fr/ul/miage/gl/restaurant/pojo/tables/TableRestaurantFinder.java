package fr.ul.miage.gl.restaurant.pojo.tables;

import java.util.List;

import io.ebean.Finder;

public class TableRestaurantFinder extends Finder<Long, TableRestaurant> {

	public TableRestaurantFinder() {
		super(TableRestaurant.class);
	}

	public TableRestaurant byUniqueName(String name) {

		return query().where().eq("name", name).findOne();
	}

	public List<TableRestaurant> byName(String name) {

		return query().where().eq("name", name).findList();
	}
}
