package fr.ul.miage.gl.restaurant.menus.roles.waiter;

import java.util.ArrayList;
import java.util.List;

import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.orders.Order;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import fr.ul.miage.gl.restaurant.util.MenuUtil;

public class TakeAnOrderMenu extends Menu {

	public static TakeAnOrderMenu instance;
	
	public TakeAnOrderMenu() {
		super("Take an Order - Assistant");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("New Client", "Select a free or reserved table"));
		itemList.add(new ItemMenu("Already Client", "Select a busy table"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			TableRestaurant tableFree = askForFreeOrReservedTable();
			if(tableFree == null) return;
			ArrayList<Dish> list = orderOfDish();
			if(list.isEmpty()) return;
			boolean confirmOrder = askForConfirmation(list);
			if(!confirmOrder) {
				System.out.println("This order has been canceled.");
				return;
			}
			sendToCooker(tableFree, list);
			break;
		case 2:
			TableRestaurant tableBusy = askForBusyTable();
			if(tableBusy == null) return;	
			ArrayList<Dish> list2 = orderOfDish();
			if(list2.isEmpty()) return;
			boolean confirmOrder2 = askForConfirmation(list2);
			if(!confirmOrder2) {
				System.out.println("This order has been canceled.");
				return;
			}
			sendToCooker(tableBusy, list2);
			break;
		}
	}
	
	private void sendToCooker(TableRestaurant to, ArrayList<Dish> what) {
		if(!to.canTakeAnOrder()) {
			System.err.println("This table cannot take any new order.");
			return;
		}else {
			SessionClient session;
			if(to.hasAlreadyASession()) {
				session = to.findCurrentSession();
			}
			else {
				session = to.createSession();
			}
			
			to.setBusy();
			to.save();
			
			Order order = session.createOrder();
			order.populateWithDish(what);
			
			System.out.println("The order has been send to cookers !");
			
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
		if(yesOrNo == 1) return true;
		else return false;
	}

	public ArrayList<Dish> orderOfDish(){
		ArrayList<Dish> choosedDishes = new ArrayList<Dish>();
		
		boolean finish = false;
		while(!finish) {
			Category cat = askForACategory();
			if(cat != null) {
				Dish dish = askForADish(cat);
				if(dish != null) {
					
					int yesOrNo = MenuUtil.askForYesOrNo("Is it for a child ?");
					if(yesOrNo == 1) dish.setForChild(true);
					else dish.setForChild(false);
					
					choosedDishes.add(dish);
				}
			}

			int yesOrNo = MenuUtil.askForYesOrNo("Do you want to choose another dish ?");
			if(yesOrNo == 1) continue;
			else finish = true;
		}
		
		
		
		return choosedDishes;
	}
	
	public Category askForACategory() {
		List<Category> categories = Category.find.all();

				int compteur = 0;
				for (Category c : categories) {
					System.out.println("[" + compteur + "] " + c.getName());
					compteur++;
				}
				
				int categoryId = MenuUtil.askForPositiveInt("Which category do you want ?");
				
				if(categories.size() <= categoryId || categories.get(categoryId) == null) {
					System.out.println("This category doesn't exist.");
					return null;
				}else {
					Category c = categories.get(categoryId);
					return c;
				}
	}
	
	public Dish askForADish(Category cat) {
		List<Dish> dishs = Dish.find.byCategory(cat);

				int compteur = 0;
				for (Dish dish : dishs) {
					System.out.println("[" + compteur + "] " + dish.getName() + " - " + dish.getPrice() + "$");
					compteur++;
				}
				
				int dishId = MenuUtil.askForPositiveInt("Which dish do you want ?");
				
				if(dishs.size() <= dishId || dishs.get(dishId) == null) {
					System.out.println("This dish doesn't exist.");
					return null;
				}else {
					Dish dish = dishs.get(dishId);
					return dish;
				}
	} 
	
	public TableRestaurant askForBusyTable() {
		List<TableRestaurant> tables = TableRestaurant.find.byStatut(EnumTableStat.BUSY);
				;

				int compteur = 0;
				for (TableRestaurant tableRestaurant : tables) {
					System.out.println("[" + compteur + "] Places: " + tableRestaurant.getSeats_amount() + " | Stat: " + tableRestaurant.getStatut());
					compteur++;
				}
				
				if(tables.size() == 0) {
					System.out.println("There is no busy tables.");
					return null;
				}
				int tableId = MenuUtil.askForPositiveInt("Which table do you want ?");
				
				if(tables.size() <= tableId || tables.get(tableId) == null) {
					System.out.println("This table doesn't exist.");
					return null;
				}else {
					TableRestaurant table = tables.get(tableId);
					return table;
				}
	}
	
	
	public TableRestaurant askForFreeOrReservedTable() {
		List<TableRestaurant> tables = TableRestaurant.find.freeOrReserved();

				int compteur = 0;
				for (TableRestaurant tableRestaurant : tables) {
					System.out.println("[" + compteur + "] Places: " + tableRestaurant.getSeats_amount() + " | Stat: " + tableRestaurant.getStatut());
					compteur++;
				}
				
				if(tables.size() == 0) {
					System.out.println("There is no free or reserved tables.");
					return null;
				}
				int tableId = MenuUtil.askForPositiveInt("Which table do you want ?");
				
				if(tables.size() <= tableId || tables.get(tableId) == null) {
					System.out.println("This table doesn't exist.");
					return null;
				}else {
					TableRestaurant table = tables.get(tableId);
					return table;
				}
	}

	
	public static TakeAnOrderMenu getInstance() {
		if (instance == null) instance = new TakeAnOrderMenu();
		return instance;
	}

	public static void setInstance(TakeAnOrderMenu instance) {
		TakeAnOrderMenu.instance = instance;
	}
	
}
