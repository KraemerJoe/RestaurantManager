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

	//retourne les tables assignées à un serveur
	public Collection<? extends TableAssignment> assignmentByStaffId(long id) {
		return query().where().eq("staff_id", id).findList();
	}

	// retourne les tables assignées occupées
	public Collection<? extends TableAssignment> getAssignedBusyTables(long id) {
		return query().where().eq("staff_id", id).findList().stream().filter(c -> c.getTable().isBusy())
				.collect(Collectors.toList());
	}

	// retourne true si une table est assigné au staff
	public boolean isAssigned(TableRestaurant table, Staff staff) {
		return query().where().eq("table", table).eq("staff", staff).findOne() != null;
	}

	// assigne une table à un staff
	public void assign(TableRestaurant table, Staff staff) {
		if(isAssigned(table, staff)) {
			System.err.println("Can't assign a table that is already assigned to the same staff !");
		}else {
			TableAssignment assign = new TableAssignment(staff, table);
			assign.save();
		}
	}
	
	// dé assigne une table à un staff
	public int unassign(TableRestaurant table, Staff staff) {
		return query().where().eq("table", table).eq("staff", staff).delete();
	}

}