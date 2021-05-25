package fr.ul.miage.gl.restaurant.pojo.tables;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.ul.miage.gl.restaurant.pojo.orders.Invoice;
import fr.ul.miage.gl.restaurant.pojo.orders.Order;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionClient;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;
import fr.ul.miage.gl.restaurant.pojo.tables.enums.EnumTableStat;
import fr.ul.miage.gl.restaurant.pojo.tables.finders.TableRestaurantFinder;
import fr.ul.miage.gl.restaurant.util.MenuUtil;
import io.ebean.Model;
import io.ebean.annotation.NotNull;

@Entity
@Table(name = "\"TABLE_RESTAURANT\"")
public class TableRestaurant extends Model {

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
		if (!statut.equals(EnumTableStat.FREE) && !statut.equals(EnumTableStat.RESERVED)) {
			return true;
		}
		return false;
	}

	public boolean createInvoice(boolean lunch) {
		SessionClient session = findCurrentSession();
		if (session == null || !session.getTable_id().isBusy()) {
			System.err.println("The table [" + table_id + "] has no session or isn't busy.");
			return false;
		}

		ArrayList<Order> ordersOfSession = new ArrayList<Order>();
		ordersOfSession.addAll(Order.find.getOrdersOfSession(session.getSession_client_id()));
		MenuUtil.line();

		double total = 0;
		for (Order order : ordersOfSession) {
			System.out.println("> ORDER #" + order.getOrder_id() + " | " + order.getDate_creation());

			ArrayList<SessionOrder> ordersSession = new ArrayList<SessionOrder>();
			ordersSession.addAll(SessionOrder.find.getSessionOrdersOfOrder(order.getOrder_id()));
			for (SessionOrder sessionO : ordersSession) {
				double price = sessionO.getDish().getPrice();
				System.out.println("    > " + sessionO.getDish().getName() + " | " + price);
				total = total + price;
			}
		}

		MenuUtil.line();
		System.out.println("Total: " + total);
		MenuUtil.line();

		if (total > 0) {
			Invoice.createInvoice(session, total, lunch);
			session.terminate();
			return true;
		} else {
			System.err.println("The total must be > 0.");
			session.terminate();
			return false;
		}

	}

	public boolean canTakeAnOrder() {
		if (!statut.equals(EnumTableStat.TO_CLEAN)) {
			return true;
		}
		return false;
	}

	public void setNeedToBeClean() {
		this.statut = EnumTableStat.TO_CLEAN;
		this.save();
	}

	public void setClean() {
		this.statut = EnumTableStat.FREE;
		save();
	}

	public boolean isBusy() {
		return this.statut.equals(EnumTableStat.BUSY);
	}

	public SessionClient createSession() {
		SessionClient session = new SessionClient(this, new Date());
		session.save();
		return session;
	}

	public SessionClient findCurrentSession() {
		return SessionClient.find.lastSessionByTableId(this);
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