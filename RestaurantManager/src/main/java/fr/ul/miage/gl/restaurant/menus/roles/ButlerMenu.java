package fr.ul.miage.gl.restaurant.menus.roles;

import fr.ul.miage.gl.restaurant.menus.Menu;

public class ButlerMenu extends Menu {

	public static ButlerMenu instance;

	public ButlerMenu() {
		super("Butler");
		instance = this;
	}

	@Override
	public void initMenuItems() {

	}

	@Override
	public void executeChoice(int choice) {

	}

	public static ButlerMenu getInstance() {
		if (instance == null)
			instance = new ButlerMenu();
		return instance;
	}

	public static void setInstance(ButlerMenu instance) {
		ButlerMenu.instance = instance;
	}

}
