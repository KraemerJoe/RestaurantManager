package fr.ul.miage.gl.restaurant.pojo.orders;

import java.util.List;

import io.ebean.Finder;

public class SessionOrderFinder extends Finder<Long, SessionOrder> {

	public SessionOrderFinder() {
		super(SessionOrder.class);
	}

	public SessionOrder byUniqueName(String name) {

		return query().where().eq("name", name).findOne();
	}

	public List<SessionOrder> byName(String name) {

		return query().where().eq("name", name).findList();
	}

	public List<SessionOrder> pendingOrdersWithChildFirst() {
		return query().where().eq("statut", EnumSessionOrderStat.PENDING).orderBy().desc("child").orderBy().asc("date_creation").findList();
	}

	public List<SessionOrder> notCompletedOrders() {
		return query().where().not().eq("statut",EnumOrderStat.COMPLET).findList();
	}

	
}
