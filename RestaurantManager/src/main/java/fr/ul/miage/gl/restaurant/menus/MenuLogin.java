package fr.ul.miage.gl.restaurant.menus;

import fr.ul.miage.gl.restaurant.ebean.EbeanManager;
import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import fr.ul.miage.gl.restaurant.util.MenuUtil;

public class MenuLogin extends Menu {

	public MenuLogin() {
		super("Home");
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
			
			Staff staff = EbeanManager.getInstance().getDb().find(Staff.class).where().eq("login", login).eq("password", password).findOne();
			if(staff != null) {
				MenuUtil.spacer(30);
				System.out.println("Welcome " + staff.getLogin() + " !");
			}	
			else {
				System.out.println("Your credentials are incorrect.");
				MenuLogin.getInstance().show();
			}	
			break;
		case 2:
			System.out.println("You have chosen to disconnect.");
			break;
		}
	}

}
