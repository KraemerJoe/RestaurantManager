package fr.ul.miage.gl.restaurant.pojo.dishes.finders;

import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import io.ebean.Finder;

public class RawMaterialFinder extends Finder<Long, RawMaterial> {

	public RawMaterialFinder() {
		super(RawMaterial.class);
	}

}
