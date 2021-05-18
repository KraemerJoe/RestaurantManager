package fr.ul.miage.gl.restaurant.pojo.dishes;

import io.ebean.Finder;

public class RawMaterialFinder extends Finder<Long, RawMaterial> {

	public RawMaterialFinder() {
		super(RawMaterial.class);
	}

	public RawMaterial byUniqueName(String name) {

		return query().where().eq("name", name).findOne();
	}

	

}
