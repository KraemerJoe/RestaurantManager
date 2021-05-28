package fr.ul.miage.gl.restaurant.waiter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestSessionClient {

	@Test
	@DisplayName("Ensure the createSession() function is working")
	public void testInsertNewClientSession() {

		final TableRestaurant tableRestaurant = new TableRestaurant(EnumTableStat.FREE, 0, 1);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			tableRestaurant.save();
			SessionClient sessionClient = tableRestaurant.createSession();

			TableRestaurant foundTable = TableRestaurant.find.byId(tableRestaurant.getTable_id());
			SessionClient foundSession = SessionClient.find.byId(sessionClient.getSession_client_id());

			assertEquals(foundTable.getTable_id(), tableRestaurant.getTable_id());
			assertEquals(foundSession.getTable_id(), sessionClient.getTable_id());

		});

	}
}
