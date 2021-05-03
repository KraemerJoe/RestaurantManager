package fr.ul.miage.gl.restaurant;

import fr.ul.miage.gl.restaurant.ebean.EbeanManager;
import fr.ul.miage.gl.restaurant.managers.MenuManager;
import fr.ul.miage.gl.restaurant.managers.SessionManager;
import fr.ul.miage.gl.restaurant.menus.MenuLogin;

public class RestaurantManager {

	public static void main(String[] args) {
		
		initManagers();
		createDBConnection();
		
		if(EbeanManager.getInstance().getDb() != null) {
			System.out.println("DB Connected !");
		}else {
			System.err.println("DB Not Connected, exit.");
			System.exit(0);
		}
		
		MenuLogin.getInstance().show();
		
	}

	private static void createDBConnection() {
		EbeanManager.getInstance().initDB();
	}

	private static void initManagers() {
		new EbeanManager();
		new MenuManager();
		new SessionManager();
	}

}
