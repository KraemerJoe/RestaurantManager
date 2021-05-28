package fr.ul.miage.gl.restaurant.director;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import fr.ul.miage.gl.restaurant.pojo.staff.enums.EnumRoles;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestCreateStaff {

	@Test
	@DisplayName("Ensure a staff is successfully created")
	public void testNoNegativeStocks() {

		final Staff staff = new Staff("", "", "login", "password", EnumRoles.WAITER);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			staff.save();

			assertEquals(staff, Staff.find.byId(staff.getStaff_id()));
			assertNotNull(staff);
		});
	}

	@Test
	@DisplayName("Ensure a created staff can login")
	public void testStaffLogin() {

		final Staff staff = new Staff("", "", "login2", "password2", EnumRoles.WAITER);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			staff.save();

			assertEquals(staff, Staff.find.credentials("login2", "password2"));
			assertNotNull(staff);
		});
	}
	
	@Test
	@DisplayName("Ensure a not existing staff return null")
	public void testStaffNotLogin() {

		final Staff staff = new Staff("", "", "login2", "password2", EnumRoles.WAITER);

		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			staff.save();

			assertNull(Staff.find.credentials("loginrdm", "loginrdm"));

		});
	}

}
