package fr.ul.miage.gl.restaurant.menus.roles;

import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.MenuLogin;
import fr.ul.miage.gl.restaurant.menus.roles.assistant.TakeAnOrderMenu;

public class AssistantMenu extends Menu {

	public static AssistantMenu instance;
	
	public AssistantMenu() {
		super("Assistant");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Take an order", "Create a new order for a client"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			TakeAnOrderMenu.getInstance().show();
			break;

		default:
			break;
		}
	}
	
	public static AssistantMenu getInstance() {
		if (instance == null) instance = new AssistantMenu();
		return instance;
	}

	public static void setInstance(AssistantMenu instance) {
		AssistantMenu.instance = instance;
	}

}
