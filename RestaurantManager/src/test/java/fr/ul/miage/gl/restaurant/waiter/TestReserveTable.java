package fr.ul.miage.gl.restaurant.waiter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestReserveTable {

	@Test
	@DisplayName("Ensure the reserve function works")
	public void testTableOfOrderIsBusy() {

	
		final TableRestaurant table = new TableRestaurant(EnumTableStat.FREE, 0, 2);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			
			table.save();
			assertEquals(table.getStatut(), EnumTableStat.FREE);
			table.reserve();
			assertEquals(table.getStatut(), EnumTableStat.RESERVED);

		});
	}
}
