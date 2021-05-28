package fr.ul.miage.gl.restaurant.managers;

import fr.ul.miage.gl.restaurant.pojo.staff.Staff;

public class SessionManager {

	/*
	 * Cette classe permet la mise en mémoire du compte et du membre du staff connecté
	 */
	
	public static SessionManager instance;
	private Staff account;

	public SessionManager() {
		instance = this;
	}

	public void login(Staff staff) {
		this.account = staff;
	}

	public void disconnect() {
		this.account = null;
	}

	public Staff getAccount() {
		return account;
	}

	public void setAccount(Staff account) {
		this.account = account;
	}

	public static SessionManager getInstance() {
		if (instance == null)
			instance = new SessionManager();
		return instance;
	}

	public static void setInstance(SessionManager instance) {
		SessionManager.instance = instance;
	}

}
