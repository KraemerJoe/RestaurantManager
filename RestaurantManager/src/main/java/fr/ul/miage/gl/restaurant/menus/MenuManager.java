package fr.ul.miage.gl.restaurant.menus;

import java.util.ArrayList;

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
	}

	public static MenuManager getInstance() {
		return instance;
	}

	public static void setInstance(MenuManager instance) {
		MenuManager.instance = instance;
	}
	
	

}
