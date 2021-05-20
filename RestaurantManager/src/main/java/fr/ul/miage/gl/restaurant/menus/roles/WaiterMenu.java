package fr.ul.miage.gl.restaurant.menus.roles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.ul.miage.gl.restaurant.managers.SessionManager;
import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;
import fr.ul.miage.gl.restaurant.pojo.orders.enums.EnumSessionOrderStat;
import fr.ul.miage.gl.restaurant.pojo.tables.TableAssignment;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import fr.ul.miage.gl.restaurant.pojo.tables.finders.TableAssignmentFinder;
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
		itemList.add(new ItemMenu("Create an invoice", "Cashing out a customer"));
		itemList.add(new ItemMenu("Checkout", "See the evolution of the dishs of each table"));
		itemList.add(new ItemMenu("Set a dish as served", "Mark a dish as served to the client"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			ArrayList<TableAssignment> tables = new ArrayList<TableAssignment>();
			
			tables.addAll(TableAssignment.find.assignmentByStaffId(SessionManager.getInstance().getAccount().getStaff_id()));
			for (TableAssignment ta : tables) {
				System.out.println(ta.getTable().getColor() + "-[" + ta.getTable().getTable_id() + "] | Floor: " + ta.getTable().getFloor() + " | Seats: " + ta.getTable().getSeats_amount());
			}
		break;
		case 2:
			ArrayList<TableAssignment> tablesToCash = new ArrayList<TableAssignment>();
			

			tablesToCash.addAll(TableAssignment.find.getAssignedBusyTables(SessionManager.getInstance().getAccount().getStaff_id()));
			
			int compteur = 0;
			for (TableAssignment ta : tablesToCash) {
				System.out.println("[" + compteur + "] " + ta.getTable().getColor() + "[" + ta.getTable().getTable_id() + "] | Floor: " + ta.getTable().getFloor() + " | Seats: " + ta.getTable().getSeats_amount() + " | " + ta.getTable().getStatut());
				compteur++;
			}
			if(tablesToCash.size() <= 0) {
				System.out.println("There is no client to collect.");
				return;
			}
			
			int clientToCollect = MenuUtil.askForPositiveInt("Which table do you want to collect ?");
			
			if(tablesToCash.size() <= clientToCollect || tablesToCash.get(clientToCollect) == null) {
				System.out.println("This table doesn't exist.");
				return;
			}else {
				TableRestaurant table = tablesToCash.get(clientToCollect).getTable();
				boolean invoiced = table.createInvoice();
				if(invoiced) System.out.println("The invoice for this table has been edited, table is now set as to clean.");
				else System.out.println("The invoiced has not been created.");
			}
		break;
		case 3:
			List<SessionOrder> list = new ArrayList<SessionOrder>();
			list = SessionOrder.find.notCompletedOrders();
			
			for (SessionOrder sessionOrder : list) {
				if(TableAssignment.find.isAssigned(sessionOrder.getOrder().getSessionClient().getTable_id(), SessionManager.getInstance().getAccount())) {
					System.out.println("[TABLE #" + sessionOrder.getOrder().getSessionClient().getTable_id().getTable_id() + "] " + sessionOrder.getDish().getName() + " | " + sessionOrder.getStatut());
				}
			}
			
		break;
		case 4:
			List<SessionOrder> listToServ = new ArrayList<SessionOrder>();
			listToServ = SessionOrder.find.notCompletedOrders();
			
			List<SessionOrder> listToServFiltered = new ArrayList<SessionOrder>();
			
			for (SessionOrder sessionOrder : listToServ) {
				if(TableAssignment.find.isAssigned(sessionOrder.getOrder().getSessionClient().getTable_id(), SessionManager.getInstance().getAccount()) && sessionOrder.getStatut().equals(EnumSessionOrderStat.READY_TO_SERVE)) {
					listToServFiltered.add(sessionOrder);
				}
			}
			
			
			int c = 0;
			for (SessionOrder sessionOrder : listToServFiltered) {
				System.out.println("[" + c + "] " + "[TABLE #" + sessionOrder.getOrder().getSessionClient().getTable_id().getTable_id() + "] " + sessionOrder.getDish().getName() + " | " + sessionOrder.getStatut());
				c++;
			}
			if(listToServFiltered.size() <= 0) {
				System.out.println("There is no dish to serve.");
				return;
			}
			
			int dishToServe = MenuUtil.askForPositiveInt("Which table do you want to collect ?");
			
			if(listToServFiltered.size() <= dishToServe || listToServFiltered.get(dishToServe) == null) {
				System.out.println("This table doesn't exist.");
				return;
			}else {
				SessionOrder order = listToServFiltered.get(dishToServe);
				order.served();
				System.out.println("The dish has been marked has served !");
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
