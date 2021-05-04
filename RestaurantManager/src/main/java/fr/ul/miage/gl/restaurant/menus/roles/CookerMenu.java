package fr.ul.miage.gl.restaurant.menus.roles;

import fr.ul.miage.gl.restaurant.menus.Menu;

public class CookerMenu extends Menu {

	public static CookerMenu instance;
	
	public CookerMenu() {
		super("Cooker");
		instance = this;
	}

	@Override
	public void initMenuItems() {

	}

	@Override
	public void executeChoice(int choice) {

	}
	
	public static CookerMenu getInstance() {
		if (instance == null)
			instance = new CookerMenu();
		return instance;
	}

	public static void setInstance(CookerMenu instance) {
		CookerMenu.instance = instance;
	}

}
