package fr.ul.miage.gl.restaurant.pojo.dishes.exceptions;

public class NegativeStockException extends Exception {

	/*
	 * Exception qui se lance si un stock passe en négatif
	 */
	public NegativeStockException(String errorMessage) {
		super(errorMessage);
	}
}