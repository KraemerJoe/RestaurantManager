package fr.ul.miage.gl.restaurant.pojo.dishes;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import fr.ul.miage.gl.restaurant.pojo.dishes.exceptions.NegativeStockException;
import fr.ul.miage.gl.restaurant.pojo.dishes.finders.DishFinder;
import io.ebean.Model;

@Entity
@Table(name = "\"DISH\"")
public class Dish extends Model{

	public static final DishFinder find = new DishFinder();
	
	@Id
	protected long dish_id;

	@ManyToOne @JoinColumn(name = "category_id")
	private Category category;
	
	private String name;
	
	private double price;

	@Transient
	private boolean forChild;
	
	public Dish(long dish_id) {
		super();
		this.dish_id = dish_id;
	}

	public Dish(Category category, String name, double price) {
		super();
		this.category = category;
		this.name = name;
		this.price = price;
	}
	
	public boolean enoughRawMaterial() {
		ArrayList<CompositionDish> composition = new ArrayList<CompositionDish>();
		composition.addAll(CompositionDish.finder.compositionOfDish(this));
		
		for (CompositionDish compositionDish : composition) {
			RawMaterial material = compositionDish.getRawMaterial();
			if(material.getStock() < compositionDish.getQuantity()) return false;
		}
		
		return true;
	}

	public void decrementStock() throws NegativeStockException {
		ArrayList<CompositionDish> composition = new ArrayList<CompositionDish>();
		composition.addAll(CompositionDish.finder.compositionOfDish(this));
		
		for (CompositionDish compositionDish : composition) {
			RawMaterial material = compositionDish.getRawMaterial();
			if(material.getStock()-compositionDish.getQuantity() < 0) {
				throw new NegativeStockException("Stock for " + material.getName() + " can't be negative !");
			}
		}
	}
	
	public long getDish_id() {
		return dish_id;
	}

	public void setDish_id(long dish_id) {
		this.dish_id = dish_id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isForChild() {
		return forChild;
	}

	public void setForChild(boolean forChild) {
		this.forChild = forChild;
	}

	
	
	

}