package fr.ul.miage.gl.restaurant.managers;

import java.util.ArrayList;

import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.MenuLogin;
import fr.ul.miage.gl.restaurant.menus.roles.AssistantMenu;
import fr.ul.miage.gl.restaurant.menus.roles.ButlerMenu;
import fr.ul.miage.gl.restaurant.menus.roles.CookerMenu;
import fr.ul.miage.gl.restaurant.menus.roles.DirectorMenu;
import fr.ul.miage.gl.restaurant.menus.roles.WaiterMenu;
import fr.ul.miage.gl.restaurant.menus.roles.waiter.WaiterTableMenu;

public class MenuManager {
	
	public static MenuManager instance;
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	
	public MenuManager() {
		instance = this;
		initMenus();
		initMenusItems();
	}
	 
	private void initMenusItems() {
		for (Menu menu : menus) {
			menu.initMenuItems();
		}
	}
	
	public void initMenus() {
		menus.add(new MenuLogin());
		menus.add(new AssistantMenu());
		menus.add(new ButlerMenu());
		menus.add(new CookerMenu());
		menus.add(new DirectorMenu());
		menus.add(new WaiterMenu());
		menus.add(new WaiterTableMenu());
	}

	public static MenuManager getInstance() {
		return instance;
	}

	public static void setInstance(MenuManager instance) {
		MenuManager.instance = instance;
	}
	
	

}
