package fr.ul.miage.gl.restaurant.menus;

import fr.ul.miage.gl.restaurant.managers.SessionManager;
import fr.ul.miage.gl.restaurant.menus.roles.AssistantMenu;
import fr.ul.miage.gl.restaurant.menus.roles.ButlerMenu;
import fr.ul.miage.gl.restaurant.menus.roles.CookerMenu;
import fr.ul.miage.gl.restaurant.menus.roles.DirectorMenu;
import fr.ul.miage.gl.restaurant.menus.roles.WaiterMenu;
import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import fr.ul.miage.gl.restaurant.util.MenuUtil;

public class MenuLogin extends Menu {

	public static MenuLogin instance;

	public MenuLogin() {
		super("Login");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Login", "Use your credentials to login"));
		itemList.add(new ItemMenu("Quit", "Exit the program"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			System.out.println("You have chosen to login.");

			String login = MenuUtil.askForString("Enter your login");
			String password = MenuUtil.askForString("Enter your password");

			Staff staff = Staff.find.credentials(login, password);
			if (staff != null) {
				MenuUtil.spacer(30);
				SessionManager.getInstance().login(staff);
				System.out.println("Welcome " + SessionManager.getInstance().getAccount().getLogin() + " !");
				switchAfterLogin();
			} else {
				System.out.println("Your credentials are incorrect.");
				MenuLogin.getInstance().show();
			}
			break;
		case 2:
			System.out.println("You have chosen to quit.");
			System.exit(0);
			break;
		}
	}

	private void switchAfterLogin() {
		switch (SessionManager.getInstance().getAccount().getRole()) {
		case WAITER:
			WaiterMenu.getInstance().show();
			break;
		case ASSISTANT:
			AssistantMenu.getInstance().show();
			break;
		case BUTLER:
			ButlerMenu.getInstance().show();
			break;
		case COOKER:
			CookerMenu.getInstance().show();
			break;
		case DIRECTOR:
			DirectorMenu.getInstance().show();
			break;
		default:
			break;
		}
	}

	public static MenuLogin getInstance() {
		if (instance == null)
			instance = new MenuLogin();
		return instance;
	}

	public static void setInstance(MenuLogin instance) {
		MenuLogin.instance = instance;
	}

}
