package fr.ul.miage.gl.restaurant.menus;

public interface InterfaceMenu {
	void title(); // afficher le titre

	void footer(); // afficher le bas du menu
	
	void show(); //afficher le meny intégrale

	void initMenuItems(); //charger en mémoire les items du menu

	void executeChoice(int choice); //choisir un choix
}
