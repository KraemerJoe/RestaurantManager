package fr.ul.miage.gl.restaurant.menus.roles;

import java.util.ArrayList;

import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;

public class DirectorMenu extends Menu {

	public static DirectorMenu instance;
	
	public DirectorMenu() {
		super("Director");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Stocks", "See all current stocks"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			ArrayList<RawMaterial> materials = new ArrayList<RawMaterial>();
			materials.addAll(RawMaterial.find.all());
			for (RawMaterial rawMaterial : materials) {
				System.out.println("- " + rawMaterial.getName() + " [" + rawMaterial.getStock() + "]");			}
		}
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
