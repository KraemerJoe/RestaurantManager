package fr.ul.miage.gl.restaurant.menus.roles;

import java.util.ArrayList;
import java.util.List;

import fr.ul.miage.gl.restaurant.managers.SessionManager;
import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.roles.waiter.WaiterTableMenu;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;
import fr.ul.miage.gl.restaurant.pojo.orders.enums.EnumSessionOrderStat;
import fr.ul.miage.gl.restaurant.pojo.tables.TableAssignment;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.util.MenuUtil;

public class WaiterMenu extends Menu {

	public static WaiterMenu instance;

	public WaiterMenu() {
		super("Waiter");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Status of the restaurant", "See my tables"));
		itemList.add(new ItemMenu("Select a table", "Manage a table (add a dish,invoice,...)"));
		itemList.add(new ItemMenu("Checkout", "See the evolution of the dishs of each table"));
		itemList.add(new ItemMenu("Set a dish as served", "Mark a dish as served to the client"));
		itemList.add(new ItemMenu("Menu of the day", "See menu of the day"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			ArrayList<TableAssignment> tables = new ArrayList<TableAssignment>();

			tables.addAll(
					TableAssignment.find.assignmentByStaffId(SessionManager.getInstance().getAccount().getStaff_id()));
			for (TableAssignment ta : tables) {
				System.out.println(ta.getTable().getColor() + " [TABLE #" + ta.getTable().getTable_id() + "] | Floor: "
						+ ta.getTable().getFloor() + " | Seats: " + ta.getTable().getSeats_amount());
			}
			break;
		case 2:

			ArrayList<TableAssignment> tables2 = new ArrayList<TableAssignment>();
			ArrayList<TableRestaurant> tablesRes = new ArrayList<TableRestaurant>();

			tables2.addAll(
					TableAssignment.find.assignmentByStaffId(SessionManager.getInstance().getAccount().getStaff_id()));

			for (TableAssignment ta : tables2) {
				tablesRes.add(ta.getTable());
			}

			int compteur2 = 0;
			for (TableRestaurant ta : tablesRes) {
				System.out.println("[" + compteur2 + "] " + ta.getColor() + " [TABLE #" + ta.getTable_id()
						+ "] | Floor: " + ta.getFloor() + " | Seats: " + ta.getSeats_amount());
				compteur2++;
			}

			if (tablesRes.size() <= 0) {
				System.out.println("There is no table for that.");
				return;
			}

			int tableToSelect = MenuUtil.askForPositiveInt("Which table do you want to manage ?");

			if (tablesRes.size() <= tableToSelect || tablesRes.get(tableToSelect) == null) {
				System.out.println("This table doesn't exist.");
				return;
			} else {
				TableRestaurant table = tablesRes.get(tableToSelect);
				WaiterTableMenu.getInstance().setTable(table);
				WaiterTableMenu.getInstance().show();
			}

			break;
		case 3:
			List<SessionOrder> list = new ArrayList<SessionOrder>();
			list = SessionOrder.find.notCompletedOrders();

			for (SessionOrder session : list) {
				System.out.println("[TABLE #" + session.getOrder().getSessionClient().getTable_id().getTable_id() + "] "
						+ session.getDish().getName() + " | " + session.getStatut());
			}
			break;
		case 4:
			List<SessionOrder> listToServ = new ArrayList<SessionOrder>();
			listToServ = SessionOrder.find.notCompletedOrders();

			List<SessionOrder> listToServFiltered = new ArrayList<SessionOrder>();

			for (SessionOrder sessionOrder : listToServ) {
				if (TableAssignment.find.isAssigned(sessionOrder.getOrder().getSessionClient().getTable_id(),
						SessionManager.getInstance().getAccount())
						&& sessionOrder.getStatut().equals(EnumSessionOrderStat.READY_TO_SERVE)) {
					listToServFiltered.add(sessionOrder);
				}
			}

			int c = 0;
			for (SessionOrder sessionOrder : listToServFiltered) {
				System.out.println("[" + c + "] " + "[TABLE #"
						+ sessionOrder.getOrder().getSessionClient().getTable_id().getTable_id() + "] "
						+ sessionOrder.getDish().getName() + " | " + sessionOrder.getStatut());
				c++;
			}
			if (listToServFiltered.size() <= 0) {
				System.out.println("There is no dish to serve.");
				return;
			}

			int dishToServe = MenuUtil.askForPositiveInt("Which table do you want to collect ?");

			if (listToServFiltered.size() <= dishToServe || listToServFiltered.get(dishToServe) == null) {
				System.out.println("This table doesn't exist.");
				return;
			} else {
				SessionOrder order = listToServFiltered.get(dishToServe);
				order.served();
				System.out.println("The dish has been marked has served !");
			}

			break;
		case 5:
			ArrayList<Dish> dishes = new ArrayList<Dish>();
			dishes.addAll(Dish.find.menuOfTheDay());

			for (Dish o : dishes) {
				System.out.println(o.getName() + " " + o.getPrice() + "$");
			}
			break;
		}
	}

	public static WaiterMenu getInstance() {
		if (instance == null)
			instance = new WaiterMenu();
		return instance;
	}

	public static void setInstance(WaiterMenu instance) {
		WaiterMenu.instance = instance;
	}

}
