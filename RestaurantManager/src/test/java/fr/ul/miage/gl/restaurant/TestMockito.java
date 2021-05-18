package fr.ul.miage.gl.restaurant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import io.ebean.DB;
import io.ebean.Database;
import io.ebean.MockiEbean;
import io.ebeaninternal.server.core.DefaultServer;

public class TestMockito {
	@Test
	public void testWithMockito() {

	    // Setup
	    final Long magicBeanId = Long.valueOf(47L);
	    Database mock = Mockito.mock(Database.class);
	    when(mock.getBeanId(null)).thenReturn(magicBeanId);

	    MockiEbean.runWithMock(mock, () -> {
	      Object value = DB.getBeanId(null);
	      assertEquals(value, magicBeanId);
	    });

	    // assert that the original EbeanServer has been restored
	    Database restoredServer = DB.getDefault();
	    assertTrue("is a real EbeanServer", restoredServer instanceof DefaultServer);
	}
}
