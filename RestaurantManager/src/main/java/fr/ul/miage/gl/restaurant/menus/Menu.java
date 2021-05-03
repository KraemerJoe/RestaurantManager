package fr.ul.miage.gl.restaurant.menus;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Menu implements InterfaceMenu {

	protected String name;
	protected ArrayList<ItemMenu> itemList = new ArrayList<ItemMenu>();
	public static Menu instance;

	public Menu(String name) {
		super();
		this.name = name;
		Menu.instance = this;
	}

	public void show() {
		title();
		int choice = -1;

		do {
			int i = 1;
			for (ItemMenu itemMenu : itemList) {
				System.out.println("[" + i + "] " + itemMenu.getDescription() + " - " + itemMenu.getDetail());
				i++;
			}
			footer();
			try {
				Scanner input = new Scanner(System.in);
				choice = input.nextInt();
			} catch (InputMismatchException e) {
				choice = -1;
				System.err.println("Une erreur est survenue dans votre insertion !");
			}
		} while (choice <= 0 || choice >= (itemList.size() + 1));
		executeChoice(choice);
	}

	public void title() {
		System.out.println("------------------[" + name + "]------------------");
	}

	public void footer() {
		System.out.println("------------------------------------------");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ItemMenu> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<ItemMenu> itemList) {
		this.itemList = itemList;
	}

	public static Menu getInstance() {
		return instance;
	}

	public static void setInstance(Menu instance) {
		Menu.instance = instance;
	}

}
