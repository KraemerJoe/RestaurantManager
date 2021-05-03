package fr.ul.miage.gl.restaurant;

import java.util.List;

import fr.ul.miage.gl.restaurant.ebean.EbeanManager;
import fr.ul.miage.gl.restaurant.pojo.Category;

public class RestaurantManager {

	public static void main(String[] args) {
		System.out.println("Restaurant Manager Sprint 2");
		initManagers();
		createDBConnection();
		
		if(EbeanManager.getInstance().getDb() != null) {
			System.out.println("DB Connected !");
			List<Category> c = EbeanManager.getInstance().getDb().find(Category.class).findList();
			for (Category category : c) {
				System.out.println(category.getName());
			}
		}else {
			System.err.println("DB Not Connected, exit.");
			System.exit(0);
		}
	}

	private static void createDBConnection() {
		EbeanManager.getInstance().initDB();
	}

	private static void initManagers() {
		new EbeanManager();
	}

}
