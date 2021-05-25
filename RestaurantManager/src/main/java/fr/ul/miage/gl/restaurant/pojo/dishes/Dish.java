package fr.ul.miage.gl.restaurant.pojo.dishes;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import fr.ul.miage.gl.restaurant.pojo.dishes.exceptions.NegativeStockException;
import fr.ul.miage.gl.restaurant.pojo.dishes.exceptions.StockOverFlowException;
import fr.ul.miage.gl.restaurant.pojo.dishes.finders.DishFinder;
import io.ebean.Model;

@Entity
@Table(name = "\"DISH\"")
public class Dish extends Model {

	public static final DishFinder find = new DishFinder();

	@Id
	protected long dish_id;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	private String name;

	private double price;

	@Transient
	private boolean forChild;
	
	private boolean menu;

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

	public String getComposition() {
		String compo = "(";
		ArrayList<CompositionDish> composition = new ArrayList<CompositionDish>();
		composition.addAll(CompositionDish.finder.compositionOfDish(this));
		for (CompositionDish compositionDish : composition) {
			compo = compo + compositionDish.getRawMaterial().getName() + " x" + compositionDish.getQuantity() + " | ";
		}
		return (compo + ")").replace(" | )", ")");
	}

	public boolean enoughRawMaterial() {
		ArrayList<CompositionDish> composition = new ArrayList<CompositionDish>();
		composition.addAll(CompositionDish.finder.compositionOfDish(this));

		for (CompositionDish compositionDish : composition) {
			RawMaterial material = compositionDish.getRawMaterial();
			if (material.getStock() < compositionDish.getQuantity())
				return false;
		}

		return true;
	}

	public void decrementStock() throws NegativeStockException, StockOverFlowException {
		ArrayList<CompositionDish> composition = new ArrayList<CompositionDish>();
		composition.addAll(CompositionDish.finder.compositionOfDish(this));

		for (CompositionDish compositionDish : composition) {
			RawMaterial material = compositionDish.getRawMaterial();
			material.decrementStock(compositionDish.getQuantity());
		}
	}
	
	public void setMenuOfTheDay(boolean yesOrNo) {
		this.menu = yesOrNo;
		save();
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

	public boolean isMenuOfTheDay() {
		return menu;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	

}