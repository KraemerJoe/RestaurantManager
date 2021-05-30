package fr.ul.miage.gl.restaurant.managers;

import java.util.ArrayList;

import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.MenuLogin;
import fr.ul.miage.gl.restaurant.menus.roles.AssistantMenu;
import fr.ul.miage.gl.restaurant.menus.roles.ButlerMenu;
import fr.ul.miage.gl.restaurant.menus.roles.CookerMenu;
import fr.ul.miage.gl.restaurant.menus.roles.DirectorMenu;
import fr.ul.miage.gl.restaurant.menus.roles.WaiterMenu;
import fr.ul.miage.gl.restaurant.menus.roles.director.DirectorStatsMenu;
import fr.ul.miage.gl.restaurant.menus.roles.waiter.WaiterTableMenu;

public class MenuManager {

	/*
	 * Cette classe permet la gestion des menus,
	 * nous avons opté pour une solution simple et efficace:
	 * - nous enregistrons chaque menu ( initMenus )
	 * - chaque menu peut être afficher via show
	 */
	
	public static MenuManager instance;
	public ArrayList<Menu> menus = new ArrayList<Menu>();

	public MenuManager() {
		instance = this;
		initMenus();
		initMenusItems();
	}

	/*
	 * Mise en mémoire de chaque option de chaque menu
	 */
	private void initMenusItems() {
		for (Menu menu : menus) {
			menu.initMenuItems();
		}
	}

	/*
	 * Enregistrement des menus
	 */
	public void initMenus() {
		menus.add(new MenuLogin());
		menus.add(new AssistantMenu());
		menus.add(new ButlerMenu());
		menus.add(new CookerMenu());
		menus.add(new DirectorMenu());
		menus.add(new WaiterMenu());
		menus.add(new WaiterTableMenu());
		menus.add(new DirectorStatsMenu());
	}

	public static MenuManager getInstance() {
		return instance;
	}

	public static void setInstance(MenuManager instance) {
		MenuManager.instance = instance;
	}

}
