package fr.ul.miage.gl.restaurant.pojo.tables.finders;

import java.util.Collection;
import java.util.stream.Collectors;

import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import fr.ul.miage.gl.restaurant.pojo.tables.TableAssignment;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import io.ebean.Finder;

public class TableAssignmentFinder extends Finder<Long, TableAssignment> {

	public TableAssignmentFinder() {
		super(TableAssignment.class);
	}

	public Collection<? extends TableAssignment> assignmentByStaffId(long id) {
		return query().where().eq("staff_id", id).findList();
	}

	public Collection<? extends TableAssignment> getAssignedBusyTables(long id) {
		return query().where().eq("staff_id", id).findList().stream().filter(c -> c.getTable().isBusy())
				.collect(Collectors.toList());
	}

	public boolean isAssigned(TableRestaurant table, Staff staff) {
		return query().where().eq("table", table).eq("staff", staff).findOne() != null;
	}

	public int unassign(TableRestaurant table, Staff staff) {
		return query().where().eq("table", table).eq("staff", staff).delete();
	}

}