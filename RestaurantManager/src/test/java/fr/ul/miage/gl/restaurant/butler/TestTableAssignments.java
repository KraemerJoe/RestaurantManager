package fr.ul.miage.gl.restaurant.butler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import fr.ul.miage.gl.restaurant.pojo.staff.enums.EnumRoles;
import fr.ul.miage.gl.restaurant.pojo.tables.TableAssignment;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import io.ebean.MockiEbean;
import io.ebean.mocker.DelegateEbeanServer;

public class TestTableAssignments {

	@Test
	@DisplayName("Ensure can't assign a table 2 times to same staff")
	public void testCantDuplicateTables() {

		final Staff staff = new Staff("test","test","test","test", EnumRoles.WAITER);
		
		final TableRestaurant table1 = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		final TableRestaurant table2 = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		final TableRestaurant table3 = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		
		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			staff.save();
			table1.save();
			table2.save();
			table3.save();
			assertEquals(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()).size(), 0);
			TableAssignment.find.assign(table1, staff);
			assertEquals(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()).size(), 1);
			TableAssignment.find.assign(table1, staff);
			assertEquals(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()).size(), 1);
			TableAssignment.find.assign(table3, staff);		
			assertEquals(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()).size(), 2);

		
		});
	}
	
	@Test
	@DisplayName("Ensure the function (unassign) is unassigning a table to a server")
	public void testTableUnassign() {

		final Staff staff = new Staff("test","test","test","test", EnumRoles.WAITER);
		
		final TableRestaurant table1 = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		final TableRestaurant table2 = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		final TableRestaurant table3 = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		
		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			staff.save();
			table1.save();
			table2.save();
			table3.save();
			TableAssignment.find.assign(table1, staff);
			TableAssignment.find.assign(table2, staff);
			TableAssignment.find.assign(table3, staff);		
			assertEquals(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()).size(), 3);
			TableAssignment.find.unassign(table1, staff);
			assertEquals(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()).size(), 2);			
			TableAssignment.find.unassign(table2, staff);		
			TableAssignment.find.unassign(table3, staff);		
			assertEquals(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()).size(), 0);
		});
	}
	
	@Test
	@DisplayName("Ensure the function (assign) is assigning a table to a server")
	public void testTableAssign() {

		final Staff staff = new Staff("test","test","test","test", EnumRoles.WAITER);
		
		final TableRestaurant table1 = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		final TableRestaurant table2 = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		final TableRestaurant table3 = new TableRestaurant(EnumTableStat.FREE, 0, 2);
		
		DelegateEbeanServer mock = new DelegateEbeanServer();
		mock.withPersisting(true);

		MockiEbean.runWithMock(mock, () -> {

			staff.save();
			table1.save();
			table2.save();
			table3.save();
			// no tables before assignments
			assertEquals(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()).size(), 0);
			// 1 table after 1 assignement
			TableAssignment.find.assign(table1, staff);
			assertEquals(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()).size(), 1);
			// 3 tables after 3 assignements
			TableAssignment.find.assign(table2, staff);
			TableAssignment.find.assign(table3, staff);
			assertEquals(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()).size(), 3);
		});
	}
}
