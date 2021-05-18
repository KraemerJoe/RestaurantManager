package fr.ul.miage.gl.restaurant.pojo.staff;

import io.ebean.Finder;

public class StaffFinder extends Finder<Long, Staff> {

	public StaffFinder() {
		super(Staff.class);
	}

	public Staff credentials(String login, String password) {

		return query().where().eq("login", login).eq("password", password).findOne();
	}

}
