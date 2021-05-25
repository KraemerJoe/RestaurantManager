package fr.ul.miage.gl.restaurant.pojo.dishes;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.ul.miage.gl.restaurant.pojo.dishes.finders.CategoryFinder;
import fr.ul.miage.gl.restaurant.util.MenuUtil;
import io.ebean.Model;
import io.ebean.annotation.NotNull;

@Entity
@Table(name = "\"CATEGORY\"")
public class Category extends Model {

	public static final CategoryFinder find = new CategoryFinder();

	@Id
	protected long category_id;

	@NotNull
	private String name;

	public Category(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Category askForACategory() {
		List<Category> categories = Category.find.all();

		int compteur = 0;
		for (Category c : categories) {
			System.out.println("[" + compteur + "] " + c.getName());
			compteur++;
		}

		int categoryId = MenuUtil.askForPositiveInt("Which category do you want ?");

		if (categories.size() <= categoryId || categories.get(categoryId) == null) {
			System.out.println("This category doesn't exist.");
			return null;
		} else {
			Category c = categories.get(categoryId);
			return c;
		}
	}

}