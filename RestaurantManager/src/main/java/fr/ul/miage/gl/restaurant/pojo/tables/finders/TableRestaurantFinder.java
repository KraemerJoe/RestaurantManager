package fr.ul.miage.gl.restaurant.pojo.tables.finders;

import java.util.List;

import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import io.ebean.Finder;

public class TableRestaurantFinder extends Finder<Long, TableRestaurant> {

	public TableRestaurantFinder() {
		super(TableRestaurant.class);
	}

	public List<TableRestaurant> byStatut(EnumTableStat stat) {
		return query().where().eq("statut", stat).findList();
	}

	public List<TableRestaurant> freeOrReserved() {
		return query().where()
				.or(io.ebean.Expr.eq("statut", EnumTableStat.FREE), io.ebean.Expr.eq("statut", EnumTableStat.RESERVED))
				.findList();
	}

	public List<TableRestaurant> tablesOrdered() {
		return query().orderBy().asc("floor").orderBy().asc("table_id").findList();
	}

	public List<TableRestaurant> tablesToClean() {
		return query().where().eq("statut", EnumTableStat.TO_CLEAN).orderBy().asc("floor").orderBy().asc("table_id")
				.findList();
	}

}
