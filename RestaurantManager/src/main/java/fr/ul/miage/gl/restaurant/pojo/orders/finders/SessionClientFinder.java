package fr.ul.miage.gl.restaurant.pojo.orders.finders;

import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import io.ebean.Finder;

public class SessionClientFinder extends Finder<Long, SessionClient> {

	public SessionClientFinder() {
		super(SessionClient.class);
	}

	public SessionClient lastSessionByTableId(TableRestaurant id) {

		return query().setMaxRows(1).where().eq("table_id", id).orderBy().desc("date_arrival").findOne();
	}
	
	public SessionClient lastSessionByTableId(long id) {

		return query().setMaxRows(1).where().eq("table_id", id).orderBy().desc("date_arrival").findOne();
	}
	
	
}
