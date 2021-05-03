package fr.ul.miage.gl.restaurant.pojo.dishes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "\"DISH\"")
public class Dish {

	@Id
	protected long dish_id;

	@ManyToOne @JoinColumn(name = "category_id")
	private Category category;
	
	private String name;
	
	private double price;

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
	
	

}