package fr.ul.miage.gl.restaurant.pojo.dishes;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ul.miage.gl.restaurant.pojo.dishes.finders.CompositionDishFinder;
import io.ebean.Model;

@Entity
@Table(name = "\"COMPOSITION_DISH\"")
public class CompositionDish extends Model{

	public static CompositionDishFinder finder = new CompositionDishFinder();
	
	@ManyToOne @JoinColumn(name = "dish_id")
	private Dish dish;
	
	@ManyToOne @JoinColumn(name = "raw_material_id")
	private RawMaterial raw_material;
	
	private int quantity;	

	public CompositionDish(Dish dish, RawMaterial raw_material, int quantity) {
		super();
		this.dish = dish;
		this.raw_material = raw_material;
		this.quantity = quantity;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public RawMaterial getRawMaterial() {
		return raw_material;
	}

	public void setRawMaterial(RawMaterial raw_material) {
		this.raw_material = raw_material;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	
	

}