package fr.ul.miage.gl.restaurant.menus.roles.assistant;

import java.util.List;

import fr.ul.miage.gl.restaurant.ebean.EbeanManager;
import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.pojo.tables.EnumTableStat;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
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
			
		case 2:
			TableRestaurant tableBusy = askForBusyTable();
			if(tableBusy == null) return;	
			
		}
	}
	
	public static TakeAnOrderMenu getInstance() {
		if (instance == null) instance = new TakeAnOrderMenu();
		return instance;
	}

	public static void setInstance(TakeAnOrderMenu instance) {
		TakeAnOrderMenu.instance = instance;
	}
	
	public TableRestaurant askForBusyTable() {
		List<TableRestaurant> tables = EbeanManager.getInstance().getDb().find(TableRestaurant.class)
				.where()
				.eq("statut",EnumTableStat.FREE).findList();

				int compteur = 0;
				for (TableRestaurant tableRestaurant : tables) {
					System.out.println("[" + compteur + "] Places: " + tableRestaurant.getSeats_amount() + " | Stat: " + tableRestaurant.getStatut());
					compteur++;
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
		List<TableRestaurant> tables = EbeanManager.getInstance().getDb().find(TableRestaurant.class)
				.where()
				.or(
				        io.ebean.Expr.eq("statut",EnumTableStat.FREE),
				        io.ebean.Expr.eq("statut",EnumTableStat.RESERVED)
				).findList();

				int compteur = 0;
				for (TableRestaurant tableRestaurant : tables) {
					System.out.println("[" + compteur + "] Places: " + tableRestaurant.getSeats_amount() + " | Stat: " + tableRestaurant.getStatut());
					compteur++;
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

}
