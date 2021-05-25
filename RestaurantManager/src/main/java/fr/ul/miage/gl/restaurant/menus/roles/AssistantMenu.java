package fr.ul.miage.gl.restaurant.menus.roles;

import java.util.ArrayList;
import java.util.List;

import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import fr.ul.miage.gl.restaurant.util.MenuUtil;

public class AssistantMenu extends Menu {

	public static AssistantMenu instance;

	public AssistantMenu() {
		super("Assistant");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Room Occupation", "See the occupation of the room"));
		itemList.add(new ItemMenu("Tables state", "See if tables are clean or not"));
		itemList.add(new ItemMenu("Tables that got available", "See which table just got available"));
		itemList.add(new ItemMenu("Set a table as clean", "Set a table is now clean and ready for another client"));
	}

	@Override
	public void executeChoice(int choice) {
		switch (choice) {
		case 1:
			List<TableRestaurant> listTable = new ArrayList<TableRestaurant>();
			listTable = TableRestaurant.find.tablesOrdered();
			for (TableRestaurant table : listTable) {
				System.out.println(
						"[TABLE #" + table.getTable_id() + "] " + table.getStatut() + " | Floor : " + table.getFloor());
			}

			break;
		case 2:
			List<TableRestaurant> listTableClean = new ArrayList<TableRestaurant>();
			listTableClean = TableRestaurant.find.tablesOrdered();
			String tableState = "";
			for (TableRestaurant table : listTableClean) {
				if (table.getStatut().equals(EnumTableStat.TO_CLEAN)) {
					tableState = "Dirty";
				} else {
					tableState = "Clean";
				}
				System.out.println("[TABLE #" + table.getTable_id() + "] Floor : " + table.getFloor()
						+ " | Table state : " + tableState);
			}

			break;
		case 3:
			List<TableRestaurant> listTableAvailable = new ArrayList<TableRestaurant>();
			listTableAvailable = TableRestaurant.find.tablesToClean();

			if (listTableAvailable.size() <= 0)
				System.out.println("There is no table that just got available.");

			for (TableRestaurant table : listTableAvailable) {
				System.out.println(
						"[TABLE #" + table.getTable_id() + "] Floor : " + table.getFloor() + " " + table.getStatut());
			}

			break;
		case 4:
			List<TableRestaurant> listTableToClean = new ArrayList<TableRestaurant>();
			listTableToClean = TableRestaurant.find.tablesToClean();

			int compteur = 0;
			for (TableRestaurant table : listTableToClean) {
				System.out.println("[" + compteur + "]" + " " + "[TABLE #" + table.getTable_id() + "] Floor : "
						+ table.getFloor() + " | " + table.getStatut());
				compteur++;
			}

			if (listTableToClean.size() <= 0) {
				System.out.println("There is no table to clean.");
				return;
			}

			int tableToClean = MenuUtil.askForPositiveInt("Which table do you want to set clean ?");

			if (listTableToClean.size() <= tableToClean || listTableToClean.get(tableToClean) == null) {
				System.out.println("This table doesn't exist.");
				return;
			} else {
				TableRestaurant table = listTableToClean.get(tableToClean);
				table.setClean();
				System.out.println("The table has been marked as clean and is now free !");
			}

			break;

		default:
			break;
		}
	}

	public static AssistantMenu getInstance() {
		if (instance == null)
			instance = new AssistantMenu();
		return instance;
	}

	public static void setInstance(AssistantMenu instance) {
		AssistantMenu.instance = instance;
	}

}
