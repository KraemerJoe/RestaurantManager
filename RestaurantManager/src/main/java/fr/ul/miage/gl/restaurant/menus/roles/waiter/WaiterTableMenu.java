package fr.ul.miage.gl.restaurant.menus.roles.waiter;

import java.util.ArrayList;
import java.util.List;

import fr.ul.miage.gl.restaurant.managers.SessionManager;
import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.roles.WaiterMenu;
import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import fr.ul.miage.gl.restaurant.pojo.orders.Order;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.tables.TableAssignment;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import fr.ul.miage.gl.restaurant.util.MenuUtil;

public class WaiterTableMenu extends Menu {

	public static WaiterTableMenu instance;
	private TableRestaurant table;

	public WaiterTableMenu() {
		super("Manage Table - Waiter");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Add a dish", "Add a new dish to the table"));
		itemList.add(new ItemMenu("Create an invoice", "Cashing out a customer"));
		itemList.add(new ItemMenu("Back to waiter menu", "Return to main waiter menu"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			if (table == null)
				return;
			ArrayList<Dish> list = orderOfDish();
			if (list.isEmpty())
				return;
			boolean confirmOrder = askForConfirmation(list);
			if (!confirmOrder) {
				System.out.println("This order has been canceled.");
				return;
			}
			sendToCooker(table, list);
			break;
		case 2:
			
			boolean lunch = true;
			int yesOrNo = MenuUtil.askForYesOrNo("Is that for the lunch ? ( no = dinner) ");
			if(yesOrNo == 1) lunch = true;
			else lunch = false;
			
			boolean invoiced = table.createInvoice(lunch);
			if (invoiced)
				System.out.println("The invoice for this table has been edited, table is now set as to clean.");
			else
				System.err.println("The invoiced has not been created.");
			
			break;
		case 3:
			WaiterMenu.getInstance().show();
			break;
		}
	}

	private void sendToCooker(TableRestaurant to, ArrayList<Dish> what) {
		if (!to.canTakeAnOrder()) {
			System.err.println("This table cannot take any new order.");
			return;
		} else {
			SessionClient session;
			if (to.hasAlreadyASession()) {
				session = to.findCurrentSession();
			} else {
				session = to.createSession();
			}

			boolean enoughForAllFish = true;
			System.out.println(" > Verification of stocks...");
			for (Dish dish : what) {
				if (!dish.enoughRawMaterial()) {
					enoughForAllFish = false;
					System.err.println("  > Not enough raw material for " + dish.getName() + " !");
				} else {
					System.out.println("  > Enough stock for " + dish.getName());
				}
			}

			if (enoughForAllFish) {
				to.setBusy();
				to.save();

				Order order = session.createOrder();
				order.populateWithDish(what);

				System.out.println("The order has been send to cookers !");
			} else {
				System.err.println("The order was cancelled, not enough raw material for one/several dishs.");
			}
		}
	}

	private boolean askForConfirmation(ArrayList<Dish> list) {
		System.out.println("Would you like to confirm this order ?");
		for (Dish dish : list) {
			System.out.println("- " + dish.getName() + " | " + dish.getPrice() + "$");
		}
		double sum = list.stream().mapToDouble(a -> a.getPrice()).sum();
		MenuUtil.line();
		System.out.println("Total: " + sum + "$");
		int yesOrNo = MenuUtil.askForYesOrNo("Confirm the order ?");
		if (yesOrNo == 1)
			return true;
		else
			return false;
	}

	public ArrayList<Dish> orderOfDish() {
		ArrayList<Dish> choosedDishes = new ArrayList<Dish>();

		boolean finish = false;
		while (!finish) {
			Category cat = Category.askForACategory();
			if (cat != null) {
				Dish dish = askForADish(cat);
				if (dish != null) {

					int yesOrNo = MenuUtil.askForYesOrNo("Is it for a child ?");
					if (yesOrNo == 1)
						dish.setForChild(true);
					else
						dish.setForChild(false);

					choosedDishes.add(dish);
				}
			}

			int yesOrNo = MenuUtil.askForYesOrNo("Do you want to choose another dish ?");
			if (yesOrNo == 1)
				continue;
			else
				finish = true;
		}

		return choosedDishes;
	}

	public Dish askForADish(Category cat) {
		List<Dish> dishs = Dish.find.byCategoryEnoughStock(cat);

		int compteur = 0;
		for (Dish dish : dishs) {
			System.out.println("[" + compteur + "] " + dish.getName() + " - " + dish.getPrice() + "$ | " + dish.getComposition());
			compteur++;
		}

		if(dishs.size() <= 0) {
			System.err.println("No dish with enough stock in this category.");
			return null;
		}
		int dishId = MenuUtil.askForPositiveInt("Which dish do you want ?");

		if (dishs.size() <= dishId || dishs.get(dishId) == null) {
			System.err.println("This dish doesn't exist.");
			return null;
		} else {
			Dish dish = dishs.get(dishId);
			return dish;
		}
	}

	public TableRestaurant askForBusyTable() {
		List<TableRestaurant> tables = TableRestaurant.find.byStatut(EnumTableStat.BUSY);
		;

		int compteur = 0;
		for (TableRestaurant tableRestaurant : tables) {
			System.out.println("[" + compteur + "] Places: " + tableRestaurant.getSeats_amount() + " | Stat: "
					+ tableRestaurant.getStatut());
			compteur++;
		}

		if (tables.size() == 0) {
			System.out.println("There is no busy tables.");
			return null;
		}
		int tableId = MenuUtil.askForPositiveInt("Which table do you want ?");

		if (tables.size() <= tableId || tables.get(tableId) == null) {
			System.out.println("This table doesn't exist.");
			return null;
		} else {
			TableRestaurant table = tables.get(tableId);
			return table;
		}
	}

	public TableRestaurant askForFreeOrReservedTable() {
		List<TableRestaurant> tables = TableRestaurant.find.freeOrReserved();

		int compteur = 0;
		for (TableRestaurant tableRestaurant : tables) {
			System.out.println("[" + compteur + "] Places: " + tableRestaurant.getSeats_amount() + " | Stat: "
					+ tableRestaurant.getStatut());
			compteur++;
		}

		if (tables.size() == 0) {
			System.out.println("There is no free or reserved tables.");
			return null;
		}
		int tableId = MenuUtil.askForPositiveInt("Which table do you want ?");

		if (tables.size() <= tableId || tables.get(tableId) == null) {
			System.out.println("This table doesn't exist.");
			return null;
		} else {
			TableRestaurant table = tables.get(tableId);
			return table;
		}
	}

	public static WaiterTableMenu getInstance() {
		if (instance == null)
			instance = new WaiterTableMenu();
		return instance;
	}

	public static void setInstance(WaiterTableMenu instance) {
		WaiterTableMenu.instance = instance;
	}

	public TableRestaurant getTable() {
		return table;
	}

	public void setTable(TableRestaurant table) {
		this.table = table;
	}

}
