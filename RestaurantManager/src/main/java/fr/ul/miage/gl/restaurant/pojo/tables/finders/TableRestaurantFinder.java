package fr.ul.miage.gl.restaurant.pojo.tables.finders;

import java.util.List;

import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import io.ebean.Finder;

public class TableRestaurantFinder extends Finder<Long, TableRestaurant> {

	public TableRestaurantFinder() {
		super(TableRestaurant.class);
	}

	//cherche toutes les tables d'un statut
	public List<TableRestaurant> byStatut(EnumTableStat stat) {
		return query().where().eq("statut", stat).findList();
	}
	
	// cherches toutes les tables libres
	public List<TableRestaurant> free() {
		return query().where().eq("statut", EnumTableStat.FREE).findList();
	}

	// cherche toutes les tables libres ou réservées
	public List<TableRestaurant> freeOrReserved() {
		return query().where()
				.or(io.ebean.Expr.eq("statut", EnumTableStat.FREE), io.ebean.Expr.eq("statut", EnumTableStat.RESERVED))
				.findList();
	}

	// retourne toutes les tables ordonnées par id et étage
	public List<TableRestaurant> tablesOrdered() {
		return query().orderBy().asc("floor").orderBy().asc("table_id").findList();
	}

	// retourne toutes les tables à laver
	public List<TableRestaurant> tablesToClean() {
		return query().where().eq("statut", EnumTableStat.TO_CLEAN).orderBy().asc("floor").orderBy().asc("table_id")
				.findList();
	}

}
