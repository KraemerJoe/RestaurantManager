package fr.ul.miage.gl.restaurant.pojo.tables;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ul.miage.gl.restaurant.pojo.orders.SessionClientFinder;
import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import io.ebean.Model;

@Entity
@Table(name = "\"TABLE_ASSIGNMENT\"")
public class TableAssignment extends Model{

	public static TableAssignmentFinder find = new TableAssignmentFinder();
	
	@ManyToOne @JoinColumn(name = "staff_id")
	private Staff staff;
	
	@ManyToOne @JoinColumn(name = "table_id")
	private TableRestaurant table;

	public TableAssignment(Staff staff, TableRestaurant table) {
		super();
		this.staff = staff;
		this.table = table;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public TableRestaurant getTable() {
		return table;
	}

	public void setTable(TableRestaurant table) {
		this.table = table;
	}
	
	

	
	

}