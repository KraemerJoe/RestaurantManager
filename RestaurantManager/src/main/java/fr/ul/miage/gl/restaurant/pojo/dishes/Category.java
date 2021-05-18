package fr.ul.miage.gl.restaurant.pojo.dishes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.ebean.Model;
import io.ebean.annotation.NotNull;

@Entity
@Table(name = "\"CATEGORY\"")
public class Category extends Model{

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

}