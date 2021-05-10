package fr.ul.miage.gl.restaurant.menus.roles;

import java.util.ArrayList;
import java.util.List;

import fr.ul.miage.gl.restaurant.ebean.EbeanManager;
import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.pojo.orders.EnumSessionOrderStat;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;

public class CookerMenu extends Menu {

	public static CookerMenu instance;
	
	public CookerMenu() {
		super("Cooker");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Incoming orders", "See incoming orders"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			List<SessionOrder> orders = new ArrayList<SessionOrder>();
			orders = EbeanManager.getInstance().getDb().find(SessionOrder.class).where().eq("statut", EnumSessionOrderStat.PENDING).orderBy().desc("child").orderBy().asc("date_creation").findList();
			
			
			System.out.println("List of pending orders: ");
			for (SessionOrder sessionOrder : orders) {
				System.out.println("- " + (sessionOrder.isChild() ? "[CHILD] " : "") + "[TABLE #" + sessionOrder.getOrder().getSessionClient().getTable_id().getTable_id() + "] " + sessionOrder.getDish().getName() + " | " + sessionOrder.getDate_creation());
			}
			break;

		default:
			break;
		}
	}
	
	public static CookerMenu getInstance() {
		if (instance == null)
			instance = new CookerMenu();
		return instance;
	}

	public static void setInstance(CookerMenu instance) {
		CookerMenu.instance = instance;
	}

}
