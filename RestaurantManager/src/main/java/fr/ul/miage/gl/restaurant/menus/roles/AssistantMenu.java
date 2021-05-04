package fr.ul.miage.gl.restaurant.menus.roles;

import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.MenuLogin;

public class AssistantMenu extends Menu {

	public static AssistantMenu instance;
	
	public AssistantMenu() {
		super("Assistant");
		instance = this;
	}

	@Override
	public void initMenuItems() {

	}

	@Override
	public void executeChoice(int choice) {

	}
	
	public static AssistantMenu getInstance() {
		if (instance == null) instance = new AssistantMenu();
		return instance;
	}

	public static void setInstance(AssistantMenu instance) {
		AssistantMenu.instance = instance;
	}

}
