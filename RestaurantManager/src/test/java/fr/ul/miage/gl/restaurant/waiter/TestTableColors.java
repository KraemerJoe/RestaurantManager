package fr.ul.miage.gl.restaurant.waiter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.CompositionDish;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import fr.ul.miage.gl.restaurant.pojo.orders.Order;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;
import fr.ul.miage.gl.restaurant.pojo.orders.enums.EnumSessionOrderStat;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestTableColors {

	@Test
	@DisplayName("Ensure BUSY is for YELLOW")
	public void testBusyIsYellow() {

		final TableRestaurant table = new TableRestaurant(EnumTableStat.BUSY, 0, 2);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {
			table.save();
			assertEquals(table.getColor(), "YELLOW");
		});
	}
	
	@Test
	@DisplayName("Ensure FREE is for GREEN")
	public void testFreeIsGreen() {

		final TableRestaurant table = new TableRestaurant(EnumTableStat.FREE, 0, 2);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {
			table.save();
			assertEquals(table.getColor(), "GREEN");
		});
	}
	
	@Test
	@DisplayName("Ensure TO_CLEAN is for RED")
	public void testToCleanIsRed() {

		final TableRestaurant table = new TableRestaurant(EnumTableStat.TO_CLEAN, 0, 2);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {
			table.save();
			assertEquals(table.getColor(), "RED");
		});
	}
	
	@Test
	@DisplayName("Ensure RESERVED is for ORANGE")
	public void testReservedIsOrange() {

		final TableRestaurant table = new TableRestaurant(EnumTableStat.RESERVED, 0, 2);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {
			table.save();
			assertEquals(table.getColor(), "ORANGE");
		});
	}
	
}
