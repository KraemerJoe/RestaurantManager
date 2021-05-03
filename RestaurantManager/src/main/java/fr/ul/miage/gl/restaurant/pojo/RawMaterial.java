package fr.ul.miage.gl.restaurant.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.ebean.annotation.NotNull;

@Entity
@Table(name = "\"RAW_MATERIAL\"")
public class RawMaterial {

	@Id
	protected long raw_material_id;
	
	@NotNull
	private String name;
	
	@NotNull
	private long stock;

	public RawMaterial(String name, long stock) {
		super();
		this.name = name;
		this.stock = stock;
	}

	public RawMaterial(long raw_material_id) {
		super();
		this.raw_material_id = raw_material_id;
	}

	public long getRaw_material_id() {
		return raw_material_id;
	}

	public void setRaw_material_id(long raw_material_id) {
		this.raw_material_id = raw_material_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	

}