package fr.ul.miage.gl.restaurant.pojo.staff.finders;

import java.util.List;

import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import fr.ul.miage.gl.restaurant.pojo.staff.enums.EnumRoles;
import io.ebean.Finder;

public class StaffFinder extends Finder<Long, Staff> {

	public StaffFinder() {
		super(Staff.class);
	}

	//fonction qui sert a vérifier les identifiants d'un staff, retourne null si faux
	public Staff credentials(String login, String password) {

		return query().where().eq("login", login).eq("password", password).findOne();
	}

	//retourne tous les serveurs
	public List<Staff> findWaiters() {
		return query().where().eq("role", EnumRoles.WAITER).findList();
	}

}
