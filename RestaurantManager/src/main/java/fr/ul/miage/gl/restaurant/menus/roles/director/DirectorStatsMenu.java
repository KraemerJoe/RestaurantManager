package fr.ul.miage.gl.restaurant.menus.roles.director;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.roles.DirectorMenu;
import fr.ul.miage.gl.restaurant.pojo.orders.Invoice;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;

public class DirectorStatsMenu extends Menu {

	public static DirectorStatsMenu instance;

	public DirectorStatsMenu() {
		super("Stats - Director");
		instance = this;
	}

	@Override
	public void initMenuItems() {
		itemList.add(new ItemMenu("Stats of receipts", "See daily, weekly and monthly receipt"));
		itemList.add(new ItemMenu("Stats rotation time", "See the average rotation time"));
		itemList.add(new ItemMenu("Stats most successful dishes (Top 5)", "See the best sellers"));
		itemList.add(new ItemMenu("Is Lunch or Dinner the best ?($$$)", "See if the profit is at lunch or dinner"));
		itemList.add(new ItemMenu("Back to director menu", "Return to main director menu"));
	}

	@Override
	public void executeChoice(int choice) {

		switch (choice) {
		case 1:
			/*
			 * Daily
			 */
			System.out.println("> Receipt of the day");
			System.out.println("  > " + Invoice.dailyReceipt() + "$");
			/*
			 * Weekly
			 */
			System.out.println("> Receipt of the week");
			System.out.println("  > " + Invoice.weeklyReceipt() + "$");
			/*
			 * Monthly
			 */
			System.out.println("> Receipt of the month");
			System.out.println("  > " + Invoice.monthlyReceipt() + "$");
			break;
		case 2:
			ArrayList<Long> sessions = new ArrayList<Long>();

			for (SessionClient sessionOrder : SessionClient.find.allWhereTimeRelease()) {
				long time = sessionOrder.getDate_release().getTime() - sessionOrder.getDate_arrival().getTime();
				sessions.add(time);
			}

			long total = 0L;
			for (Long l : sessions) {
				total = total + l;
			}
			long divided = total / sessions.size();

			System.out.println(" > " + "Average rotation time:");

			System.out.println(String.format("    > %dmn %ds", TimeUnit.MILLISECONDS.toMinutes(divided),
					TimeUnit.MILLISECONDS.toSeconds(divided)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(divided))));

			break;
		case 3:
			System.out.println("TOP #5 - BEST SELLERS");
			Map<String, Long> result = SessionOrder.find.all().stream()
					.collect(Collectors.groupingBy(p -> ((SessionOrder) p).getDish().getName(), Collectors.counting()))
					.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
					.limit(5)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (l, r) -> l, LinkedHashMap::new));

			result.forEach((key, value) -> System.out.println(" > " + key + ": " + value));

			break;
		case 4:
			System.out.println("Is Lunch or Dinner the best ?($$$)");
			double totalLunch = 0;
			double totalDinner = 0;

			for (Invoice invoice : Invoice.find.all()) {
				if (invoice.isLunch())
					totalLunch = totalLunch + invoice.getTotal_price();
				else
					totalDinner = totalDinner + invoice.getTotal_price();
			}

			if (totalLunch > totalDinner) {
				System.out.println("  > Lunch: " + totalLunch + " $ < BEST");
				System.out.println(" > Dinner: " + totalDinner + " $");
			} else {
				System.out.println("  > Dinner: " + totalDinner + " $ < BEST");
				System.out.println(" > Lunch: " + totalLunch + " $");
			}

			break;
		case 5:
			DirectorMenu.getInstance().show();
			break;
		}
	}

	public static DirectorStatsMenu getInstance() {
		if (instance == null)
			instance = new DirectorStatsMenu();
		return instance;
	}

	public static void setInstance(DirectorStatsMenu instance) {
		DirectorStatsMenu.instance = instance;
	}

}
