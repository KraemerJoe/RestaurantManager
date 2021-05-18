package fr.ul.miage.gl.restaurant.pojo.orders.finders;

import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import io.ebean.Finder;

public class SessionClientFinder extends Finder<Long, SessionClient> {

	public SessionClientFinder() {
		super(SessionClient.class);
	}

	public SessionClient lastSessionByTableId(long id) {

		return query().where().eq("table_id", id).orderBy().desc("date_arrival").findOne();
	}
}
