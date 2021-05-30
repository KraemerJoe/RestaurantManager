package fr.ul.miage.gl.restaurant.pojo.dishes.exceptions;

public class StockOverFlowException extends Exception {

	
	public StockOverFlowException(String errorMessage) {
		super(errorMessage);
	}
}