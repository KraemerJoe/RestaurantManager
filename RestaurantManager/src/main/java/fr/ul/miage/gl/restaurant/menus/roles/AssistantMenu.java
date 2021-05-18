package fr.ul.miage.gl.restaurant.menus.roles;

import java.util.ArrayList;
import java.util.List;

import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.roles.assistant.TakeAnOrderMenu;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;
import fr.ul.miage.gl.restaurant.pojo.tables.EnumTableStat;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;

public class AssistantMenu extends Menu {

	public static AssistantMenu instance;
	
	public AssistantMenu() {
		super("Assistant");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Take an order", "Create a new order for a client"));
		itemList.add(new ItemMenu("Checkout", "See the evolution of the dishs of each table"));
		itemList.add(new ItemMenu("Room Occupation", "See the occupation of the room"));
		itemList.add(new ItemMenu("Tables state", "See if tables are clean or not"));
		itemList.add(new ItemMenu("Tables that got available", "See which table just got available"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			TakeAnOrderMenu.getInstance().show();
			break;
		case 2:
			List<SessionOrder> list = new ArrayList<SessionOrder>();
			list = SessionOrder.find.notCompletedOrders();
			
			for (SessionOrder session : list) {
				System.out.println("[TABLE #" + session.getOrder().getSessionClient().getTable_id().getTable_id() + "] " + session.getDish().getName() + " | " + session.getStatut());
			}
		case 3:
			List<TableRestaurant> listTable = new ArrayList<TableRestaurant>();
			listTable = TableRestaurant.find.tablesOrdered();
			for (TableRestaurant table : listTable) {
				System.out.println("[TABLE #" + table.getTable_id() + "] " + table.getStatut() + " | Floor : " + table.getFloor());
			}
			
			break;
		case 4:
			List<TableRestaurant> listTableClean = new ArrayList<TableRestaurant>();
			listTableClean = TableRestaurant.find.tablesOrdered();
			String tableState="";
			for (TableRestaurant table : listTableClean) {
				if(table.getStatut().equals(EnumTableStat.TO_CLEAN)) {
					tableState="Dirty";
				}else {
					tableState="Clean";
				}
				System.out.println("[TABLE #" + table.getTable_id() + "] Floor : " + table.getFloor() + " | Table state : " + tableState);
			}
			
			break;
		case 5:
			List<TableRestaurant> listTableAvailable = new ArrayList<TableRestaurant>();
			listTableAvailable = TableRestaurant.find.tablesOrdered();
			for (TableRestaurant table : listTableAvailable) {
				if(table.getStatut().equals(EnumTableStat.TO_CLEAN) && table.getStatut().equals(EnumTableStat.FREE)) {
					System.out.println("[TABLE #" + table.getTable_id() + "] Floor : " + table.getFloor());
				}
			}
			
			break;

		default:
			break;
		}
	}
	
	public static AssistantMenu getInstance() {
		if (instance == null) instance = new AssistantMenu();
		return instance;
	}

	public static void setInstance(AssistantMenu instance) {
		AssistantMenu.instance = instance;
	}

}
