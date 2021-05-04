package fr.ul.miage.gl.restaurant.menus.roles;

import fr.ul.miage.gl.restaurant.menus.Menu;

public class WaiterMenu extends Menu {

	public static WaiterMenu instance;
	
	public WaiterMenu() {
		super("Waiter");
		instance = this;
	}

	@Override
	public void initMenuItems() {

	}

	@Override
	public void executeChoice(int choice) {

	}
	
	public static WaiterMenu getInstance() {
		if (instance == null)
			instance = new WaiterMenu();
		return instance;
	}

	public static void setInstance(WaiterMenu instance) {
		WaiterMenu.instance = instance;
	}

}
