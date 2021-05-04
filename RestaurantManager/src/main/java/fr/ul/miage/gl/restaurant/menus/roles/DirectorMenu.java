package fr.ul.miage.gl.restaurant.menus.roles;

import fr.ul.miage.gl.restaurant.menus.Menu;

public class DirectorMenu extends Menu {

	public static DirectorMenu instance;
	
	public DirectorMenu() {
		super("Director");
		instance = this;
	}

	@Override
	public void initMenuItems() {

	}

	@Override
	public void executeChoice(int choice) {

	}
	
	public static DirectorMenu getInstance() {
		if (instance == null)
			instance = new DirectorMenu();
		return instance;
	}

	public static void setInstance(DirectorMenu instance) {
		DirectorMenu.instance = instance;
	}

}
