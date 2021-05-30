package fr.ul.miage.gl.restaurant.pojo.orders.finders;

import java.util.List;

import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import io.ebean.Finder;

public class SessionClientFinder extends Finder<Long, SessionClient> {

	public SessionClientFinder() {
		super(SessionClient.class);
	}

	//retourne la dernière session en cours d'une table
	public SessionClient lastSessionByTableId(TableRestaurant id) {

		return query().setMaxRows(1).where().eq("table_id", id).orderBy().desc("date_arrival").findOne();
	}

	// retourne la derniere session d'une table par l'id 
	public SessionClient lastSessionByTableId(long id) {

		return query().setMaxRows(1).where().eq("table_id", id).orderBy().desc("date_arrival").findOne();
	}

	//retourne toutes les sessions avec une date_release c'est a dire un client qui est partie
	public List<SessionClient> allWhereTimeRelease() {
		return query().where().isNotNull("date_release").findList();
	}

}
