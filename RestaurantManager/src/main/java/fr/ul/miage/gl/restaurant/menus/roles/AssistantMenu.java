package fr.ul.miage.gl.restaurant.menus.roles;

import java.util.ArrayList;
import java.util.List;

import fr.ul.miage.gl.restaurant.ebean.EbeanManager;
import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.roles.assistant.TakeAnOrderMenu;
import fr.ul.miage.gl.restaurant.pojo.orders.EnumOrderStat;
import fr.ul.miage.gl.restaurant.pojo.orders.EnumSessionOrderStat;
import fr.ul.miage.gl.restaurant.pojo.orders.Order;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;
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
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			TakeAnOrderMenu.getInstance().show();
			break;
		case 2:
			List<SessionOrder> list = new ArrayList<SessionOrder>();
			list = EbeanManager.getInstance().getDb().find(SessionOrder.class).where().not().eq("statut",EnumOrderStat.COMPLET).findList();
			
			for (SessionOrder session : list) {
				System.out.println("[TABLE #" + session.getOrder().getSessionClient().getTable_id().getTable_id() + "] " + session.getDish().getName() + " | " + session.getStatut());
			}
		case 3:
			List<TableRestaurant> listTable = new ArrayList<TableRestaurant>();
			listTable = EbeanManager.getInstance().getDb().find(TableRestaurant.class).orderBy().asc("floor").orderBy().asc("table_id").findList();
			for (TableRestaurant table : listTable) {
				System.out.println("[TABLE #" + table.getTable_id() + "] " + table.getStatut() + " | Floor : " + table.getFloor());
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
