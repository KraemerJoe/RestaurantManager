package fr.ul.miage.gl.restaurant;

import fr.ul.miage.gl.restaurant.ebean.EbeanManager;
import fr.ul.miage.gl.restaurant.managers.MenuManager;
import fr.ul.miage.gl.restaurant.managers.SessionManager;
import fr.ul.miage.gl.restaurant.menus.MenuLogin;
import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import fr.ul.miage.gl.restaurant.pojo.staff.finders.StaffFinder;
import fr.ul.miage.gl.restaurant.pojo.tables.TableAssignment;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;

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
