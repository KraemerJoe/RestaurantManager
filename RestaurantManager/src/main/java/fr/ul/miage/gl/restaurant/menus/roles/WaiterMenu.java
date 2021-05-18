package fr.ul.miage.gl.restaurant.menus.roles;

import java.util.ArrayList;

import fr.ul.miage.gl.restaurant.managers.SessionManager;
import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.pojo.tables.TableAssignment;

public class WaiterMenu extends Menu {

	public static WaiterMenu instance;

	public WaiterMenu() {
		super("Waiter");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Status of the restaurant", "See my tables"));
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
