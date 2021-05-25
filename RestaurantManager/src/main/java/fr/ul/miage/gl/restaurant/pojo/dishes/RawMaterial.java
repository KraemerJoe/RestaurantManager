package fr.ul.miage.gl.restaurant.pojo.dishes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.ul.miage.gl.restaurant.pojo.dishes.exceptions.NegativeStockException;
import fr.ul.miage.gl.restaurant.pojo.dishes.exceptions.StockOverFlowException;
import fr.ul.miage.gl.restaurant.pojo.dishes.finders.RawMaterialFinder;
import io.ebean.Model;
import io.ebean.annotation.NotNull;

@Entity
@Table(name = "\"RAW_MATERIAL\"")
public class RawMaterial extends Model {

	public static RawMaterialFinder find = new RawMaterialFinder();
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
	
	public void setNewStock(long stock) throws NegativeStockException, StockOverFlowException {
		if(stock > Long.MAX_VALUE) {
			throw new StockOverFlowException("Stocks cannot be larger than Long.MAX_VALUE");
		}
		else if(stock < 0) {
			throw new NegativeStockException("Stock must be positives");
		}else {
			this.stock = stock;
			save();
		}
	}
	
	public void decrementStock(long s) throws NegativeStockException, StockOverFlowException {
		setNewStock(stock - s);
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