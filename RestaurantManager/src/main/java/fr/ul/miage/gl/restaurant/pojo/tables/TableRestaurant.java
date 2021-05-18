package fr.ul.miage.gl.restaurant.pojo.tables;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import fr.ul.miage.gl.restaurant.pojo.tables.finders.TableRestaurantFinder;
import io.ebean.Model;
import io.ebean.annotation.NotNull;

@Entity
@Table(name = "\"TABLE_RESTAURANT\"")
public class TableRestaurant extends Model{

	public static final TableRestaurantFinder find = new TableRestaurantFinder();
	  
	@Id
	protected long table_id;

	@Enumerated(EnumType.STRING)
	private EnumTableStat statut;

	@NotNull
	private int floor;

	@NotNull
	private int seats_amount;

	public TableRestaurant(EnumTableStat statut, int floor, int seats_amount) {
		super();
		this.statut = statut;
		this.floor = floor;
		this.seats_amount = seats_amount;
	}
	
	public void setReserved() {
		statut = EnumTableStat.RESERVED;
	}
	
	public void setFree() {
		statut = EnumTableStat.FREE;
	}
	
	public void setToClean() {
		statut = EnumTableStat.TO_CLEAN;
	}
	
	public void setBusy() {
		statut = EnumTableStat.BUSY;
	}
	
	public boolean hasAlreadyASession() {
		if(!statut.equals(EnumTableStat.FREE) && !statut.equals(EnumTableStat.RESERVED)) {
			return true;
		}
		return false;
	}
	
	public boolean canTakeAnOrder() {
		if(!statut.equals(EnumTableStat.TO_CLEAN)) {
			return true;
		}
		return false;
	}
	
	public SessionClient createSession() {
		SessionClient session = new SessionClient(this, new Date());
		session.save();
		return session;
	}
	
	public SessionClient findCurrentSession() {
		return SessionClient.find.lastSessionByTableId(this.getTable_id());
	}
	
	public String getColor() {
		switch (statut) {
		case BUSY:
			return "YELLOW";
		case FREE:
			return "GREEN";
		case RESERVED:
			return "ORANGE";
		case TO_CLEAN:
			return "RED";
		default:
			return "RED";
		}
	}

	public TableRestaurant(long table_id) {
		super();
		this.table_id = table_id;
	}

	public long getTable_id() {
		return table_id;
	}

	public void setTable_id(long table_id) {
		this.table_id = table_id;
	}

	public EnumTableStat getStatut() {
		return statut;
	}

	public void setStatut(EnumTableStat statut) {
		this.statut = statut;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getSeats_amount() {
		return seats_amount;
	}

	public void setSeats_amount(int seats_amount) {
		this.seats_amount = seats_amount;
	}







}