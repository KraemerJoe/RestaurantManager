package fr.ul.miage.gl.restaurant.pojo.tables;

import java.util.Collection;
import java.util.List;

import fr.ul.miage.gl.restaurant.managers.SessionManager;
import io.ebean.Finder;

public class TableAssignmentFinder extends Finder<Long, TableAssignment> {

	public TableAssignmentFinder() {
		super(TableAssignment.class);
	}

	public TableAssignment byUniqueName(String name) {

		return query().where().eq("name", name).findOne();
	}

	public List<TableAssignment> byName(String name) {

		return query().where().eq("name", name).findList();
	}

	public Collection<? extends TableAssignment> assignmentByStaffId(long id) {
		return query().where().eq("staff_id", id).findList();
	}
}
