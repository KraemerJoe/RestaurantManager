package fr.ul.miage.gl.restaurant.ebean;

import io.ebean.DB;
import io.ebean.Database;

public class EbeanManager {

	/*
	 * Ce fichier concerne la gestion de la base de données
	 * PostgreSQL 
	 * 
	 * Nous avons utilisé Ebean pour mapper nos POJO sur le SGBD
	 * 
	 */
	
	public static EbeanManager instance;
	public Database db;

	public EbeanManager() {
		instance = this;
	}

	public void initDB() {
		db = DB.getDefault();
	}

	/*
	 * Singleton de la connexion à la BDD
	 */
	public static EbeanManager getInstance() {
		if (instance == null)
			instance = new EbeanManager();
		return instance;
	}

	public static void setInstance(EbeanManager instance) {
		EbeanManager.instance = instance;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

}
