package fr.ul.miage.gl.restaurant.pojo.orders;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "\"ORDER\"")
public class Order {

	@Id
	protected long order_id;

	@ManyToOne
	@JoinColumn(name = "session_client_id")
	private SessionClient sessionClient;

	private Date date_creation;

	private Date date_completion;

	private String statut;

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public SessionClient getSessionClient() {
		return sessionClient;
	}

	public void setSessionClient(SessionClient sessionClient) {
		this.sessionClient = sessionClient;
	}

	public Date getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}

	public Date getDate_completion() {
		return date_completion;
	}

	public void setDate_completion(Date date_completion) {
		this.date_completion = date_completion;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

}