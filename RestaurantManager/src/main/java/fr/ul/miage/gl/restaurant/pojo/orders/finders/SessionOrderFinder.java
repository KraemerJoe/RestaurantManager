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

	// retourne toutes les commandes en cours ordonnées par leur date d'arrivée
	// premier venu, premier partie sauf on mets les enfants en premier
	public List<SessionOrder> pendingOrdersWithChildFirst() {
		return query().where().eq("statut", EnumSessionOrderStat.PENDING).orderBy().desc("child").orderBy()
				.asc("date_creation").findList();
	}

	// retourne les commandes qui n'ont pas encore était completement terminées ( des plats ne sont pas encore servis)
	public List<SessionOrder> notCompletedOrders() {
		return query().where().not().eq("statut", EnumOrderStat.COMPLET).findList();
	}

	// retourne tous les plats d'une commande
	public List<SessionOrder> getSessionOrdersOfOrder(long id) {
		return query().where().eq("order_id", id).findList();
	}

	// retourne tous les plats qui ont une date_completion c'est a dire qui ont été servis
	public List<SessionOrder> allWhereTime() {
		return query().where().isNotNull("date_completion").findList();
	}

}
