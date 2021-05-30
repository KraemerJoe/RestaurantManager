package fr.ul.miage.gl.restaurant.pojo.dishes;

import java.util.ArrayList;

public class PreparationTimeDish {

	/*
	 * Classe permettant le calcul de statistique sur le temps de préparation d'un plat
	 */
	private Dish dish;
	private ArrayList<Long> timeDish = new ArrayList<Long>();

	public PreparationTimeDish(Dish dish) {
		super();
		this.dish = dish;
	}

	public PreparationTimeDish(Dish dish, ArrayList<Long> timeDish) {
		super();
		this.dish = dish;
		this.timeDish = timeDish;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public ArrayList<Long> getTimeDish() {
		return timeDish;
	}

	public void setTimeDish(ArrayList<Long> timeDish) {
		this.timeDish = timeDish;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dish == null) ? 0 : dish.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreparationTimeDish other = (PreparationTimeDish) obj;
		if (dish == null) {
			if (other.dish != null)
				return false;
		} else if (!(dish.getDish_id() == (other.dish.getDish_id())))
			return false;
		return true;
	}

}