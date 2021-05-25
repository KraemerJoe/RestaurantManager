package fr.ul.miage.gl.restaurant.pojo.orders.finders;

import java.util.List;

import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;
import fr.ul.miage.gl.restaurant.pojo.orders.enums.EnumOrderStat;
import fr.ul.miage.gl.restaurant.pojo.orders.enums.EnumSessionOrderStat;
import io.ebean.Finder;

public class SessionOrderFinder extends Finder<Long, SessionOrder> {

	public SessionOrderFinder() {
		super(SessionOrder.class);
	}

	public List<SessionOrder> pendingOrdersWithChildFirst() {
		return query().where().eq("statut", EnumSessionOrderStat.PENDING).orderBy().desc("child").orderBy()
				.asc("date_creation").findList();
	}

	public List<SessionOrder> notCompletedOrders() {
		return query().where().not().eq("statut", EnumOrderStat.COMPLET).findList();
	}

	public List<SessionOrder> getSessionOrdersOfOrder(long id) {
		return query().where().eq("order_id", id).findList();
	}

	public List<SessionOrder> allWhereTime() {
		return query().where().isNotNull("date_completion").findList();
	}

}
