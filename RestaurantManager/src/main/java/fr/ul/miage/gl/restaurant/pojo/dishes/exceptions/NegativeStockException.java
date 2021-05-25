package fr.ul.miage.gl.restaurant.pojo.dishes.exceptions;

public class NegativeStockException extends Exception {
	
	public NegativeStockException(String errorMessage) {
		super(errorMessage);
	}
}