package fr.ul.miage.gl.restaurant.menus.roles;

import java.util.ArrayList;

import fr.ul.miage.gl.restaurant.managers.SessionManager;
import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.roles.director.DirectorStatsMenu;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import fr.ul.miage.gl.restaurant.pojo.dishes.exceptions.NegativeStockException;
import fr.ul.miage.gl.restaurant.pojo.dishes.exceptions.StockOverFlowException;
import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import fr.ul.miage.gl.restaurant.pojo.staff.enums.EnumRoles;
import fr.ul.miage.gl.restaurant.util.MenuUtil;

public class DirectorMenu extends Menu {

	public static DirectorMenu instance;

	public DirectorMenu() {
		super("Director");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Stocks", "See all current stocks"));
		itemList.add(new ItemMenu("Manage Stocks", "Edit stocks"));
		itemList.add(new ItemMenu("Details of staff", "See the details of a staff"));
		itemList.add(new ItemMenu("Create a staff", "Add a new staff to the team"));
		itemList.add(new ItemMenu("Edit a staff", "Edit role of a staff"));
		itemList.add(new ItemMenu("Stats", "Access to director's stats"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			ArrayList<RawMaterial> materials = new ArrayList<RawMaterial>();
			materials.addAll(RawMaterial.find.all());
			for (RawMaterial rawMaterial : materials) {
				System.out.println("- " + rawMaterial.getName() + " [" + rawMaterial.getStock() + "]");
			}

			break;
		case 2:
			
			int compteur3 = 0;
			ArrayList<RawMaterial> raws = new ArrayList<RawMaterial>();
			raws.addAll(RawMaterial.find.all());
			
			for (RawMaterial o : raws) {
				System.out.println("[" + compteur3 + "] " + o.getName() + " | Stocks " + o.getStock());
				compteur3++;
			}

			if (raws.size() <= 0) {
				System.out.println("There is no raw material.");
				return;
			}

			int rawC = MenuUtil.askForPositiveInt("Which raw material do you want to edit the stock ?");

			if (raws.size() <= rawC || raws.get(rawC) == null) {
				System.out.println("This raw material doesn't exist.");
				return;
			} else {
				RawMaterial s = raws.get(rawC);
				long stock = MenuUtil.askForPositiveLong("How much do you want to set the stock at ?");
				try {
					s.setNewStock(stock);
				} catch (NegativeStockException | StockOverFlowException e) {
					e.printStackTrace();
				}
			}
			
			break;
		case 3:
			ArrayList<Staff> staffs = new ArrayList<Staff>();
			staffs.addAll(Staff.find.all());

			int compteur = 0;
			for (Staff o : staffs) {
				System.out.println("[" + compteur + "] " + o.getName() + " " + o.getSurname());
				compteur++;
			}

			if (staffs.size() <= 0) {
				System.out.println("There is no staff.");
				return;
			}

			int staffC = MenuUtil.askForPositiveInt("Which staff do you want to check ?");

			if (staffs.size() <= staffC || staffs.get(staffC) == null) {
				System.out.println("This staff doesn't exist.");
				return;
			} else {
				Staff s = staffs.get(staffC);
				System.out.println("You are about to create a staff with: ");
				System.out.println(" > NAME: " + s.getName());
				System.out.println(" > SURNAME: " + s.getSurname());
				System.out.println(" > LOGIN: " + s.getLogin());
				System.out.println(" > ROLE: " + s.getRole());

			}
			break;
		case 4:
			String name = MenuUtil.askForString("Enter the name of the staff.");
			String surname = MenuUtil.askForString("Enter the surname of the staff.");
			String login = MenuUtil.askForString("Enter the login of the staff.");
			String password = MenuUtil.askForString("Enter the password of the staff.");

			EnumRoles role = chooseRole();
			if (role == null)
				return;

			System.out.println("You are about to create a staff with: ");
			System.out.println(" > NAME: " + name);
			System.out.println(" > SURNAME: " + surname);
			System.out.println(" > LOGIN: " + login);
			System.out.println(" > PASSWORD: " + password);
			System.out.println(" > ROLE: " + role);

			int yesOrNo = MenuUtil.askForYesOrNo("Do you confirm the creation ?");

			if (yesOrNo == 1) {
				Staff staff = new Staff(name, surname, login, password, role);
				staff.save();
				System.out.println("The creation of the staff is a success.");
			} else {
				System.err.println("The creation was cancelled.");
			}
		case 5:
			ArrayList<Staff> staffs1 = new ArrayList<Staff>();
			staffs1.addAll(Staff.find.all());

			int compteur1 = 0;
			for (Staff o : staffs1) {
				System.out.println("[" + compteur1 + "] " + o.getName() + " " + o.getSurname());
				compteur1++;
			}

			if (staffs1.size() <= 0) {
				System.out.println("There is no staff.");
				return;
			}

			int staffC1 = MenuUtil.askForPositiveInt("Which staff do you want to edit ?");

			if (staffs1.size() <= staffC1 || staffs1.get(staffC1) == null) {
				System.out.println("This staff doesn't exist.");
				return;
			} else {
				Staff s = staffs1.get(staffC1);
				EnumRoles roleC = chooseRole();
				if (roleC == null) {
					System.err.println("This role doesn't exist");
					return;
				} else {
					if (s.getStaff_id() == SessionManager.getInstance().getAccount().getStaff_id()) {
						System.err.println("You can't edit your own role !");
					} else {
						s.setRole(roleC);
						s.save();
						System.out.println("The role of the staff has been edited !");
					}

				}

			}

			break;
		case 6:
			DirectorStatsMenu.getInstance().show();
			break;
		default:
			break;

		}

	}

	public EnumRoles chooseRole() {
		int compteur = 0;
		for (EnumRoles role : EnumRoles.values()) {
			System.out.println("[" + compteur + "]" + " " + role.toString());
			compteur++;
		}

		int roleToChoose = MenuUtil.askForPositiveInt("Which role do you want for this staff ?");
		EnumRoles role = null;

		if (EnumRoles.values().length <= roleToChoose || EnumRoles.values()[roleToChoose] == null) {
			System.out.println("This role doesn't exist.");
			return null;
		} else {
			return EnumRoles.values()[roleToChoose];
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